package pers.xjh.note.runtime;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import pers.xjh.note.utils.Constant;

/**
 * 运行时变量
 * Created by XJH on 2017/3/27.
 */

public class RtEnv {

    private static Map<String, Object> mData = new HashMap<>();

    private RtEnv() {}

    public static Object get(String key) {
        return mData.get(key);
    }

    public static void put(String key, Object obj) {
        mData.put(key, obj);
    }

    /**
     * 获得当前的application
     * @return
     */
    public static Application getApplication() {
        if(get(Constant.RT_APP) != null) {
            return (Application) get(Constant.RT_APP);
        } else {
            return null;
        }
    }

    /**
     * 获得当前的activity
     * @return
     */
    public static Activity getCurrentActivity() {
        if(get(Constant.RT_CURRENT_ACTIVITY) != null) {
            return (Activity) get(Constant.RT_CURRENT_ACTIVITY);
        } else {
            return null;
        }
    }

    /**
     * 生成id
     * @return
     */
    private static int ID = 0x0000;
    public static int makeID() {
        return ++ID;
    }

    /**
     * 跳转到对应页面
     * @param activity
     */
    public static void startActivity(Class activity) {
        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
        RtEnv.getCurrentActivity().startActivity(intent);
    }

    /**
     * 跳转到对应页面
     * @param activity
     * @param flag 启动模式
     */
    public static void startActivity(Class activity, int flag) {
        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
        intent.setFlags(flag);
        RtEnv.getCurrentActivity().startActivity(intent);
    }


    public static void startActivityForResult(Class activity, int requestCode) {
        Intent intent = new Intent(RtEnv.getCurrentActivity(), activity);
        RtEnv.getCurrentActivity().startActivityForResult(intent, requestCode);
    }
}
