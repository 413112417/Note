package pers.xjh.note.ui.detail.jni;

import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.JNITest;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-8-11.
 */

public class JNITestActivity extends BaseActivity {

    private TextView mTvContent;

    @Override
    protected int initContentView() {
        return R.layout.activity_jni_test;
    }

    @Override
    protected void initView() {
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvContent.setText(JNITest.sayHello());
    }
}
