package pers.xjh.note.ui.detail.algorithm;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.algorithm.Search;
import pers.xjh.note.algorithm.structure.graph.Graph;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.GraphSurfaceView;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/5/2.
 */

public class GraphActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtPointCount, mEtMaxEdgeCount;

    private GraphSurfaceView mGraphSurfaceView;

    private TextView mTvSearchWay, mTvTime;

    private Button mBtnBuild, mBtnDFS, mBtnBFS;

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
        return R.layout.activity_graph;
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
        mGraphSurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGraphSurfaceView.stop();
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
                buildGraph();
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

    private void startSearch(int type) {
        if(mGraph != null) {
            disableButton();
            showResult(type, "--");
            ThreadPool.execute(new SearchTask(type));
        } else {
            showErrorDialog("图为空!");
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
                Search.depthFirstSearch(mGraph);
                break;
            case 1:
                Search.broadFirstSearch(mGraph);
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
