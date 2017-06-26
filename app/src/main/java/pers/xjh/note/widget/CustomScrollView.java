package pers.xjh.note.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by XJH on 2017/4/9.
 */

public class CustomScrollView extends ViewGroup {

    //ViewGroup的高度
    private int mHeight;
    //Y方向高度的差值
    private int mDiffYHeight;
    //记录上一次点的坐标
    private float mLastY;
    //记录移动时候点的坐标
    private float mMoveY;
    //记录移动的差值
    private int mDiffY;
    //滚动
    private Scroller mScroller;

    private boolean isFirst = true;

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 测量高度
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        int childViewHeight = 0;
        int count = getChildCount();
        for(int i=0; i<count; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() != View.GONE) {
                childViewHeight += child.getMeasuredHeight();
            }
        }

        if(specMode == MeasureSpec.UNSPECIFIED || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if(specMode == MeasureSpec.AT_MOST) {
            result = Math.min(childViewHeight, specSize);
        }
        if(isFirst) {
            mDiffYHeight = childViewHeight - specSize;
            isFirst = false;
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //将子控件的高度叠加在一起，得到ViewGroup的高度。
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        int viewGroupHeight = 0;
        int count = getChildCount();
        for(int i=0; i<count; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() != View.GONE) {
                int viewHeight = getChildAt(i).getMeasuredHeight();
                child.layout(l, viewGroupHeight, r, viewGroupHeight + viewHeight);
                viewGroupHeight += viewHeight;
            }
        }
        mHeight = viewGroupHeight;
        mlp.height = viewGroupHeight;
        setLayoutParams(mlp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getY();

                mDiffY = (int) (mMoveY - mLastY);
                scrollBy(0, -mDiffY);

                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                mLastY = event.getY();

                int scrollY = getScrollY();
                if(scrollY < 0) {
                    mScroller.startScroll(0, scrollY, 0, -scrollY);
                    postInvalidate(); //此处不重绘， 有时候会使动画停止，但实际动画执行了
                } else if(scrollY > mDiffYHeight) {
                    mScroller.startScroll(0, scrollY, 0, mDiffYHeight - scrollY);
                    postInvalidate();
                }

                mMoveY = 0;
                mLastY = 0;
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
