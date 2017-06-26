package pers.xjh.note.ui.detail.android;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.RecyclerViewDivider;

/**
 * Created by XJH on 2017/4/29.
 */

public class PackageManagerActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private AppInfoAdapter mAdapter;

    private List<ApplicationInfo> mAllApplicationInfoList;

    private List<ApplicationInfo> mShowApplicationInfoList;

    @Override
    protected int initContentView() {
        return R.layout.activity_package_manager;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_all).setOnClickListener(this);
        findViewById(R.id.btn_system).setOnClickListener(this);
        findViewById(R.id.btn_third).setOnClickListener(this);
        findViewById(R.id.btn_sd_card).setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(0, 0, 0, 10));

        mAdapter = new AppInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void start() {
        mAllApplicationInfoList = getPackageManager().getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        mShowApplicationInfoList = new ArrayList<>();
        mShowApplicationInfoList.addAll(mAllApplicationInfoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        mShowApplicationInfoList.clear();
        switch (v.getId()) {
            case R.id.btn_all:
                for(ApplicationInfo app : mAllApplicationInfoList) {
                    mShowApplicationInfoList.add(app);
                }
                break;
            case R.id.btn_system:
                for(ApplicationInfo app : mAllApplicationInfoList) {
                    if((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        mShowApplicationInfoList.add(app);
                    }
                }
                break;
            case R.id.btn_third:
                for(ApplicationInfo app : mAllApplicationInfoList) {
                    if((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        mShowApplicationInfoList.add(app);
                    } else if((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        mShowApplicationInfoList.add(app);
                    }
                }
                break;
            case R.id.btn_sd_card:
                for(ApplicationInfo app : mAllApplicationInfoList) {
                    if((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        mShowApplicationInfoList.add(app);
                    }
                }
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    private class AppInfoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAppIcon;

        private TextView tvAppName, tvAppPackage;

        public AppInfoViewHolder(View itemView) {
            super(itemView);
            imgAppIcon = (ImageView) itemView.findViewById(R.id.img_app_icon);
            tvAppName = (TextView) itemView.findViewById(R.id.tv_app_name);
            tvAppPackage = (TextView) itemView.findViewById(R.id.tv_app_package);
        }

        public void bindView(ApplicationInfo applicationInfo) {
            imgAppIcon.setImageDrawable(applicationInfo.loadIcon(getPackageManager()));
            tvAppName.setText(applicationInfo.loadLabel(getPackageManager()));
            tvAppPackage.setText(applicationInfo.packageName);
        }
    }

    private class AppInfoAdapter extends RecyclerView.Adapter<AppInfoViewHolder> {

        @Override
        public AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AppInfoViewHolder(LayoutInflater.from(PackageManagerActivity.this).inflate(R.layout.item_app_info, parent, false));
        }

        @Override
        public void onBindViewHolder(AppInfoViewHolder holder, int position) {
            holder.bindView(mShowApplicationInfoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mShowApplicationInfoList == null ? 0 : mShowApplicationInfoList.size();
        }
    }
}
