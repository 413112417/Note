package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by XJH on 2017/5/12.
 */

public class SortView extends View {

    private int[] mData;

    private int mWidth, mHeight;

    private Paint mPaint;

    public SortView(Context context) {
        this(context, null);
    }

    public SortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(int[] data) {
        mData = data;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mData == null || mData.length <= 0) return;

        float width = mWidth * 1.0f / mData.length;
        for(int i=0; i<mData.length; i++) {
            float height = mData[i] / 100.f * mHeight;
            canvas.drawRect(i*width, mHeight - height, (i+1)*width, mHeight, mPaint);
        }
        postInvalidate();
    }
}
