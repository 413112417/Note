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

    private EditText mEtCount, mEtWeight1, mEtWeight2;

    private FittingSurfaceView mFittingSurfaceView;

    private TextView mTvSearchWay, mTvTime;

    private Button mBtnBuild, mBtnBGD, mBtnSGD;

    private Tree mTree = new Tree();

    private Point[] mPoints;

    private float[] mWeights;

    private Random mRandom = new Random();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    enableButton();
                    showResult(msg.arg1, msg.arg2 + "ms");
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
        mFittingSurfaceView = (FittingSurfaceView) findViewById(R.id.fitting_surface_view);
        mTvSearchWay = (TextView) findViewById(R.id.tv_search_way);
        mTvTime = (TextView) findViewById(R.id.tv_time);

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
                startSearch(0);
                break;
            case R.id.btn_sgd:
                startSearch(1);
                break;
        }
    }

    /**
     * 生成数据
     */
    private void buildData() {
        //如果输入长度为空，则输入默认长度
        if (TextUtils.isEmpty(mEtCount.getText())) {
            mEtCount.setText("100");
            mEtWeight1.setText("0.2");
            mEtWeight2.setText("1");
            buildData();
            return;
        }

        try {
            int count = Integer.parseInt(mEtCount.getText().toString());
            float weight1 = Float.parseFloat(mEtWeight1.getText().toString());
            float weight2 = Float.parseFloat(mEtWeight2.getText().toString());

            mWeights = new float[2];
            mPoints = new Point[count];
            for (int i = 0; i < count; i++) {
                float x = mRandom.nextFloat();
                mPoints[i] = new Point(x,  weight1 + weight2 * x + (mRandom.nextFloat() - 0.5f) * 0.5f);
            }
            mFittingSurfaceView.setPoints(mPoints);
            mFittingSurfaceView.setWeights(mWeights);
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    private void startSearch(int type) {
        if(mTree != null) {
            disableButton();
            showResult(type, "--");
            ThreadPool.execute(new SearchTask(type));
        } else {
            showErrorDialog("树为空!");
        }
    }

    /**
     * 遍历任务
     */
    private class SearchTask implements Runnable {

        private int type;

        public SearchTask(int type) {
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
                GradientDescent.batchGradientDescent(mPoints, mWeights);
                break;
            case 1:
                Search.broadFirstSearch(mTree);
                break;
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 显示处理结果
     */
    private void showResult(int way, String time) {
        switch (way) {
            case 0:
                mTvSearchWay.setText("批量梯度下降");
                break;
            case 1:
                mTvSearchWay.setText("随机梯度下降");
                break;
        }
        mTvTime.setText(time);
    }
}
