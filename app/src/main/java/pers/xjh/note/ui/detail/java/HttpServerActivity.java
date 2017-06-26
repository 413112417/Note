package pers.xjh.note.ui.detail.java;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.WebViewActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.server.HttpConfig;
import pers.xjh.server.HttpServer;

/**
 * Created by XJH on 2017/5/25.
 */

public class HttpServerActivity extends BaseActivity implements View.OnClickListener {

    private HttpConfig mHttpConfig;

    private HttpServer mHttpServer;

    private Button mBtnStartStop;

    private boolean mIsEnable;

    @Override
    protected int initContentView() {
        return R.layout.activity_http_server;
    }

    @Override
    protected void initView() {
        mBtnStartStop = (Button) findViewById(R.id.btn_start_stop);
        mBtnStartStop.setOnClickListener(this);

        findViewById(R.id.btn_open).setOnClickListener(this);

        mHttpConfig = new HttpConfig();
        mHttpConfig.setPort(8088);
        mHttpConfig.setMaxParallels(5);
        mHttpServer = new HttpServer(mHttpConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHttpServer != null) {
            try {
                mHttpServer.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_stop:
                if(!mIsEnable) {
                    mHttpServer.start();
                    mIsEnable = true;
                    mBtnStartStop.setText("停止服务");
                } else {
                    try {
                        mHttpServer.stop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mIsEnable = false;
                    mBtnStartStop.setText("开启服务");
                }
                break;
            case R.id.btn_open:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Constant.KEY_WEB_URL, "http://localhost:" + mHttpConfig.getPort());
                startActivity(intent);
                break;
        }
    }
}
