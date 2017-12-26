package pers.xjh.note.ui.detail.android;

import android.media.MediaRecorder;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.FileUtil;
import pers.xjh.note.utils.ToastUtil;

/**
 * 音频录制
 * Created by XJH on 2017/4/20.
 */

public class AudioRecorderActivity extends BaseActivity {

    ///开始按钮
    private Button mBtnStart;

    private MediaRecorder mRecorder;

    private boolean isRecording = false; //标记是否已经在录制

    @Override
    protected int initContentView() {
        return R.layout.activity_audio_recorder;
    }

    @Override
    protected void initView() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording) {
                    stopRecord();
                } else {
                    startRecord();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRecorder != null) {
            mRecorder.reset();
            mRecorder.release();
        }
    }

    /**
     * 开始录音
     */
    private void startRecord() {
        if(mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(FileUtil.getAudioFile(System.currentTimeMillis() + ".mp3").getPath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
        mBtnStart.setText("停止录音");
        isRecording = true;
    }

    /**
     * 停止录音
     */
    private void stopRecord() {
        try {
            //停止录制
            mRecorder.stop();
            //重置
            mRecorder.reset();
            ToastUtil.show("音频已保存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBtnStart.setText("开始录音");
        isRecording = false;
    }
}
