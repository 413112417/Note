package pers.xjh.note.ui.detail.android;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;


/**
 * Created by XJH on 2017/4/11.
 */

public class FingerprintActivity extends BaseActivity {

    private TextView tvMessage;

    private FingerprintManagerCompat manager;

    @Override
    protected int initContentView() {
        return R.layout.activity_fingerprint;
    }

    @Override
    protected void initView() {
        tvMessage = (TextView) findViewById(R.id.tv_msg);

        // 获取一个FingerPrintManagerCompat的实例
        manager = FingerprintManagerCompat.from(this);

        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.authenticate(null, 0, null, new MyCallBack(), null);
                tvMessage.setText("请验证指纹");
            }
        });
    }

    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息，不再监听指纹sensor
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Toast.makeText(FingerprintActivity.this, errString, Toast.LENGTH_SHORT).show();
            tvMessage.setText("点击开始验证指纹");
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(FingerprintActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Toast.makeText(FingerprintActivity.this, helpString, Toast.LENGTH_SHORT).show();
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult
                                                      result) {
            Toast.makeText(FingerprintActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
            tvMessage.setText("点击开始验证指纹");
        }
    }

}
