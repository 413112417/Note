package pers.xjh.note.model.bean;

/**
 * Created by xjh on 17-10-25.
 */

public class USBCamera {

    public static native void init();

    public static native byte[] getFrame();

    public static native void release();

    static {
        System.loadLibrary("usbCamera");
    }
}
