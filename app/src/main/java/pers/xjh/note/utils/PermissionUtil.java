package pers.xjh.note.utils;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.runtime.RunTime;

/**
 * 权限管理工具
 * Created by XJH on 2017/4/11.
 */

public class PermissionUtil {

    private static int mRequestCode;

    /**
     * 检查权限
     * @return true 全部授权 false 未全部授权
     */
    public static boolean checkPermission(String... permission) {
        if(filterPermission(permission) == null) {
            return true;
        }
        return false;
    }

    /**
     * 如果没有对应权限，则请求该权限
     * @param permission
     */
    public static void requestPermission(String... permission) {
        if(!checkPermission(permission)) {
            String[] permissionNotGranted = filterPermission(permission);
            mRequestCode = RunTime.makeID();
            ActivityCompat.requestPermissions(RunTime.getCurrentActivity(),
                    permissionNotGranted, mRequestCode);
        }
    }

    /**
     * 筛选权限，去除已有的
     * @return
     */
    private static String[] filterPermission(String... permission) {

        if(permission == null || permission.length == 0) {
            return null;
        }

        List<String> permissionNotGranted = new ArrayList<>();
        for(int i=0; i<permission.length; i++) {
            if(ContextCompat.checkSelfPermission(RunTime.getCurrentActivity(), permission[i])
                    != PackageManager.PERMISSION_GRANTED) {
                permissionNotGranted.add(permission[i]);
            }
        }

        if(permissionNotGranted.size() > 0) {
            return permissionNotGranted.toArray(new String[permissionNotGranted.size()]);
        } else {
            return null;
        }
    }

    /**
     * 获取请求码
     * @return
     */
    public static int getRequestCode() {
        return mRequestCode;
    }
}
