package pers.xjh.note.ui.detail.algorithm;

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
import pers.xjh.note.algorithm.structure.tree.BinaryTree;
import pers.xjh.note.algorithm.structure.tree.BinaryTreeNode;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.MathUtil;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.BinaryTreeSurfaceView;
import pers.xjh.note.widget.SortSurfaceView;
import pers.xjh.note.widget.TitleBar;
import pers.xjh.note.widget.dialog.InputDialog;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * Created by XJH on 2017/5/17.
 */

public class BinarySearchTreeActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtArrayCount;

    private Button mBtnBuildArray, mBtnBuildTree, mBtnStart;

    private SortSurfaceView mSortSurfaceView;

    private BinaryTreeSurfaceView mBinaryTreeSurfaceView;

    private TextView mTvSearchResult, mTvTime;

    private int[] mArray;

    private BinaryTree mBinaryTree = new BinaryTree();

    private Random mRandom = new Random();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    enableButton();
                    break;
                case 1:
                    enableButton();
                    mTvTime.setText(msg.arg1 + "ms");
                    if(msg.obj != null) {
                        mTvSearchResult.setText("查找成功");
                    } else {
                        mTvSearchResult.setText("未找到");
                    }
                    break;
            }
        }
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_binary_search_tree;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitleRight("说明");
    }

    @Override
    protected void initView() {
        mEtArrayCount = (EditText) findViewById(R.id.et_array_count);
        mBtnBuildArray = (Button) findViewById(R.id.btn_build_array);
        mBtnBuildTree = (Button) findViewById(R.id.btn_build_tree);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mSortSurfaceView = (SortSurfaceView) findViewById(R.id.sort_surface_view);
        mBinaryTreeSurfaceView = (BinaryTreeSurfaceView) findViewById(R.id.binary_tree_surface_view);
        mTvSearchResult = (TextView) findViewById(R.id.tv_search_result);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        mBtnBuildArray.setOnClickListener(this);
        mBtnBuildTree.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSortSurfaceView.start();
        mBinaryTreeSurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSortSurfaceView.stop();
        mBinaryTreeSurfaceView.stop();
    }

    /**
     * 禁用按钮
     */
    private void disableButton() {
        mBtnBuildArray.setEnabled(false);
        mBtnBuildTree.setEnabled(false);
        mBtnStart.setEnabled(false);
    }

    /**
     * 启用按钮
     */
    private void enableButton() {
        mBtnBuildArray.setEnabled(true);
        mBtnBuildTree.setEnabled(true);
        mBtnStart.setEnabled(true);
    }

    /**
     * 清除查找结果
     */
    private void clearResult() {
        mTvSearchResult.setText("");
        mTvTime.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_build_array:
                showBuildArrayDialog();
                break;
            case R.id.btn_build_tree:
                buildTree();
                break;
            case R.id.btn_start:
                showInputDialog();
                break;
        }
    }

    /**
     * 显示构造数组的对话框
     */
    private void showBuildArrayDialog() {
        new PickDialog.Builder(this)
                .setTitle("生成数组的方式")
                .setItemDatas("随机","正序","倒序")
                .setInAnimation(R.anim.soft_in)
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, int position) {
                        buildArray(position);
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 显示输入对话框，输入要查找的数
     */
    private void showInputDialog() {
        new InputDialog.Builder(this)
                .setTitle("请输入要查找的数")
                .setPositiveButton("确定", new InputDialog.OnClickPositiveListener() {
                    @Override
                    public void onClick(String inputString, InputDialog dialog) {
                        dialog.dismiss();
                        hideSoftInput();
                        disableButton();
                        clearResult();
                        try{
                            int data = Integer.parseInt(inputString);

                            ThreadPool.execute(new SearchTask(data));
                        } catch (Exception e) {
                            showErrorDialog(e.getMessage());
                            enableButton();
                        }
                    }
                }).setNegativeButton("取消", new InputDialog.OnClickNegativeListener() {
                    @Override
                    public void onClick(InputDialog dialog) {
                        dialog.dismiss();
                    }
                }).setInAnimation(R.anim.soft_in)
                .build().show();
    }

    /**
     * 查找任务
     */
    private class SearchTask implements Runnable {

        private int data;

        public SearchTask(int data) {
            this.data = data;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            BinaryTreeNode node = mBinaryTree.search(data);
            long end = System.currentTimeMillis();
            Message msg = mHandler.obtainMessage(1);
            msg.obj = node;
            msg.arg1 = (int) (end - start);
            mHandler.sendMessage(msg);
        }
    }


    /**
     * 生成数组
     * @param type
     */
    private void buildArray(int type) {
        //如果输入长度为空，则输入默认长度
        if(TextUtils.isEmpty(mEtArrayCount.getText())) {
            mEtArrayCount.setText("5000");
            buildArray(type);
            return;
        }

        try {
            disableButton();
            int count = Integer.parseInt(mEtArrayCount.getText().toString());
            mArray = new int[count];
            for (int i = 0; i < count; i++) {
                mArray[i] = mRandom.nextInt(count);
            }
            mSortSurfaceView.setData(mArray);

            ThreadPool.execute(new BuildArrayTask(type));
        } catch (Exception e) {
            mHandler.sendMessage(mHandler.obtainMessage(0));
            showErrorDialog(e.getMessage());
        }
    }

    /**
     * 构造数组任务
     */
    private class BuildArrayTask implements Runnable {

        private int type;

        public BuildArrayTask(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            switch (type) {
                case 0:
                    break;
                case 1:
                    Sort.shellSort(mArray, false);
                    break;
                case 2:
                    Sort.shellSort(mArray, true);
                    break;
            }
            mHandler.sendMessage(mHandler.obtainMessage(0));
        }
    }

    /**
     * 生成树
     */
    private void buildTree() {
        if(mArray == null || mArray.length <=0) {
            showErrorDialog("数组为空");
            return;
        }

        try {
            disableButton();
            clearResult();
            mBinaryTreeSurfaceView.setMaxValue(MathUtil.getMaxValue(mArray));
            mBinaryTreeSurfaceView.setTree(mBinaryTree);

            ThreadPool.execute(new BuildTreeTask());
        } catch (Exception e) {
            mHandler.sendMessage(mHandler.obtainMessage(0));
            showMsgDialog(e.getMessage());
        }
    }

    /**
     * 构造二叉查找树任务
     */
    private class BuildTreeTask implements Runnable {

        @Override
        public void run() {
            mBinaryTree.buildBinarySearchTree(mArray);
            mHandler.sendMessage(mHandler.obtainMessage(0));
        }
    }
}
