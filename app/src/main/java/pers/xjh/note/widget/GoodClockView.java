package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 时钟控件
 * Created by XJH on 2017/4/22.
 */

public class GoodClockView extends View {

    //背景画笔
    private Paint mPaintBackground;
    //指针画笔
    private Paint mPaintPointer;
    //时、分、秒读数
    private int mHour = 3, mMinute = 20, mSecond = 45;
    //保存背景的bitmap
    private Bitmap mBackgroundBitmap;
    //宽高
    private int mWidth, mHeight;
    //半径
    private int mRadius;

    public GoodClockView(Context context) {
        this(context, null);
    }

    public GoodClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaintBackground = new Paint();
        mPaintBackground.setStyle(Paint.Style.STROKE); //设置空心
        mPaintBackground.setStrokeWidth(5); //线宽度
        mPaintBackground.setColor(Color.BLACK); //设置黑色
        mPaintBackground.setAntiAlias(true); //设置抗锯齿

        mPaintPointer = new Paint();
        mPaintPointer.setStyle(Paint.Style.STROKE); //设置实心
        mPaintPointer.setStrokeWidth(10); //线宽度
        mPaintPointer.setColor(Color.BLACK); //设置黑色
        mPaintPointer.setAntiAlias(true); //设置抗锯齿
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRadius = mWidth < mHeight ? mWidth / 2 : mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画背景
        if(mBackgroundBitmap == null) {
            mBackgroundBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_4444);
            Canvas backgroundCanvas = new Canvas(mBackgroundBitmap);
            drawBackground(backgroundCanvas);
        }
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);

        //画指针
        drawPointer(canvas);

        //开始计时
        tickTack();
    }

    /**
     * 画背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {

        //刻度线长度
        int tickLength = mRadius / 10;

        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaintBackground);

        mPaintBackground.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius / 30, mPaintBackground);
        mPaintBackground.setStyle(Paint.Style.STROKE);

        canvas.save();
        for(int i=0; i<12; i++) {
            canvas.drawLine(mWidth / 2, mHeight / 2 - mRadius, mWidth / 2, mHeight / 2 - mRadius + tickLength, mPaintBackground);
            for(int j=0; j<5; j++) {
                canvas.rotate(6, mWidth / 2, mHeight / 2);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mRadius, mWidth / 2, mHeight / 2 - mRadius + tickLength / 2, mPaintBackground);
            }
        }
        canvas.restore();
    }

    /**
     * 画指针
     */
    private void drawPointer(Canvas canvas) {

        canvas.save();
        canvas.rotate(6 * mHour, mWidth / 2, mHeight / 2);
        mPaintPointer.setStrokeWidth(15); //时针宽度
        canvas.drawLine(mWidth / 2, mHeight / 2 , mWidth / 2, mHeight / 2 - mRadius / 2, mPaintPointer);
        canvas.restore();

        canvas.save();
        canvas.rotate(6 * mMinute, mWidth / 2, mHeight / 2);
        mPaintPointer.setStrokeWidth(10); //分针宽度
        canvas.drawLine(mWidth / 2, mHeight / 2 , mWidth / 2, mHeight / 2 - mRadius * 2 / 3, mPaintPointer);
        canvas.restore();

        canvas.save();
        canvas.rotate(6 * mSecond, mWidth / 2, mHeight / 2);
        mPaintPointer.setStrokeWidth(5); //秒针宽度
        canvas.drawLine(mWidth / 2, mHeight / 2 , mWidth / 2, mHeight / 2 - mRadius * 3 / 4, mPaintPointer);
        canvas.restore();
    }

    /**
     * 开始计时
     */
    private void tickTack() {
        mSecond += 1;
        if(mSecond >= 60) {
            mSecond -= 60;
            mMinute += 1;
            if(mMinute >= 60) {
                mMinute -= 60;
                mHour += 1;
                if(mHour >= 60) {
                    mHour -= 60;
                }
            }
        }

        postInvalidateDelayed(1000);
    }
}
