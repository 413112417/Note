package pers.xjh.note.widget.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 利用LayoutParamss设置参数进行滚动
 * Created by XJH on 2017/4/18.
 */

public class LayoutParamsScrollView extends View {

    //记录按下的坐标
    private float mDownX, mDownY;
    //记录上次的坐标
    private float mLastX, mLastY;
    //左边和顶部的间隙
    private int mLeftMargin, mTopMargin;

    public LayoutParamsScrollView(Context context) {
        super(context);
    }

    public LayoutParamsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutParamsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLeftMargin = getLeft();
        mTopMargin = getTop();
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

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin = getLeft() - mLeftMargin + offsetX;
                params.topMargin = getTop() - mTopMargin + offsetY;
                setLayoutParams(params);
                break;
        }
        return true;
    }
}
