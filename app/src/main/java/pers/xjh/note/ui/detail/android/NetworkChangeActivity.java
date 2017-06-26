package pers.xjh.note.ui.detail.android;

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

    @Override
    protected int initContentView() {
        return R.layout.activity_network_change;
    }

    @Override
    protected void initView() {
        mTvState = (TextView) findViewById(R.id.tv_state);

        NetworkReceiver.addOnNetworkChangeListener(new NetworkReceiver.OnNetworkChangeListener() {
            @Override
            public void onNetworkChange(int networkState) {
                setTvState(networkState);
            }
        });
    }

    @Override
    protected void start() {
        setTvState(NetworkUtil.getNetworkState(this));
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
