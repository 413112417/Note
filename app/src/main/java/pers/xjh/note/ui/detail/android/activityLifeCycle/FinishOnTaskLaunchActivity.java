package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.os.Bundle;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.runtime.Runtime;

/**
 * Created by XJH on 2017/4/28.
 */

public class FinishOnTaskLaunchActivity extends LifeCycleActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_on_task_launch);
        findViewById(R.id.btn_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                Runtime.startActivity(AActivity.class);
                break;
        }
    }
}
