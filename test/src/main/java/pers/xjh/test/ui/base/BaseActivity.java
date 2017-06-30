package pers.xjh.test.ui.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import pers.xjh.test.R;
import pers.xjh.test.widget.TitleBar;


/**
 * Activity基类
 * Created by XJH on 2017/3/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected TitleBar mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        setContentView(R.layout.test_activity_base);
        //界面初始化
        LayoutInflater.from(this).inflate(initContentView(), (ViewGroup) findViewById(R.id.content_container));
        //从Intent中获取数据
        getIntentData();
        //初始化标题
        initTitle();
        //控件初始化
        initView();
        //开始处理
        start();
    }

    /** 界面初始化，返回布局id即可 */
    protected abstract int initContentView();

    /** 从Intent中获取数据 */
    protected void getIntentData() {}

    /**
     * 设置标题
     */
    protected void initTitle() {
        mTitleBar = (TitleBar) findViewById(R.id.test_title_bar);
    }

    /** 控件初始化 */
    protected abstract void initView();

    /** 初始化完成后的处理 */
    protected void start() {}
}
