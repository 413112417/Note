package pers.xjh.note.ui.detail.java.classloader;

/**
 * Created by xjh on 17-12-26.
 */

public class A {

    static {
        System.out.print("this is from A static {}\n");
    }

    {
        System.out.print("this is from A {}\n");
    }

    public A() {
        System.out.print("this is from A()\n");
    }
}
