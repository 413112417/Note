package pers.xjh.note.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 子控件左右滑动不沖突的下拉刷新控件
 * Created by XJH on 2017/7/21.
 */
public class NiceRefreshLayout extends SwipeRefreshLayout {

    private float startY;

    private float startX;
    // 记录子控件是否移动的标记
    private boolean mIsChildMove;

    private int mTouchSlop;

    public NiceRefreshLayout(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NiceRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsChildMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果子控件正在拖拽中，那么不拦截它的事件，直接return false；
                if(mIsChildMove) {
                    return false;
                }

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给子控件处理。
                if(distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsChildMove = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsChildMove = false;
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给NiceRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }
}
