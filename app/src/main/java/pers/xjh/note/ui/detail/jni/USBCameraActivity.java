package pers.xjh.note.ui.detail.jni;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                mImg.setImageBitmap(BitmapFactory.decodeByteArray(frameData, 0, frameData.length, options));
            }
        });

        mImg = (ImageView) findViewById(R.id.img);
    }
}
