package pers.xjh.note.ui.detail.jni.usbCamera;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.ImageProc;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-10-25.
 */

public class USBCameraActivity extends BaseActivity {

    static {
        ImageProc.nativeInitCameraCount(2);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_usb_camera;
    }

    @Override
    protected void initView() {

    }
}
