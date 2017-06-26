package pers.xjh.test.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pers.xjh.test.R;
import pers.xjh.test.model.CrashInfo;
import pers.xjh.test.ui.base.BaseActivity;
import pers.xjh.test.util.CompratorByLastModified;
import pers.xjh.test.util.FileUtil;
import pers.xjh.test.util.RecyclerViewDivider;

/**
 * Created by XJH on 2017/4/30.
 */

public class CrashListActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private CrashListAdapter mAdapter;

    private List<CrashInfo> mCrashInfoList = new ArrayList<>();

    /** 时间格式化 */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH: mm: ss");

    @Override
    protected int initContentView() {
        return R.layout.test_activity_crash_list;
    }

    /**
     * 初始化标题
     */
    @Override
    protected void initTitle() {
        super.initTitle();
        mTitleBar.setTitle("崩溃列表");
        mTitleBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new CrashListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(0, 0, 0, 10));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void start() {
        loadCrashInfo(mCrashInfoList);
    }

    /**
     * 加载崩溃信息
     */
    public void loadCrashInfo(List<CrashInfo> crashInfoList) {

        if(crashInfoList == null) {
            return;
        }

        File[] crashFiles = FileUtil.getSortedCrashFile(this, new CompratorByLastModified());
        for(int a=0; a<crashFiles.length; a++) {
            CrashInfo crashInfo = new CrashInfo(sdf.format(new Date(crashFiles[a].lastModified())), loadCrashInfo(crashFiles[a]));
            crashInfoList.add(crashInfo);
        }
    }

    /**
     * 加载崩溃信息
     */
    public String loadCrashInfo(File crashFile) {
        StringBuilder sbContent = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(crashFile);
            int length;
            byte[] buffer = new byte[1024];
            while((length = fis.read(buffer)) != -1) {
                sbContent.append(new String(buffer, 0, length));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbContent.toString();
    }

    private class CrashListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCrashTime;

        private String info;

        public CrashListViewHolder(View itemView) {
            super(itemView);
            tvCrashTime = (TextView) itemView.findViewById(R.id.tv_crash_time);
        }

        public void bindView(CrashInfo crashInfo) {
            tvCrashTime.setText(crashInfo.getTime());
            info = crashInfo.getInfo();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CrashListActivity.this, CrashInfoActivity.class);
            //intent传递数据过大会奔溃
            if(info.length() > 1024) {
                info = info.substring(0, 1024) + "...";
            }
            intent.putExtra("crashInfo", info);
            startActivity(intent);
        }
    }

    private class CrashListAdapter extends RecyclerView.Adapter<CrashListViewHolder> {

        @Override
        public CrashListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CrashListViewHolder(LayoutInflater.from(CrashListActivity.this).inflate(R.layout.test_item_crash, parent, false));
        }

        @Override
        public void onBindViewHolder(CrashListViewHolder holder, int position) {
            holder.bindView(mCrashInfoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrashInfoList == null ? 0 : mCrashInfoList.size();
        }
    }
}
