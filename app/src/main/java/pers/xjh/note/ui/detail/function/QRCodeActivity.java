package pers.xjh.note.ui.detail.function;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.note.NoteTextActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.zxing.activity.CaptureFragment;
import pers.xjh.zxing.activity.CodeUtils;

/**
 * 二维码扫描
 * Created by XJH on 2017/4/11.
 */

public class QRCodeActivity extends BaseActivity {

    private CaptureFragment captureFragment;

    @Override
    protected int initContentView() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initView() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.view_qr_code_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    protected void start() {

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent intent = new Intent(QRCodeActivity.this, QRCodeResultActivity.class);
            intent.putExtra(Constant.KEY_STRING, result);
            QRCodeActivity.this.startActivity(intent);
            QRCodeActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtil.show("解析异常");
        }
    };
}
