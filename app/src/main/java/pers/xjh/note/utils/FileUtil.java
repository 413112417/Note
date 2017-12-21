package pers.xjh.note.utils;


import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pers.xjh.note.runtime.AppRuntime;

/**
 * 文件管理工具类
 * Created by xjh on 2017/1/18.
 */
public class FileUtil {

    /**
     * 注:getExternalCacheDir得到外部的缓存路径
     *    getExternalFilesDir得到外部文件路径
     *    getCacheDir得到内部的缓存路径
     *    getFilesDir得到内部文件路径
     */

    /**
     * 得到视频的文件夹的路径
     * @return
     */
    public static File getVideoDir(Context context) {
        File videoDir= new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath());
        if(!videoDir.exists()) {
            videoDir.mkdir();
        }
        return videoDir;
    }

    /**
     * 得到音频的文件夹的路径
     * @return
     */
    public static File getAudioDir(Context context) {
        File audioDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath());
        if(!audioDir.exists()) {
            audioDir.mkdir();
        }
        return audioDir;
    }

    /**
     * 得到下载的文件夹的路径
     * @return
     */
    public static File getDownloadDir(Context context) {
        File downloadDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        if(!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        return downloadDir;
    }

    /**
     * 得到图片的文件夹的路径
     * @return
     */
    public static File getImageDir(Context context) {
        File imageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        if(!imageDir.exists()) {
            imageDir.mkdir();
        }
        return imageDir;
    }

    /**
     * 生成一个对应名字的视频路径
     * @return
     */
    public static File getVideoFile(Context context, String fileName) {
        File videoFile = new File(getVideoDir(context).getAbsolutePath(), fileName);
        try {
            if(!videoFile.exists()) {
                videoFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoFile;
    }

    /**
     * 生成一个对应名字的音频路径
     * @return
     */
    public static File getAudioFile(Context context, String fileName) {
        File audioFile = new File(getAudioDir(context).getAbsolutePath(), fileName);
        try {
            if(!audioFile.exists()) {
                audioFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return audioFile;
    }

    /**
     * 得到下载文件
     * @param fileName
     * @return
     */
    public static File getDownloadFile(Context context, String fileName) {
        File downloadFile = new File(getDownloadDir(context).getAbsolutePath(), fileName);
        try {
            if(!downloadFile.exists()) {
                downloadFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadFile;
    }

    /**
     * 得到图片文件
     * @param fileName
     * @return
     */
    public static File getImageFile(Context context, String fileName) {
        File imageFile = new File(getImageDir(context).getAbsolutePath(), fileName);
        try {
            if(!imageFile.exists()) {
                imageFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    /**
     * 删除一个文件
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * 从资源文件中读取文本
     * @return
     */
    public static String readTextFileFromResourse(Context context, int resourseId) {
        StringBuilder body = new StringBuilder();

        InputStream inputStream = context.getResources().openRawResource(resourseId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String lineText;
        try {
            while ((lineText = bufferedReader.readLine()) != null) {
                body.append(lineText);
                body.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body.toString();
    }
}
