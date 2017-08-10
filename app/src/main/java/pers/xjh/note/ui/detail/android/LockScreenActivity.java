package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.runtime.AppRuntime;
import pers.xjh.note.ui.note.NoteActivity;

/**
 * Created by XJH on 2017/4/17.
 */

public class LockScreenActivity extends AppCompatActivity implements View.OnClickListener {

    //二维码扫描
    private ImageView mImaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        //锁屏时显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_lock_screen);
        initView();
    }

    private void initView() {
        mImaView = (ImageView) findViewById(R.id.img);
        mImaView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img:
                //以SingleTask模式启动
                AppRuntime.startActivity(NoteActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                break;
        }
    }
}
