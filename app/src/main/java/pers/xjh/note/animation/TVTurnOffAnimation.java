package pers.xjh.note.animation;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * 电视关闭动画
 * Created by XJH on 2017/4/27.
 */

public class TVTurnOffAnimation extends Animation {

    private int mWidth, mHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mWidth = width;
        mHeight = height;
        //结束后保留状态
        setFillAfter(true);
        setDuration(150);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        matrix.preScale(1, 1 - interpolatedTime, mWidth / 2, mHeight / 2);
    }
}
