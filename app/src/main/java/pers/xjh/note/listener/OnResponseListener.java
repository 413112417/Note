package pers.xjh.note.listener;

/**
 * 异步响应回调
 * Created by XJH on 2017/3/25.
 */

public interface OnResponseListener<T> {
    /**
     * 成功的回调
     * @param response 响应结果
     */
    void onSuccess(T response);

    /**
     * 失败的回调
     * @param msg 消息
     */
    void onFail(String msg);
}
