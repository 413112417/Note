package pers.xjh.network;

import java.io.IOException;

/**
 * http请求的响应
 * Created by xjh on 2017/1/11.
 */
public class Response {

    private String mBodyString;

    public Response() {
    }

    public Response(String bodyString) {
        mBodyString = bodyString;
    }

    public Response(okhttp3.Response response) {
        try {
            if(response != null) {
                mBodyString = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBodyString() {
        return mBodyString;
    }

    public void setBodyString(String bodyString) {
        mBodyString = bodyString;
    }
}
