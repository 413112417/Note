package pers.xjh.note.widget.dispatchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by XJH on 2017/4/13.
 */

public class MyView extends View {

    //是否消费了
    private boolean isDisposed = false;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDisposed(boolean disposed) {
        isDisposed = disposed;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(), "MyView dispatchTouchEvent", Toast.LENGTH_SHORT).show();
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(), "MyView onTouchEvent", Toast.LENGTH_SHORT).show();
        return isDisposed;
    }
}
