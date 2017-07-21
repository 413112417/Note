package pers.xjh.note.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by xujunhui on 2017/7/21.
 */

public class FragmentPagerAdapter<T extends Fragment> extends android.support.v4.app.FragmentPagerAdapter {

    private List<T> mFragmentList;
    private String[] mTitles;

    public FragmentPagerAdapter(FragmentManager fm, List<T> list, String[] titles) {
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
