package pers.xjh.note.ui.detail.android;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xujunhui on 2017/7/28.
 */

public class FrameAnimationActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_frame_animation;
    }

    @Override
    protected void initView() {
        ImageView img = (ImageView) findViewById(R.id.img);
        ((AnimationDrawable) img.getDrawable()).start();
    }
}
