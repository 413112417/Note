package pers.xjh.note.widget.viewLifeCycle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import pers.xjh.note.runtime.RunTime;

/**
 * Created by XJH on 2017/4/18.
 */

public class LifeCycleFrameLayout extends FrameLayout {

    private int index = 0;

    public LifeCycleFrameLayout(Context context) {
        super(context);
        Toast.makeText(RunTime.getApplication(), ++index + ":FrameLayout构造函数", Toast.LENGTH_SHORT).show();
    }

    public LifeCycleFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Toast.makeText(RunTime.getApplication(), ++index + ":FrameLayout构造函数", Toast.LENGTH_SHORT).show();
    }

    public LifeCycleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Toast.makeText(RunTime.getApplication(), ++index + ":FrameLayout构造函数", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Toast.makeText(RunTime.getApplication(), ++index + ":onFinishInflate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Toast.makeText(RunTime.getApplication(), ++index + ":onMeasure", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Toast.makeText(RunTime.getApplication(), ++index + ":onSizeChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Toast.makeText(RunTime.getApplication(), ++index + ":onLayout", Toast.LENGTH_SHORT).show();
    }
}
