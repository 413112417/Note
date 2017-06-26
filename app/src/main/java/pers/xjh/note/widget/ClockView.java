package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 时钟控件
 * Created by XJH on 2017/4/22.
 */

public class ClockView extends View {

    //背景画笔
    private Paint mPaintBackground;
    //指针画笔
    private Paint mPaintPointer;
    //时、分、秒读数
    private int mHour = 3, mMinute = 20, mSecond = 45;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onDraw(Canvas canvas) {

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int radius = width < height ? width / 2 : height / 2;

        //刻度线长度
        int tickLength = radius / 10;

        canvas.drawCircle(width / 2, height / 2, radius, mPaintBackground);

        mPaintBackground.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, radius / 30, mPaintBackground);
        mPaintBackground.setStyle(Paint.Style.STROKE);

        canvas.save();

        for(int i=0; i<12; i++) {
            canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 - radius + tickLength, mPaintBackground);

            for(int j=0; j<5; j++) {
                if(mHour == i * 5 + j) {
                    mPaintPointer.setStrokeWidth(15); //时针宽度
                    canvas.drawLine(width / 2, height / 2 , width / 2, height / 2 - radius / 2, mPaintPointer);
                }

                if(mMinute == i * 5 + j) {
                    mPaintPointer.setStrokeWidth(10); //分针宽度
                    canvas.drawLine(width / 2, height / 2 , width / 2, height / 2 - radius * 2 / 3, mPaintPointer);
                }

                if(mSecond == i * 5 + j) {
                    mPaintPointer.setStrokeWidth(5); //秒针宽度
                    canvas.drawLine(width / 2, height / 2 , width / 2, height / 2 - radius * 3 / 4, mPaintPointer);
                }

                canvas.rotate(6, width / 2, height / 2);
                canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 - radius + tickLength / 2, mPaintBackground);
            }
        }

        canvas.restore();

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
