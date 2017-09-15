package pers.xjh.note.ui.detail.android;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.HashMap;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ToastUtil;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by xjh on 17-9-15.
 */

public class MediaPlayerNetworkActivity extends BaseActivity implements SurfaceHolder.Callback,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private Button mBtnPlay, mBtnTake;
    private EditText mEtUrl;
    private MediaPlayer mMediaPlayer = new MediaPlayer();;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private FFmpegMediaMetadataRetriever mRetriever = new FFmpegMediaMetadataRetriever();
    private ImageView mImg;

    @Override
    protected int initContentView() {
        return R.layout.activity_media_play_network;
    }

    @Override
    protected void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mEtUrl = (EditText) findViewById(R.id.et_url);
        mImg = (ImageView) findViewById(R.id.img);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnTake = (Button) findViewById(R.id.btn_take);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEtUrl.getText().toString();
                if(!TextUtils.isEmpty(url)) {
                    Uri uri = Uri.parse(url);
                    try {
                        mMediaPlayer.setDisplay(mSurfaceHolder);
                        mMediaPlayer.setDataSource(MediaPlayerNetworkActivity.this, uri);
                        mRetriever.setDataSource(url);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mBtnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImg.setImageBitmap(mRetriever.getFrameAtTime());
            }
        });

        mSurfaceHolder = mSurfaceView.getHolder(); // SurfaceHolder是SurfaceView的控制接口
        mSurfaceHolder.addCallback(this);// 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


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
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release(); //释放内存
        }
    }
}
