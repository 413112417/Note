package pers.xjh.test.ui;

import android.app.ActivityManager;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

import pers.xjh.test.R;
import pers.xjh.test.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/4.
 */

public class MemoryInfoActivity extends BaseActivity {

    private TextView mTvMemoryInfo;

    private DecimalFormat mDecimalFormat;

    @Override
    protected int initContentView() {
        return R.layout.test_activity_memory_info;
    }

    /**
     * 初始化标题
     */
    @Override
    protected void initTitle() {
        super.initTitle();
        mTitleBar.setTitle("内存监测");
        mTitleBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mTvMemoryInfo = (TextView) findViewById(R.id.tv_memory_info);
        mDecimalFormat = new DecimalFormat("0.##");
        findViewById(R.id.btn_gc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                System.runFinalization();
            }
        });
        displayBriefMemory();
    }

    private void displayBriefMemory() {

        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(info);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTvMemoryInfo.setText("系统总内存:" + getFormatSize(info.totalMem) + "\n\n"
                    + "系统剩余内存:"+ getFormatSize(info.availMem) + "\n\n"
                    + "系统是否处于低内存运行:"+ info.lowMemory + "\n\n"
                    + "当系统剩余内存低于"+ getFormatSize(info.threshold) +"时就看成低内存运行");
        }
    }

    private String getFormatSize(long size) {
        if(size <= 1000) {
            return size + "";
        } else if(size > 1000 && size <= 1000000) {
            return mDecimalFormat.format(size / 1000f) + "K";
        } else if(size > 1000000 && size <= 1000000000) {
            return mDecimalFormat.format(size / 1000000f) + "M";
        } else {
            return mDecimalFormat.format(size / 1000000000f) + "G";
        }
    }
}
