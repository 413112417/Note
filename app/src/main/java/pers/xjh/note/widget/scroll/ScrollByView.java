package pers.xjh.note.widget.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 利用Layout方法进行滚动
 * Created by XJH on 2017/4/18.
 */

public class ScrollByView extends View {

    //记录按下的坐标
    private float mDownX, mDownY;
    //记录上次的坐标
    private float mLastX, mLastY;

    public ScrollByView(Context context) {
        super(context);
    }

    public ScrollByView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollByView(Context context, AttributeSet attrs, int defStyleAttr) {
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

                //此处offsetX， offsetY前面加负号的原因是：
                //((View) getParent()).scrollBy（a, b）是将父布局
                //向x轴正方向移动a距离，向y轴正方向移动b距离，而父布局
                //里的内容，从视觉效果看，移动方向正好相反。

                //注意scrollBy（）方法移动的是view中的内容，而不是view本身
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
        }
        return true;
    }
}
