package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.Set;

import pers.xjh.note.algorithm.structure.graph.Graph;
import pers.xjh.note.algorithm.structure.graph.Point;

/**
 * Created by XJH on 2017/5/12.
 */

public class GraphSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private boolean mIsStop;

    private Paint mBluePaint, mRedPaint;

    private Graph mGraph;
    //控件宽高
    private int mWidth, mHeight;

    public GraphSurfaceView(Context context) {
        super(context);
        init();
    }

    public GraphSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mBluePaint.setColor(Color.BLUE);

        mRedPaint = new Paint();
        mRedPaint.setStyle(Paint.Style.FILL);
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
                if(mGraph != null) {
                    drawGraph(mGraph);
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

    private void drawGraph(Graph graph) {
        if(mIsStop) return;

        List<Point> points = graph.getPoints();
        for(Point point : points) {
            Set<Point> neighborPoints = point.getNeighborPoints();
            if(neighborPoints != null) {
                for (Point neighborPoint : neighborPoints) {
                    if(mIsStop) return;
                    if(neighborPoint.isVisited()) {
                        mCanvas.drawLine(point.getX(), point.getY(), neighborPoint.getX(), neighborPoint.getY(), mRedPaint);
                    }
                    else {
                        mCanvas.drawLine(point.getX(), point.getY(), neighborPoint.getX(), neighborPoint.getY(), mBluePaint);
                    }
                }
            }
        }
    }

    /**
     * 设置图
     * @param graph
     */
    public void setGraph(Graph graph) {
        mGraph = graph;
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
