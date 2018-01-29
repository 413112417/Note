package pers.xjh.note.ui.detail.android;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.runtime.AppRuntime;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.FileUtil;

/**
 * Created by xjh on 18-1-23.
 */

public class FileEncryptActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    /** 加密文件路径 */
    private List<String> mEncryptedFiles = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_file_encrypt;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new FileEncryptAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FileEncryptActivity.this));
        mRecyclerView.setAdapter(mAdapter);

        queryEncryptedFiles(mEncryptedFiles);

        mAdapter.notifyDataSetChanged();
    }

    private class FileEncryptViewHolder extends RecyclerView.ViewHolder {

        private String path;

        public FileEncryptViewHolder(View itemView) {
            super(itemView);
        }

        public void setPath(String path) {
            this.path = path;
            ((TextView) itemView.findViewById(R.id.tv_file_name)).setText(path);
        }
    }

    private class FileEncryptAdapter extends RecyclerView.Adapter<FileEncryptViewHolder> {

        @Override
        public FileEncryptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FileEncryptViewHolder(LayoutInflater.from(FileEncryptActivity.this).inflate(R.layout.item_file_encrypt, parent, false));
        }

        @Override
        public void onBindViewHolder(FileEncryptViewHolder holder, int position) {
            holder.setPath(mEncryptedFiles.get(position));
        }

        @Override
        public int getItemCount() {
            return mEncryptedFiles == null ? 0 : mEncryptedFiles.size();
        }
    }

    /**
     * 查询加密文件
     */
    public void queryEncryptedFiles(List<String> encryptedFiles) {
        if(encryptedFiles == null) return;

        encryptedFiles.clear();

        String[] projection = new String[] { MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        Cursor cursor = getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " like ?",
                new String[]{"%" + FileUtil.CIPHER_TEXT_SUFFIX},
                null);

        while(cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            encryptedFiles.add(path);
        }
    }
}
