package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.runtime.AppRuntime;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;

/**
 * 图片墙
 * Created by XJH on 2017/4/29.
 */

public class ImageWallActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    /** 图片路径 */
    private ArrayList<String> mImagePathList = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_image_wall;
    }

    @Override
    protected void initView() {
        mAdapter = new ImageWallAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ImageWallActivity.this, 3));
        mRecyclerView.setAdapter(mAdapter);

        queryImages(mImagePathList);

        mAdapter.notifyDataSetChanged();
    }

    private class ImageWallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        /** 图片的路径 */
        private int index;

        public ImageWallViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        public void setIndex(int position) {
            this.index = position;
        }

        public void show(String path) {
            showImage(imageView, path);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ImageWallActivity.this, ImageDetailActivity.class);
            intent.putExtra(Constant.KEY_IMAGE_URL, mImagePathList.toArray(new String[]{}));
            intent.putExtra(Constant.KEY_IMAGE_INDEX, index);
            ImageWallActivity.this.startActivity(intent);
        }
    }

    private class ImageWallAdapter extends RecyclerView.Adapter<ImageWallViewHolder> {

        @Override
        public ImageWallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ImageWallActivity.this).inflate(R.layout.item_image_wall, parent, false);
            return new ImageWallViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageWallViewHolder holder, int position) {
            String path = mImagePathList.get(position);
            holder.setIndex(position);
            holder.show(path);
        }

        @Override
        public int getItemCount() {
            if(mImagePathList != null) {
                return mImagePathList.size();
            }
            return 0;
        }
    }

    /**
     * 查询图片
     */
    public void queryImages(List<String> imagePathList) {
        //通过ContentResolver去查询手机里jpg和png格式的图片,按照修改的时间排序
        Cursor cursor = AppRuntime.getApplication().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
        while(cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imagePathList.add(path);
        }
        if(imagePathList != null && imagePathList.size() > 0) {
            //排序反转，把最近修改的图片放前面
            Collections.reverse(imagePathList);
        }
    }
}
