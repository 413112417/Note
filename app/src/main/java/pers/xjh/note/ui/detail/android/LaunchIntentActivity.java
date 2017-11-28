package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-11-28.
 */

public class LaunchIntentActivity extends BaseActivity {


    @Override
    protected int initContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                startActivity(intent);
            }
        });
    }
}
