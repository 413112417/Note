package pers.xjh.note.ui.detail.android;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import pers.xjh.note.R;
import pers.xjh.note.runtime.RunTime;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.widget.SlidingFinishLayout;

/**
 * Created by XJH on 2017/6/12.
 */

public class SlidingFinishActivity extends AppCompatActivity {

    private SlidingFinishLayout mSlidingFinishLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunTime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));

        getSupportActionBar().hide();

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        setContentView(R.layout.activity_sliding_finish_layout);

        mSlidingFinishLayout = (SlidingFinishLayout) findViewById(R.id.sliding_finish_layout);
        mSlidingFinishLayout.setOnSlidingFinishListener(new SlidingFinishLayout.OnSlidingFinishListener() {
            @Override
            public void onSlidingFinish() {
                SlidingFinishActivity.this.finish();
                overridePendingTransition(0, R.anim.right_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RunTime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));
    }
}
