package pers.xjh.note.ui.detail.android;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 视图动画
 * Created by XJH on 2017/4/27.
 */

public class SVGAnimationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageView1, mImageView2;

    @Override
    protected int initContentView() {
        return R.layout.activity_svg_animation;
    }

    @Override
    protected void initView() {
        mImageView1 = (ImageView) findViewById(R.id.img_1);
        mImageView2 = (ImageView) findViewById(R.id.img_2);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                ((Animatable) mImageView1.getDrawable()).start();
                break;
            case R.id.btn_2:
                ((Animatable) mImageView2.getDrawable()).start();
                break;
        }
    }
}
