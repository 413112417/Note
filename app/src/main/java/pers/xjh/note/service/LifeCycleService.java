package pers.xjh.note.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import pers.xjh.note.runtime.AppRuntime;

/**
 * service生命周期
 * Created by xjh on 17-7-27.
 */

public class LifeCycleService extends Service {

    private Binder mBinder = new LifeCycleServiceBinder();

    @Override
    public void onCreate() {
        Toast.makeText(AppRuntime.getApplication(), "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(AppRuntime.getApplication(), "onStartCommand", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(AppRuntime.getApplication(), "onBind", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(AppRuntime.getApplication(), "onUnbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(AppRuntime.getApplication(), "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public class LifeCycleServiceBinder extends Binder {

        public void binderMethod() {
            Toast.makeText(AppRuntime.getApplication(), "binderMethod", Toast.LENGTH_SHORT).show();
        }
    }
}
