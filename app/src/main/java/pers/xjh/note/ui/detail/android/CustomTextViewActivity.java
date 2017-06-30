package pers.xjh.note.ui.detail.android;

import android.graphics.Color;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;

/**
 * 自定义的TextView
 * Created by XJH on 2017/4/5.
 */

public class CustomTextViewActivity extends BaseActivity implements ViewSwitcher.ViewFactory {

    private TextSwitcher mTextSwitcher;

    private Random mRandom = new Random();

    private boolean mRunning = true;

    @Override
    protected int initContentView() {
        return R.layout.activity_custom_textview;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv).setSelected(true);
        mTextSwitcher = (TextSwitcher) findViewById(R.id.text_switcher);
        mTextSwitcher.setFactory(this);
        mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_in));
        mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (mRunning) {
                        Thread.sleep(1000);
                        CustomTextViewActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextSwitcher.setText(String.valueOf(mRandom.nextInt()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRunning = false;
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setTextSize(36);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}
