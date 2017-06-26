package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Canvas API测试
 * Created by XJH on 2017/4/20.
 */

public class CanvasAPIView extends View {

    //画笔
    private Paint mPaint;

    public CanvasAPIView(Context context) {
        this(context, null);
    }

    public CanvasAPIView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasAPIView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK); //画笔颜色
        mPaint.setStrokeWidth(5); //线的宽度
        mPaint.setAntiAlias(true); //抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(10, 10, 100, 100, mPaint); //实心矩形

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(110, 10, 200, 100, mPaint); //空心矩形

        canvas.drawPoint(250, 50, mPaint); //点

        canvas.drawLine(300, 50, 400, 50, mPaint); //线

        float[] lines = {450, 50, 480, 80, 480, 80, 510, 20, 510, 20, 540, 40};
        canvas.drawLines(lines, mPaint); //多条线

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(600, 10, 690, 100, 10, 10, mPaint); //圆角矩形(API 21以上可以使用)
        }

        canvas.drawCircle(760, 50, 40, mPaint); //圆

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(10, 210, 100, 300, 20, 120, true, mPaint); //扇形(API 21以上可以使用)

            canvas.drawArc(150, 210, 240, 300, 20, 120, false, mPaint); //圆弧(API 21以上可以使用)

            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawArc(300, 210, 390, 300, 20, 120, true, mPaint); //实心扇形(API 21以上可以使用)

            canvas.drawArc(450, 210, 540, 300, 20, 120, false, mPaint); //实心圆弧(API 21以上可以使用)

            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawOval(600, 210, 800, 300, mPaint); //椭圆(API 21以上可以使用)
        }

        float[] points = {10, 500, 50, 530, 90, 560, 130, 590, 170, 620, 210, 620, 250, 590, 290, 560, 330, 530, 360, 500};
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawPosText("HelloWorld", points, mPaint);  //这个方法不支持字形组合和分解因此不应该用于呈现复杂的脚本。它还不处理补充字符(如emoji)

        mPaint.setStrokeWidth(5);
        Path path = new Path();
        path.moveTo(500, 500);
        path.lineTo(600, 600);
        path.lineTo(600, 500);
        path.lineTo(700, 600);
        canvas.drawPath(path, mPaint); //路径

        path.moveTo(800, 500);
        path.lineTo(900, 600);
        path.lineTo(900, 500);
        path.lineTo(1000, 600);
        path.close();
        canvas.drawPath(path, mPaint); //封闭路径
    }
}
