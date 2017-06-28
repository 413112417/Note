package pers.xjh.note.ui.detail.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.ui.detail.android.fragment.AFragment;
import pers.xjh.note.ui.detail.android.fragment.BFragment;
import pers.xjh.note.ui.detail.android.fragment.CFragment;
import pers.xjh.note.ui.detail.android.fragment.DFragment;

/**
 * Created by XJH on 2017/5/18.
 */

public class FragmentPagerActivity extends BaseActivity {

    private ViewPager mViewPager;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_fragment_pager;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AFragment());
        fragmentList.add(new BFragment());
        fragmentList.add(new CFragment());
        fragmentList.add(new DFragment());

        mAdapter = new FragmentPagerAdapter<>(getSupportFragmentManager(), fragmentList, new String[] {"A", "B", "C", "D"});
        mViewPager.setAdapter(mAdapter);

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

        mTitleBar.setTitle("A");
    }

    private class FragmentPagerAdapter<T extends Fragment> extends android.support.v4.app.FragmentPagerAdapter {

        private List<T> mFragmentList;
        private String[] mTitles;

        private FragmentPagerAdapter(FragmentManager fm, List<T> list, String[] titles) {
            super(fm);
            mFragmentList = list;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles == null ? super.getPageTitle(position) : mTitles[position];
        }
    }
}
