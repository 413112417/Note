package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.os.Bundle;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.runtime.RtEnv;

/**
 * Created by XJH on 2017/4/28.
 */

public class CActivity extends LifeCycleActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_c);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                RtEnv.startActivity(AActivity.class);
                break;
            case R.id.btn_2:
                RtEnv.startActivity(CActivity.class);
                break;
        }
    }
}
