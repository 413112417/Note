package pers.xjh.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pers.xjh.network.interfaces.Callback;
import pers.xjh.network.interfaces.ProcessCallback;

/**
 * http请求客户端
 * Created by xjh on 2017/1/11.
 */
public class HttpClient {

    /** 超时时间*/
    private static final long TIME_OUT = 30;

    private static OkHttpClient mClient;

    static {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        /** 连接超时时间*/
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /** 读取超时时间*/
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        /** 写入超时时间*/
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        /** 允许重定向*/
        okHttpBuilder.followRedirects(true);

        mClient = okHttpBuilder.build();
    }

    /**
     * 同步get请求
     * @param url
     */
    public static pers.xjh.network.Response getSync(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            return new pers.xjh.network.Response(mClient.newCall(request).execute());
        } catch (Exception e) {
            return new pers.xjh.network.Response(e.getMessage());
        }
    }

    /**
     * 异步get请求
     * @param url
     * @param callback
     */
    public static void getAsync(String url, final Callback callback) {
        try {
            Request request = new Request.Builder().url(url).build();
            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback.onResponse(new pers.xjh.network.Response(response));
                }
            });
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    /**
     * 同步post请求
     * @param url
     * @param params
     */
    public static pers.xjh.network.Response postSync(String url, Map<String, String> params) {
        try {
            FormBody.Builder builder = new FormBody.Builder();

            if(params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }

            FormBody formBody = builder.build();

            Request request = new Request.Builder().url(url).post(formBody).build();

            return new pers.xjh.network.Response(mClient.newCall(request).execute());
        } catch (Exception e) {
            return new pers.xjh.network.Response(e.getMessage());
        }
    }

    /**
     * 异步post请求
     * @param url
     * @param params
     * @param callback
     */
    public static void postAsync(String url, Map<String, String> params, final Callback callback) {
        try {
            FormBody.Builder builder = new FormBody.Builder();

            if(params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }

            FormBody formBody = builder.build();

            Request request = new Request.Builder().url(url).post(formBody).build();

            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback.onResponse(new pers.xjh.network.Response(response));
                }
            });
        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    /**
     * 下载文件
     * @param url 请求地址
     * @param file 保存的文件名
     * @param processCallback 回调函数
     */
    public static void download(String url, final File file, final ProcessCallback processCallback) {
        try {
            Request request = new Request.Builder().url(url).build();
            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    processCallback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    FileOutputStream fos = null;
                    byte[] buffer = new byte[1024];
                    int length = 0;

                    try {
                        is = response.body().byteStream();
                        fos = new FileOutputStream(file);

                        long total = response.body().contentLength();
                        long sum = 0;

                        while ((length = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, length);
                            sum += length;
                            int progress = (int) (sum * 1.0f / total * 100);
                            processCallback.onProcess(progress);
                        }
                        fos.flush();
                        processCallback.onResponse(new pers.xjh.network.Response("下载成功"));
                    } catch (Exception e) {
                        processCallback.onFailure(e);
                    } finally {
                        try {
                            if (is != null) is.close();
                        } catch (IOException e) {}
                        try {
                            if (fos != null) fos.close();
                        } catch (IOException e) {}
                    }
                }
            });
        } catch (Exception e) {
            processCallback.onFailure(e);
        }
    }
}
