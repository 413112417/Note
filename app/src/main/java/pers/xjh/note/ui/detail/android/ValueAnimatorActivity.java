package pers.xjh.note.ui.detail.android;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/27.
 */

public class ValueAnimatorActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTextView;

    @Override
    protected int initContentView() {
        return R.layout.activity_value_animator;
    }

    @Override
    protected void initView() {
        mTextView = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
                animator.setTarget(mTextView);
                animator.setDuration(1000).start();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mTextView.setText(animation.getAnimatedValue() + "");
                    }
                });
                break;
        }
    }
}
