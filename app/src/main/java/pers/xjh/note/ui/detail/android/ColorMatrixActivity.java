package pers.xjh.note.ui.detail.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * Created by XJH on 2017/4/24.
 */

public class ColorMatrixActivity extends BaseActivity implements View.OnClickListener {

    private GridLayout mGridLayout;

    private List<EditText> mEditTextList;

    private Button mBtnConfirm, mBtnCommon, mBtnReset;

    private ImageView mImageView;

    private Bitmap mBitmap;

    private float[] mMatrix;

    @Override
    protected int initContentView() {
        return R.layout.activity_color_matrix;
    }

    @Override
    protected void initView() {
        mGridLayout = (GridLayout) findViewById(R.id.grid_layout);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mBtnCommon = (Button) findViewById(R.id.btn_common);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mImageView = (ImageView) findViewById(R.id.img);

        mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();

        mBtnConfirm.setOnClickListener(this);
        mBtnCommon.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);

        mEditTextList = new ArrayList<>();
        mMatrix = new float[20];

        mGridLayout.post(new Runnable() {
            @Override
            public void run() {
                addEditText();
            }
        });
    }

    /**
     * 添加输入框
     */
    private void addEditText() {
        int etWidth = mGridLayout.getWidth() / 5;
        int etHeight = mGridLayout.getHeight() / 4;

        for(int i=0; i<20; i++) {
            EditText et = new EditText(ColorMatrixActivity.this);
            mGridLayout.addView(et);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) et.getLayoutParams();
            params.width = etWidth;
            params.height = etHeight;
            et.setLayoutParams(params);

            et.setInputType(InputType.TYPE_CLASS_PHONE);

            mEditTextList.add(et);
        }
        initMatrix();
    }

    /**
     * 初始化矩阵
     */
    private void initMatrix() {
        for(int i=0; i<20; i++) {
            if(i % 6 == 0) {
                mEditTextList.get(i).setText("1");
            } else {
                mEditTextList.get(i).setText("0");
            }
        }
    }

    /**
     * 重置矩阵
     */
    private void resetMatrix() {
        for(int i=0; i<20; i++) {
            if(i % 6 == 0) {
                mEditTextList.get(i).setText("1");
            } else {
                mEditTextList.get(i).setText("0");
            }
        }

        effectMatrix();
    }

    /**
     * 将矩阵作用到图片上
     */
    private void effectMatrix() {
        for(int i=0; i<mEditTextList.size(); i++) {
            try {
                mMatrix[i] = Float.valueOf(mEditTextList.get(i).getText().toString());
            } catch (Exception e) {
                mMatrix[i] = 0;
            }
        }

        Bitmap newBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        mImageView.setImageBitmap(newBitmap);
    }

    /**
     * 输入矩阵
     */
    private void inputMatrix(float[] matrix) {
        for(int i=0; i<20; i++) {
            try {
                mEditTextList.get(i).setText(Float.valueOf(matrix[i]) + "");
            } catch (Exception e) {
                mEditTextList.get(i).setText("0");
            }
        }
    }

    /**
     * 弹出选择对话框
     */
    private void showPickDialog() {
        PickDialog.Builder builder = new PickDialog.Builder(this);
        builder.setTitle("常用效果")
                .setItemDatas(new String[] {"灰度效果", "图像反转", "怀旧效果", "去色效果",
                        "高饱和度"})
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, int position) {
                        handlePick(position);
                        dialog.dismiss();
                    }
                }).setInAnimation(R.anim.soft_in).build().show();
    }

    /**
     * 处理选择
     */
    private void handlePick(int position) {

        switch (position) {
            case 0:
                inputMatrix(new float[] {
                        0.33f, 0.59f, 0.11f, 0, 0,
                        0.33f, 0.59f, 0.11f, 0, 0,
                        0.33f, 0.59f, 0.11f, 0, 0,
                        0, 0, 0, 1, 0
                });
                break;
            case 1:
                inputMatrix(new float[] {
                        -1, 0, 0, 1, 1,
                        0, -1, 0, 1, 1,
                        0, 0, -1, 1, 1,
                        0, 0, 0, 1, 0
                });
                break;
            case 2:
                inputMatrix(new float[] {
                        0.393f, 0.769f, 0.189f, 0, 0,
                        0.349f, 0.686f, 0.168f, 0, 0,
                        0.272f, 0.534f, 0.131f, 0, 0,
                        0, 0, 0, 1, 0
                });
                break;
            case 3:
                inputMatrix(new float[] {
                        0.15f, 0.15f, 0.15f, 0, -1,
                        0.15f, 0.15f, 0.15f, 0, -1,
                        0.15f, 0.15f, 0.15f, 0, -1,
                        0, 0, 0, 1, 0
                });
                break;
            case 4:
                inputMatrix(new float[] {
                        1.438f, -0.122f, -0.016f, 0, -0.03f,
                        -0.062f, 1.378f, -0.016f, 0, 0.05f,
                        -0.062f, -0.122f, 1.483f, 0, -0.02f,
                        0, 0, 0, 1, 0
                });
                break;
        }

        effectMatrix();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                effectMatrix();
                break;
            case R.id.btn_common:
                showPickDialog();
                break;
            case R.id.btn_reset:
                resetMatrix();
                break;
        }
    }
}
