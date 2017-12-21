package pers.xjh.note.ui.detail.android;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.UiUtil;

/**
 * 布局动画
 * Created by XJH on 2017/4/27.
 */

public class LayoutAnimationActivity extends BaseActivity implements View.OnClickListener {

    private ViewGroup mViewGroup1, mViewGroup2;

    @Override
    protected int initContentView() {
        return R.layout.activity_layout_animation;
    }

    @Override
    protected void initView() {
        mViewGroup1 = (ViewGroup) findViewById(R.id.ll_1);
        mViewGroup2 = (ViewGroup) findViewById(R.id.ll_2);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);

        //设置过渡动画
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1);
        sa.setDuration(300);
        //设置布局动画显示属性
        LayoutAnimationController lac = new LayoutAnimationController(sa, 0.5f);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //为ViewGroup设置布局动画
        mViewGroup2.setLayoutAnimation(lac);
        mViewGroup2.startLayoutAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                TextView tv1 = new TextView(this);
                tv1.setTextSize(UiUtil.dp2px(this,10));
                tv1.setTextColor(Color.BLACK);
                tv1.setText("测试");
                mViewGroup1.addView(tv1);
                break;
            case R.id.btn_2:
                TextView tv2 = new TextView(this);
                tv2.setTextSize(UiUtil.dp2px(this,10));
                tv2.setTextColor(Color.BLACK);
                tv2.setText("测试");
                mViewGroup2.addView(tv2);
                mViewGroup2.startLayoutAnimation();
                break;
        }
    }
}
