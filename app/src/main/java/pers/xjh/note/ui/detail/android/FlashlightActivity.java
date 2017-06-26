package pers.xjh.note.ui.detail.android;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/23.
 */

public class FlashlightActivity extends BaseActivity implements View.OnClickListener {

    private Camera mCamera;
    //Android6.0 已经抛弃了Camer 相关的API，改用新的API接口CamerManager
    private CameraManager mCameraManager;

    private Button mBtnFlashlight, mBtnFlashing;

    private boolean mIsFlashlightOpening;

    private boolean mIsFlashing;

    @Override
    protected int initContentView() {
        return R.layout.activity_flash_light;
    }

    @Override
    protected void initView() {
        mBtnFlashlight = (Button) findViewById(R.id.btn_1);
        mBtnFlashlight.setOnClickListener(this);

        mBtnFlashing = (Button) findViewById(R.id.btn_2);
        mBtnFlashing.setOnClickListener(this);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeFlashlight();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                if(!mIsFlashlightOpening) {
                    openFlashlight();
                    mBtnFlashlight.setText("关闭闪光灯");
                    mIsFlashlightOpening = true;
                } else {
                    closeFlashlight();
                    mBtnFlashlight.setText("打开闪光灯");
                    mIsFlashlightOpening = false;
                }
                break;
            case R.id.btn_2:
                if(!mIsFlashing) {
                    mBtnFlashing.setText("停止闪烁");
                    mIsFlashing = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (mIsFlashing) {
                                    openFlashlight();
                                    Thread.sleep(300);
                                    closeFlashlight();
                                    Thread.sleep(300);
                                }
                            } catch (Exception e) {
                                showErrorDialog(e.getMessage());
                            }
                        }
                    }).start();
                } else {
                    mBtnFlashing.setText("开始闪烁");
                    mIsFlashing = false;
                }
                break;
        }
    }

    private void openFlashlight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            openFlashlightNew();
        } else {
            openFlashlightOld();
        }
    }

    private void closeFlashlight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            closeFlashlightNew();
        } else {
            closeFlashlightOld();
        }
    }

    /**
     * 打开闪光灯(6.0以上)
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openFlashlightNew() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mCameraManager.setTorchMode("0", true);
            } catch (CameraAccessException e) {
                showErrorDialog(e.getMessage());
            }
        }
    }

    /**
     * 打开闪光灯
     *
     * @return
     */
    private void openFlashlightOld() {
        try {
            mCamera = Camera.open();
            mCamera.startPreview();
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    /**
     * 关闭闪光灯(6.0以上)
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void closeFlashlightNew() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mCameraManager.setTorchMode("0", false);
            } catch (CameraAccessException e) {
                showErrorDialog(e.getMessage());
            }
        }
    }

    /**
     * 关闭闪光灯
     *
     * @return
     */
    private void closeFlashlightOld() {
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.release();
            mCamera = null;
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }
}
