package pers.xjh.note.ui.detail.android;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/6/14.
 */

public class RefreshActivity extends BaseActivity {

    private SwipeRefreshLayout mRefreshLayout;

    private TextView mTvState;

    @Override
    protected int initContentView() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mTvState = (TextView) findViewById(R.id.tv_state);

        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTvState.setText("刷新中");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            RefreshActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRefreshLayout.setRefreshing(false);
                                    mTvState.setText("刷新完成");
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
