package pers.xjh.note.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;


import java.util.List;

public class CameraPreview extends TextureView implements TextureView.SurfaceTextureListener {

    private static final int CAMERA_ID = 0;

    private static final String TAG = "CameraPreview";
    @Nullable
    private Camera mCamera;
    @Nullable
    private Camera.CameraInfo mCameraInfo;
    private Camera.PreviewCallback mCallbacks;

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSurfaceTextureListener(this);
    }

    public void setCameraCallbacks(Camera.PreviewCallback callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            openCamera();
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(CAMERA_ID, info);
            int rotation = Surface.ROTATION_0;
            if (getContext() instanceof Activity) {
                rotation = ((Activity) getContext())
                        .getWindowManager().getDefaultDisplay().getRotation();
            }
            setCamera(mCamera, info, rotation);
            startPreview(surface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        stopPreviewAndFreeCamera();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private void startPreview(SurfaceTexture surface) {
        // The Surface has been created, now tell the camera where to draw the preview.
        if (mCamera == null || mCameraInfo == null) {
            return;
        }
        try {
            mCamera.setPreviewTexture(surface);
            List<Camera.Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
            Camera.Size expected = sizes.get(sizes.size() - 1);
            for (Camera.Size size : sizes) {
                if (size.width == 1280 && size.height == 720) {
                    expected = size;
                    break;
                }
            }
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(expected.width, expected.height);
//            parameters.setPreviewFormat(ImageFormat.YUY2);
//            parameters.setFocusMode(Camera.Parameters.FOCUS_DISTANCE_FAR_INDEX);
            //parameters.setPreviewFpsRange();
//            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(parameters);
            // Start camera preview when id scanned. Del by linhx 20170428 begin
            mCamera.startPreview();
            if (mCallbacks != null) {
                mCamera.setPreviewCallback(mCallbacks);
            }
            // Start camera preview when id scanned. Del by linhx 20170428 end
        } catch (Exception e) {

        }
    }

    public void pausePreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
        }
    }

    public void restartPreview() {
        if (mCamera != null) {
            mCamera.startPreview();
            if (mCallbacks != null) {
                mCamera.setPreviewCallback(mCallbacks);
            }
        }
    }

    private void openCamera() throws Exception {
        if (Camera.getNumberOfCameras() > 0) {
            try {
                mCamera = Camera.open(CAMERA_ID);
                mCamera.cancelAutoFocus();
                assert mCamera != null;
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            throw new Exception();
        }
    }

    private void stopPreviewAndFreeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private void setCamera(Camera camera, Camera.CameraInfo cameraInfo, int displayOrientation) {
        mCamera = camera;
        mCameraInfo = cameraInfo;
    }
}
