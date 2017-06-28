package pers.xjh.note.ui.detail.android;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.adapter.ViewPagerAdapter;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.DetailImageView;

/**
 * Created by XJH on 2017/5/18.
 */

public class ViewPagerActivity extends BaseActivity {

    private ViewPager mViewPager;

    @Override
    protected int initContentView() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        ImageView imageView1 = new DetailImageView(this);
        imageView1.setImageResource(R.drawable.xihu);

        ImageView imageView2 = new DetailImageView(this);
        imageView2.setImageResource(R.drawable.beauty);

        ImageView imageView3 = new DetailImageView(this);
        imageView3.setImageResource(R.drawable.luffy);

        ImageView imageView4 = new DetailImageView(this);
        imageView4.setImageResource(R.drawable.james);

        List<View> viewList = new ArrayList<>();

        viewList.add(imageView1);
        viewList.add(imageView2);
        viewList.add(imageView3);
        viewList.add(imageView4);

        mViewPager.setAdapter(new ViewPagerAdapter<>(viewList));
    }
}
