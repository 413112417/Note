package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 图层
 * Created by XJH on 2017/4/23.
 */

public class LayerView extends View {

    //画笔
    private Paint mPaint;

    public LayerView(Context context) {
        this(context, null);
    }

    public LayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 100, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, mPaint);


        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(550, 150, 100, mPaint);

        canvas.saveLayerAlpha(400, 0, 800, 400, 127, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(600, 200, 100, mPaint);
        canvas.restore();
    }
}
