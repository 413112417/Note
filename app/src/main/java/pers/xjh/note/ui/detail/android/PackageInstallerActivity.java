package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/4.
 */

public class PackageInstallerActivity extends BaseActivity {

    private Uri mPackageUri;

    private PackageManager mPm;

    private PackageInfo mPackageInfo;

    @Override
    protected int initContentView() {
        return R.layout.activity_package_installer;
    }

    @Override
    protected void getIntentData(Intent intent) {
        mPackageUri = intent.getData();
        mPm = getPackageManager();
//        mPackageInfo = PackageUtil.getPackageInfo(mPackageURI);
    }

    @Override
    protected void initView() {

    }
}
