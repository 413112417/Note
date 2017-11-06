package pers.xjh.note.ui.detail.jni;

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
import pers.xjh.note.model.bean.USBCamera;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-10-25.
 */

public class USBCameraActivity extends BaseActivity {

    private Button mBtnOpen;

    private ImageView mImg;

    @Override
    protected int initContentView() {
        return R.layout.activity_usb_camera;
    }

    @Override
    protected void initView() {
        USBCamera.init();
        mBtnOpen = (Button) findViewById(R.id.btn_open);
        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] frameData = USBCamera.getFrame();
                //将rawImage转换成bitmap
                Mat matSrc = new Mat();
                matSrc.put(0, 0, frameData);
                Mat matDst = new Mat(480, 640, CvType.CV_8UC2);

                Imgproc.cvtColor(matSrc, matDst, Imgproc.COLOR_YUV2BGRA_YUYV);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                mImg.setImageBitmap(BitmapFactory.decodeByteArray(frameData, 0, frameData.length, options));
            }
        });

        mImg = (ImageView) findViewById(R.id.img);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    //openCV4Android 需要加载用到
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {

                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
}
