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
import pers.xjh.note.algorithm.Search;
import pers.xjh.note.algorithm.structure.tree.Tree;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.MathUtil;
import pers.xjh.note.widget.TitleBar;
import pers.xjh.note.widget.TreeSurfaceView;
import pers.xjh.note.widget.dialog.PickDialog;

/**
 * Created by XJH on 2017/5/2.
 */

public class TreeActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtCount;

    private TreeSurfaceView mTreeSurfaceView;

    private TextView mTvSearchWay, mTvTime;

    private Button mBtnBuild, mBtnDFS, mBtnBFS;

    private Tree mTree = new Tree();

    private int[] mArray;

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
        return R.layout.activity_tree;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        mTitleBar.setTitleRight("说明");
    }

    @Override
    protected void initView() {
        mEtCount = (EditText) findViewById(R.id.et_count);
        mTreeSurfaceView = (TreeSurfaceView) findViewById(R.id.tree_surface_view);
        mTvSearchWay = (TextView) findViewById(R.id.tv_search_way);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        mBtnBuild = (Button) findViewById(R.id.btn_build);
        mBtnBuild.setOnClickListener(this);
        mBtnDFS = (Button) findViewById(R.id.btn_dfs);
        mBtnDFS.setOnClickListener(this);
        mBtnBFS = (Button) findViewById(R.id.btn_bfs);
        mBtnBFS.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTreeSurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTreeSurfaceView.stop();
    }

    /**
     * 禁用按钮
     */
    private void disableButton() {
        mBtnBuild.setEnabled(false);
        mBtnDFS.setEnabled(false);
        mBtnBFS.setEnabled(false);
    }

    /**
     * 启用按钮
     */
    private void enableButton() {
        mBtnBuild.setEnabled(true);
        mBtnDFS.setEnabled(true);
        mBtnBFS.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_build:
                showBuildTypeDialog();
                break;
            case R.id.btn_dfs:
                startSearch(0);
                break;
            case R.id.btn_bfs:
                startSearch(1);
                break;
        }
    }

    /**
     * 显示选择生成树类型的对话框
     */
    private void showBuildTypeDialog() {
        new PickDialog.Builder(this)
                .setTitle("树的类型")
                .setItemDatas("随机树", "完全二叉树", "最大堆", "最小堆")
                .setInAnimation(R.anim.soft_in)
                .setItemClickListener(new PickDialog.OnItemClickListener() {
                    @Override
                    public void onItemClicked(PickDialog dialog, int position) {
                        buildData(position);
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 生成数据
     */
    private void buildData(int type) {
        //如果输入长度为空，则输入默认长度
        if (TextUtils.isEmpty(mEtCount.getText())) {
            mEtCount.setText("10000");
            buildData(type);
            return;
        }

        try {
            int count = Integer.parseInt(mEtCount.getText().toString());
            mArray = new int[count];
            for (int i = 0; i < count; i++) {
                mArray[i] = mRandom.nextInt(count);
            }
            mTreeSurfaceView.setMaxValue(MathUtil.getMaxValue(mArray));
            mTreeSurfaceView.setTree(mTree);
            new Thread(new BuildTask(type)).start();
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    /**
     * 构造数据任务
     */
    private class BuildTask implements Runnable {

        private int type;

        public BuildTask(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            switch (type) {
                case 0:
                    mTree.buildRandomTree(mArray);
                    break;
                case 1:
                    mTree.buildCompleteBinaryTree(mArray);
                    break;
                case 2:
                    mTree.buildMaxHeap(mArray);
                    break;
                case 3:
                    mTree.buildMinHeap(mArray);
                    break;
            }
        }
    }

    private void startSearch(int type) {
        if(mTree != null) {
            disableButton();
            showResult(type, "--");
            new Thread(new SearchTask(type)).start();
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
                long time = search(type);
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
    private long search(int way) {
        long start = System.currentTimeMillis();
        switch (way) {
            case 0:
                Search.depthFirstSearch(mTree);
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
                mTvSearchWay.setText("深度优先");
                break;
            case 1:
                mTvSearchWay.setText("广度优先");
                break;
        }
        mTvTime.setText(time);
    }
}
