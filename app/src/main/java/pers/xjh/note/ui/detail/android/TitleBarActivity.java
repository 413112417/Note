package pers.xjh.note.ui.detail.android;

import android.view.View;
import android.widget.Toast;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/4/5.
 */

public class TitleBarActivity extends BaseActivity {

    private TitleBar mTitleBar;

    @Override
    protected int initContentView() {
        return R.layout.activity_title_bar;
    }

    @Override
    protected void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TitleBarActivity.this, "你点击了标题的左边", Toast.LENGTH_SHORT).show();
            }
        });

        mTitleBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TitleBarActivity.this, "你点击了标题的右边", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
