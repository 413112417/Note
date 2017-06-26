package pers.xjh.note.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import pers.xjh.note.R;


/**
 * 矩形进度条
 * Created by xjh on 2017/1/19.
 */
public class RectProgressBar extends View {

    /** 背景色 */
    private int mBackgroundColor;
    /** 进度的颜色 */
    private int mProgressColor;
    /** 进度值 */
    private int mProgress;
    /** 背景画笔 */
    private Paint mBackgroundPaint;
    /** 进度画笔 */
    private Paint mProgressPaint;

    public RectProgressBar(Context context) {
        this(context, null);
    }

    public RectProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectProgressBar);
        mBackgroundColor = typedArray.getColor(R.styleable.RectProgressBar_backgroundColor, Color.WHITE);
        mProgressColor = typedArray.getColor(R.styleable.RectProgressBar_progressColor, Color.RED);
        typedArray.recycle();

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        canvas.drawRect(0, 0, width, height, mBackgroundPaint);
        canvas.drawRect(0, 0, width * mProgress / 100, height, mProgressPaint);
    }

    /**
     * 设置进度值
     * @param progress
     */
    public void setProgress(int progress) {
        if(progress >= 0 && progress <= 100) {
            mProgress = progress;
        } else {
            mProgress = 0;
        }
        invalidate();
    }
}
