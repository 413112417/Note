package pers.xjh.note.ui.detail.android;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 逐帧动画
 * Created by XJH on 2017/7/28.
 */

public class FrameAnimationActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_frame_animation;
    }

    @Override
    protected void initView() {
        ImageView img = (ImageView) findViewById(R.id.img);
        final AnimationDrawable animationDrawable = ((AnimationDrawable) img.getDrawable());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animationDrawable.isRunning()) {
                    animationDrawable.stop();
                } else {
                    animationDrawable.start();
                }
            }
        });
        animationDrawable.start();
    }
}
