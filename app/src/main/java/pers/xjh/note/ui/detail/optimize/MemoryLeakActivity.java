package pers.xjh.note.ui.detail.optimize;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 内存泄露
 * Created by xjh on 17-6-28.
 */

public class MemoryLeakActivity extends BaseActivity {

    private static InnerClass mInnerClass;

    @Override
    protected int initContentView() {
        return R.layout.activity_memory_leak;
    }

    @Override
    protected void initView() {
        //内部类导致内存泄露
        mInnerClass = new InnerClass();
        //传递Activity.this导致内存泄露
        MemoryLeakTestUtil instance = MemoryLeakTestUtil.getInstance(MemoryLeakActivity.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {}
            }
        }).start();
    }

    /**
     * 内部类
     */
    private class InnerClass {

    }
}
