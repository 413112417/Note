package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import pers.xjh.note.algorithm.structure.tree.BinaryTree;
import pers.xjh.note.algorithm.structure.tree.BinaryTreeNode;

/**
 * Created by XJH on 2017/5/12.
 */

public class BinaryTreeSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private boolean mIsStop;

    private Paint mBluePaint, mRedPaint;

    private BinaryTree mBinaryTree;
    //控件宽高
    private int mWidth, mHeight;
    //数组最大值
    private int mMaxValue;

    public BinaryTreeSurfaceView(Context context) {
        super(context);
        init();
    }

    public BinaryTreeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BinaryTreeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                if(mBinaryTree != null) {
                    drawTree(mBinaryTree.getRootNode(), mBinaryTree.getMaxDegree());
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
    private void drawTree(BinaryTreeNode node, int maxDegree) {
        if(mIsStop) return;

        BinaryTreeNode leftChild = node.getLeftChildNode();
        BinaryTreeNode rightChild = node.getRightChildNode();

        if(leftChild != null) {
            if(leftChild.isVisited()) {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * leftChild.getValue() / mMaxValue * mWidth,
                        1.0f * leftChild.getDegree() / maxDegree * mHeight,
                        mRedPaint);
            } else {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * leftChild.getValue() / mMaxValue * mWidth,
                        1.0f * leftChild.getDegree() / maxDegree * mHeight,
                        mBluePaint);
            }
            drawTree(leftChild, maxDegree);
        }

        if(rightChild != null) {
            if(rightChild.isVisited()) {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * rightChild.getValue() / mMaxValue * mWidth,
                        1.0f * rightChild.getDegree() / maxDegree * mHeight,
                        mRedPaint);
            } else {
                mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                        1.0f * node.getDegree() / maxDegree * mHeight,
                        1.0f * rightChild.getValue() / mMaxValue * mWidth,
                        1.0f * rightChild.getDegree() / maxDegree * mHeight,
                        mBluePaint);
            }
            drawTree(rightChild, maxDegree);
        }
    }

    /**
     * 设置树
     * @param tree
     */
    public void setTree(BinaryTree tree) {
        mBinaryTree = tree;
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
