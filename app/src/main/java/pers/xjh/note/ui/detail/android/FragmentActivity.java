package pers.xjh.note.ui.detail.android;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import pers.xjh.note.R;
import pers.xjh.note.runtime.Runtime;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.fragment.AFragment;
import pers.xjh.note.ui.detail.android.fragment.BFragment;
import pers.xjh.note.ui.detail.android.fragment.CFragment;
import pers.xjh.note.ui.detail.android.fragment.DFragment;
import pers.xjh.note.ui.detail.android.fragment.NestFragment;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/5/18.
 */

public class FragmentActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;

    private Fragment mAFragment, mBFragment, mCFragment,mDFragment;

    private String className = this.getClass().getSimpleName() + ":";

    @Override
    protected int initContentView() {
        Toast.makeText(getApplicationContext(), className + "onCreate", Toast.LENGTH_SHORT).show();
        return R.layout.activity_fragment;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitleRight("图片");
        titleBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentActivity.this, ImageDetailActivity.class);
                intent.putExtra(Constant.KEY_IMAGE_URL, new int[] {R.drawable.fragment});
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mFragmentManager = getSupportFragmentManager();

        findViewById(R.id.tv_a).setOnClickListener(this);
        findViewById(R.id.tv_b).setOnClickListener(this);
        findViewById(R.id.tv_c).setOnClickListener(this);
        findViewById(R.id.tv_d).setOnClickListener(this);

        mAFragment = new NestFragment();
        mBFragment = new BFragment();
        mCFragment = new CFragment();
        mDFragment = new DFragment();
    }

    @Override
    protected void start() {
        mFragmentManager.beginTransaction().replace(R.id.content, mAFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a:
                mFragmentManager.beginTransaction().replace(R.id.content, mAFragment).commit();
                break;
            case R.id.tv_b:
                mFragmentManager.beginTransaction().replace(R.id.content, mBFragment).commit();
                break;
            case R.id.tv_c:
                mFragmentManager.beginTransaction().replace(R.id.content, mCFragment).commit();
                break;
            case R.id.tv_d:
                mFragmentManager.beginTransaction().replace(R.id.content, mDFragment).commit();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), className + "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Runtime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));
        Toast.makeText(getApplicationContext(), className + "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), className + "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), className + "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), className + "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(), className + "onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(getApplicationContext(), className + "onConfigurationChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(getApplicationContext(), className + "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getApplicationContext(), className + "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }
}
