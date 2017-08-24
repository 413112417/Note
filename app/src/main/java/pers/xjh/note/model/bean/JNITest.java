package pers.xjh.note.model.bean;

/**
 * Created by xjh on 17-8-11.
 */

public class JNITest {

    public static int staticField = 0;

    public static native String sayHello();

    public static native int add(int a, int b);

    public static native String changeString(String s);

    public static native int sumArray(int[] array);

    public static native String objectArray(Note[] array);

    public static native String getNoteName(Note note);

    public static native void setNoteName(Note note);

    public static native void staticFieldAccess();

    public static native String hello();

    static {
        System.loadLibrary("JniTest");
    }
}
