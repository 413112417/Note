package pers.xjh.note.ui.detail.jni;

import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.JNITest;
import pers.xjh.note.model.bean.Note;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-8-11.
 */

public class JNITestActivity extends BaseActivity {

    private TextView mTv1, mTv2, mTv3, mTv4, mTv5, mTv6, mTv7, mTv8;

    @Override
    protected int initContentView() {
        return R.layout.activity_jni_test;
    }

    @Override
    protected void initView() {
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mTv6 = (TextView) findViewById(R.id.tv_6);
        mTv7 = (TextView) findViewById(R.id.tv_7);
        mTv8 = (TextView) findViewById(R.id.tv_8);

        mTv1.setText(JNITest.sayHello());
        mTv2.setText("2+3=" + JNITest.add(2, 3));
        mTv3.setText(JNITest.changeString("字符串操作测试"));
        mTv4.setText(JNITest.sumArray(new int[] {1, 2, 3, 4, 5}) + "");

        JNITest.staticFieldAccess();
        mTv4.setText(JNITest.staticField + "");

        Note note = new Note("test");
        mTv5.setText(JNITest.getNoteName(note) + "");

        Note[] notes = new Note[] {new Note("第一个对象"), new Note("第二个对象"), new Note("第三个对象")};
        mTv6.setText(JNITest.objectArray(notes));

        Note jniNote = new Note("jni");
        JNITest.setNoteName(jniNote);
        mTv7.setText(jniNote.getNoteName());

        mTv8.setText(JNITest.hello());
    }
}
