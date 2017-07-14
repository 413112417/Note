package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import pers.xjh.note.runtime.RunTime;
import pers.xjh.note.utils.Constant;

/**
 * Created by XJH on 2017/4/27.
 */

public class LifeCycleActivity extends Activity {

    private String className = this.getClass().getSimpleName() + ":";

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Toast.makeText(RunTime.getApplication(), className + "onAttachedToWindow. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Toast.makeText(RunTime.getApplication(), className + "onDetachedFromWindow. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunTime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));
        Toast.makeText(RunTime.getApplication(), className + "onCreate. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(RunTime.getApplication(), className + "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RunTime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));
        Toast.makeText(RunTime.getApplication(), className + "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(RunTime.getApplication(), className + "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(RunTime.getApplication(), className + "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(RunTime.getApplication(), className + "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(RunTime.getApplication(), className + "onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(RunTime.getApplication(), className + "onConfigurationChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(RunTime.getApplication(), className + "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(RunTime.getApplication(), className + "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }
}
