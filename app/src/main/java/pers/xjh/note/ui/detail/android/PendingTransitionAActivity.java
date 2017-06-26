package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/19.
 */

public class PendingTransitionAActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_pending_transition_a;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PendingTransitionAActivity.this, PendingTransitionBActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.soft_in, 0);
    }
}
