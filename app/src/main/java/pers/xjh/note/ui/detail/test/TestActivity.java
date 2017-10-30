package pers.xjh.note.ui.detail.test;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 测试专用
 * Created by xjh on 17-10-16.
 */

public class TestActivity extends BaseActivity {

    private Button mBtnTest;

    @Override
    protected int initContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               test();
            }
        });
    }

    private void test() {
        test();
    }
}
