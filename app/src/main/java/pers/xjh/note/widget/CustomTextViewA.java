package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import pers.xjh.note.R;

/**
 * 自定义的textView
 * Created by XJH on 2017/4/5.
 */

public class CustomTextViewA extends TextView {

    //外部背景画笔
    private Paint mPaintBackgroundOut;
    //内部背景画笔
    private Paint mPaintBackgroundIn;
    //边框宽度
    private int mBorderWidth;

    public CustomTextViewA(Context context) {
        this(context, null);
    }

    public CustomTextViewA(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextViewA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaintBackgroundOut = new Paint();
        mPaintBackgroundOut.setColor(getResources().getColor(R.color.royalblue));
        mPaintBackgroundOut.setStyle(Paint.Style.FILL); //设置实心

        mPaintBackgroundIn = new Paint();
        mPaintBackgroundIn.setColor(getResources().getColor(R.color.yellow));
        mPaintBackgroundIn.setStyle(Paint.Style.FILL); //设置实心

        mBorderWidth = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaintBackgroundOut);
        canvas.drawRect(10, 10, getWidth() - 10, getHeight() -10, mPaintBackgroundIn);
        canvas.save();
        canvas.translate(10, 0);
        super.onDraw(canvas);
        canvas.restore();
    }
}
