package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

import pers.xjh.note.algorithm.structure.tree.TreeNode;
import pers.xjh.note.algorithm.structure.tree.Tree;

/**
 * Created by XJH on 2017/5/12.
 */

public class TreeSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private boolean mIsStop;

    private Paint mBluePaint, mRedPaint;

    private Tree mTree;
    //控件宽高
    private int mWidth, mHeight;
    //树中的最大值
    private int mMaxValue;

    public TreeSurfaceView(Context context) {
        super(context);
        init();
    }

    public TreeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TreeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                if(mTree != null) {
                    drawTree(mTree.getRootNode(), mTree.getMaxDegree());
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
     * 注意：递归中尽量减少参数的传递。否则栈内存会很快耗尽。
     * @param node 节点
     * @param maxDegree 最大层数
     */
    private void drawTree(TreeNode node, int maxDegree) {
        if(mIsStop) return;

        List<TreeNode> childNodes = node.getChildNodes();
        if(childNodes != null && childNodes.size() > 0) {
            for(int i=0; i<childNodes.size(); i++) {
                TreeNode childNode = childNodes.get(i);
                if(!childNode.isVisited()) {
                    mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                            1.0f * node.getDegree() / maxDegree * mHeight,
                            1.0f * childNode.getValue() / mMaxValue * mWidth,
                            1.0f * childNode.getDegree() / maxDegree * mHeight,
                            mBluePaint);
                } else {
                    mCanvas.drawLine(1.0f * node.getValue() / mMaxValue * mWidth,
                            1.0f * node.getDegree() / maxDegree * mHeight,
                            1.0f * childNode.getValue() / mMaxValue * mWidth,
                            1.0f * childNode.getDegree() / maxDegree * mHeight,
                            mRedPaint);
                }
                drawTree(childNode, maxDegree);
            }
        } else {
            return;
        }
    }

    /**
     * 设置树
     * @param tree
     */
    public void setTree(Tree tree) {
        mTree = tree;
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
