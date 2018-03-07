package pers.xjh.note.ui.detail.android;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 18-3-7.
 */

public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    private List<String> mItems = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);

        mItems.add("1");
        mItems.add("2");
        mItems.add("3");
        mItems.add("4");
        mItems.add("5");
        mItems.add("6");
        mItems.add("7");
        mItems.add("8");
        mItems.add("9");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addItem();
                break;

            case R.id.btn_delete:
                deleteItem();
                break;
        }
    }

    private void addItem() {
        mItems.add(0, "新增条目");
        mAdapter.notifyItemInserted(0);
    }

    private void deleteItem() {
        mItems.remove(0);
        mAdapter.notifyItemRemoved(0);
    }

    private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvNoteName;

        public RecyclerViewViewHolder(View itemView) {
            super(itemView);
            mTvNoteName = itemView.findViewById(R.id.tv_note_name);
        }

        public void setText(String noteName) {
            mTvNoteName.setText(noteName);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

        @Override
        public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_note, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
            holder.setText(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 :mItems.size();
        }
    }
}
