package pers.xjh.server;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by XJH on 2017/5/25.
 */

public class HttpServer {

    //Http配置类
    private final HttpConfig mHttpConfig;
    //线程池
    private final ExecutorService mThreadPool;
    //服务端套接字
    private ServerSocket mServerSocket;
    //记录是否启动服务
    private boolean mIsEnable;

    public HttpServer(HttpConfig httpConfig) {
        mHttpConfig = httpConfig;
        mThreadPool = Executors.newCachedThreadPool();
    }

    /**
     * 启动server（异步）
     */
    public void start() {
        mIsEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                precess();
            }
        }).start();
    }

    /**
     * 停止server（异步）
     */
    public void stop() throws IOException {
        if (mIsEnable) {
            mIsEnable = false;
            mServerSocket.close();
            mServerSocket = null;
        }
    }

    /**
     *
     */
    private void precess() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(mHttpConfig.getPort());
            mServerSocket = new ServerSocket();
            mServerSocket.bind(socketAddress);
            while (mIsEnable) {
                //获取客户端链接
                final Socket clientSocket = mServerSocket.accept();
                mThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        onAcceptClientSocket(clientSocket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptClientSocket(Socket clientSocket) {
        try {
            InputStream is = clientSocket.getInputStream();
            String headLine;
            while ((headLine = StreamToolkit.readLine(is)) != null) {
                if(headLine.equals("\r\n")) {
                    break;
                }
                Log.d("xjh", headLine);
            }
            clientSocket.getOutputStream().write("lalala".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
