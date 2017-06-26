package pers.xjh.note.ui.detail.android;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.ProcessInfo;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.RecyclerViewDivider;

/**
 * Created by XJH on 2017/4/30.
 */

public class ActivityManagerActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private ActivityManager mActivityManager;

    private List<ProcessInfo> mProcessInfoList;

    @Override
    protected int initContentView() {
        return R.layout.activity_activity_manager;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(0, 0, 0, 10));
    }

    @Override
    protected void start() {
        mProcessInfoList = getRunningProcessInfo();
        mRecyclerView.setAdapter(new ProcessInfoAdapter());
    }

    /**
     * 获得正在运行的进程的信息
     * @return
     */
    private List<ProcessInfo> getRunningProcessInfo() {
        List<ProcessInfo> processInfoList = new ArrayList<>();

        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = mActivityManager.getRunningAppProcesses();

        for(int i=0; i<runningAppProcessInfoList.size(); i++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcessInfoList.get(i);
            int pid = runningAppProcessInfo.pid;
            int uid = runningAppProcessInfo.uid;
            String processName = runningAppProcessInfo.processName;

            int[] memoryPid = new int[] {pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(memoryPid);

            int memorySize = memoryInfo[0].getTotalPss();

            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setPid(pid + "");
            processInfo.setUid(uid + "");
            processInfo.setProcessName(processName);
            processInfo.setMemorySize(memorySize + "");

            processInfoList.add(processInfo);
        }
        return processInfoList;
    }

    private class ProcessInfoViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1, tv2, tv3, tv4;

        public ProcessInfoViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv_a);
            tv2 = (TextView) itemView.findViewById(R.id.tv_b);
            tv3 = (TextView) itemView.findViewById(R.id.tv_c);
            tv4 = (TextView) itemView.findViewById(R.id.tv_d);
        }

        public void bindView(ProcessInfo processInfo) {
            tv1.setText("pid:" + processInfo.getPid());
            tv2.setText("uid:" + processInfo.getUid());
            tv3.setText("内存:" + processInfo.getMemorySize() + "KB");
            tv4.setText("进程名:" + processInfo.getProcessName());
        }
    }

    private class ProcessInfoAdapter extends RecyclerView.Adapter<ProcessInfoViewHolder> {

        @Override
        public ProcessInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProcessInfoViewHolder(LayoutInflater.from(ActivityManagerActivity.this).inflate(R.layout.item_process_info, parent, false));
        }

        @Override
        public void onBindViewHolder(ProcessInfoViewHolder holder, int position) {
            holder.bindView(mProcessInfoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mProcessInfoList == null ? 0 : mProcessInfoList.size();
        }
    }
}
