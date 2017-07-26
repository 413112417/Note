package pers.xjh.network.interfaces;


import pers.xjh.network.Response;

/**
 * 处理中的回调
 * Created by xjh on 2017/1/11.
 */
public interface ProcessCallback {
    /** 进度的回调 */
    void onProcess(int progress);
    /** 成功的回调 */
    void onResponse(Response response);
    /** 失败的回调 */
    void onFailure(Exception e);
}
