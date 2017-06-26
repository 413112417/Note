package pers.xjh.note.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import pers.xjh.note.widget.calendar.CalendarWeekView;

/**
 * Created by XJH on 2017/3/28.
 */

public class CalendarAdapter extends PagerAdapter {

    //上下文
    private Context mContext;
    //当前页面
    private View mCurrentView;

    public static int PAGE_COUNT = 600;

    public CalendarAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CalendarWeekView weekView = new CalendarWeekView(mContext);
        weekView.setPosition(CalendarAdapter.PAGE_COUNT / 2 - position);
        container.addView(weekView);
        return weekView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }
}
