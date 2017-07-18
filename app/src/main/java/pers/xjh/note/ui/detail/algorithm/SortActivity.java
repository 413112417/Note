package pers.xjh.note.ui.detail.algorithm;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import pers.xjh.note.R;
import pers.xjh.note.algorithm.Sort;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.ImageDetailActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.SortSurfaceView;
import pers.xjh.note.widget.TitleBar;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * Created by XJH on 2017/5/2.
 */

public class SortActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtCount;

    private SortSurfaceView mSortSurfaceView;

    private TextView mTvSortWay, mTvTime;

    private Button mBtnBuild, mBtnStart;

    private Random mRandom = new Random();

    //排序类型（false正序， true倒序）
    private boolean mOrder;

    private int[] mArray;

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
        return R.layout.activity_sort;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitleRight("图片");
        titleBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SortActivity.this, ImageDetailActivity.class);
                intent.putExtra(Constant.KEY_IMAGE_URL, new int[] {R.drawable.sort});
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mEtCount = (EditText) findViewById(R.id.et_count);
        mSortSurfaceView = (SortSurfaceView) findViewById(R.id.sort_surface_view);
        mTvSortWay = (TextView) findViewById(R.id.tv_sort_way);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        findViewById(R.id.btn_way).setOnClickListener(this);
        findViewById(R.id.btn_gc).setOnClickListener(this);

        mBtnBuild = (Button) findViewById(R.id.btn_build);
        mBtnStart = (Button) findViewById(R.id.btn_start);

        mBtnBuild.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSortSurfaceView.start();
    }

    /**
     * 在onPause中停止绘画。
     * 因为onPause会立即调用。
     * 绘画时间过长的时候，会导致onStop，onDestroy延迟调用，阻塞主线程，导致卡顿。
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSortSurfaceView.stop();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_build:
                buildArray();
                break;
            case R.id.btn_way:
                showPickSortWayDialog();
                break;
            case R.id.btn_start:
                showPickSortTypeDialog();
                break;
            case R.id.btn_gc:
                //强制调用已经失去引用的对象的finalize方法
                System.runFinalization();
                //告诉垃圾收集器打算进行垃圾收集，而垃圾收集器进不进行收集是不确定的
                System.gc();
                break;
        }
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
            int count = Integer.parseInt(mEtCount.getText().toString());
            mArray = new int[count];
            for(int i=0; i<count; i++) {
                mArray[i] = mRandom.nextInt(count);
            }
            mSortSurfaceView.setData(mArray);
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    /**
     * 显示处理结果
     */
    private void showResult(int way, String time) {
        switch (way) {
            case 0:
                mTvSortWay.setText("选择排序");
                break;
            case 1:
                mTvSortWay.setText("插入排序");
                break;
            case 2:
                mTvSortWay.setText("冒泡排序");
                break;
            case 3:
                mTvSortWay.setText("希尔排序");
                break;
            case 4:
                mTvSortWay.setText("快速排序");
                break;
            case 5:
                mTvSortWay.setText("堆排序");
                break;
            case 6:
                mTvSortWay.setText("归并排序");
                break;
            case 7:
                mTvSortWay.setText("基数排序");
                break;
        }
        mTvTime.setText(time);
    }

    /**
     * 显示选择排序类型对话框
     */
    private void showPickSortWayDialog() {
        new PickDialog.Builder(this)
                .setTitle("排序类型")
                .setItemDatas(new String[] {"正序", "倒序"})
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, final int position) {
                        dialog.dismiss();
                        handlePickSortWay(position);
                    }
                }).setInAnimation(R.anim.soft_in).build().show();
    }

    /**
     * 显示选择排序方式对话框
     */
    private void showPickSortTypeDialog() {
        new PickDialog.Builder(this)
                .setTitle("排序方式")
                .setItemDatas("选择排序", "插入排序", "冒泡排序", "希尔排序", "快速排序","堆排序","归并排序","基数排序")
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, int position) {
                        if(mArray != null) {
                            hideSoftInput();
                            disableButton();
                            showResult(position, "--");
                            ThreadPool.execute(new SortTask(position));
                        } else {
                            showErrorDialog("数组为空");
                        }
                        dialog.dismiss();
                    }
                }).setInAnimation(R.anim.soft_in).build().show();
    }

    /**
     * 排序任务
     */
    private class SortTask implements Runnable {

        private int type;

        public SortTask(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            try {
                long time = handlePickSortType(type);
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
     * 处理选择排序类型结果
     * @param position
     */
    private void handlePickSortWay(int position) {
        switch (position) {
            case 0:
                mOrder = false;
                break;
            case 1:
                mOrder = true;
                break;
        }
    }

    /**
     * 处理选择排序方式结果
     * @param position
     */
    private long handlePickSortType(int position) {
        long start = System.currentTimeMillis();
        switch (position) {
            case 0:
                Sort.selectSort(mArray,mOrder);
                break;
            case 1:
                Sort.insertSort(mArray,mOrder);
                break;
            case 2:
                Sort.bubbleSort(mArray,mOrder);
                break;
            case 3:
                Sort.shellSort(mArray,mOrder);
                break;
            case 4:
                Sort.quickSort(mArray,mOrder);
                break;
            case 5:
                Sort.heapSort(mArray,mOrder);
                break;
            case 6:
                Sort.mergeSort(mArray,mOrder);
                break;
            case 7:
                Sort.radixSort(mArray,mOrder);
                break;
        }
        long end = System.currentTimeMillis();
        return end - start;
    }
}
