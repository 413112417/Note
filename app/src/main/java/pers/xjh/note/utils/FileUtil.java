package pers.xjh.note.utils;


import android.os.Environment;

import java.io.File;
import java.io.IOException;

import pers.xjh.note.runtime.Runtime;

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
    public static File getVideoDir() {
        File videoDir= new File(Runtime.getApplication().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath());
        if(!videoDir.exists()) {
            videoDir.mkdir();
        }
        return videoDir;
    }

    /**
     * 得到音频的文件夹的路径
     * @return
     */
    public static File getAudioDir() {
        File audioDir = new File(Runtime.getApplication().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath());
        if(!audioDir.exists()) {
            audioDir.mkdir();
        }
        return audioDir;
    }

    /**
     * 得到下载的文件夹的路径
     * @return
     */
    public static File getDownloadDir() {
        File downloadDir = new File(Runtime.getApplication().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        if(!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        return downloadDir;
    }

    /**
     * 得到图片的文件夹的路径
     * @return
     */
    public static File getImageDir() {
        File imageDir = new File(Runtime.getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        if(!imageDir.exists()) {
            imageDir.mkdir();
        }
        return imageDir;
    }

    /**
     * 生成一个对应名字的视频路径
     * @return
     */
    public static File getVideoFile(String fileName) {
        File videoFile = new File(getVideoDir().getAbsolutePath(), fileName);
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
    public static File getAudioFile(String fileName) {
        File audioFile = new File(getAudioDir().getAbsolutePath(), fileName);
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
    public static File getDownloadFile(String fileName) {
        File downloadFile = new File(getDownloadDir().getAbsolutePath(), fileName);
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
    public static File getImageFile(String fileName) {
        File imageFile = new File(getImageDir().getAbsolutePath(), fileName);
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
}
