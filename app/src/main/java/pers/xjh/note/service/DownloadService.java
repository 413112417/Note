package pers.xjh.note.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import pers.xjh.network.HttpClient;
import pers.xjh.network.Response;
import pers.xjh.network.interfaces.ProgressCallback;
import pers.xjh.note.utils.FileUtil;
import pers.xjh.note.utils.ThreadPool;

/**
 * Created by xjh on 17-7-27.
 */

public class DownloadService extends Service {

    private int mProgress;

    private Binder mBinder = new DownloadBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private void startDownload(String url, String saveFileName) {
        ThreadPool.execute(new DownloadTask(url, saveFileName));
    }

    public class DownloadBinder extends Binder {

        public void startDownload(String url, String saveFileName) {
            DownloadService.this.startDownload(url, saveFileName);
        }

        public int getProgress() {
            return mProgress;
        }
    }

    /**
     * 静态内部类防止内存泄漏
     */
    private class DownloadTask implements Runnable {

        private String url;

        private String saveFileName;

        public DownloadTask(String url, String saveFileName) {
            this.url = url;
            this.saveFileName = saveFileName;
        }

        @Override
        public void run() {
            HttpClient.download(url, FileUtil.getDownloadFile(DownloadService.this, saveFileName), new ProgressCallback() {
                @Override
                public void onProgress(int progress) {
                    mProgress = progress;
                }

                @Override
                public void onResponse(Response response) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }
}
