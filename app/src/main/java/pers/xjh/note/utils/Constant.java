package pers.xjh.note.utils;

import pers.xjh.note.runtime.AppRuntime;

/**
 * 常量
 * Created by XJH on 2017/3/27.
 */

public class Constant {

    /** 运行时变量存储的key */
    public static final String RT_APP = "APP"; //当前的application
    public static final String RT_CURRENT_ACTIVITY = "CURRENT_ACTIVITY"; //当前的activity

    /** 笔记类型 */
    public static final int NOTE_ALL = AppRuntime.makeID(); //所有笔记
    public static final int NOTE_JAVA = AppRuntime.makeID(); //JAVA基础
    public static final int NOTE_JNI = AppRuntime.makeID(); //JNI
    public static final int NOTE_ANDROID = AppRuntime.makeID(); //安卓基础
    public static final int NOTE_LINUX = AppRuntime.makeID(); //Linux
    public static final int NOTE_OPTIMIZE = AppRuntime.makeID(); //优化
    public static final int NOTE_ALGORITHM = AppRuntime.makeID(); //算法
    public static final int NOTE_DESIGN = AppRuntime.makeID(); //设计模式
    public static final int NOTE_AI = AppRuntime.makeID(); //人工智能
    public static final int NOTE_FUNCTION = AppRuntime.makeID(); //功能

    /** 页面传值字段 */
    public static final String KEY_TITLE = "KEY_TITLE"; //笔记描述标题
    public static final String KEY_STRING = "KEY_STRING"; //笔记描述文本资源id
    public static final String KEY_NOTE_TYPE = "KEY_NOTE_TYPE"; //笔记类型
    public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL"; //图片地址
    public static final String KEY_SKIP_CACHE = "KEY_SKIP_CACHE"; //是否跳过缓存
    public static final String KEY_IMAGE_INDEX = "KEY_IMAGE_INDEX"; //图片地址
    public static final String KEY_WEB_URL = "KEY_WEB_URL"; //网址

    /** SharedPreferences相关 */
    public static final String SP_SETTING = "SP_SETTING"; //设置SP
    public static final String SP_LOCK_SCREEN_STATE = "SP_LOCK_SCREEN_STATE"; //锁屏服务是否开启

    /** 数据库相关 */
    public static final String DB_TEST = "DB_TEST"; //测试数据库
    public static final int DB_TEST_VERSION = 1; //测试数据库版本
}
