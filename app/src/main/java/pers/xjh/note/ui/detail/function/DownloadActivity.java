package pers.xjh.note.ui.detail.function;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import pers.xjh.network.HttpClient;
import pers.xjh.network.Response;
import pers.xjh.network.interfaces.ProcessCallback;
import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.ImageDetailActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.FileUtil;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.RectProgressBar;

/**
 * Created by xujunhui on 2017/7/26.
 */

public class DownloadActivity extends BaseActivity {

    private EditText mEtUrl;

    private RectProgressBar mProgressBar;

    private Button mBtnDownload, mBtnView;

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

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = mEtUrl.getText().toString();
                if(!TextUtils.isEmpty(url)) {
                    ThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            HttpClient.download(url, FileUtil.getDownloadFile("test.jpg"), new ProcessCallback() {
                                @Override
                                public void onProcess(final int progress) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(progress);
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(final Response response) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.show(response.getBodyString());
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(final Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.show(e.getMessage());
                                        }
                                    });
                                }
                            });
                        }
                    });
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
                DownloadActivity.this.startActivity(intent);
            }
        });
    }
}
