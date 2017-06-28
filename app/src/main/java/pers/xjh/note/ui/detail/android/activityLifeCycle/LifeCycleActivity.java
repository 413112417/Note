package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import pers.xjh.note.runtime.RtEnv;
import pers.xjh.note.utils.Constant;

/**
 * Created by XJH on 2017/4/27.
 */

public class LifeCycleActivity extends Activity {

    private String className = this.getClass().getSimpleName() + ":";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RtEnv.put(Constant.RT_CURRENT_ACTIVITY, this);
        Toast.makeText(RtEnv.getApplication(), className + "onCreate. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(RtEnv.getApplication(), className + "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RtEnv.put(Constant.RT_CURRENT_ACTIVITY, this);
        Toast.makeText(RtEnv.getApplication(), className + "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(RtEnv.getApplication(), className + "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(RtEnv.getApplication(), className + "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(RtEnv.getApplication(), className + "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(RtEnv.getApplication(), className + "onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(RtEnv.getApplication(), className + "onConfigurationChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(RtEnv.getApplication(), className + "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(RtEnv.getApplication(), className + "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }
}
