package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/19.
 */

public class PendingTransitionBActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_pending_transition_b;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PendingTransitionBActivity.this, PendingTransitionAActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.bottom_out);
    }
}
