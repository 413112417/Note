package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import pers.xjh.note.R;

/**
 *
 * Created by XJH on 2017/4/25.
 */

public class WavingImageView extends View {

    private Bitmap mBitmap;//图片
    private final int HEIGHT = 100;//竖着的网格数
    private final int WIDTH = 100;//横着的网格数
    private float[] mOrig, mVerts;//原始坐标点，改变后的坐标点
    private int A = 30;//波动的力度
    private float k = 1;

    public WavingImageView(Context context) {
        this(context, null);
    }

    public WavingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WavingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.xihu);
        mOrig = new float[(HEIGHT + 1) * (WIDTH + 1) * 2];
        mVerts = new float[(HEIGHT + 1) * (WIDTH + 1) * 2];
        //初始化坐标
        initOrig();
    }

    private void initOrig() {
        //获取位图的宽高
        float width = mBitmap.getWidth();
        float height = mBitmap.getHeight();
        int index = 0 ;
        for(int y = 0 ; y <= HEIGHT ;y++) {
            //纵坐标
            float fy = height * y / HEIGHT;
            for(int x = 0;x <= WIDTH ; x++) {
                //横坐标
                float fx = width * x / WIDTH ;
                mOrig[index * 2 + 0] = mVerts[index * 2 +0] = fx;
                //在这里人为将坐标加100是为了让图像下移，避免扭曲后被屏幕遮挡
                mOrig[index * 2 + 1] = mVerts[index * 2 + 1] = fy + 100;
                index += 1;
            }
        }
    }

    /** * @fun 波动 * 改变纵坐标的值，横坐标不变 */
    private void wave() {
        for(int j = 0;j <= HEIGHT ;j++) {
            for(int i = 0 ; i <= WIDTH ;i++) {
                //横坐标不变
                mVerts[(j * (WIDTH + 1) + i) * 2 + 0] += 0;
                float offsetY = (float) Math.sin((float)i / WIDTH * 2 * Math.PI + Math.PI * k);
                mVerts[(j * (WIDTH + 1) + i) * 2 + 1] = mOrig[(j * WIDTH + i) * 2 + 1] + offsetY * A;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        wave();
        k += 0.1F;
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, mVerts, 0, null, 0, null);
        invalidate();
    }
}
