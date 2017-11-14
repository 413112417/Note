package pers.xjh.note.ui.detail.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-11-6.
 */

public class TakePictureActivity extends BaseActivity {

    private ImageView mImg;

    private Camera mCamera;

    private Button mBtnTakePicture;

    @Override
    protected int initContentView() {
        return R.layout.activity_take_picture;
    }

    @Override
    protected void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mBtnTakePicture = (Button) findViewById(R.id.btn_take_picture);

        mBtnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        mCamera = Camera.open(0);

        List<Camera.Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
        Camera.Size expected = sizes.get(sizes.size() - 1);
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureSize(expected.width, expected.height);

        mCamera.startPreview();

        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                        Log.d("asd", "onPictureTaken");
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        if (bitmap != null) {
                            mImg.setImageBitmap(bitmap);
                        }

                        mCamera.release();
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
