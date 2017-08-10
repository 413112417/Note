package pers.xjh.note.ui.detail.android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pers.xjh.note.R;
import pers.xjh.note.runtime.AppRuntime;
import pers.xjh.note.service.LifeCycleService;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-7-27.
 */

public class ServiceLifeCycleActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnStartStop, mBtnBindUnbind;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(AppRuntime.getApplication(), "onServiceConnected", Toast.LENGTH_SHORT).show();
            ((LifeCycleService.LifeCycleServiceBinder) service).binderMethod();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //正常情况下是不被调用的,调用时机是当Service服务被意外销毁时,例如内存的资源不足时这个方法才被自动调用.
            Toast.makeText(AppRuntime.getApplication(), "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_service_life_cycle;
    }

    @Override
    protected void initView() {
        mBtnStartStop = (Button) findViewById(R.id.btn_start_stop);
        mBtnBindUnbind = (Button) findViewById(R.id.btn_bind_unbind);

        mBtnStartStop.setOnClickListener(this);
        mBtnBindUnbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_stop:
                if("开启服务".equals(mBtnStartStop.getText().toString())) {
                    startService(new Intent(this, LifeCycleService.class));
                    mBtnStartStop.setText("关闭服务");
                } else if("关闭服务".equals(mBtnStartStop.getText().toString())) {
                    stopService(new Intent(this, LifeCycleService.class));
                    mBtnStartStop.setText("开启服务");
                }
                break;
            case R.id.btn_bind_unbind:
                if("绑定服务".equals(mBtnBindUnbind.getText().toString())) {
                    bindService(new Intent(this, LifeCycleService.class), mServiceConnection, BIND_AUTO_CREATE);
                    mBtnBindUnbind.setText("解绑服务");
                } else if("解绑服务".equals(mBtnBindUnbind.getText().toString())) {
                    unbindService(mServiceConnection);
                    mBtnBindUnbind.setText("绑定服务");
                }
                break;
        }
    }
}
