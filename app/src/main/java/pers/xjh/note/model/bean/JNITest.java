package pers.xjh.note.model.bean;

/**
 * Created by xjh on 17-8-11.
 */

public class JNITest {

    public static native String sayHello();

    static {
        System.loadLibrary("JniTest");
    }
}
