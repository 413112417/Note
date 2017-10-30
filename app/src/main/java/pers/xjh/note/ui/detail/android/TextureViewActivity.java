package pers.xjh.note.ui.detail.android;

import android.view.TextureView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-10-23.
 */

public class TextureViewActivity extends BaseActivity {

    private TextureView mTextureView;

    @Override
    protected int initContentView() {
        return R.layout.activity_texture_view;
    }

    @Override
    protected void initView() {
        mTextureView = (TextureView) findViewById(R.id.texture_view);

    }
}
