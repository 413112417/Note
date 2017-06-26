package pers.xjh.test.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import pers.xjh.test.R;
import pers.xjh.test.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/30.
 */

public class TestMainActivity extends BaseActivity {

    private TextView mBtnCrash, mBtnMemory;

    @Override
    protected int initContentView() {
        return R.layout.test_activity_test_main;
    }

    /**
     * 初始化标题
     */
    @Override
    protected void initTitle() {
        super.initTitle();
        mTitleBar.setTitle("测试工具");
        mTitleBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        mBtnCrash = (TextView) findViewById(R.id.btn_crash);
        mBtnCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestMainActivity.this, CrashListActivity.class);
                startActivity(intent);
            }
        });

        mBtnMemory = (TextView) findViewById(R.id.btn_memory);
        mBtnMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestMainActivity.this, MemoryInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
