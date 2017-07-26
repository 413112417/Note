package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * 头像裁剪的ImageView
 * Created by xjh on 2017/1/18.
 */
public class ClipImageView extends ImageView {
    /** 图片的状态：正常，放大 */
    private enum MODE {
        NORMAL, ZOOM
    }
    /** 默认状态为正常 */
    private MODE mMode = MODE.NORMAL;
    /** 正常状态下的缩放比例 */
    private float mNormalScale;
    /** 最大缩放比例 */
    private float mMaxScale = 2.0f;
    /** 最小缩放比例 */
    private float mMinScale = 0.5f;
    /** 操作图片的矩阵 */
    private Matrix mMatrix;
    /** 用于存放矩阵的9个值 */
    private final float[] mMatrixValues = new float[9];
    /** 点击的点的坐标 */
    private float mDownX, mDownY;
    /** 拖动时上次的坐标 */
    private float mLastX, mLastY;
    /** 放大的中心坐标 */
    private float mZoomX, mZoomY;
    /** 缩放的手势检测 */
    private ScaleGestureDetector mScaleGestureDetector;
    /** 用于双击检测 */
    private GestureDetector mDoubleClickGestureDetector;
    /** 当前是否处于自动缩放状态中 */
    private boolean mIsAutoScale;
    /** 缩放速度 */
    private int mScaleSpeed = 5;
    /** 记录左边、右边是否接触到边界 */
    private boolean mIsLeftTouched, mIsRightTouched;
    /** 标识图片长宽比相对于屏幕是更宽还是更长 */
    private boolean mIsFlat;
    /** 水平方向的padding */
    private int mHorizontalPadding;
    /** 垂直方向的padding */
    private int mVerticalPadding;
    /** 画阴影的画笔对象 */
    private Paint mPaintShadow;
    /** 画矩形的画笔对象 */
    private Paint mPaintRect;

    public ClipImageView(Context context) {
        this(context, null);
    }

    public ClipImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //设置ScaleType为ScaleType.MATRIX
        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();
        mDoubleClickGestureDetector = new GestureDetector(context, onDoubleListener);
        mScaleGestureDetector = new ScaleGestureDetector(context, mOnScaleListener);

        //设置阴影画笔颜色，用与画阴影部分
        mPaintShadow = new Paint();
        mPaintShadow.setColor(Color.argb(150,0,0,0));

        //设置画矩形的画笔
        mPaintRect = new Paint();
        mPaintRect.setStrokeWidth(1);
        mPaintRect.setStyle(Paint.Style.STROKE);
        mPaintRect.setColor(Color.WHITE);
    }

    /**
     * 双击监听器
     */
    private GestureDetector.SimpleOnGestureListener onDoubleListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            float x = e.getX();
            float y = e.getY();

            RectF rectF = getMatrixRectF();

            switch (mMode) {
                case NORMAL:
                    mMode = MODE.ZOOM;
                    if (rectF.contains(x, y) && getScale() < mNormalScale * mMaxScale) {
                        mIsAutoScale = true;
                        ClipImageView.this.post(new ScaleTask(mNormalScale * mMaxScale, x, y));
                    }
                    break;
                case ZOOM:
                    mMode = MODE.NORMAL;
                    if (rectF.contains(x, y) && getScale() > mNormalScale) {
                        mIsAutoScale = true;
                        ClipImageView.this.post(new ScaleTask(mNormalScale));
                    }
                    break;
            }
            return true;
        }
    };

    /**
     * 缩放手势监听
     */
    private ScaleGestureDetector.OnScaleGestureListener mOnScaleListener = new ScaleGestureDetector.OnScaleGestureListener() {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            if((getScale() >= mNormalScale * mMaxScale && scaleFactor > 1.0f)
                    || (getScale() < mNormalScale * mMinScale && scaleFactor < 1.0f)) {
                return false;
            }
            mMode = MODE.ZOOM;
            //记录放大时的中心位置
            mZoomX = detector.getFocusX();
            mZoomY = detector.getFocusY();
            if(mIsFlat) {
                mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), getHeight() / 2);
            } else {
                mMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, detector.getFocusY());
            }
            setImageMatrix(mMatrix);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        moveToCenter();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if(width == 0 || height == 0) {
            return;
        }

        //计算间距
        mHorizontalPadding = width / 10;
        int side = width - mHorizontalPadding * 2;
        mVerticalPadding = (height - side) / 2;

        //画出阴影区域
        canvas.drawRect(0, 0, width, mVerticalPadding, mPaintShadow);
        canvas.drawRect(0, height - mVerticalPadding, width, height, mPaintShadow);
        canvas.drawRect(0, mVerticalPadding, mHorizontalPadding, height - mVerticalPadding, mPaintShadow);
        canvas.drawRect(width - mHorizontalPadding, mVerticalPadding, width, height - mVerticalPadding, mPaintShadow);

        //画出中间白色框
        canvas.drawRect(mHorizontalPadding, mVerticalPadding, width - mHorizontalPadding, height - mVerticalPadding, mPaintRect);
    }

    /**
     * 剪切图片，返回剪切后的bitmap对象
     * @return
     */
    public Bitmap clip()
    {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        //这个“1”是为了去掉白色框部分
        return Bitmap.createBitmap(bitmap, mHorizontalPadding + 1,
                mVerticalPadding + 1, getWidth() - 2 * mHorizontalPadding - 1,
                getWidth() - 2 * mHorizontalPadding - 1);
    }

    /**
     * 将图片移动到中心位置
     */
    private void moveToCenter() {
        //控件的宽高
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        Drawable drawable = getDrawable();

        if(drawable == null) {
            return;
        }

        //获取图片宽高
        float imageWidth = drawable.getIntrinsicWidth();
        float imageHeight = drawable.getIntrinsicHeight();

        //设置缩放比例和平移距离，使图片居中
        float scale, scaleX, scaleY, translateX, translateY;

        scaleX = width / imageWidth;
        scaleY = height / imageHeight;
        scale = scaleX > scaleY ? scaleY : scaleX;
        //记录图片相对于屏幕是更宽还是更长，用于缩放中心点的选择
        mIsFlat = scaleX > scaleY ? false : true;

        if(scaleX < scaleY) {
            translateX = 0;
            translateY = (height - imageHeight * scale) / 2;
        } else {
            translateX = (width - imageWidth * scale) / 2;
            translateY = 0;
        }
        mMatrix.setScale(scale, scale);
        mMatrix.postTranslate(translateX, translateY);
        setImageMatrix(mMatrix);
        //获取进行缩放后的比例作为正常比例
        mNormalScale = getScale();
    }

    /**
     * 缩放任务
     */
    private class ScaleTask implements Runnable {

        //缩放的目标值
        private float targetScale;
        //每次放大缩小的比例
        private float BIGGER = 1.08f;
        private float SMALLER = 0.92f;

        //放大时，记录点击位置，根据点击位置，计算放大中心位置
        public ScaleTask(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            if(mMode == MODE.ZOOM) {
                mZoomX = x;
                mZoomY = y;
            }
        }
        //缩小时不需要记录点击位置，缩小中心点根据放大中心点位置，加上平移计算得出
        public ScaleTask(float targetScale) {
            this.targetScale = targetScale;
        }

        @Override
        public void run() {
            float currentScale = getScale();
            if(currentScale < targetScale && mMode == MODE.ZOOM) {
                if(mIsFlat) {
                    mMatrix.postScale(BIGGER, BIGGER, mZoomX, (mZoomY + getHeight() / 2) / 2);
                } else {
                    mMatrix.postScale(BIGGER, BIGGER, (mZoomX + getWidth() / 2) / 2, mZoomY);
                }
                setImageMatrix(mMatrix);
                ClipImageView.this.postDelayed(this, mScaleSpeed);
            } else if(currentScale > targetScale + 0.1 && mMode == MODE.NORMAL) {
                if(mIsFlat) {
                    mMatrix.postScale(SMALLER, SMALLER, mZoomX, (mZoomY + getHeight() / 2) / 2);
                } else {
                    mMatrix.postScale(SMALLER, SMALLER, (mZoomX + getWidth() / 2) / 2, mZoomY);
                }
                setImageMatrix(mMatrix);
                ClipImageView.this.postDelayed(this, mScaleSpeed);
            } else if(mMode == MODE.NORMAL) {
                moveToCenter();
                mIsAutoScale = false;
            } else {
                mIsAutoScale = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果正在执行自动缩放则不进行任何操作
        if(mIsAutoScale) {
            return false;
        }
        //先执行双击和缩放事件
        if(mDoubleClickGestureDetector.onTouchEvent(event)) {
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mLastX = mDownX;
                mLastY = mDownY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsAutoScale = false;
                break;
            case MotionEvent.ACTION_MOVE:
                RectF rectF = getMatrixRectF();
                float moveX = event.getX() - mLastX;
                float moveY = event.getY() - mLastY;

                mMatrix.postTranslate(moveX, moveY);
                setImageMatrix(mMatrix);
                mLastX = event.getX();
                mLastY = event.getY();
                //改变放大中心坐标，使得缩小更平滑
                mZoomX -= moveX * getWidth() / (rectF.width() - getWidth());
                mZoomY -= moveY * getHeight() / (rectF.height() - getHeight());
                break;
        }
        return true;
    }

    /**
     * 在dispatchTouchEvent中判断是否允许父控件拦截事件分发
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                if(getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                //按下时记录左右边是否顶到边界
                RectF rectF = getMatrixRectF();
                if(rectF.left >= 0) {
                    mIsLeftTouched = true;
                }
                if(rectF.right <= getWidth()) {
                    mIsRightTouched = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //当左边顶到边界，并且是向左边移动的时候，就把这次事件交给父控件处理。(右边同理)
                rectF = getMatrixRectF();
                if((rectF.left >= 0 && event.getX() > mDownX && mIsLeftTouched) && getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if((rectF.right <= getWidth() && event.getX() < mDownX) && mIsRightTouched && getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                mIsLeftTouched = false;
                mIsRightTouched = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 获得当前的缩放比例
     */
    public final float getScale() {
        mMatrix.getValues(mMatrixValues);
        return mMatrixValues[Matrix.MSCALE_X];
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     */
    private RectF getMatrixRectF() {
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mMatrix.mapRect(rect);
        }
        return rect;
    }
}
