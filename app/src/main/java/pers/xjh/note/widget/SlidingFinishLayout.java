package pers.xjh.note.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 右划返回布局
 * Created by XJH on 2017/6/6.
 */

public class SlidingFinishLayout extends LinearLayout {

    private ViewGroup mParentView;
    /** 按下的X坐标 */
    private float mDownX, mDownY;
    /** 手指移动后的X坐标 */
    private float mLastX, mLastY;
    /** 判定开始滑动的最小间距 */
    private int mTouchSlop;
    /** 判定快速移动的值 */
    private int mSpeedFinishTouchSlop = 30;
    /** 用于计算速度的时间 */
    private int mSpeedComputeTime = 100;
    /** 判定触发结束事件的间距 */
    private int mFinishTouchSlop = 300;
    /** 是否允许滑动 */
    private boolean mAllowSlide;
    /** 是否允许强制打断 */
    private boolean mAllowIntercept;
    /** 是否要强制打断 */
    private boolean mForceIntercept;
    /** 滚动类 */
    private Scroller mScroller;
    /** 监测手指移动速度 */
    private VelocityTracker mVelocityTracker;
    /** 滑动结束的回调 */
    private OnSlidingFinishListener mOnSlidingFinishListener;
    /** 滑动的回调 */
    private OnSlidingListener mOnSlidingListener;
    /** 是否可以滑动 */
    private boolean mSliding = true;
    /** x, y偏移量 */
    private int mOffsetX, mOffsetY;

    public SlidingFinishLayout(Context context) {
        super(context);
        init();
    }

    public SlidingFinishLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingFinishLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            mParentView = (ViewGroup) getParent();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(mSliding && mForceIntercept) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = event.getX();
                    mDownY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mLastX = event.getX();
                    mLastY = event.getY();

                    mOffsetX = (int) (mLastX - mDownX);
                    mOffsetY = (int) (mLastY - mDownY);

                    if (!mAllowIntercept && mOffsetX > mTouchSlop && (mOffsetY <= mTouchSlop && mOffsetY >= -mTouchSlop)) {
                        mAllowIntercept = true;
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //滑动过程中将事件拦截
        if(mAllowSlide || (mAllowIntercept && mForceIntercept)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mSliding) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = event.getX();
                    mDownY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(mSpeedComputeTime);

                    mLastX = event.getX();
                    mLastY = event.getY();

                    mOffsetX = (int) (mLastX - mDownX);
                    mOffsetY = (int) (mLastY - mDownY);

                    if (!mAllowSlide && mOffsetX > mTouchSlop && (mOffsetY <= mTouchSlop && mOffsetY >= -mTouchSlop)) {
                        mAllowSlide = true;
                        mDownX = mLastX;
                    } else if (mAllowSlide && mParentView.getScrollX() <= 0) {
                        //防止滑动太快超出屏幕
                        if (mOffsetX < mParentView.getScrollX()) {
                            ((View) getParent()).scrollBy(-mParentView.getScrollX(), 0);
                        } else {
                            ((View) getParent()).scrollBy(-mOffsetX, 0);
                        }
                        if(mOnSlidingListener != null) {
                            mOnSlidingListener.onSliding(mParentView.getScrollX());
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mVelocityTracker.getXVelocity() > mSpeedFinishTouchSlop && mOffsetX > 0 && mAllowSlide) {
                        if (mOnSlidingFinishListener != null) {
                            mOnSlidingFinishListener.onSlidingFinish();
                        }
                    } else if (mParentView.getScrollX() < -mFinishTouchSlop) {
                        if (mOnSlidingFinishListener != null) {
                            mOnSlidingFinishListener.onSlidingFinish();
                        }
                    } else {
                        mScroller.startScroll(mParentView.getScrollX(), 0, -mParentView.getScrollX(), 0);
                        invalidate();
                    }
                    mAllowSlide = false;
                    mAllowIntercept = false;
                    break;
            }
            //返回true表示：当所有控件都不处理down事件时，由本控件处理。防止后续move和up事件不分发。
            return true;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断mScroller是否执行完毕
        if(mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 设置是否可滑动
     * @param sliding 是否可滑动
     * @param forceIntercept 是否要强制打断（true，滑动布局优先级高于子布局）
     */
    public void setSliding(boolean sliding, boolean forceIntercept) {
        this.mSliding = sliding;
        this.mForceIntercept = forceIntercept;
    }

    /**
     * 设置滑动结束回调
     * @param onSlidingFinishListener
     */
    public void setOnSlidingFinishListener(OnSlidingFinishListener onSlidingFinishListener) {
        this.mOnSlidingFinishListener = onSlidingFinishListener;
    }

    /**
     * 设置滑动回调
     * @param onSlidingListener
     */
    public void setOnSlidingListener(OnSlidingListener onSlidingListener) {
        this.mOnSlidingListener = onSlidingListener;
    }

    /**
     * 滑动的回调
     */
    public interface OnSlidingListener {
        void onSliding(int scrollX);
    }

    /**
     * 滑动结束的回调
     */
    public interface OnSlidingFinishListener {
        void onSlidingFinish();
    }
}
