package pers.xjh.note.widget.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.adapter.CalendarAdapter;

import java.text.SimpleDateFormat;

/**
 * 日历控件
 * Created by XJH on 2017/3/28.
 */

public class CalendarView extends FrameLayout implements View.OnClickListener {

    //切换的ViewPager
    private CalendarViewPager mCalendarViewPager;
    //时间范围
    private TextView mTvTimeRange;
    //左右箭头
    private View leftArrow, rightArrow;
    //时间格式转换
    private SimpleDateFormat mSimpleDateFormat;
    //时间标签
    private TextView tvMon, tvTues, tvWed, tvThur, tvFri, tvSat, tvSun;
    //点击监听器
    private OnPositionClickListener onPositionClickListener;
    //页面切换坚挺
    private OnPageSelectedListener onPageSelectedListener;
    //记录是否处于滚动状态
    private boolean isScroll;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    /**
     * 初始化
     */
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_calendar, this, true);

        mTvTimeRange = (TextView) findViewById(R.id.tv_time_range);
        mCalendarViewPager = (CalendarViewPager) findViewById(R.id.calendar_view_pager);
        leftArrow = findViewById(R.id.left_arrow);
        rightArrow = findViewById(R.id.right_arrow);
        tvMon = (TextView) findViewById(R.id.tv_mon);
        tvTues = (TextView) findViewById(R.id.tv_tues);
        tvWed = (TextView) findViewById(R.id.tv_wed);
        tvThur = (TextView) findViewById(R.id.tv_thur);
        tvFri = (TextView) findViewById(R.id.tv_fri);
        tvSat = (TextView) findViewById(R.id.tv_sat);
        tvSun = (TextView) findViewById(R.id.tv_sun);

        tvMon.setOnClickListener(this);
        tvTues.setOnClickListener(this);
        tvWed.setOnClickListener(this);
        tvThur.setOnClickListener(this);
        tvFri.setOnClickListener(this);
        tvSat.setOnClickListener(this);
        tvSun.setOnClickListener(this);

        mCalendarViewPager.setOffscreenPageLimit(0);

        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);

        mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

        mCalendarViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                CalendarWeekView calendarWeekView = (CalendarWeekView) ((CalendarAdapter) mCalendarViewPager.getAdapter()).getPrimaryItem();
                calendarWeekView.setCalendarView(CalendarView.this);
                CalendarView.this.clearColor();
                calendarWeekView.clearColor();

                mTvTimeRange.setText(mSimpleDateFormat.format(calendarWeekView.getStartTime()) +
                        " ～ " + mSimpleDateFormat.format(calendarWeekView.getEndTime()));
            }

            @Override
            public void onPageSelected(int position) {
                CalendarWeekView calendarWeekView = (CalendarWeekView) ((CalendarAdapter) mCalendarViewPager.getAdapter()).getPrimaryItem();
                calendarWeekView.setCalendarView(CalendarView.this);
                if(onPageSelectedListener != null) {
                    onPageSelectedListener.onPageSelected();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 1) {
                    isScroll = true;
                } else {
                    isScroll = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        clearColor();

        //正在滚动则不处理
        if(isScroll) {
            return;
        }

        //获取当前显示的View
        CalendarWeekView calendarWeekView = (CalendarWeekView) ((CalendarAdapter) mCalendarViewPager.getAdapter()).getPrimaryItem();
        calendarWeekView.clearColor();
        switch(v.getId()) {
            case R.id.tv_mon:
                setClickPosition(0);
                calendarWeekView.click(0);
                break;
            case R.id.tv_tues:
                setClickPosition(1);
                calendarWeekView.click(1);
                break;
            case R.id.tv_wed:
                setClickPosition(2);
                calendarWeekView.click(2);
                break;
            case R.id.tv_thur:
                setClickPosition(3);
                calendarWeekView.click(3);
                break;
            case R.id.tv_fri:
                setClickPosition(4);
                calendarWeekView.click(4);
                break;
            case R.id.tv_sat:
                setClickPosition(5);
                calendarWeekView.click(5);
                break;
            case R.id.tv_sun:
                setClickPosition(6);
                calendarWeekView.click(6);
                break;
            case R.id.left_arrow:
                mCalendarViewPager.setCurrentItem(mCalendarViewPager.getCurrentItem() + 1, true);
                break;
            case R.id.right_arrow:
                mCalendarViewPager.setCurrentItem(mCalendarViewPager.getCurrentItem() - 1, true);
                break;
        }
    }

    /**
     * 设置点击的位置
     */
    public void setClickPosition(int position) {
        clearColor();
        switch (position) {
            case 0:
                tvMon.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 1:
                tvTues.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 2:
                tvWed.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 3:
                tvThur.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 4:
                tvFri.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 5:
                tvSat.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 6:
                tvSun.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
        }
        if(onPositionClickListener != null) {
            onPositionClickListener.onPositionClick(position);
        }
    }
    /**
     * 清除颜色
     */
    public void clearColor() {
        tvMon.setBackgroundColor(getResources().getColor(R.color.white));
        tvTues.setBackgroundColor(getResources().getColor(R.color.white));
        tvWed.setBackgroundColor(getResources().getColor(R.color.white));
        tvThur.setBackgroundColor(getResources().getColor(R.color.white));
        tvFri.setBackgroundColor(getResources().getColor(R.color.white));
        tvSat.setBackgroundColor(getResources().getColor(R.color.white));
        tvSun.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void setOnPositionClickListener(OnPositionClickListener listener) {
        onPositionClickListener = listener;
    }

    public interface OnPositionClickListener {
        void onPositionClick(int position);
    }

    public void setOnPageSelectedListener(OnPageSelectedListener listener) {
        onPageSelectedListener = listener;
    }

    public interface OnPageSelectedListener {
        void onPageSelected();
    }
}
