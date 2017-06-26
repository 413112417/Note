package pers.xjh.note.widget.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.adapter.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by XJH on 2017/3/28.
 */

public class CalendarWeekView extends LinearLayout implements View.OnClickListener {

    private TextView tvMon, tvTues, tvWed, tvThur, tvFri, tvSat, tvSun;

    private View bgMon, bgTues, bgWed, bgThur, bgFri, bgSat, bgSun;

    private View layoutMon, layoutTues, layoutWed, layoutThur, layoutFri, layoutSat, layoutSun;
    //开始时间和结束时间
    private long mStartTime, mEndTime;
    //日历对象
    private Calendar mCalendar;
    //日历界面对象
    private CalendarView mCalendarView;

    private List<TextView> tvWeekList;

    public CalendarWeekView(Context context) {
        this(context, null);
    }

    public CalendarWeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置位置（0：当前星期，1：下个星期， -1：上个星期。依次类推）
     */
    public void setPosition(int position) {
        if(position < -CalendarAdapter.PAGE_COUNT / 2 || position > CalendarAdapter.PAGE_COUNT / 2) {
            throw new RuntimeException("time is out of range !");
        }
        //计算一天的毫秒数
        long dayTime = 24 * 60 * 60 * 1000;
        //获取当前时间
        long time = mCalendar.getTime().getTime();

        time += position * 7 * dayTime;

        mCalendar.setTimeInMillis(time);

        //获取当前是星期几
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        if(position == 0) {
            switch(dayOfWeek) {
                case Calendar.MONDAY:
                    bgMon.setVisibility(View.VISIBLE);
                    tvMon.setTextColor(getResources().getColor(R.color.white));
                    break;
                case Calendar.TUESDAY:
                    bgTues.setVisibility(View.VISIBLE);
                    tvTues.setTextColor(getResources().getColor(R.color.white));
                    break;
                case Calendar.WEDNESDAY:
                    bgWed.setVisibility(View.VISIBLE);
                    tvWed.setTextColor(getResources().getColor(R.color.white));
                    break;
                case Calendar.THURSDAY:
                    bgThur.setVisibility(View.VISIBLE);
                    tvThur.setTextColor(getResources().getColor(R.color.white));
                    break;
                case Calendar.FRIDAY:
                    bgFri.setVisibility(View.VISIBLE);
                    tvFri.setTextColor(getResources().getColor(R.color.white));
                    break;
                case Calendar.SATURDAY:
                    bgSat.setVisibility(View.VISIBLE);
                    tvSat.setTextColor(getResources().getColor(R.color.white));
                    break;
                case Calendar.SUNDAY:
                    bgSun.setVisibility(View.VISIBLE);
                    tvSun.setTextColor(getResources().getColor(R.color.white));
                    break;
            }
        }

        //将日期移动到星期一
        while(dayOfWeek != Calendar.MONDAY) {
            time -= dayTime;
            mCalendar.setTimeInMillis(time);
            dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        }

        //设置这个星期里的日期
        for(int i=0; i<7; i++) {
            if(i == 0) {
                mStartTime = time;
            } else if(i == 6) {
                mEndTime = time;
            }
            tvWeekList.get(i).setText(mCalendar.get(Calendar.DAY_OF_MONTH) + "");
            time += dayTime;
            mCalendar.setTimeInMillis(time);
        }
    }

    /**
     * 获取这个星期的起始时间
     * @return
     */
    public long getStartTime() {
        return mStartTime;
    }

    /**
     * 获取这个星期的结束时间
     * @return
     */
    public long getEndTime() {
        return mEndTime;
    }

    /**
     * 设置日历界面对象
     */
    public void setCalendarView(CalendarView calendarView) {
        mCalendarView = calendarView;
    }

    /**
     * 初始化
     */
    private void init() {
        //添加布局
        LayoutInflater.from(getContext()).inflate(R.layout.view_calendar_week, this, true);
        //获取日历对象实例
        mCalendar = Calendar.getInstance();

        tvMon = (TextView) findViewById(R.id.tv_mon);
        tvTues = (TextView) findViewById(R.id.tv_tues);
        tvWed = (TextView) findViewById(R.id.tv_wed);
        tvThur = (TextView) findViewById(R.id.tv_thur);
        tvFri = (TextView) findViewById(R.id.tv_fri);
        tvSat = (TextView) findViewById(R.id.tv_sat);
        tvSun = (TextView) findViewById(R.id.tv_sun);

        bgMon = findViewById(R.id.bg_mon);
        bgTues = findViewById(R.id.bg_tues);
        bgWed = findViewById(R.id.bg_wed);
        bgThur = findViewById(R.id.bg_thur);
        bgFri = findViewById(R.id.bg_fri);
        bgSat = findViewById(R.id.bg_sat);
        bgSun = findViewById(R.id.bg_sun);

        layoutMon = findViewById(R.id.layout_mon);
        layoutTues = findViewById(R.id.layout_tues);
        layoutWed = findViewById(R.id.layout_wed);
        layoutThur = findViewById(R.id.layout_thur);
        layoutFri = findViewById(R.id.layout_fri);
        layoutSat = findViewById(R.id.layout_sat);
        layoutSun = findViewById(R.id.layout_sun);

        layoutMon.setOnClickListener(this);
        layoutTues.setOnClickListener(this);
        layoutWed.setOnClickListener(this);
        layoutThur.setOnClickListener(this);
        layoutFri.setOnClickListener(this);
        layoutSat.setOnClickListener(this);
        layoutSun.setOnClickListener(this);

        tvWeekList = new ArrayList<>();
        tvWeekList.add(tvMon);
        tvWeekList.add(tvTues);
        tvWeekList.add(tvWed);
        tvWeekList.add(tvThur);
        tvWeekList.add(tvFri);
        tvWeekList.add(tvSat);
        tvWeekList.add(tvSun);
    }


    /**
     * 外部点击触发改变颜色
     * @param position
     */
    public void click(int position) {
        if(position < 0 || position > 6) {
            throw new RuntimeException("index is out of range !");
        }
        clearColor();
        setClickPosition(position);
    }

    /**
     * 清除颜色
     */
    public void clearColor() {
        layoutMon.setBackgroundColor(getResources().getColor(R.color.white));
        layoutTues.setBackgroundColor(getResources().getColor(R.color.white));
        layoutWed.setBackgroundColor(getResources().getColor(R.color.white));
        layoutThur.setBackgroundColor(getResources().getColor(R.color.white));
        layoutFri.setBackgroundColor(getResources().getColor(R.color.white));
        layoutSat.setBackgroundColor(getResources().getColor(R.color.white));
        layoutSun.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void setClickPosition(int position) {
        switch (position) {
            case 0:
                layoutMon.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 1:
                layoutTues.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 2:
                layoutWed.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 3:
                layoutThur.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 4:
                layoutFri.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 5:
                layoutSat.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
            case 6:
                layoutSun.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        clearColor();
        if(mCalendarView != null) {
            mCalendarView.clearColor();
        }
        switch (v.getId()) {
            case R.id.layout_mon:
                setClickPosition(0);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(0);
                }
                break;
            case R.id.layout_tues:
                setClickPosition(1);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(1);
                }
                break;
            case R.id.layout_wed:
                setClickPosition(2);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(2);
                }
                break;
            case R.id.layout_thur:
                setClickPosition(3);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(3);
                }
                break;
            case R.id.layout_fri:
                setClickPosition(4);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(4);
                }
                break;
            case R.id.layout_sat:
                setClickPosition(5);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(5);
                }
                break;
            case R.id.layout_sun:
                setClickPosition(6);
                if(mCalendarView != null) {
                    mCalendarView.setClickPosition(6);
                }
                break;
        }
    }
}
