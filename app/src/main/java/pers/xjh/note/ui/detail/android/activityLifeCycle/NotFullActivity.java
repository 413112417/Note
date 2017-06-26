package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import pers.xjh.note.R;
import pers.xjh.note.runtime.RtEnv;

/**
 * 透明的activity
 * Created by XJH on 2017/4/28.
 */

public class NotFullActivity extends LifeCycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_not_full);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.0f; //设置窗口之外部分透明程度
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = 800;
        attributes.height = 1024;
        getWindow().setAttributes(attributes);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RtEnv.startActivity(BActivity.class);
            }
        });
    }
}
