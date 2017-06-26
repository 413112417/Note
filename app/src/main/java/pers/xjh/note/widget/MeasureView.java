package pers.xjh.note.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * View的测量
 * Created by XJH on 2017/4/2.
 */

public class MeasureView extends View {

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 若不重写onMeasure，则wrap_content和match_parent效果一样
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 测量宽度
     */
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        //ScrollView的子View传入的Mode为UNSPECIFIED
        if(specMode == MeasureSpec.UNSPECIFIED || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if(specMode == MeasureSpec.AT_MOST) {
            result = Math.min(200, specSize); //此处计算出这个控件在warp_content时候需要的大小，这里就直接写200.
        }
        return result;
    }

    /**
     * 测量高度
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.UNSPECIFIED || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if(specMode == MeasureSpec.AT_MOST) {
            result = Math.min(200, specSize); //此处计算出这个控件在warp_content时候需要的大小，这里就直接写200.
        }
        return result;
    }
}
