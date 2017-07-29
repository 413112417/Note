package pers.xjh.note.ui.detail.android;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.broadcast.NetworkReceiver;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.NetworkUtil;

/**
 * Created by XJH on 2017/5/2.
 */

public class NetworkChangeActivity extends BaseActivity {

    private TextView mTvState;

    private NetworkReceiver mNetworkReceiver;

    private NetworkReceiver.OnNetworkChangeListener mOnNetworkChangeListener = new NetworkReceiver.OnNetworkChangeListener() {
        @Override
        public void onNetworkChange(int networkState) {
            setTvState(networkState);
            showMsgDialog("网络状态改变", mTvState.getText().toString());
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_network_change;
    }

    @Override
    protected void initView() {
        mTvState = (TextView) findViewById(R.id.tv_state);

        mNetworkReceiver = new NetworkReceiver();
        //安卓为了提高后台的性能，去掉了网络改变广播的静态注册方式（同时去除的还有拍照广播和录视频广播）
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mNetworkReceiver.addOnNetworkChangeListener(mOnNetworkChangeListener);
    }

    @Override
    protected void start() {
        setTvState(NetworkUtil.getNetworkState(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkReceiver.removeListener(mOnNetworkChangeListener);
    }

    private void setTvState(int networkState) {
        switch (networkState) {
            case NetworkUtil.NETWORK_NONE:
                mTvState.setText("没有网络");
                break;
            case NetworkUtil.NETWORK_MOBILE:
                mTvState.setText("移动网络");
                break;
            case NetworkUtil.NETWORK_WIFI:
                mTvState.setText("WIFI网络");
                break;
        }
    }
}
