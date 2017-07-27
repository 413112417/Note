package pers.xjh.note.ui.detail.function;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by xjh on 17-7-27.
 */

public class QRCodeResultActivity extends BaseActivity {

    private TextView mTvContent;
    //文字
    private String mString;

    @Override
    protected int initContentView() {
        return R.layout.activity_qr_code_result;
    }

    @Override
    protected void getIntentData(Intent intent) {
        mString = intent.getStringExtra(Constant.KEY_STRING);
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitle("扫描结果");
        titleBar.setTitleRight("复制");
        titleBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mString)) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", mString);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.show("已复制到剪切板");
                }
            }
        });
    }

    @Override
    protected void initView() {
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void start() {
        if(!TextUtils.isEmpty(mString)) {
            mTvContent.setText(mString);
        }
    }
}
