package pers.xjh.note.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * Created by XJH on 2017/4/27.
 */

public class CameraAnimation extends Animation {

    private Camera mCamera;

    private int mWidth, mHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mWidth = width;
        mHeight = height;
        mCamera = new Camera();
        //设置时长
        setDuration(2000);
        //结束后保留状态
        setFillAfter(true);
        //设置默认插值器
        setInterpolator(new BounceInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        mCamera.save();
        //设置Camera旋转45度
        mCamera.rotateY(45 * interpolatedTime);
        //将旋转作用到matrix上
        mCamera.getMatrix(matrix);
        mCamera.restore();
        //通过pre方法设置矩阵作用前的偏移量来改变旋转中心
        matrix.preTranslate(mWidth / 2, mHeight / 2);
        matrix.postTranslate(-mWidth / 2, -mHeight / 2);
    }
}
