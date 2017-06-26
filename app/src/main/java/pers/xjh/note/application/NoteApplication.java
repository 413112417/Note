package pers.xjh.note.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import java.util.List;

import pers.xjh.note.runtime.RtEnv;
import pers.xjh.note.utils.Constant;
import pers.xjh.test.handler.CrashHandler;

/**
 * Created by XJH on 2017/4/17.
 */

public class NoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        int pid = android.os.Process.myPid();

        //多进程存在时，会多次初始化Application，根据进程名进行区分，进行不同的初始化操作。
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
                if (processInfo.pid == pid) {
                    if (processInfo.processName.equals("pers.xjh.note")) {
                        initMainProcess();
                    } else if (processInfo.processName.equals("pers.xjh.test")) {
                        initTestProcess();
                    }
                }
            }
        }
    }

    /**
     * 对主进程进行初始化
     */
    private void initMainProcess() {
        RtEnv.put(Constant.RT_APP, this);

        CrashHandler.getInstance().init(this);

        initBaiduMap();
    }

    /**
     * 对测试进程进行初始化
     */
    private void initTestProcess() {
        RtEnv.put(Constant.RT_APP, this);

        CrashHandler.getInstance().init(this);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        SDKInitializer.initialize(getApplicationContext());
    }
}
