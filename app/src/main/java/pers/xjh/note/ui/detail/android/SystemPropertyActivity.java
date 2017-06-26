package pers.xjh.note.ui.detail.android;

import android.os.Build;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/28.
 */

public class SystemPropertyActivity extends BaseActivity {

    private TextView mTextView;

    @Override
    protected int initContentView() {
        return R.layout.activity_system_property;
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
            mTextView.setText("OS版本：" + System.getProperty("os.version") + "\n\n"
                    + "OS名称：" + System.getProperty("os.name") + "\n\n"
                    + "OS架构：" + System.getProperty("os.arch") + "\n\n"
                    + "Home属性：" + System.getProperty("user.home") + "\n\n"
                    + "Name属性：" + System.getProperty("user.name") + "\n\n"
                    + "Dir属性：" + System.getProperty("user.dir") + "\n\n"
                    + "时区：" + System.getProperty("user.timezone") + "\n\n"
                    + "路径分隔符：" + System.getProperty("path.separator") + "\n\n"
                    + "行分隔符：" + System.getProperty("line.separator") + "\n\n"
                    + "文件分隔符：" + System.getProperty("file.separator") + "\n\n"
                    + "Java 版本：" + System.getProperty("java.version") + "\n\n"
                    + "Java Home 属性：" + System.getProperty("java.home") + "\n\n"
                    + "Java Class 版本：" + System.getProperty("java.class.version") + "\n\n"
                    + "Java Class 路径：" + System.getProperty("java.class.path") + "\n\n"
                    + "Java Vendor：" + System.getProperty("java.vendor") + "\n\n"
                    + "Java vendor URL属性：" + System.getProperty("java.vendor.url"));
        }
    }
}
