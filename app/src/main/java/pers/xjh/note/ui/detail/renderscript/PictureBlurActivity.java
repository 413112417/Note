package pers.xjh.note.ui.detail.renderscript;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.Type;
import android.widget.ImageView;
import android.widget.SeekBar;

import pers.xjh.note.R;
import pers.xjh.note.renderscript.ScriptC_sketch;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.RenderScriptUtil;

/**
 * Created by xjh on 17-10-9.
 */

public class PictureBlurActivity extends BaseActivity {

    private ImageView mImg;

    private SeekBar mSeekBar;

    @Override
    protected int initContentView() {
        return R.layout.activity_picture_blur;
    }

    @Override
    protected void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mSeekBar = (SeekBar) findViewById(R.id.sb_blur);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.luffy);
        mImg.setImageBitmap(bmp);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress > 0) {
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.luffy);
                    mImg.setImageBitmap(RenderScriptUtil.blurBitmap(bmp, progress, getApplication()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
