package pers.xjh.note.ui.detail.function;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import pers.xjh.note.R;
import pers.xjh.note.service.DownloadService;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.ImageDetailActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.FileUtil;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.RectProgressBar;

/**
 * Created by XJH on 2017/7/26.
 */

public class DownloadActivity extends BaseActivity {

    private EditText mEtUrl;

    private RectProgressBar mProgressBar;

    private Button mBtnDownload, mBtnView;

    private DownloadService.DownloadBinder mBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = ((DownloadService.DownloadBinder) service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Timer mTimer = new Timer();


    @Override
    protected int initContentView() {
        return R.layout.activity_download;
    }

    @Override
    protected void initView() {
        mEtUrl = (EditText) findViewById(R.id.et_url);
        mProgressBar = (RectProgressBar) findViewById(R.id.progress_bar);
        mBtnDownload = (Button) findViewById(R.id.btn_download);
        mBtnView = (Button) findViewById(R.id.btn_view);

        bindService(new Intent(DownloadActivity.this, DownloadService.class), mServiceConnection, BIND_AUTO_CREATE);

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = mEtUrl.getText().toString();
                if(!TextUtils.isEmpty(url)) {
                    mBinder.startDownload(url, "test.jpg");
                } else {
                    ToastUtil.show("URL不能为空!");
                }
            }
        });

        mBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this, ImageDetailActivity.class);
                intent.putExtra(Constant.KEY_IMAGE_URL, new String[] {FileUtil.getDownloadFile("test.jpg").getAbsolutePath()});
                intent.putExtra(Constant.KEY_SKIP_CACHE, true);
                DownloadActivity.this.startActivity(intent);
            }
        });

        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mProgressBar != null && mBinder != null) {
                    mProgressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mBinder.getProgress());
                        }
                    });
                }
            }
        }, 0, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
