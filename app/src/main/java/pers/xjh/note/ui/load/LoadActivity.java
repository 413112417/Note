package pers.xjh.note.ui.load;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pers.xjh.note.R;
import pers.xjh.note.ui.note.NoteActivity;

/**
 * Created by XJH on 2017/6/4.
 */

public class LoadActivity extends AppCompatActivity {

    private ImageView mImgLoad;

    private TextView mTvTime;

    private LoadTask mLoadTask;

    private int mTime = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mTime-=1;
            mTvTime.setText(mTime + "秒后跳过");
            if(mTime == 0) {
                mTvTime.setClickable(false);
                mHandler.removeCallbacks(mLoadTask);
                Intent intent = new Intent(LoadActivity.this, NoteActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                finish();
            } else {
                mHandler.postDelayed(mLoadTask, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_load);

        mImgLoad = (ImageView) findViewById(R.id.img_load);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        Glide.with(this).load(R.drawable.load).asGif().into(mImgLoad);

        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mLoadTask);
                Intent intent = new Intent(LoadActivity.this, NoteActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                finish();
            }
        });

        mLoadTask = new LoadTask();
        mHandler.postDelayed(mLoadTask, 1000);
    }

    private class LoadTask implements Runnable {

        @Override
        public void run() {
            mHandler.sendMessage(mHandler.obtainMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mLoadTask);
    }
}
