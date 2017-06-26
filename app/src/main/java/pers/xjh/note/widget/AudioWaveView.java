package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import pers.xjh.note.R;

import java.util.Random;

/**
 * 音频条形图
 * Created by XJH on 2017/4/7.
 */

public class AudioWaveView extends View {

    //控件宽度
    private int mWidth;
    //控件高度
    private int mHeight;
    //条形数量
    private int mWaveformCount = 20;
    //内间距
    private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;
    //画笔
    private Paint mPaint;
    //条形图宽度
    private int mWaveformWidth;
    //随机数
    private Random mRandom;
    //条形图间隔
    private int mOffset = 10;
    //渐变色
    private LinearGradient mLinearGradient;

    public AudioWaveView(Context context) {
        this(context, null);
    }

    public AudioWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.red));

        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();

        mRandom = new Random(System.currentTimeMillis());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mWaveformWidth = (mWidth - mPaddingLeft - mPaddingRight - mOffset * (mWaveformCount - 1)) / mWaveformCount;

        mLinearGradient = new LinearGradient(0, 0, mWidth, mHeight, new int[] {Color.BLUE, Color.RED}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(int i=0; i<mWaveformCount; i++) {
            canvas.drawRect(mPaddingLeft + i * mWaveformWidth + i * mOffset,
                    mPaddingTop + (mHeight - mPaddingTop - mPaddingBottom) * mRandom.nextFloat(),
                    mPaddingLeft + (i + 1) * mWaveformWidth + i * mOffset,
                    mHeight - mPaddingBottom, mPaint);
        }

        postInvalidateDelayed(100);
    }
}
