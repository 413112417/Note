package pers.xjh.note.ui.detail.android;

import android.hardware.Camera;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 双摄像头同时工作
 * Created by xjh on 17-10-30.
 */

public class TwoCameraActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_two_camera;
    }

    @Override
    protected void initView() {
        if (Camera.getNumberOfCameras() > 1) {
            Camera.open(0);
            Camera.open(1);
        }
    }
}
