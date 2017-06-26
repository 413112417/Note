package pers.xjh.note.ui.detail.android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.animation.CameraAnimation;
import pers.xjh.note.animation.TVTurnOffAnimation;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/27.
 */

public class CustomAnimationActivity extends BaseActivity implements View.OnClickListener {

    private ViewGroup mViewGroup;

    private TextView mTextView;

    @Override
    protected int initContentView() {
        return R.layout.activity_custom_animation;
    }

    @Override
    protected void initView() {
        mViewGroup = (ViewGroup) findViewById(R.id.ll_root);
        mTextView = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mViewGroup.startAnimation(new TVTurnOffAnimation());
                break;
            case R.id.btn_2:
                mTextView.startAnimation(new CameraAnimation());
                break;
        }
    }
}
