package pers.xjh.note.model.bean;

/**
 * Created by xjh on 17-9-27.
 */

public class NeonTest {

    public static native void normalArrayMul(float[] array_a, float[] array_b, float[] array_result);

    public static native void neonArrayMul(float[] array_a, float[] array_b, float[] array_result);

    public static native void normalVectorMul(int size);

    public static native void neonVectorMul(int size);

    static {
//        System.loadLibrary("NeonTest");
    }
}
