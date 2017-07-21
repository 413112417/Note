package pers.xjh.note.ui.detail.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.adapter.FragmentPagerAdapter;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.fragment.AFragment;
import pers.xjh.note.ui.detail.android.fragment.BFragment;
import pers.xjh.note.ui.detail.android.fragment.CFragment;
import pers.xjh.note.ui.detail.android.fragment.DFragment;
import pers.xjh.note.widget.PagerSlidingTabStrip;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/5/18.
 */

public class FragmentPagerActivity extends BaseActivity {

    private ViewPager mViewPager;

    private PagerSlidingTabStrip mTab;

    private TitleBar mTitleBar;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_fragment_pager;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        mTitleBar = titleBar;
        mTitleBar.setTitle("A");
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTab = (PagerSlidingTabStrip) findViewById(R.id.tab);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AFragment());
        fragmentList.add(new BFragment());
        fragmentList.add(new CFragment());
        fragmentList.add(new DFragment());

        mAdapter = new FragmentPagerAdapter<>(getSupportFragmentManager(), fragmentList, new String[] {"A", "B", "C", "D"});
        mViewPager.setAdapter(mAdapter);
        mTab.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(mAdapter.getPageTitle(position) != null) {
                    mTitleBar.setTitle(mAdapter.getPageTitle(position).toString());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
