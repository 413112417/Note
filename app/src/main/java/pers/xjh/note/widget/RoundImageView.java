package pers.xjh.note.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import pers.xjh.note.R;

/**
 * 圆角图片
 * Created by XJH on 2017/4/26.
 */

public class RoundImageView extends View {

    private Bitmap mBitmap;

    private Paint mPaint;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.xihu);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    /**
     * 获得圆角的bitmap
     * @return
     */
    private Bitmap getRoundBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), 80, 80, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //为什么这样无效？
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), 80, 80, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }*/
        canvas.drawBitmap(getRoundBitmap(), 0, 0, null);
    }
}
