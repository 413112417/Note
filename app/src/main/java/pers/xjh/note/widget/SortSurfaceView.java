package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.common.detector.MathUtils;

import pers.xjh.note.utils.MathUtil;
import pers.xjh.note.utils.ToastUtil;

/**
 * Created by XJH on 2017/5/12.
 */

public class SortSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private boolean mIsStop;

    private Paint mPaint;

    private int[] mData;
    //数组最大值
    private int mMaxValue;

    public SortSurfaceView(Context context) {
        super(context);
        init();
    }

    public SortSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SortSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            while (!mIsStop) {
                draw();
            }
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if(mCanvas != null) {
                //在此处画图
                mCanvas.drawColor(Color.WHITE);
                if(mData != null && mData.length > 0) {
                    float width = mCanvas.getWidth() * 1.0f / mData.length;
                    for (int i = 0; i < mData.length; i++) {
                        if(mIsStop) return;
                        float height = 1.0f * mData[i] / mMaxValue * mCanvas.getHeight();
                        mCanvas.drawRect(i * width, mCanvas.getHeight() - height, (i + 1) * width, mCanvas.getHeight(), mPaint);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(mCanvas != null) {
                synchronized (mCanvas) {
                    if(mCanvas != null) {
                        mHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
            }
        }
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(int[] data) {
        mData = data;
        mMaxValue = MathUtil.getMaxValue(data);
    }

    /**
     * 开始绘画
     */
    public void start() {
        mIsStop = false;
    }

    /**
     * 停止绘画
     */
    public void stop() {
        mIsStop = true;
    }
}
