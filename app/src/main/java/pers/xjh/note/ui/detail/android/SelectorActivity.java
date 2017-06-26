package pers.xjh.note.ui.detail.android;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/15.
 */

public class SelectorActivity extends BaseActivity {

    private Button mBtn;

    private TextView mTv;

    @Override
    protected int initContentView() {
        return R.layout.activity_selector;
    }

    @Override
    protected void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mTv = (TextView) findViewById(R.id.tv);

        findViewById(R.id.btn_btn_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtn.isEnabled()) {
                    mBtn.setEnabled(false);
                } else {
                    mBtn.setEnabled(true);
                }
            }
        });

        findViewById(R.id.btn_tv_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTv.isEnabled()) {
                    mTv.setEnabled(false);
                } else {
                    mTv.setEnabled(true);
                }
            }
        });
    }
}
