package pers.xjh.note.model.bean;

/**
 * Created by xjh on 17-12-15.
 */

public class RenderScriptTest {

    public static native void init();

    static {
        System.loadLibrary("RenderScript");
    }
}
