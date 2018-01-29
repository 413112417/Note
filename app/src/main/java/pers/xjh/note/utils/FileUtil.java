package pers.xjh.note.utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

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

    public static String ROOT_PATH = AppRuntime.getApplication().getFilesDir().getAbsolutePath();

    /**
     * 得到视频的文件夹的路径
     * @return
     */
    public static File getVideoDir() {
        File videoDir= new File(ROOT_PATH, "/video");
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
        File audioDir = new File(ROOT_PATH, "/audio");
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
        File downloadDir = new File(ROOT_PATH, "/download");
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
        File imageDir = new File(ROOT_PATH, "/image");
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

    /**
     * 复制文件目录
     * @param srcDir 要复制的源目录 eg:/mnt/sdcard/DB
     * @param destDir 复制到的目标目录 eg:/mnt/sdcard/db/
     * @return
     */
    public static boolean copyDir(String srcDir, String destDir){
        File sourceDir = new File(srcDir);
        //判断文件目录是否存在
        if(!sourceDir.exists()){
            return false;
        }
        //判断是否是目录
        if (sourceDir.isDirectory()) {
            File[] fileList = sourceDir.listFiles();
            File targetDir = new File(destDir);
            //创建目标目录
            if(!targetDir.exists()){
                targetDir.mkdirs();
            }
            //遍历要复制该目录下的全部文件
            for(int i= 0;i<fileList.length;i++){
                if(fileList[i].isDirectory()){//如果如果是子目录进行递归
                    copyDir(fileList[i].getPath()+ "/",
                            destDir + fileList[i].getName() + "/");
                }else{//如果是文件则进行文件拷贝
                    copyFile(fileList[i].getPath(), destDir +fileList[i].getName());
                }
            }
            return true;
        }else {
            copyFileToDir(srcDir,destDir);
            return true;
        }
    }


    /**
     * 复制文件（非目录）
     * @param srcFile 要复制的源文件
     * @param destFile 复制到的目标文件
     * @return
     */
    private static boolean copyFile(String srcFile, String destFile){
        try{
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[]=new byte[1024];
            int len;
            while ((len= streamFrom.read(buffer)) > 0){
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch(Exception ex){
            return false;
        }
    }


    /**
     * 把文件拷贝到某一目录下
     * @param srcFile
     * @param destDir
     * @return
     */
    public static boolean copyFileToDir(String srcFile, String destDir){
        File fileDir = new File(destDir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        String destFile = destDir +"/" + new File(srcFile).getName();
        try{
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[]=new byte[1024];
            int len;
            while ((len= streamFrom.read(buffer)) > 0){
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
            return true;
        } catch(Exception ex){
            return false;
        }
    }


    /**
     * 移动文件目录到某一路径下
     * @param srcFile
     * @param destDir
     * @return
     */
    public static boolean moveFile(String srcFile, String destDir) {
        //复制后删除原目录
        if (copyDir(srcFile, destDir)) {
            deleteFile(new File(srcFile));
            return true;
        }
        return false;
    }

    /**
     * 删除文件（包括目录）
     * @param delFile
     */
    public static void deleteFile(File delFile) {
        //如果是目录递归删除
        if (delFile.isDirectory()) {
            File[] files = delFile.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        }else{
            delFile.delete();
        }
        //如果不执行下面这句，目录下所有文件都删除了，但是还剩下子目录空文件夹
        delFile.delete();
    }

    /**
     * 加密后的文件的后缀
     */
    public static final String CIPHER_TEXT_SUFFIX = ".cipher";

    /**
     * 加解密时以32K个字节为单位进行加解密计算
     */
    private static final int CIPHER_BUFFER_LENGHT = 32 * 1024;

    /**
     * 加密，这里主要是演示加密的原理，没有用什么实际的加密算法
     *
     * @param filePath 明文文件绝对路径
     * @return
     */
    public static boolean encrypt(String filePath, CipherListener listener) {
        try {
            long startTime = System.currentTimeMillis();
            File f = new File(filePath);
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            long totalLenght = raf.length();
            FileChannel channel = raf.getChannel();

            long multiples = totalLenght / CIPHER_BUFFER_LENGHT;
            long remainder = totalLenght % CIPHER_BUFFER_LENGHT;

            MappedByteBuffer buffer = null;
            byte tmp;
            byte rawByte;

            //先对整除部分加密
            for(int i = 0; i < multiples; i++){
                buffer = channel.map(FileChannel.MapMode.READ_WRITE, i * CIPHER_BUFFER_LENGHT, (i + 1) * CIPHER_BUFFER_LENGHT);
                //此处的加密方法很简单，只是简单的异或计算
                for (int j = 0; j < CIPHER_BUFFER_LENGHT; ++j) {
                    rawByte = buffer.get(j);
                    tmp = (byte) (rawByte ^ j);
                    buffer.put(j, tmp);

                    if(null != listener){
                        listener.onProgress(i * CIPHER_BUFFER_LENGHT + j, totalLenght);
                    }
                }
                buffer.force();
                buffer.clear();
            }

            //对余数部分加密
            buffer = channel.map(FileChannel.MapMode.READ_WRITE, multiples * CIPHER_BUFFER_LENGHT, multiples * CIPHER_BUFFER_LENGHT + remainder);

            for (int j = 0; j < remainder; ++j) {
                rawByte = buffer.get(j);
                tmp = (byte) (rawByte ^ j);
                buffer.put(j, tmp);

                if(null != listener){
                    listener.onProgress(multiples * CIPHER_BUFFER_LENGHT + j, totalLenght);
                }
            }
            buffer.force();
            buffer.clear();

            channel.close();
            raf.close();

            //对加密后的文件重命名，增加.cipher后缀
            copyFile(filePath, getImageFile("123" + CIPHER_TEXT_SUFFIX).getAbsolutePath());
            deleteFile(filePath);
            Log.d("加密用时：", (System.currentTimeMillis() - startTime) /1000 + "s");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 解密，这里主要是演示加密的原理，没有用什么实际的加密算法
     *
     * @param filePath 密文文件绝对路径，文件需要以.cipher结尾才会认为其实可解密密文
     * @return
     */
    public static boolean decrypt(String filePath, CipherListener listener) {
        try {
            long startTime = System.currentTimeMillis();
            File f = new File(filePath);

            if(!f.getPath().toLowerCase().endsWith(CIPHER_TEXT_SUFFIX)){
                //后缀不同，认为是不可解密的密文
                return false;
            }

            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            long totalLenght = raf.length();
            FileChannel channel = raf.getChannel();

            long multiples = totalLenght / CIPHER_BUFFER_LENGHT;
            long remainder = totalLenght % CIPHER_BUFFER_LENGHT;

            MappedByteBuffer buffer = null;
            byte tmp;
            byte rawByte;

            //先对整除部分解密
            for(int i = 0; i < multiples; i++){
                buffer = channel.map(FileChannel.MapMode.READ_WRITE, i * CIPHER_BUFFER_LENGHT, (i + 1) * CIPHER_BUFFER_LENGHT);

                //此处的解密方法很简单，只是简单的异或计算
                for (int j = 0; j < CIPHER_BUFFER_LENGHT; ++j) {
                    rawByte = buffer.get(j);
                    tmp = (byte) (rawByte ^ j);
                    buffer.put(j, tmp);

                    if(null != listener){
                        listener.onProgress(i * CIPHER_BUFFER_LENGHT + j, totalLenght);
                    }
                }
                buffer.force();
                buffer.clear();
            }

            //对余数部分解密
            buffer = channel.map(FileChannel.MapMode.READ_WRITE, multiples * CIPHER_BUFFER_LENGHT, multiples * CIPHER_BUFFER_LENGHT + remainder);

            for (int j = 0; j < remainder; ++j) {
                rawByte = buffer.get(j);
                tmp = (byte) (rawByte ^ j);
                buffer.put(j, tmp);

                if(null != listener){
                    listener.onProgress(multiples * CIPHER_BUFFER_LENGHT + j, totalLenght);
                }
            }
            buffer.force();
            buffer.clear();

            channel.close();
            raf.close();

            //解密后去掉.cipher后缀
            f.renameTo(new File(f.getPath().substring(f.getPath().toLowerCase().indexOf(CIPHER_TEXT_SUFFIX))));

            Log.d("解密用时：", (System.currentTimeMillis() - startTime) / 1000 + "s");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用于加解密进度的监听器
     */
    public interface CipherListener{
        void onProgress(long current, long total);
    }
}
