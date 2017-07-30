package pers.xjh.note.ui.detail.ai;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import pers.xjh.ai.algorithm.GradientDescent;
import pers.xjh.ai.algorithm.structure.Point;
import pers.xjh.note.R;
import pers.xjh.note.algorithm.Search;
import pers.xjh.note.algorithm.structure.tree.Tree;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.FittingSurfaceView;
import pers.xjh.note.widget.TitleBar;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * Created by XJH on 2017/5/2.
 */

public class FittingActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtCount, mEtWeight1, mEtWeight2, mEtFloat, mEtDescent;

    private FittingSurfaceView mFittingSurfaceView;

    private TextView mTvWeight1, mTvWeight2;

    private Button mBtnBuild, mBtnBGD, mBtnSGD;

    private Point[] mPoints;

    private float[] mWeights;

    private float mDescender;

    private Random mRandom = new Random();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    enableButton();
                    showResult();
                    break;
                case 1:
                    enableButton();
                    Exception e = (Exception) msg.obj;
                    showErrorDialog(e.getMessage());
                    break;
            }
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_fitting;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitleRight("说明");
    }

    @Override
    protected void initView() {
        mEtCount = (EditText) findViewById(R.id.et_count);
        mEtWeight1 = (EditText) findViewById(R.id.et_weight_1);
        mEtWeight2 = (EditText) findViewById(R.id.et_weight_2);
        mEtFloat = (EditText) findViewById(R.id.et_float);
        mEtDescent = (EditText) findViewById(R.id.et_descent);
        mFittingSurfaceView = (FittingSurfaceView) findViewById(R.id.fitting_surface_view);
        mTvWeight1 = (TextView) findViewById(R.id.tv_weight_1);
        mTvWeight2 = (TextView) findViewById(R.id.tv_weight_2);

        mBtnBuild = (Button) findViewById(R.id.btn_build);
        mBtnBuild.setOnClickListener(this);
        mBtnBGD = (Button) findViewById(R.id.btn_bgd);
        mBtnBGD.setOnClickListener(this);
        mBtnSGD = (Button) findViewById(R.id.btn_sgd);
        mBtnSGD.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFittingSurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFittingSurfaceView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //避免handler引起内存泄漏
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 禁用按钮
     */
    private void disableButton() {
        mBtnBuild.setEnabled(false);
        mBtnBGD.setEnabled(false);
        mBtnSGD.setEnabled(false);
    }

    /**
     * 启用按钮
     */
    private void enableButton() {
        mBtnBuild.setEnabled(true);
        mBtnBGD.setEnabled(true);
        mBtnSGD.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_build:
                buildData();
                break;
            case R.id.btn_bgd:
                startFitting(0);
                break;
            case R.id.btn_sgd:
                startFitting(1);
                break;
        }
    }

    /**
     * 生成数据
     */
    private void buildData() {
        //如果输入长度为空，则输入默认长度
        if (TextUtils.isEmpty(mEtCount.getText())) {
            mEtCount.setText("1000");
            mEtWeight1.setText("0.1");
            mEtWeight2.setText("1");
            mEtFloat.setText("0.5");
            mEtDescent.setText("0.1");
            buildData();
            return;
        }

        try {
            int count = Integer.parseInt(mEtCount.getText().toString());
            float weight1 = Float.parseFloat(mEtWeight1.getText().toString());
            float weight2 = Float.parseFloat(mEtWeight2.getText().toString());
            float floatValue = Float.parseFloat(mEtFloat.getText().toString());
            mDescender = Float.parseFloat(mEtDescent.getText().toString());

            mWeights = new float[2];
            mPoints = new Point[count];
            for (int i = 0; i < count; i++) {
                float x = mRandom.nextFloat();
                mPoints[i] = new Point(x,  weight1 + weight2 * x + (mRandom.nextFloat() - 0.5f) * floatValue);
            }
            mFittingSurfaceView.setPoints(mPoints);
            mFittingSurfaceView.setWeights(mWeights);
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    private void startFitting(int type) {
        if(mPoints != null) {
            disableButton();
            clearResult();
            ThreadPool.execute(new FittingTask(type));
        } else {
            showErrorDialog("样本为空!");
        }
    }

    /**
     * 拟合任务
     */
    private class FittingTask implements Runnable {

        private int type;

        public FittingTask(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            try {
                long time = gradientDescent(type);
                Message msg = Message.obtain(mHandler, 0);
                msg.arg1 = type;
                msg.arg2 = ((int) time);
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                Message msg = Message.obtain(mHandler, 1);
                msg.obj = e;
                mHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 开始遍历
     */
    private long gradientDescent(int way) {
        long start = System.currentTimeMillis();
        switch (way) {
            case 0:
                GradientDescent.batchGradientDescent(mPoints, mWeights, mDescender);
                break;
            case 1:
                break;
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 显示处理结果
     */
    private void showResult() {
        mTvWeight1.setText(mWeights[0] + "");
        mTvWeight2.setText(mWeights[1] + "");
    }

    /**
     * 清除结果
     */
    private void clearResult() {
        mTvWeight1.setText("");
        mTvWeight2.setText("");
    }
}
