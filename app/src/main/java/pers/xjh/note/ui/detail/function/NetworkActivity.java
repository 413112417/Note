package pers.xjh.note.ui.detail.function;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import pers.xjh.network.HttpClient;
import pers.xjh.network.Response;
import pers.xjh.network.interfaces.Callback;
import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.dialog.MapDialog;

/**
 * Created by XJH on 2017/6/1.
 */

public class NetworkActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtUrl;

    private TextView mTvContent;

    private Map<String, String> mMap = new HashMap<>();

    //第一步，将Handler改成静态内部类。
    private static class NetworkHandler extends Handler {
        //第二步，将需要引用Activity的地方，改成弱引用。
        private WeakReference<NetworkActivity> activityInstance;

        public NetworkHandler(NetworkActivity activity) {
            this.activityInstance = new WeakReference<NetworkActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            NetworkActivity activity = activityInstance == null ? null : activityInstance.get();

            //如果Activity被释放回收了，则不处理这些消息
            if (activity == null || activity.isFinishing()) {
                ToastUtil.show("activity已被回收");
                return;
            }

            switch (msg.what) {
                case 0:
                    activity.mTvContent.setText(((String) msg.obj));
                    break;
            }
        }
    }

    private Handler mHandler = new NetworkHandler(this);

    @Override
    protected int initContentView() {
        return R.layout.activity_network;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_build_post).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);

        mEtUrl = (EditText) findViewById(R.id.et_url);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //避免handler引起内存泄漏，这是最佳方法，上面的弱引用为测试方案
//        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        hideSoftInput();
        switch (v.getId()) {
            case R.id.btn_build_post:
                showBuildPostMapDialog();
                break;
            case R.id.btn_1:
                getSync();
                break;
            case R.id.btn_2:
                postSync();
                break;
            case R.id.btn_3:
                getAsync();
                break;
            case R.id.btn_4:
                postAsync();
                break;
        }
    }

    private void showBuildPostMapDialog() {
        new MapDialog.Builder(this)
                .setTitle("设置POST参数")
                .setMap(mMap)
                .setInAnimation(R.anim.soft_in)
                .build()
                .show();
    }

    private void getSync() {
        final String url = mEtUrl.getText().toString();
        if(!TextUtils.isEmpty(url)) {
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(0, HttpClient.getSync(url).getBodyString()));
                }
            });
        } else {
            ToastUtil.show("URL不能为空!");
        }
    }

    private void postSync() {
        final String url = mEtUrl.getText().toString();
        if(!TextUtils.isEmpty(url)) {
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendMessage(mHandler.obtainMessage(0, HttpClient.postSync(url, mMap).getBodyString()));
                }
            });
        } else {
            ToastUtil.show("URL不能为空!");
        }
    }

    private void getAsync() {
        final String url = mEtUrl.getText().toString();
        if(!TextUtils.isEmpty(url)) {
            HttpClient.getAsync(url, new Callback() {
                @Override
                public void onResponse(Response response) {
                    mHandler.sendMessage(mHandler.obtainMessage(0, response.getBodyString()));
                }

                @Override
                public void onFailure(Exception e) {
                    mHandler.sendMessage(mHandler.obtainMessage(0, e.getMessage()));
                }
            });
        } else {
            ToastUtil.show("URL不能为空!");
        }
    }

    private void postAsync() {
        final String url = mEtUrl.getText().toString();
        if(!TextUtils.isEmpty(url)) {
            HttpClient.postAsync(url, mMap, new Callback() {
                @Override
                public void onResponse(Response response) {
                    mHandler.sendMessage(mHandler.obtainMessage(0, response.getBodyString()));
                }

                @Override
                public void onFailure(Exception e) {
                    mHandler.sendMessage(mHandler.obtainMessage(0, e.getMessage()));
                }
            });
        } else {
            ToastUtil.show("URL不能为空!");
        }
    }
}
