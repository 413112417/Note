package pers.xjh.note.ui.detail.android;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.FileUtil;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/6/14.
 */

public class VideoViewActivity extends BaseActivity {

    private VideoView mVideoView;
    //播放位置
    private int mPosition;

    @Override
    protected int initContentView() {
        Configuration configuration = getResources().getConfiguration();
        int ori = configuration.orientation;

        if(ori == configuration.ORIENTATION_LANDSCAPE) {
            //取消状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        return R.layout.activity_video_view;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        Configuration configuration = getResources().getConfiguration();
        int ori = configuration.orientation;
        //横屏时隐藏标题
        if(ori == configuration.ORIENTATION_LANDSCAPE) {
            titleBar.setVisibility(View.GONE);
        } else {
            titleBar.setVisibility(View.VISIBLE);
            titleBar.setTitleRight("全屏");
            titleBar.setRightTitleClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //切换为横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            });
        }
    }

    @Override
    protected void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(new MediaController(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            File[] videoFiles = FileUtil.getVideoDir().listFiles();
            if(videoFiles.length > 0) {
                Uri uri = Uri.parse(videoFiles[videoFiles.length-1].getAbsolutePath());
                mVideoView.setVideoURI(uri);
                mVideoView.start();
                // 并直接从指定位置开始播放
                mVideoView.seekTo(mPosition);
                mVideoView.requestFocus();
            } else {
                showErrorDialog("文件不存在");
            }
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoView != null) {
            //将VideoView所占用的资源释放掉
            mVideoView.suspend();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mVideoView != null) {
            outState.putInt("duration", mVideoView.getCurrentPosition());
        } else {
            outState.putInt("duration", 0);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPosition = savedInstanceState.getInt("duration", 0);
    }
}
