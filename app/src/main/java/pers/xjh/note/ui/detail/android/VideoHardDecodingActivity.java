package pers.xjh.note.ui.detail.android;

import android.hardware.Camera;
import android.view.SurfaceView;

import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-10-18.
 */

public class VideoHardDecodingActivity extends BaseActivity {

    private SurfaceView mSurfaceView;

    @Override
    protected int initContentView() {
        return R.layout.activity_video_hard_decoding;
    }

    @Override
    protected void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        try {
            Camera camera = Camera.open();
            camera.setPreviewDisplay(mSurfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
