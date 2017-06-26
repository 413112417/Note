package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import pers.xjh.note.R;

/**
 * 渐变效果
 * Created by XJH on 2017/4/26.
 */

public class ShaderView extends View {

    private Bitmap mBitmapBig, mBitmapSmall;
    private Paint mPaint;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mBitmapBig = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.xihu);
        mBitmapSmall = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.android);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setShader(new BitmapShader(mBitmapBig, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawCircle(200, 200, 100, mPaint);
        mPaint.setShader(new BitmapShader(mBitmapSmall, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        canvas.drawCircle(500, 200, 100, mPaint);
        mPaint.setShader(new BitmapShader(mBitmapSmall, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
        canvas.drawCircle(800, 200, 100, mPaint);

        mPaint.setShader(new LinearGradient(100, 400, 300, 600, Color.BLUE, Color.RED, Shader.TileMode.CLAMP));
        canvas.drawRect(100, 400, 300, 600, mPaint);
        mPaint.setShader(new LinearGradient(400, 400, 450, 450, Color.BLUE, Color.RED, Shader.TileMode.REPEAT));
        canvas.drawRect(400, 400, 600, 600, mPaint);
        mPaint.setShader(new LinearGradient(700, 400, 750, 450, Color.BLUE, Color.RED, Shader.TileMode.MIRROR));
        canvas.drawRect(700, 400, 900, 600, mPaint);

        mPaint.setShader(new RadialGradient(200, 800, 50, Color.BLUE, Color.RED, Shader.TileMode.CLAMP));
        canvas.drawRect(100, 700, 300, 900, mPaint);
        mPaint.setShader(new RadialGradient(500, 800, 50, Color.BLUE, Color.RED, Shader.TileMode.REPEAT));
        canvas.drawRect(400, 700, 600, 900, mPaint);
        mPaint.setShader(new RadialGradient(800, 800, 50, Color.BLUE, Color.RED, Shader.TileMode.MIRROR));
        canvas.drawRect(700, 700, 900, 900, mPaint);

        mPaint.setShader(new SweepGradient(200, 1100, Color.BLUE, Color.RED));
        canvas.drawRect(100, 1000, 300, 1200, mPaint);

        mPaint.setShader(new ComposeShader(new BitmapShader(mBitmapSmall, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT),
                new RadialGradient(200, 1400, 50, Color.BLUE, Color.RED, Shader.TileMode.REPEAT),
                PorterDuff.Mode.DARKEN));
        canvas.drawRect(100, 1300, 300, 1500, mPaint);
    }
}
