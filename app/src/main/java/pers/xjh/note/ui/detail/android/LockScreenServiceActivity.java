package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import pers.xjh.note.R;
import pers.xjh.note.runtime.AppRuntime;
import pers.xjh.note.service.LockScreenService;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.SpUtil;

/**
 * Created by XJH on 2017/4/28.
 */

public class LockScreenServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton;

    private boolean mIsRunning;

    @Override
    protected int initContentView() {
        return R.layout.activity_lock_screen_service;
    }

    @Override
    protected void initView() {
        mButton = (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(this);

        mIsRunning = SpUtil.getBoolean(AppRuntime.getApplication(), Constant.SP_SETTING, Constant.SP_LOCK_SCREEN_STATE);

        if(mIsRunning) {
            mButton.setText("关闭锁屏服务");
        } else {
            mButton.setText("开启锁屏服务");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if(mIsRunning) {
                    stopLockScreenService();
                    mIsRunning = false;
                    mButton.setText("开启锁屏服务");
                    SpUtil.putBoolean(AppRuntime.getApplication(), Constant.SP_SETTING, Constant.SP_LOCK_SCREEN_STATE, false);
                } else {
                    startLockScreenService();
                    mIsRunning = true;
                    mButton.setText("关闭锁屏服务");
                    SpUtil.putBoolean(AppRuntime.getApplication(), Constant.SP_SETTING, Constant.SP_LOCK_SCREEN_STATE, true);
                }
                break;
        }
    }

    /**
     * 启动锁屏服务
     */
    private void startLockScreenService() {
        Intent intent = new Intent(this, LockScreenService.class);
        startService(intent);
    }

    /**
     * 关闭锁屏服务
     */
    private void stopLockScreenService() {
        Intent intent = new Intent(this, LockScreenService.class);
        stopService(intent);
    }
}
