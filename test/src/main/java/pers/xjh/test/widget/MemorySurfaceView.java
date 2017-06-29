package pers.xjh.test.widget;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Debug;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJH on 2017/5/12.
 */

public class MemorySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable  {

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    private boolean mIsDrawing;

    private Paint mPaint, mTextPaint;

    private Context mContext;

    private ActivityManager mActivityManager;

    private List<Debug.MemoryInfo> mMemoryInfoList;

    private DecimalFormat mDecimalFormat;

    public MemorySurfaceView(Context context) {
        super(context);
        init();
    }

    public MemorySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MemorySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);

        mContext = getContext();
        mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

        mMemoryInfoList = new ArrayList<>();

        mDecimalFormat = new DecimalFormat("0.0");
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
            try {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = mActivityManager.getRunningAppProcesses().get(0);
                Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(new int[] {runningAppProcessInfo.pid});
                mMemoryInfoList.add(memoryInfo[0]);

                draw();
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if(mCanvas != null) {
                //在此处画图
                mCanvas.drawColor(Color.WHITE);

                int size = mMemoryInfoList.size();

                if(mMemoryInfoList != null && size > 0) {

                    int width = (int) (mCanvas.getWidth() / 100.0f);

                    mPaint.setShader(new LinearGradient(0, 0, 0, mCanvas.getHeight(), Color.RED, Color.BLUE, Shader.TileMode.CLAMP));

                    //以最新的程序总内存作为Y轴最大值
                    Debug.MemoryInfo infoNow = mMemoryInfoList.get(size - 1);


                    for(int i=0; i<size; i++) {
                        Debug.MemoryInfo info = mMemoryInfoList.get(size - 1 - i);
                        mCanvas.drawRect(mCanvas.getWidth()-i*width, mCanvas.getHeight() - info.getTotalPrivateDirty() * 1.0f / infoNow.getTotalPss() * mCanvas.getHeight(),
                                mCanvas.getWidth()-(i+1)*width, mCanvas.getHeight(), mPaint);
                    }

                    mCanvas.drawText(infoNow.getTotalPrivateDirty() + "KB/" + infoNow.getTotalPss() + "KB    "
                            +  mDecimalFormat.format(infoNow.getTotalPrivateDirty() * 100.0f / infoNow.getTotalPss()) + "%",
                            10, 30, mTextPaint);

                    if(width * size > mCanvas.getWidth()) {
                        mMemoryInfoList.remove(0);
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
}
