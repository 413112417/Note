package pers.xjh.test.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 文件工具
 * Created by xjh on 2017/2/7.
 */
public class FileUtil {

    /**
     * 获得崩溃日志保存的路径
     * @return
     */
    public static File getCrashDir(Context context) {
        File crashDir = new File(context.getFilesDir() + File.separator + "crash");
        if(!crashDir.exists()) {
            crashDir.mkdir();
        }
        return crashDir;
    }

    /**
     * 获得崩溃日志文件
     * @param fileName
     * @return
     */
    public static File getCrashFile(Context context, String fileName) throws IOException {
        File crashFile = new File(getCrashDir(context).getAbsolutePath() + File.separator + fileName);
        if(!crashFile.exists()) {
            crashFile.createNewFile();
        }
        return crashFile;
    }

    /**
     * 返回排好序的崩溃文件列表
     * @return
     */
    public static File[] getSortedCrashFile(Context context, Comparator comparator) {
        File crashDir = getCrashDir(context);
        File[] crashFiles = crashDir.listFiles();
        Arrays.sort(crashFiles, comparator);
        return crashFiles;
    }

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file) {
        if(file.exists()) {
            file.delete();
        }
    }
}
