package pers.xjh.note.ui.detail.test;

import android.opengl.*;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arialyy.aria.core.Aria;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.RenderScriptTest;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.FileUtil;

/**
 * 测试专用
 * Created by xjh on 17-10-16.
 */

public class TestActivity extends BaseActivity {

    private Button mBtnTest;

    @Override
    protected int initContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               test();
            }
        });

        Aria.download(this).register();
    }

    private void test() {
        Aria.download(this)
                .load("http://a.hiphotos.baidu.com/image/pic/item/0824ab18972bd407bd49abdc77899e510fb30900.jpg")     //读取下载地址
                .setDownloadPath(FileUtil.getDownloadFile("test2.jpg").getAbsolutePath()) //设置文件保存的完整路径
                .start();


    }
}
