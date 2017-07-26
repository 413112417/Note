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
import pers.xjh.note.algorithm.structure.graph.Point;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.GraphSurfaceView;
import pers.xjh.note.widget.TitleBar;
import pers.xjh.note.widget.dialog.InputDialog;

/**
 * 最短路径
 * Created by XJH on 2017/5/2.
 */

public class ShortestPathActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtPointCount, mEtMaxEdgeCount;

    private GraphSurfaceView mGraphSurfaceView;

    private TextView mTvResult, mTvTime;

    private Button mBtnBuild, mBtnStart;

    private Point mStartPoint, mEndPoint;

    private int mShortestPath;

    private Graph mGraph = new Graph();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    enableButton();
                    showResult(mShortestPath + "", msg.arg2 + "ms");
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
        return R.layout.activity_shortest_path;
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
                showPointInputDialog();
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

    /**
     * 显示顶点输入框
     */
    private void showPointInputDialog() {
        if(mGraph == null) {
            showErrorDialog("图为空");
            return;
        }

        new InputDialog.Builder(this)
                .setTitle("请输入顶点1下标")
                .setPositiveButton("确定", new InputDialog.OnClickPositiveListener() {

                    @Override
                    public void onClick(String inputString, InputDialog dialog) {
                        dialog.dismiss();

                        try {
                            int indexStart = Integer.parseInt(inputString);
                            mStartPoint = mGraph.getPoints().get(indexStart);

                            new InputDialog.Builder(ShortestPathActivity.this)
                                    .setTitle("请输入顶点2下标")
                                    .setPositiveButton("确定", new InputDialog.OnClickPositiveListener() {

                                        @Override
                                        public void onClick(String inputString, InputDialog dialog) {
                                            dialog.dismiss();

                                            try {
                                                int indexEnd = Integer.parseInt(inputString);
                                                mEndPoint = mGraph.getPoints().get(indexEnd);
                                                startSearch();
                                            } catch (Exception e) {
                                                showErrorDialog(e.getMessage());
                                            }
                                        }
                                    }).setNegativeButton("取消", new InputDialog.OnClickNegativeListener() {

                                @Override
                                public void onClick(InputDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).setInAnimation(R.anim.soft_in)
                                    .build().show();
                        } catch (Exception e) {
                            showErrorDialog(e.getMessage());
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

    private void startSearch() {
        if(mGraph != null) {
            disableButton();
            showResult("--", "--");
            ThreadPool.execute(new SearchTask());
        } else {
            showErrorDialog("图为空");
        }
    }

    /**
     * 遍历任务
     */
    private class SearchTask implements Runnable {

        @Override
        public void run() {
            try {
                long time = search(mStartPoint, mEndPoint);
                Message msg = Message.obtain(mHandler, 0);
                msg.arg1 = ((int) time);
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                Message msg = Message.obtain(mHandler, 1);
                msg.obj = e;
                mHandler.sendMessage(msg);
            }
        }
    }

    /**
     * 开始查找最短路径
     */
    private long search(Point startPoint, Point endPoint) {
        long start = System.currentTimeMillis();

        mShortestPath = Search.shortestPath(startPoint, endPoint);

        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 显示处理结果
     */
    private void showResult(String result, String time) {
        mTvResult.setText(result);
        mTvTime.setText(time);
    }
}
