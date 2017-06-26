package pers.xjh.note.widget.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 利用Layout方法进行滚动
 * Created by XJH on 2017/4/18.
 */

public class LayoutScrollView extends View {

    //记录按下的坐标
    private float mDownX, mDownY;
    //记录上次的坐标
    private float mLastX, mLastY;

    public LayoutScrollView(Context context) {
        super(context);
    }

    public LayoutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastX = event.getX();
                mLastY = event.getY();

                int offsetX = (int) (mLastX - mDownX);
                int offsetY = (int) (mLastY - mDownY);

                layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);
                break;
        }
        return true;
    }
}
