package pers.xjh.note.ui.detail.renderscript;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.RenderScriptUtil;

/**
 * Created by xjh on 17-10-9.
 */

public class SketchActivity extends BaseActivity {

    private ImageView mImg;

    @Override
    protected int initContentView() {
        return R.layout.activity_sketch;
    }

    @Override
    protected void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.luffy);
        RenderScriptUtil.sketchBitmap(bmp, SketchActivity.this);
        mImg.setImageBitmap(bmp);
    }
}
