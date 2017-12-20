package pers.xjh.note.ui.detail.android;

import android.hardware.Camera;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.CameraPreview;

/**
 * Created by xjh on 17-8-22.
 */

public class CameraActivity extends BaseActivity {

    private CameraPreview mCameraView;

    private ImageView mImg;

    private Camera.Size mPreviewSize;

    @Override
    protected int initContentView() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView() {
        mCameraView = (CameraPreview) findViewById(R.id.camera_view);
        mImg = (ImageView) findViewById(R.id.img);

        mCameraView.setCameraCallbacks(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

//                if (mPreviewSize == null) {
//                    mPreviewSize = camera.getParameters().getPreviewSize();
//                }
//
//                YuvImage yuvimage = new YuvImage(
//                        data,
//                        ImageFormat.NV21,
//                        mPreviewSize.width,
//                        mPreviewSize.height,
//                        null);
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                yuvimage.compressToJpeg(new Rect(0, 0, yuvimage.getWidth(), yuvimage.getHeight()), 50, baos);// 80--JPG图片的质量[0-100],100最高
//                byte[] rawImage = baos.toByteArray();
//                //将rawImage转换成bitmap
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
//                Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);
//                mImg.setImageBitmap(bitmap);
            }
        });
    }
}
