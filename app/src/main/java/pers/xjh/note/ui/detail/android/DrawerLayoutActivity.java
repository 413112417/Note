package pers.xjh.note.ui.detail.android;

import android.support.v4.widget.DrawerLayout;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-7-6.
 */

public class DrawerLayoutActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_drawer_layout;
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}
