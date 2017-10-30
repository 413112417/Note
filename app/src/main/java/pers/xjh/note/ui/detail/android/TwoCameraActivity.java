package pers.xjh.note.ui.detail.android;

import android.Manifest;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.nio.ByteBuffer;
import java.util.Arrays;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.PermissionUtil;
import pers.xjh.note.utils.ToastUtil;

/**
 * 双摄像头同时工作
 * Created by xjh on 17-10-30.
 */

public class TwoCameraActivity extends BaseActivity {

    private SurfaceView mSurfaceView1, mSurfaceView2;

    private SurfaceHolder mSurfaceViewHolder1, mSurfaceViewHolder2;

    private Handler childHandler1, childHandler2, mainHandler;

    private String mCameraID;//摄像头Id 0 为后  1 为前

    private ImageReader mImageReader1, mImageReader2;

    private CameraManager mCameraManager;//摄像头管理器

    private CameraCaptureSession mCameraCaptureSession;

    private CameraDevice mCameraDevice1, mCameraDevice2;

    @Override
    protected int initContentView() {
        return R.layout.activity_two_camera;
    }

    @Override
    protected void initView() {
        mSurfaceView1 = (SurfaceView) findViewById(R.id.surface_view_1);
        mSurfaceView2 = (SurfaceView) findViewById(R.id.surface_view_2);

        mSurfaceViewHolder1 = mSurfaceView1.getHolder();
        mSurfaceViewHolder2 = mSurfaceView2.getHolder();

        mSurfaceViewHolder1.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    initCamera1();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mSurfaceViewHolder2.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    initCamera2();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    /**
     * 初始化Camera1
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera1() {
        HandlerThread handlerThread = new HandlerThread("Camera1");
        handlerThread.start();
        childHandler1 = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;
        //后摄像头
        mImageReader1 = ImageReader.newInstance(mSurfaceView1.getWidth(), mSurfaceView1.getHeight(), ImageFormat.JPEG, 1);
        mImageReader1.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = reader.acquireLatestImage();
                //我们可以将这帧数据转成字节数组，类似于Camera1的PreviewCallback回调的预览帧数据
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                image.close();
                ToastUtil.show("jinlaile");
            }
        }, mainHandler);

        //获取摄像头管理
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //打开摄像头
        try {
            if(!PermissionUtil.checkPermission(Manifest.permission.CAMERA)) {
                PermissionUtil.requestPermission(Manifest.permission.CAMERA);
            }
            //打开相机，第一个参数指示打开哪个摄像头，第二个参数stateCallback为相机的状态回调接口，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            mCameraManager.openCamera(mCameraID, stateCallback1, mainHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化Camera2
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        childHandler2 = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());
        mCameraID = "" + CameraCharacteristics.LENS_FACING_BACK;
        //后摄像头
        mImageReader2 = ImageReader.newInstance(mSurfaceView2.getWidth(), mSurfaceView2.getHeight(), ImageFormat.JPEG, 1);
        mImageReader2.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = reader.acquireLatestImage();
                //我们可以将这帧数据转成字节数组，类似于Camera1的PreviewCallback回调的预览帧数据
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                image.close();
                ToastUtil.show("jinlaile");
            }
        }, mainHandler);

        //获取摄像头管理
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //打开摄像头
        try {
            if(!PermissionUtil.checkPermission(Manifest.permission.CAMERA)) {
                PermissionUtil.requestPermission(Manifest.permission.CAMERA);
            }
            //打开相机，第一个参数指示打开哪个摄像头，第二个参数stateCallback为相机的状态回调接口，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            mCameraManager.openCamera(mCameraID, stateCallback2, mainHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private CameraDevice.StateCallback stateCallback1 = new CameraDevice.StateCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice1 = camera;
            //开启预览
            takePreview1();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            if (null != mCameraDevice1) {
                mCameraDevice1.close();
                mCameraDevice1 = null;
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            ToastUtil.show("开启摄像头1失败:" + error);
        }
    };

    private CameraDevice.StateCallback stateCallback2 = new CameraDevice.StateCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice2 = camera;
            //开启预览
            takePreview2();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            if (null != mCameraDevice2) {
                mCameraDevice2.close();
                mCameraDevice2 = null;
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            ToastUtil.show("开启摄像头2失败:" + error);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void takePreview1() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            final CaptureRequest.Builder previewRequestBuilder = mCameraDevice1.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(mSurfaceViewHolder1.getSurface());
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice1.createCaptureSession(Arrays.asList(mSurfaceViewHolder1.getSurface(), mImageReader1.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (null == mCameraDevice1) return;
                    // 当摄像头已经准备好时，开始显示预览
                    mCameraCaptureSession = session;
                    try {
                        // 自动对焦
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        // 打开闪光灯
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        // 显示预览
                        CaptureRequest previewRequest = previewRequestBuilder.build();
                        mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler1);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    ToastUtil.show("配置失败");
                }
            }, childHandler1);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void takePreview2() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            final CaptureRequest.Builder previewRequestBuilder = mCameraDevice2.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(mSurfaceViewHolder2.getSurface());
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice2.createCaptureSession(Arrays.asList(mSurfaceViewHolder2.getSurface(), mImageReader2.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (null == mCameraDevice2) return;
                    // 当摄像头已经准备好时，开始显示预览
                    mCameraCaptureSession = session;
                    try {
                        // 自动对焦
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        // 打开闪光灯
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        // 显示预览
                        CaptureRequest previewRequest = previewRequestBuilder.build();
                        mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler2);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    ToastUtil.show("配置失败");
                }
            }, childHandler2);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
