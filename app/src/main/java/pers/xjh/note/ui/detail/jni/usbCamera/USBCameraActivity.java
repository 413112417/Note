package pers.xjh.note.ui.detail.jni.usbCamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.ImageProc;
import pers.xjh.note.model.bean.USBCamera;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-10-25.
 */

public class USBCameraActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_usb_camera;
    }

    @Override
    protected void initView() {
        ImageProc.nativeInitCameraCount(2);
    }
}
