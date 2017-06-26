package pers.xjh.network.interfaces;


import pers.xjh.network.Response;

/**
 * 响应的回调
 * Created by xjh on 2017/1/11.
 */
public interface Callback {
    /** 成功的回调 */
    void onResponse(Response response);
    /** 失败的回调 */
    void onFailure(Exception e);
}
