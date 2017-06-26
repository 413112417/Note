package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义的textView
 * Created by XJH on 2017/4/5.
 */

public class CustomTextViewB extends TextView {

    //TextView原来的Paint对象
    private Paint mPaint;
    //控件的宽度
    private int mViewWidth;
    //线性渲染
    private LinearGradient mLinearGradient;

    private Matrix mMatrix;
    //矩阵偏移量
    private int mTranslate;

    public CustomTextViewB(Context context) {
        super(context);
    }

    public CustomTextViewB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextViewB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //为什么要在onSizeChanged里初始化？
        //因为在构造函数里初始化的话，无法获取宽度
        if(mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if(mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[] {Color.BLUE, Color.RED, Color.BLUE}, null, Shader.TileMode.CLAMP);
                mMatrix = new Matrix();
                mPaint.setShader(mLinearGradient);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mMatrix != null) {
            mTranslate += mViewWidth / 20;
            if(mTranslate > mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }
}
