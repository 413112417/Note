package pers.xjh.note.ui.detail.android;

import android.graphics.Canvas;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-9-15.
 */

public class VideoViewNetworkActivity extends BaseActivity {

    private Button mBtnPlay, mBtnTake;
    private EditText mEtUrl;
    private VideoView mVideoView;

    @Override
    protected int initContentView() {
        return R.layout.activity_video_view_network;
    }

    @Override
    protected void initView() {
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnTake = (Button) findViewById(R.id.btn_take);
        mEtUrl = (EditText) findViewById(R.id.et_url);
        mVideoView = (VideoView) findViewById(R.id.video_view);

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEtUrl.getText().toString();
                if(!TextUtils.isEmpty(url)) {
                    Uri uri = Uri.parse(url);
                    mVideoView.setVideoURI(uri);
                    mVideoView.start();
                }
            }
        });

        Canvas canvas = new Canvas();

        mBtnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
