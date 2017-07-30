package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import pers.xjh.ai.algorithm.structure.Point;

/**
 * 拟合
 * Created by xjh on 17-7-30.
 */

public class FittingSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private boolean mIsStop;

    private Paint mBluePaint, mRedPaint;

    private Point[] mPoints;
    //控件宽高
    private int mWidth, mHeight;

    private float[] mWeights;

    public FittingSurfaceView(Context context) {
        super(context);
        init();
    }

    public FittingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FittingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mBluePaint = new Paint();
        mBluePaint.setStyle(Paint.Style.FILL);
        mBluePaint.setStrokeWidth(5);
        mBluePaint.setColor(Color.BLUE);

        mRedPaint = new Paint();
        mRedPaint.setStyle(Paint.Style.FILL);
        mRedPaint.setStrokeWidth(5);
        mRedPaint.setColor(Color.RED);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
        mWidth = holder.getSurfaceFrame().width();
        mHeight = holder.getSurfaceFrame().height();
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
                if(mPoints != null) {
                    for(Point point : mPoints) {
                        mCanvas.drawPoint(point.getX() * mWidth, (1 - point.getY()) * mHeight, mBluePaint);
                    }
                    mCanvas.drawLine(0, (1-mWeights[0]) * mHeight, mWidth, (1-mWeights[0]-mWeights[1]) * mHeight, mRedPaint);
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
     * 设置所有的点
     */
    public void setPoints(Point[] points) {
        this.mPoints = points;
    }

    /**
     * 设置权重
     */
    public void setWeights(float[] weights) {
        this.mWeights = weights;
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
