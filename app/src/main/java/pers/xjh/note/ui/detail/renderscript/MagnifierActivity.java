package pers.xjh.note.ui.detail.renderscript;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.RenderScriptUtil;
import pers.xjh.note.utils.UiUtil;

/**
 * Created by xjh on 17-10-9.
 */

public class MagnifierActivity extends BaseActivity {

    private ImageView mImg;

    private Bitmap mBmp;
    //圆的半径
    private int mRadius = 50;
    //放大倍数
    private int mScale = 2;

    @Override
    protected int initContentView() {
        return R.layout.activity_magnifier;
    }

    @Override
    protected void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
        mImg.setImageBitmap(mBmp);

        mImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RenderScriptUtil.magnifierBitmap(mBmp, ((int) event.getX()), ((int) event.getY()), UiUtil.dp2px(mRadius), mScale, MagnifierActivity.this);
                        mImg.setImageBitmap(mBmp);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //先恢复原图
                        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
                        mImg.setImageBitmap(mBmp);
                        //进行放大
                        RenderScriptUtil.magnifierBitmap(mBmp, ((int) event.getX()), ((int) event.getY()), UiUtil.dp2px(mRadius), mScale, MagnifierActivity.this);
                        mImg.setImageBitmap(mBmp);
                        break;
                    case MotionEvent.ACTION_UP:
                        //松开时恢复原图
                        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
                        mImg.setImageBitmap(mBmp);
                        break;
                }
                return true;
            }
        });
    }
}
