package pers.xjh.note.ui.base;

/**
 * Presenter的基类
 * Created by XJH on 2017/3/25.
 */

public interface BasePresenter<T> {

    /**
     * 解除与View的绑定关系，防止内存泄漏
     */
    void detachView();
}
