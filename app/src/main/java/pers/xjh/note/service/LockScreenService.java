package pers.xjh.note.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import pers.xjh.note.ui.detail.android.LockScreenActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.SpUtil;


/**
 * 监听锁屏的service
 * Created by XJH on 2017/4/17.
 */

public class LockScreenService extends Service {

    //屏幕变亮时的广播
    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //屏幕变亮时，打开自定义锁屏界面

        }
    };

    //屏幕变暗时的广播
    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //屏幕变暗时，去掉原来的锁屏界面，打开自定义锁屏界面
            startActivity(mLockScreenIntent);
        }
    };

    private Intent mLockScreenIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mLockScreenIntent = new Intent(this, LockScreenActivity.class);
        mLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        registerReceiver(mScreenOnReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
        registerReceiver(mScreenOffReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mScreenOnReceiver);
        unregisterReceiver(mScreenOffReceiver);

        //重新启动，让服务常驻内存
//        startService(new Intent(this, LockScreenService.class));
    }
}
