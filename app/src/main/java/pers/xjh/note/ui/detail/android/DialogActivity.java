package pers.xjh.note.ui.detail.android;

import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.dialog.AlertDialog;

/**
 * Created by XJH on 2017/5/12.
 */

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int initContentView() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                new AlertDialog.Builder(this).setTitle("测试对话框")
                        .setContent("1")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .build().show();
                break;
            case R.id.btn_2:
                showMsgDialog("测试对话框", 1 + "", false, null, null, "取消", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_3:
                showMsgDialog("测试对话框",1 + "");
                showMsgDialog("测试对话框2");
                showMsgDialog("测试对话框",3 + "");
                showMsgDialog("测试对话框",4 + "");
                showMsgDialog("测试对话框",5 + "");
                break;
            case R.id.btn_4:
                showMsgDialog("测试对话框",1 + "",false, null, null, "取消", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                showMsgDialog("测试对话框",2 + "");
                break;
            case R.id.btn_5:
                showMsgDialog("测试对话框",1 + "");
                showMsgDialog("测试对话框",2 + "", false, null, null, "取消", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                showMsgDialog("测试对话框",3 + "");
                showMsgDialog("测试对话框",4 + "", false, null, null, "取消", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                showMsgDialog("测试对话框",5 + "");
                break;
        }
    }
}
