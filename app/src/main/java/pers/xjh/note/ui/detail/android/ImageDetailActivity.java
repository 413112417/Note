package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.adapter.ViewPagerAdapter;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.widget.DetailImageView;
import pers.xjh.note.widget.PinchImageView;

/**
 * Created by XJH on 2017/4/25.
 */

public class ImageDetailActivity extends BaseActivity {

    //图片资源
    private int[] mImageResourceIds;
    //图片路径
    private String[] mImagePaths;
    //图片下标
    private int mIndex;

    private ViewPager mViewPager;

    @Override
    protected int initContentView() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if(intent != null) {
            mImageResourceIds = intent.getIntArrayExtra(Constant.KEY_IMAGE_URL);
            mImagePaths = intent.getStringArrayExtra(Constant.KEY_IMAGE_URL);
            mIndex = intent.getIntExtra(Constant.KEY_IMAGE_INDEX, 0);
        }
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        if(mImageResourceIds != null) {
            buildDetailImageView(mImageResourceIds);
        } else if(mImagePaths != null){
            buildDetailImageView(mImagePaths);
        }
    }

    @Override
    protected void start() {
        mViewPager.setCurrentItem(mIndex, false);
    }

    private void buildDetailImageView(int[] imageResourceIds) {
        List<ImageView> imageViewList = new ArrayList<>();
        for(int i : imageResourceIds) {
            ImageView imageView = new PinchImageView(this);
            imageView.setImageResource(i);
            imageViewList.add(imageView);
        }
        mViewPager.setAdapter(new ViewPagerAdapter<>(imageViewList));
    }

    private void buildDetailImageView(String[] mImagePaths) {
        List<ImageView> imageViewList = new ArrayList<>();
        for(String path : mImagePaths) {
            ImageView imageView = new PinchImageView(this);
            showImage(imageView, path);
            imageViewList.add(imageView);
        }
        mViewPager.setAdapter(new ViewPagerAdapter<>(imageViewList));
    }
}
