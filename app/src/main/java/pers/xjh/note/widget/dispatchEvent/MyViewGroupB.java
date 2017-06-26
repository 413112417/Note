package pers.xjh.note.widget.dispatchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by XJH on 2017/4/13.
 */

public class MyViewGroupB extends RelativeLayout {

    //是否打断
    private boolean isIntercept = false;
    //是否消费了
    private boolean isDisposed = false;

    public MyViewGroupB(Context context) {
        super(context);
    }

    public MyViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Toast.makeText(getContext(), "MyViewGroupB dispatchTouchEvent", Toast.LENGTH_SHORT).show();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Toast.makeText(getContext(), "MyViewGroupB onInterceptTouchEvent", Toast.LENGTH_SHORT).show();
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(), "MyViewGroupB onTouchEvent", Toast.LENGTH_SHORT).show();
        return isDisposed;
    }
}
