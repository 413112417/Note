package pers.xjh.note.ui.detail.android;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * 像素点分析
 * Created by XJH on 2017/4/25.
 */

public class PixelsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageView;

    private Button mBtnCommon, mBtnReset;

    private Bitmap mBitmap;

    @Override
    protected int initContentView() {
        return R.layout.activity_pixels;
    }

    @Override
    protected void initView() {
        mBtnCommon = (Button) findViewById(R.id.btn_common);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mImageView = (ImageView) findViewById(R.id.img);

        mBtnCommon.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);

        mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
    }

    /**
     * 获得受影响的bitmap
     */
    private Bitmap getEffectedBitmap(Bitmap bitmap, int type) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] pixels = new int[width * height];

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        newBitmap.setPixels(getEffectedPixels(pixels, type), 0, width, 0, 0, width, height);

        return newBitmap;
    }

    /**
     * 获得受影响的像素数组
     */
    private int[] getEffectedPixels(int[] pixels, int type) {
        int color;
        int r, g, b, a;

        int[] newPixels = new int[pixels.length];

        for(int i=0; i<pixels.length; i++) {
            color = pixels[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            switch (type) {
                case 0:
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    break;
                case 1:
                    r = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                    g = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                    b = (int) (0.272 * r + 0.534 * g + 0.131 * b);
                    break;
                case 2:
                    if(i > 0 && i < pixels.length -1) {
                        r = Color.red(pixels[i+1]) - Color.red(pixels[i-1]) + 127;
                        g = Color.green(pixels[i+1]) - Color.green(pixels[i-1]) + 127;
                        b = Color.blue(pixels[i+1]) - Color.blue(pixels[i-1]) + 127;
                    }
                    break;
            }

            if(r > 255) r = 255; if(r < 0) r = 0;
            if(g > 255) g = 255; if(g < 0) g = 0;
            if(b > 255) b = 255; if(b < 0) b = 0;

            newPixels[i] = Color.argb(a, r, g, b);
        }
        return  newPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_common:
                showPickDialog();
                break;
            case R.id.btn_reset:
                reset();
                break;
        }
    }

    /**
     * 还原
     */
    private void reset() {
        mImageView.setImageBitmap(mBitmap);
    }

    /**
     * 弹出选择对话框
     */
    private void showPickDialog() {
        new PickDialog.Builder(this)
                .setTitle("常用效果")
                .setItemDatas(new String[] {"底片效果", "老照片效果", "浮雕效果"})
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, int position) {
                        handlePick(position);
                        dialog.dismiss();
                    }
                }).setInAnimation(R.anim.soft_in).build().show();
    }

    /**
     * 处理选择结果
     * @param position
     */
    private void handlePick(int position) {
        mImageView.setImageBitmap(getEffectedBitmap(mBitmap, position));
    }
}
