package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import pers.xjh.note.algorithm.structure.tree.RedBlackTree;
import pers.xjh.note.algorithm.structure.tree.RedBlackTreeNode;

/**
 * Created by XJH on 2017/5/12.
 */

public class RedBlackTreeSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private boolean mIsStop;
    //边的画笔
    private Paint mRedEdgePaint, mBlueEdgePaint;
    //点的画笔
    private Paint mRedPointPaint, mBlackPointPaint;

    private RedBlackTree mRedBlackTree;
    //控件宽高
    private int mWidth, mHeight;
    //数组最大值
    private int mMaxValue;
    //点的半径
    private int mPointRadius = 2;

    public RedBlackTreeSurfaceView(Context context) {
        super(context);
        init();
    }

    public RedBlackTreeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedBlackTreeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mRedEdgePaint = new Paint();
        mRedEdgePaint.setStyle(Paint.Style.FILL);
        mRedEdgePaint.setColor(Color.RED);

        mBlueEdgePaint = new Paint();
        mBlueEdgePaint.setStyle(Paint.Style.FILL);
        mBlueEdgePaint.setColor(Color.BLUE);

        mRedPointPaint = new Paint();
        mRedPointPaint.setStyle(Paint.Style.FILL);
        mRedPointPaint.setColor(Color.RED);
        mRedPointPaint.setAntiAlias(true);

        mBlackPointPaint = new Paint();
        mBlackPointPaint.setStyle(Paint.Style.FILL);
        mBlackPointPaint.setColor(Color.BLACK);
        mBlackPointPaint.setAntiAlias(true);
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
                if(mRedBlackTree != null) {
                    drawTree(mRedBlackTree.getRootNode(), mRedBlackTree.getMaxDegree());
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
     * 画出树
     * @param node 节点
     * @param maxDegree 最大层数
     */
    private void drawTree(RedBlackTreeNode node, int maxDegree) {
        if(mIsStop) return;

        RedBlackTreeNode leftChild = node.getLeftChildNode();
        RedBlackTreeNode rightChild = node.getRightChildNode();

        if(leftChild != null) {
            if(leftChild.isVisited()) {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * leftChild.getValue() / mMaxValue * mWidth,
                        1.0f * leftChild.getDegree() / maxDegree * mHeight,
                        mRedEdgePaint);
            } else {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * leftChild.getValue() / mMaxValue * mWidth,
                        1.0f * leftChild.getDegree() / maxDegree * mHeight,
                        mBlueEdgePaint);
            }
            drawTree(leftChild, maxDegree);
        }

        if(rightChild != null) {
            if(rightChild.isVisited()) {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * rightChild.getValue() / mMaxValue * mWidth,
                        1.0f * rightChild.getDegree() / maxDegree * mHeight,
                        mRedEdgePaint);
            } else {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * rightChild.getValue() / mMaxValue * mWidth,
                        1.0f * rightChild.getDegree() / maxDegree * mHeight,
                        mBlueEdgePaint);
            }
            drawTree(rightChild, maxDegree);
        }

        if(node.isRed()) {
            mCanvas.drawCircle(1.0f * node.getValue() / mMaxValue * mWidth,
                    1.0f * node.getDegree() / maxDegree * mHeight, mPointRadius, mRedPointPaint);
        } else {
            mCanvas.drawCircle(1.0f * node.getValue() / mMaxValue * mWidth,
                    1.0f * node.getDegree() / maxDegree * mHeight, mPointRadius, mBlackPointPaint);
        }
    }

    /**
     * 设置树
     * @param tree
     */
    public void setTree(RedBlackTree tree) {
        mRedBlackTree = tree;
    }

    /**
     * 设置最大值
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
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
