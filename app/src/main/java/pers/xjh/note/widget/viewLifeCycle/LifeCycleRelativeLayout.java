package pers.xjh.note.widget.viewLifeCycle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by XJH on 2017/4/18.
 */

public class LifeCycleRelativeLayout extends RelativeLayout {

    private int index = 0;

    public LifeCycleRelativeLayout(Context context) {
        super(context);
        Toast.makeText(getContext(), ++index + ":RelativeLayout构造函数", Toast.LENGTH_SHORT).show();
    }

    public LifeCycleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Toast.makeText(getContext(), ++index + ":RelativeLayout构造函数", Toast.LENGTH_SHORT).show();
    }

    public LifeCycleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Toast.makeText(getContext(), ++index + ":RelativeLayout构造函数", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Toast.makeText(getContext(), ++index + ":onFinishInflate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Toast.makeText(getContext(), ++index + ":onMeasure", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Toast.makeText(getContext(), ++index + ":onSizeChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Toast.makeText(getContext(), ++index + ":onLayout", Toast.LENGTH_SHORT).show();
    }
}
