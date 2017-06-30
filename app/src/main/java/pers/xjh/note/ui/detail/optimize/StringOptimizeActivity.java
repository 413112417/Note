package pers.xjh.note.ui.detail.optimize;

import android.view.View;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-6-30.
 */

public class StringOptimizeActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTime;

    @Override
    protected int initContentView() {
        return R.layout.activity_string_optimize;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        mTvTime = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final long start = System.currentTimeMillis();
                        String s = "";
                        for(int i=0; i<10000; i++) {
                            s += i;
                        }
                        final long end = System.currentTimeMillis();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvTime.setText((end - start) + "ms");
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btn_2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final long start = System.currentTimeMillis();
                        StringBuilder s = new StringBuilder();
                        for(int i=0; i<10000; i++) {
                            s.append(i);
                        }
                        final long end = System.currentTimeMillis();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvTime.setText((end - start) + "ms");
                            }
                        });
                    }
                }).start();
                break;
        }
    }
}
