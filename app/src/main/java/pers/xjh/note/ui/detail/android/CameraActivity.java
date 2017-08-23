package pers.xjh.note.ui.detail.android;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.CameraPreview;

/**
 * Created by xjh on 17-8-22.
 */

public class CameraActivity extends BaseActivity {

    private CameraPreview mCameraView;

    @Override
    protected int initContentView() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView() {
        mCameraView = (CameraPreview) findViewById(R.id.camera_view);
    }
}
