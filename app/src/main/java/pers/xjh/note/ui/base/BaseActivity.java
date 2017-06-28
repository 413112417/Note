package pers.xjh.note.ui.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import pers.xjh.note.R;
import pers.xjh.note.application.NoteApplication;
import pers.xjh.note.runtime.RtEnv;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.GlideRoundTransform;
import pers.xjh.note.widget.SlidingFinishLayout;
import pers.xjh.note.widget.TitleBar;
import pers.xjh.note.widget.dialog.AlertDialog;
import pers.xjh.zxing.ZApplication;

/**
 * Activity基类
 * Created by XJH on 2017/3/16.
 */

public abstract class BaseActivity extends AppCompatActivity  {

    protected TitleBar mTitleBar;

    protected String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RtEnv.put(Constant.RT_CURRENT_ACTIVITY, this);

        getSupportActionBar().hide();

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(0, 176, 255));
        }

        setContentView(R.layout.activity_base);

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

    @Override
    protected void onResume() {
        super.onResume();
        RtEnv.put(Constant.RT_CURRENT_ACTIVITY, this);
    }

    /** 界面初始化，返回布局id即可 */
    protected abstract int initContentView();

    /** 从Intent中获取数据 */
    protected void getIntentData() {}

    /**
     * 设置标题
     */
    protected void initTitle() {
        mTitle = getIntent().getStringExtra(Constant.KEY_TITLE);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setTitle(mTitle);
    }

    /** 控件初始化 */
    protected abstract void initView();

    /** 初始化完成后的处理 */
    protected void start() {}

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示图片
     */
    protected void showImage(ImageView imageView, int resourceId) {
        Glide.with(this).load(resourceId).into(imageView);
    }

    /**
     * 显示图片
     */
    protected void showImage(ImageView imageView, String path) {
        Glide.with(this).load(path).into(imageView);
    }

    /**
     * 显示圆角图片
     */
    protected void showImageAsRound(ImageView imageView, String path) {
        Glide.with(this).load(path).transform(new GlideRoundTransform(this)).into(imageView);
    }

    /**
     * 显示动态图片
     */
    protected void showImageAsGif(ImageView imageView, String path) {
        Glide.with(this).load(path).asGif().into(imageView);
    }

    /**
     * 显示动态图片
     */
    protected void showImageAsGif(ImageView imageView, int resourceId) {
        Glide.with(this).load(resourceId).asGif().into(imageView);
    }

    /**
     * 显示图片，跳过缓存
     */
    protected void showImageSkipCache(ImageView imageView, int resourceId) {
        Glide.with(this).load(resourceId).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * 显示图片，跳过缓存
     */
    protected void showImageSkipCache(ImageView imageView, String path) {
        Glide.with(this).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * 显示消息对话框
     * @param message
     */
    protected void showMsgDialog(String message) {
        showMsgDialog(null, message);
    }

    /**
     * 显示消息对话框
     * @param message
     */
    protected void showMsgDialog(String title, String message) {
        showMsgDialog(title, message, true, null, null, null, null);
    }

    /**
     * 显示错误对话框
     * @param message
     */
    protected void showErrorDialog(String message) {
        showMsgDialog("错误", message, true, null, null, null, null);
    }

    /**
     * 显示消息对话框
     * @param message
     */
    protected void showMsgDialog(String title, String message, boolean cancelable,
                                 String positiveStr, AlertDialog.OnClickListener positiveListener,
                                 String negativeStr, AlertDialog.OnClickListener negativeListener) {
        if(!TextUtils.isEmpty(title) || !TextUtils.isEmpty(message)) {
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setContent(message)
                    .setCancelable(cancelable)
                    .setPositiveButton(positiveStr, positiveListener)
                    .setNegativeButton(negativeStr, negativeListener)
                    .build().show();
        } else {
            return;
        }
    }
}
