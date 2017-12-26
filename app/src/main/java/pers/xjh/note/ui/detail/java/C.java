package pers.xjh.note.ui.detail.java;

/**
 * Created by xjh on 17-12-26.
 */

public class C {

    static {
        System.out.print("this is from C static {}\n");
    }

    {
        System.out.print("this is from C {}\n");
    }

    public C() {
        System.out.print("this is from C()\n");
    }
}
