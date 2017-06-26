package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * SurfaceView模版
 * Created by XJH on 2017/4/26.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    public SurfaceViewTemplate(Context context) {
        super(context);
        init();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setKeepScreenOn(true);
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
            draw();
        }
    }

    private void draw() {
        try {
            //得到画布
            mCanvas = mHolder.lockCanvas();
            if(mCanvas != null) {
                //在此处画图
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(mCanvas != null) {
                //用同步锁防止在执行unlockCanvasAndPost()方法过程中mCanvas对象变为null，导致异常。
                synchronized (mCanvas) {
                    if(mCanvas != null) {
                        //若unlockCanvasAndPost()没有执行，下一次lockCanvas()就会阻塞。
                        //因为lockCanvas()用到了一个锁。在执行unlockCanvasAndPost()方法后会释放。
                        //若lockCanvas()阻塞，会导致surfaceDestroyed延迟执行。
                        //surfaceDestroyed延迟执行,mIsDrawing一直为true，会导致循环调用lockCanvas()。
                        //此时会阻塞主线程。会导致activity的onStop和onDestroy阻塞。
                        //解决办法是，在onPause中手动设置mIsDrawing为false，这样就会退出while循环。
                        mHolder.unlockCanvasAndPost(mCanvas); //提交绘制内容
                    }
                }
            }
        }
    }
}
