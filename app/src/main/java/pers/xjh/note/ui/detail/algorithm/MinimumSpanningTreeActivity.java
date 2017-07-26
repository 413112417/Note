package pers.xjh.note.ui.detail.algorithm;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.algorithm.structure.graph.Graph;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.GraphSurfaceView;
import pers.xjh.note.widget.TitleBar;

/**
 * 最短路径
 * Created by XJH on 2017/5/2.
 */

public class MinimumSpanningTreeActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtPointCount, mEtMaxEdgeCount;

    private GraphSurfaceView mGraphSurfaceView, mMinimumSpanningGraphSurfaceView;

    private TextView mTvResult, mTvTime;

    private Button mBtnBuild, mBtnStart;

    private Graph mGraph = new Graph();

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
        return R.layout.activity_minimum_spanning_tree;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitleRight("说明");
    }

    @Override
    protected void initView() {
        mEtPointCount = (EditText) findViewById(R.id.et_point_count);
        mEtMaxEdgeCount = (EditText) findViewById(R.id.et_max_edge_count);
        mGraphSurfaceView = (GraphSurfaceView) findViewById(R.id.graph_surface_view);
        mMinimumSpanningGraphSurfaceView = (GraphSurfaceView) findViewById(R.id.minimum_spanning_tree_graph_surface_view);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        mBtnBuild = (Button) findViewById(R.id.btn_build);
        mBtnBuild.setOnClickListener(this);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGraphSurfaceView.start();
        mMinimumSpanningGraphSurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGraphSurfaceView.stop();
        mMinimumSpanningGraphSurfaceView.stop();
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
                buildGraph();
                break;
            case R.id.btn_start:
                buildMinimumSpanningTree();
                break;
        }
    }



    /**
     * 生成图
     */
    private void buildGraph() {
        //如果输入长度为空，则输入默认长度
        if (TextUtils.isEmpty(mEtPointCount.getText())) {
            mEtPointCount.setText("100");
            buildGraph();
            return;
        }

        if (TextUtils.isEmpty(mEtMaxEdgeCount.getText())) {
            mEtMaxEdgeCount.setText("50");
            buildGraph();
            return;
        }

        try {
            int pointCount = Integer.parseInt(mEtPointCount.getText().toString());
            int maxEdgeCount = Integer.parseInt(mEtMaxEdgeCount.getText().toString());
            mGraphSurfaceView.setGraph(mGraph);
            ThreadPool.execute(new BuildTask(pointCount, maxEdgeCount));
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    /**
     * 生成最小生成树
     */
    private void buildMinimumSpanningTree() {
        disableButton();
        ThreadPool.execute(new BuildMinimumSpanningTreeTask());
    }

    /**
     * 构造图任务
     */
    private class BuildTask implements Runnable {

        private int pointCount;

        private int maxEdgeCount;

        public BuildTask(int pointCount, int maxEdgeCount) {
            this.pointCount = pointCount;
            this.maxEdgeCount = maxEdgeCount;
        }

        @Override
        public void run() {
            mGraph.buildGraph(pointCount, maxEdgeCount, mGraphSurfaceView.getWidth(), mGraphSurfaceView.getHeight());
        }
    }

    /**
     * 生成最小生成树任务
     */
    private class BuildMinimumSpanningTreeTask implements Runnable {

        @Override
        public void run() {
            try{
                long start = System.currentTimeMillis();
                mMinimumSpanningGraphSurfaceView.setGraph(Graph.prim(mGraph));
                long end = System.currentTimeMillis();

                Message msg = Message.obtain(mHandler, 0);
                msg.arg1 = 0;
                msg.arg2 = (int) (end - start);
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
    private void showResult(int type, String time) {
        switch (type) {
            case 0:
                mTvResult.setText("prim算法");
                break;
            case 1:
                mTvResult.setText("kruskal算法");
                break;
        }
        mTvTime.setText(time);
    }
}
