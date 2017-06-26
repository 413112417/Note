package pers.xjh.note.ui.detail.android;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/27.
 */

public class SearchBarActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected int initContentView() {
        return R.layout.activity_search_bar;
    }

    @Override
    protected void initView() {
        mImageView = (ImageView) findViewById(R.id.img);
    }

    @Override
    protected void start() {
        Drawable drawable = mImageView.getDrawable();
        if(drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
