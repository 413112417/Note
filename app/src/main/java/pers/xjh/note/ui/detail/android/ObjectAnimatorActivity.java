package pers.xjh.note.ui.detail.android;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.ObjectAnimatorBean;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/27.
 */

public class ObjectAnimatorActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5;

    private ObjectAnimator mAnimator;

    private ObjectAnimatorBean mObjectAnimatorBean;

    @Override
    protected int initContentView() {
        return R.layout.activity_object_animator;
    }

    @Override
    protected void initView() {
        mTextView1 = (TextView) findViewById(R.id.tv_a);
        mTextView2 = (TextView) findViewById(R.id.tv_b);
        mTextView3 = (TextView) findViewById(R.id.tv_c);
        mTextView4 = (TextView) findViewById(R.id.tv_d);
        mTextView5 = (TextView) findViewById(R.id.tv_5);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);

        mObjectAnimatorBean = new ObjectAnimatorBean();
        mObjectAnimatorBean.setOnValueChangeListener(new ObjectAnimatorBean.OnValueChangeListener() {
            @Override
            public void onValueChanged(int value) {
                mTextView2.setText(value + "");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mAnimator = ObjectAnimator.ofFloat(mTextView1, "translationX", 300);
                mAnimator.setDuration(300);
                mAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mTextView1.setText("开始");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mTextView1.setText("结束");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mAnimator.start();
                break;
            case R.id.btn_2:
                mAnimator = ObjectAnimator.ofInt(mObjectAnimatorBean, "objectValue", 300);
                mAnimator.setDuration(3000);
                mAnimator.start();
                break;
            case R.id.btn_3:
                PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 300f);
                PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
                PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
                ObjectAnimator.ofPropertyValuesHolder(mTextView3, pvh1, pvh2, pvh3).setDuration(1000).start();
                break;
            case R.id.btn_4:
                ObjectAnimator oa1 = ObjectAnimator.ofFloat(mTextView4, "translationX", 300f);
                ObjectAnimator oa2 = ObjectAnimator.ofFloat(mTextView4, "scaleX", 1f, 0, 1f);
                ObjectAnimator oa3 = ObjectAnimator.ofFloat(mTextView4, "scaleY", 1f, 0, 1f);

                AnimatorSet as = new AnimatorSet();
                as.setDuration(1000);
                as.playTogether(oa1, oa2, oa3);
                as.start();
                break;
            case R.id.btn_5:
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scalex);
                animator.setTarget(mTextView5);
                animator.start();
                break;
        }
    }
}
