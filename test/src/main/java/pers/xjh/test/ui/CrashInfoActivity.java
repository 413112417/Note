package pers.xjh.test.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import pers.xjh.test.R;
import pers.xjh.test.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/30.
 */

public class CrashInfoActivity extends BaseActivity {

    private TextView mTvCrashInfo;

    private String mInfo;

    @Override
    protected int initContentView() {
        return R.layout.test_activity_crash_info;
    }

    /**
     * 初始化标题
     */
    @Override
    protected void initTitle() {
        super.initTitle();
        mTitleBar.setTitle("崩溃详情");
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
        Intent intent = getIntent();
        if(intent != null) {
            mInfo = intent.getStringExtra("crashInfo");
        }
        mTvCrashInfo = (TextView) findViewById(R.id.tv_crash_info);
        mTvCrashInfo.setText(mInfo);
    }
}
