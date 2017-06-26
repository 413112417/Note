package pers.xjh.note.widget.viewLifeCycle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by XJH on 2017/4/18.
 */

public class LifeCycleView extends View {

    private int index = 0;

    public LifeCycleView(Context context) {
        super(context);
        Toast.makeText(getContext(), ++index + ":View构造函数", Toast.LENGTH_SHORT).show();
    }

    public LifeCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Toast.makeText(getContext(), ++index + ":View构造函数", Toast.LENGTH_SHORT).show();
    }

    public LifeCycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Toast.makeText(getContext(), ++index + ":View构造函数", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Toast.makeText(getContext(), ++index + ":onDraw", Toast.LENGTH_SHORT).show();
    }
}
