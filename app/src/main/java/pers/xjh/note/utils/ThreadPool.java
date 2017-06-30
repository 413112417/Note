package pers.xjh.note.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * Created by xjh on 17-6-30.
 */

public class ThreadPool {

    private ThreadPool() {}

    private static ExecutorService mThreadPool = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable) {
        mThreadPool.execute(runnable);
    }
}
