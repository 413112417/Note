package pers.xjh.note.ui.detail.android.activityLifeCycle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import pers.xjh.note.runtime.Runtime;
import pers.xjh.note.utils.Constant;

/**
 * Created by XJH on 2017/4/27.
 */

public class LifeCycleActivity extends Activity {

    private String className = this.getClass().getSimpleName() + ":";

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Toast.makeText(Runtime.getApplication(), className + "onAttachedToWindow. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Toast.makeText(Runtime.getApplication(), className + "onDetachedFromWindow. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Runtime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));
        Toast.makeText(Runtime.getApplication(), className + "onCreate. TaskId=" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(Runtime.getApplication(), className + "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Runtime.put(Constant.RT_CURRENT_ACTIVITY, new WeakReference(this));
        Toast.makeText(Runtime.getApplication(), className + "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(Runtime.getApplication(), className + "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(Runtime.getApplication(), className + "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(Runtime.getApplication(), className + "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(Runtime.getApplication(), className + "onNewIntent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(Runtime.getApplication(), className + "onConfigurationChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(Runtime.getApplication(), className + "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(Runtime.getApplication(), className + "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }
}
