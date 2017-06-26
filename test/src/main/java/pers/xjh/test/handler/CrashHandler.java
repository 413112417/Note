package pers.xjh.test.handler;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import pers.xjh.test.util.CompratorByLastModified;
import pers.xjh.test.util.FileUtil;

/**
 * 奔溃日志处理
 * Created by xjh on 2017/2/6.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /** 实例 */
    private static CrashHandler mInstance;
    /** 系统默认的处理器 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /** 上下文 */
    private Context mContext;
    /** 私有化构造器 */
    private CrashHandler(){}

    /** 单例模式（懒汉模式） */
    public static CrashHandler getInstance() {
        if(mInstance == null) {
            synchronized (CrashHandler.class) {
                if(mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //此处对捕获到的异常进行处理
        try {
            saveExceptionToFile(ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //保存玩信息后，交给系统默认的处理器去处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 保存崩溃信息到本地
     * @param ex
     */
    private void saveExceptionToFile(Throwable ex) throws Exception {
        //获取保存崩溃日志的文件
        Date date = new Date();
        String fileName = date.getTime() + "";
        File crashFile = FileUtil.getCrashFile(mContext, fileName);

        //将崩溃信息写入文件中
        FileOutputStream fos = new FileOutputStream(crashFile);
        ex.printStackTrace(new PrintStream(fos));
        fos.flush();
        fos.close();

        //如果崩溃日志文件多于20个，删除最早的文件
        File[] crashFiles = FileUtil.getSortedCrashFile(mContext, new CompratorByLastModified());
        if(crashFiles.length > 20) {
            FileUtil.deleteFile(crashFiles[crashFiles.length - 1]);
        }
    }
}
