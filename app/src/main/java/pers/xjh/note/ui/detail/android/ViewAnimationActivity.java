package pers.xjh.note.ui.detail.android;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 视图动画
 * Created by XJH on 2017/4/27.
 */

public class ViewAnimationActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTextView;

    private Animation mAnimation;

    @Override
    protected int initContentView() {
        return R.layout.activity_view_animation;
    }

    @Override
    protected void initView() {
        mTextView = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mAnimation = new AlphaAnimation(0, 1);
                mAnimation.setDuration(1000);
                mAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mTextView.setText("开始");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTextView.setText("结束");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mTextView.startAnimation(mAnimation);
                break;
            case R.id.btn_2:
                mAnimation = new RotateAnimation(0, 360, 100, 100);
                mAnimation.setDuration(1000);
                mTextView.startAnimation(mAnimation);
                break;
            case R.id.btn_3:
                mAnimation = new TranslateAnimation(0, 200, 0, 300);
                mAnimation.setDuration(1000);
                mTextView.startAnimation(mAnimation);
                break;
            case R.id.btn_4:
                mAnimation = new ScaleAnimation(0, 2, 0, 2);
                mAnimation.setDuration(1000);
                mTextView.startAnimation(mAnimation);
                break;
            case R.id.btn_5:
                AnimationSet as = new AnimationSet(true);
                as.setDuration(1000);

                AlphaAnimation aa = new AlphaAnimation(0, 1);
                aa.setDuration(1000);
                as.addAnimation(aa);

                TranslateAnimation ta = new TranslateAnimation(0, 100, 0, 200);
                ta.setDuration(1000);
                as.addAnimation(ta);

                mTextView.startAnimation(as);
                break;
        }
    }
}
