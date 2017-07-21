package pers.xjh.note.ui.detail.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.adapter.FragmentPagerAdapter;
import pers.xjh.note.widget.PagerSlidingTabStrip;

/**
 * 嵌套的fragment
 * Created by XJH on 2017/7/21.
 */

public class NestFragment extends Fragment {

    private ViewPager mViewPager;

    private PagerSlidingTabStrip mTab;

    private FragmentPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nest, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mTab = (PagerSlidingTabStrip) view.findViewById(R.id.tab);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AFragment());
        fragmentList.add(new BFragment());
        fragmentList.add(new CFragment());

        //getChildFragmentManager()很关键
        //如果用getActivity().getSupportFragmentManager()会产生冲突
        mAdapter = new FragmentPagerAdapter<>(getChildFragmentManager(), fragmentList, new String[] {"A", "B", "C"});
        mViewPager.setAdapter(mAdapter);
        mTab.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
