package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.os.Bundle;
import android.view.View;

import pers.xjh.note.R;
import pers.xjh.note.runtime.RtEnv;

/**
 * 透明的activity
 * Created by XJH on 2017/4/28.
 */

public class TransparentActivity extends LifeCycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_transparent);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RtEnv.startActivity(BActivity.class);
            }
        });
    }
}
