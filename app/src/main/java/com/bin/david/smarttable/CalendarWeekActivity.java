package com.bin.david.smarttable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bin.david.calendar.CalendarRecyclerHelper;
import com.bin.david.calendar.CalendarView;
import com.bin.david.calendar.CustomDate;
import com.bin.david.smarttable.adapter.CustomCalendarAdapter;
/**
 * Created by huang on 2017/11/9.
 */

public class CalendarWeekActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private TextView showTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        showTv = (TextView) findViewById(R.id.tv_show_date);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        CalendarRecyclerHelper.init(this, recyclerView, new CustomCalendarAdapter(CustomCalendarAdapter.WEEK_MODE), new CalendarView.OnCalendarPageChanged() {
            @Override
            public void onPageChanged(CustomDate showDate) {
                showTv.setText(showDate.year+"年"+showDate.month+"月");
            }
        });
    }
}
