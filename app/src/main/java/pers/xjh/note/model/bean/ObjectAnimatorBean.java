package pers.xjh.note.model.bean;

/**
 * 属性动画
 * Created by XJH on 2017/4/27.
 */

public class ObjectAnimatorBean {

    private int objectValue;

    private OnValueChangeListener onValueChangeListener;

    public int getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(int objectValue) {
        this.objectValue = objectValue;
        if(onValueChangeListener != null) {
            onValueChangeListener.onValueChanged(this.objectValue);
        }
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public interface OnValueChangeListener {
        void onValueChanged(int value);
    }
}
