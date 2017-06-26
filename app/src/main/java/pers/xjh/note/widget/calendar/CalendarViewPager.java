package pers.xjh.note.widget.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import pers.xjh.note.adapter.CalendarAdapter;

/**
 * Created by XJH on 2017/3/28.
 */

public class CalendarViewPager extends ViewPager {

    //适配器
    private CalendarAdapter mAdapter;

    public CalendarViewPager(Context context) {
        this(context, null);
    }

    public CalendarViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mAdapter = new CalendarAdapter(getContext());
        this.setAdapter(mAdapter);
        //移动到最后一页
        this.setCurrentItem(mAdapter.getCount() / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return true;
    }
}
