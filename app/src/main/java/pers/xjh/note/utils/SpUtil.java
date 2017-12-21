package pers.xjh.note.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import java.util.Map;

import pers.xjh.note.runtime.AppRuntime;

/**
 * SharedPreferences操作类
 * Created by xjh on 2017/1/4.
 */
public class SpUtil {

    private SpUtil() {}

    /**
     * 检测是否有该键值
     * @param spName
     * @param key
     * @return
     */
    public static Boolean containsKey(ContextWrapper contextWrapper, String spName, String key) {
        SharedPreferences sp = contextWrapper.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 插入一条字符串数据
     * @param spName
     * @param key
     * @param value
     */
    public static void putString(ContextWrapper contextWrapper, String spName, String key, String value) {
        SharedPreferences sp = contextWrapper.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 得到一条字符串数据
     * @param spName
     * @param key
     * @return
     */
    public static String getString(ContextWrapper contextWrapper, String spName, String key) {
        SharedPreferences sp = contextWrapper.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 插入一条布尔类型的数据
     * @param spName
     * @param key
     * @param value
     */
    public static void putBoolean(ContextWrapper contextWrapper, String spName, String key, Boolean value) {
        SharedPreferences sp = contextWrapper.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 得到一条字符串数据
     * @param spName
     * @param key
     * @return
     */
    public static Boolean getBoolean(ContextWrapper contextWrapper, String spName, String key) {
        SharedPreferences sp = contextWrapper.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 插入多条字符串数据
     * @param spName
     * @param data
     */
    public static void putAllString(ContextWrapper contextWrapper, String spName, Map<String, String> data) {
        SharedPreferences sp = contextWrapper.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for(Map.Entry<String, String> entry : data.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }
}
