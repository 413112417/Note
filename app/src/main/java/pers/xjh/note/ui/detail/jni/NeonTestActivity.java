package pers.xjh.note.ui.detail.jni;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.NeonTest;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * Created by xjh on 17-9-27.
 */

public class NeonTestActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtCount;

    private TextView mTvSortWay, mTvTime;

    private Button mBtnBuild, mBtnWay, mBtnStart;

    private float[] array_a, array_b, array_result;

    private Random mRandom = new Random();

    private int mCount;

    private int mWay;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    enableButton();
                    showResult(msg.arg1, msg.arg2 + "ms");
                    break;
                case 1:
                    Exception e = (Exception) msg.obj;
                    showMsgDialog(e.getMessage());
                    break;
            }
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_neon_test;
    }

    @Override
    protected void initView() {
        mEtCount = (EditText) findViewById(R.id.et_count);
        mTvSortWay = (TextView) findViewById(R.id.tv_sort_way);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mBtnBuild = (Button) findViewById(R.id.btn_build);
        mBtnWay = (Button) findViewById(R.id.btn_way);
        mBtnStart = (Button) findViewById(R.id.btn_start);

        mBtnBuild.setOnClickListener(this);
        mBtnWay.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_build:
                buildArray();
                break;
            case R.id.btn_way:
                showPickWayDialog();
                break;
            case R.id.btn_start:
                startCalculation();
                break;
        }
    }

    /**
     * 禁用按钮
     */
    private void disableButton() {
        mBtnBuild.setEnabled(false);
        mBtnStart.setEnabled(false);
    }

    /**
     * 启用按钮
     */
    private void enableButton() {
        mBtnBuild.setEnabled(true);
        mBtnStart.setEnabled(true);
    }

    /**
     * 生成数据
     */
    private void buildArray() {
        //如果输入长度为空，则输入默认长度
        if(TextUtils.isEmpty(mEtCount.getText())) {
            mEtCount.setText("10000");
            buildArray();
        }

        try {
            hideSoftInput();
            mCount = Integer.parseInt(mEtCount.getText().toString());
            disableButton();
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    array_a = new float[mCount];
                    array_b = new float[mCount];
                    array_result = new float[mCount];
                    for(int i=0; i<mCount; i++) {
                        array_a[i] = mRandom.nextFloat();
                        array_b[i] = mRandom.nextFloat();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableButton();
                        }
                    });
                }
            });
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    private void showPickWayDialog() {
        new PickDialog.Builder(this)
                .setTitle("计算类型")
                .setItemDatas(new String[] {"数组相乘", "NEON数组相乘", "矢量相乘", "NEON矢量相乘"})
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, final int position) {
                        dialog.dismiss();
                        mWay = position;
                    }
                }).setInAnimation(R.anim.soft_in).build().show();
    }

    /**
     * 开始计算
     */
    private void startCalculation() {
        disableButton();
        ThreadPool.execute(new CalculationTask(mWay));
    }

    /**
     * 计算
     */
    private void Calculation(int way) {
        switch (way) {
            case 0:
                NeonTest.normalArrayMul(array_a, array_b, array_result);
                break;
            case 1:
                NeonTest.neonArrayMul(array_a, array_b, array_result);
                break;
            case 2:
                NeonTest.normalVectorMul(mCount);
                break;
            case 3:
                NeonTest.neonVectorMul(mCount);
                break;
        }
    }

    /**
     * 排序任务
     */
    private class CalculationTask implements Runnable {

        private int type;

        public CalculationTask(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            try {
                long time_start = System.currentTimeMillis();
                Calculation(type);
                long time_end = System.currentTimeMillis();
                Message msg = Message.obtain(mHandler, 0);
                msg.arg1 = type;
                msg.arg2 = ((int) (time_end - time_start));
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                Message msg = Message.obtain(mHandler, 1);
                msg.obj = e;
                mHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 显示处理结果
     */
    private void showResult(int way, String time) {
        switch (way) {
            case 0:
                mTvSortWay.setText("数组相乘");
                break;
            case 1:
                mTvSortWay.setText("NEON数组相乘");
                break;
            case 2:
                mTvSortWay.setText("矢量相乘");
                break;
            case 3:
                mTvSortWay.setText("NEON矢量相乘");
                break;
        }
        mTvTime.setText(time);
    }
}
