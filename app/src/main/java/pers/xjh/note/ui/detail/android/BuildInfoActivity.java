package pers.xjh.note.ui.detail.android;

import android.os.Build;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/28.
 */

public class BuildInfoActivity extends BaseActivity {

    private TextView mTextView;

    @Override
    protected int initContentView() {
        return R.layout.activity_build_info;
    }

    @Override
    protected void initView() {
        mTextView = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void start() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            String[] abis = Build.SUPPORTED_ABIS;
            StringBuilder abisStr = new StringBuilder();
            for(String s : abis) {
                abisStr.append(s + " ");
            }
            mTextView.setText("主板：" + Build.BOARD + "\n\n"
                    + "Android系统定制商：" + Build.BRAND + "\n\n"
                    + "CPU指令集：" + abisStr.toString() + "\n\n"
                    + "设备参数：" + Build.DEVICE + "\n\n"
                    + "显示器参数：" + Build.DISPLAY + "\n\n"
                    + "唯一编号：" + Build.FINGERPRINT + "\n\n"
                    + "硬件序列号：" + Build.SERIAL + "\n\n"
                    + "修订版本列表：" + Build.ID + "\n\n"
                    + "硬件制造商：" + Build.MANUFACTURER + "\n\n"
                    + "版本：" + Build.MODEL + "\n\n"
                    + "硬件名：" + Build.HARDWARE + "\n\n"
                    + "手机产品名：" + Build.PRODUCT + "\n\n"
                    + "描述Build的标签：" + Build.TAGS + "\n\n"
                    + "Builder类型：" + Build.TYPE + "\n\n"
                    + "当前开发代号：" + Build.VERSION.CODENAME + "\n\n"
                    + "源码控制版本号：" + Build.VERSION.INCREMENTAL + "\n\n"
                    + "版本字符串：" + Build.VERSION.RELEASE + "\n\n"
                    + "版本号：" + Build.VERSION.SDK_INT + "\n\n"
                    + "Host值：" + Build.HOST + "\n\n"
                    + "User名：" + Build.USER + "\n\n"
                    + "编译时间：" + sdf.format(Build.TIME));
        }
    }
}
