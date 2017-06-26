package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 折线图
 * Created by XJH on 2017/3/31.
 */

public class LineChartView extends View {

    //坐标轴的画笔
    private Paint mAxisPaint;
    //画点的画笔
    private Paint mPointPaint;
    //白色点的画笔
    private Paint mWhitePointPaint;
    //内间距
    private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;
    //控件宽高
    private int mWidth, mHeight;
    //点的信息
    private int[] mPoints = {0,2,2,8,15,2,7,4,8,6};
    //外部点的半径 (这里最好改为UIUtil.dptopx()形式)
    private int mOutPointRadius = 6;
    //内部点的半径
    private int mInPointRadius = 4;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mAxisPaint = new Paint();
        mAxisPaint.setColor(Color.rgb(0,0,0));
        mAxisPaint.setTextSize(30);
        mAxisPaint.setAntiAlias(true); //设置抗锯齿

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.rgb(53,81,181));
        mPointPaint.setAntiAlias(true); //设置抗锯齿
        mPointPaint.setStyle(Paint.Style.FILL); //设置实心
        mPointPaint.setTextSize(30); //设置字体大小
        mPointPaint.setAntiAlias(true); //设置抗锯齿

        mWhitePointPaint = new Paint();
        mWhitePointPaint.setColor(Color.rgb(255, 255, 255));
        mWhitePointPaint.setStyle(Paint.Style.FILL); //设置实心
        mWhitePointPaint.setAntiAlias(true); //设置抗锯齿

        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();
    }

    /**
     * 设置点的信息
     * @param points
     */
    public void setPoints(int[] points) {
        mPoints = points;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mWidth = getWidth();
        mHeight = getHeight();

        int space = (mWidth - mPaddingLeft - mPaddingRight) / 12;

        for(int i=1; i<13; i++) {
            canvas.drawText(i + "月", mPaddingLeft + space * (i - 1), mHeight - mPaddingBottom, mAxisPaint);
        }

        //找到最大值
        if(mPoints != null && mPoints.length > 0) {
            float max = 0;
            for (int i = 0; i < mPoints.length; i++) {
                if(mPoints[i] > max) {
                    max = mPoints[i];
                }
            }
            //设置y轴最大值,设为最大值的（4/3）这样更好看一点
            float yAxisMax = max / 3 * 4 > 1 ? max / 3 * 4 : 1;
            //设置y轴高度
            float yAxisHeight = (int) (mHeight - mPaddingBottom - mPaddingTop - mAxisPaint.measureText("月") * 2);
            float lastX = -1; //上次的x轴坐标
            float lastY = -1; //上次的y轴坐标

            for(int i = 0; i < mPoints.length; i++) {
                float pointX = mPaddingLeft + space * i + mAxisPaint.measureText(i + 1 + "月") / 2;
                float pointY = yAxisHeight - mPoints[i] / yAxisMax * yAxisHeight + mPaddingTop;

                if(i > 0) {
                    canvas.drawLine(lastX, lastY, pointX, pointY, mPointPaint);
                }

                //画出实心蓝色球
                canvas.drawCircle(pointX, pointY, mOutPointRadius, mPointPaint);

                //画出半径略小的实心白色球，形成空心假象
                canvas.drawCircle(lastX, lastY, mInPointRadius, mWhitePointPaint);

                //画出点上面的字
                canvas.drawText(mPoints[i] + "", pointX - mPointPaint.measureText(mPoints[i] + "") / 2,
                        pointY - mOutPointRadius * 2, mPointPaint);

                lastX = pointX;
                lastY = pointY;
            }
        }
    }
}
