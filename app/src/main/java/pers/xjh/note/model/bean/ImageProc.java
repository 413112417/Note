package pers.xjh.note.model.bean;

import android.graphics.Bitmap;

public class ImageProc {

    public static final int CAMERA_PIX_FMT_MJPEG = 0; // V4L2_PIX_FMT_MJPEG
    public static final int CAMERA_PIX_FMT_YUYV = 1; // V4L2_PIX_FMT_YUYV

    public static final int IMG_WIDTH = 1280;
    public static final int IMG_HEIGHT = 720;

    private static RecordCallback mRecordCallback;

    public ImageProc() {
    }

    public interface RecordCallback {
        void onDataEncode(byte[] data);
    }

    public void setRecordCallback(RecordCallback callback){
        mRecordCallback = callback;
    }

    public static void encodeYuv2H264(byte[] yuv420sp){
        mRecordCallback.onDataEncode(yuv420sp);
    }

    /*
    * width, height: (640, 480), (1280, 720)
    */
    public static native void nativeInitCameraCount(int count);
    public native int nativePrepareCamera(int index, int width, int height, int pixelFormat);
    public native int nativeProcessCamera(int index);
    public native int nativeStopCamera(int index);
    public native void nativePixelToBmp(int index, Bitmap bitmap);
    public native int nativeStartRecord(int index);
    public native int nativeStopRecord(int index);

    static {
        System.loadLibrary("UsbCameraProc");
    }
}
