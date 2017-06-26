package pers.xjh.note.widget.dispatchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by XJH on 2017/4/13.
 */

public class MyViewGroupA extends RelativeLayout {

    //是否打断
    private boolean isIntercept = false;
    //是否消费了
    private boolean isDisposed = false;

    public MyViewGroupA(Context context) {
        super(context);
    }

    public MyViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

    public void setDisposed(boolean disposed) {
        isDisposed = disposed;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Toast.makeText(getContext(), "MyViewGroupA dispatchTouchEvent", Toast.LENGTH_SHORT).show();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Toast.makeText(getContext(), "MyViewGroupA onInterceptTouchEvent", Toast.LENGTH_SHORT).show();
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(), "MyViewGroupA onTouchEvent", Toast.LENGTH_SHORT).show();
        return isDisposed;
    }
}
