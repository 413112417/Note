package pers.xjh.note.ui.detail.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.SeekBar;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/24.
 */

public class ColorMatrixApiActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private ImageView mImageView;

    private Bitmap mBitmap;

    private float mHue = 1, mSaturation = 1, mLum = 1;

    @Override
    protected int initContentView() {
        return R.layout.activity_color_matrix_api;
    }

    @Override
    protected void initView() {
        mImageView = (ImageView) findViewById(R.id.img);

        ((SeekBar) findViewById(R.id.sb_hue)).setProgress(50);
        ((SeekBar) findViewById(R.id.sb_saturation)).setProgress(50);
        ((SeekBar) findViewById(R.id.sb_lum)).setProgress(50);

        ((SeekBar) findViewById(R.id.sb_hue)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.sb_saturation)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.sb_lum)).setOnSeekBarChangeListener(this);

        mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - 50) * 1.0f / 50 * 180;
                break;
            case R.id.sb_saturation:
                mSaturation = progress * 1.0f / 50;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0f / 50;
                break;
        }
        mImageView.setImageBitmap(getEffectedBitmap(mBitmap));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * 获得受影响后的bitmap
     */
    private Bitmap getEffectedBitmap(Bitmap bitmap) {

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, mHue);
        hueMatrix.setRotate(1, mHue);
        hueMatrix.setRotate(2, mHue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(mSaturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(mLum, mLum, mLum, 1);

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.postConcat(hueMatrix);
        colorMatrix.postConcat(saturationMatrix);
        colorMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return newBitmap;
    }
}
