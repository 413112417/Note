package pers.xjh.note.ui.detail.android;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by xjh on 17-9-15.
 */

public class MediaPlayerNetworkActivity extends BaseActivity implements TextureView.SurfaceTextureListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private Button mBtnPlay, mBtnTake;
    private EditText mEtUrl;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private Surface mSurface;
    private TextureView mTextureView;
    private SurfaceHolder mSurfaceHolder;
    private FFmpegMediaMetadataRetriever mRetriever = new FFmpegMediaMetadataRetriever();
    private ImageView mImg;

    @Override
    protected int initContentView() {
        return R.layout.activity_media_play_network;
    }

    @Override
    protected void initView() {
        mEtUrl = (EditText) findViewById(R.id.et_url);
        mImg = (ImageView) findViewById(R.id.img);
        mTextureView = (TextureView) findViewById(R.id.texture_view);
        mTextureView.setSurfaceTextureListener(this);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnTake = (Button) findViewById(R.id.btn_take);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PlayerVideo().start();//开启一个线程去播放视频
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final Bitmap bitmap = mTextureView.getBitmap(480, 640);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImg.setImageBitmap(bitmap);
                        }
                    });

                }
            }
        }).start();
//        mBtnTake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        mSurfaceHolder = mSurfaceView.getHolder(); // SurfaceHolder是SurfaceView的控制接口
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();//释放内存
            mMediaPlayer = null;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        surface = null;
        mSurface = null;
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private class PlayerVideo extends Thread{
        @Override
        public void run(){
            try {
                String url = mEtUrl.getText().toString();
                if(!TextUtils.isEmpty(url)) {
                    Uri uri = Uri.parse(url);
                    mMediaPlayer= new MediaPlayer();
                    mMediaPlayer.setDataSource(MediaPlayerNetworkActivity.this, uri);
                    mMediaPlayer.setSurface(mSurface);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp){
                            mMediaPlayer.start();
                        }
                    });
                    mMediaPlayer.prepare();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
