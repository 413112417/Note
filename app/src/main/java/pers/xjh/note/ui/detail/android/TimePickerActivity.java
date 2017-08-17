package pers.xjh.note.ui.detail.android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ToastUtil;

/**
 * Created by xjh on 17-8-17.
 */

public class TimePickerActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int initContentView() {
        return R.layout.activity_time_picker;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_date_picker).setOnClickListener(this);
        findViewById(R.id.btn_time_picker).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_date_picker:
                showDatePickerDialog();
                break;
            case R.id.btn_time_picker:
                showTimePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog() {
        final Calendar calender = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ToastUtil.show(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calender = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ToastUtil.show(hourOfDay + "时" + minute + "分");
            }
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE),
                true);

        dialog.show();
    }
}
