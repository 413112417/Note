package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/5/5.
 */

public class OverdrawActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_over_draw;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        mTitleBar.setTitleRight("设置");
        mTitleBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转开发人员选项
                Intent intent =  new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {

    }
}
