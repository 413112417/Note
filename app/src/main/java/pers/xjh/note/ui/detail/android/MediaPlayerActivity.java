package pers.xjh.note.ui.detail.android;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.FileUtil;

/**
 * Created by XJH on 2017/6/14.
 */

public class MediaPlayerActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnStart;

    private MediaPlayer mMediaPlayer;

    private boolean mIsPlaying;

    @Override
    protected int initContentView() {
        return R.layout.activity_media_player;
    }

    @Override
    protected void initView() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);

        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            File[] voiceFiles = FileUtil.getAudioDir().listFiles();
            if(voiceFiles.length > 0) {
                mMediaPlayer.setDataSource(voiceFiles[voiceFiles.length - 1].getAbsolutePath());
                mMediaPlayer.prepare();
            } else {
                showErrorDialog("文件不存在");
            }
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if(!mIsPlaying) {
                    mMediaPlayer.start();
                    mBtnStart.setText("暂停");
                    mIsPlaying = true;
                } else {
                    mMediaPlayer.pause();
                    mBtnStart.setText("开始");
                    mIsPlaying = false;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer != null) {
            mMediaPlayer.reset(); //重置MediaPlayer
            mMediaPlayer.release(); //释放MediaPlayer
        }
    }
}
