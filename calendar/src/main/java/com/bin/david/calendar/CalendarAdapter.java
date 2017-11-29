package com.bin.david.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Calendar;

/**
 * Created by huang on 2017/11/22.
 */

public abstract class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    protected Context mContext;
    private CustomDate today = new CustomDate();

    public static final int MONTH_MODE = 0;
    public static final int WEEK_MODE = 1;
    private int mode = MONTH_MODE;

    public CalendarAdapter(int mode) {
        this.mode = mode;
    }

    public CalendarAdapter() {
        this.mode = MONTH_MODE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        CalendarView calendarView;
        if(viewType == MONTH_MODE) {
            calendarView = (CalendarMonthView) mLayoutInflater.inflate(R.layout.item_month_calendar, parent, false);
        }else{
             calendarView = (CalendarWeekView) mLayoutInflater.inflate(R.layout.item_week_calendar, parent, false);
        }
        decorateCalendarView(calendarView);
        return new ViewHolder(calendarView);
    }

    //负责装饰日历
    protected abstract void decorateCalendarView(CalendarView calendarView);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        int realPosition = position - Integer.MAX_VALUE / 2;
        if(mode == MONTH_MODE) {
            CalendarMonthView monthView = (CalendarMonthView) holder.itemView;
            calendar.add(Calendar.MONTH, realPosition);
            CustomDate customDate = new CustomDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
            monthView.setShowDate(customDate);
        }else{
            CalendarWeekView monthView = (CalendarWeekView)holder.itemView;
            int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
            calendar.add(Calendar.DATE,(realPosition*7 -week));
            CustomDate customDate = new CustomDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
            monthView.setShowDate(customDate);
        }
    }

    public int getMonthPosition(CustomDate customDate){
        int position = (customDate.year - today.year)*12
                + (customDate.month - today.month);
        return  Integer.MAX_VALUE / 2 + position;
    }
    public int getWeekPosition(CustomDate customDate){

       int day =  DateUtil.betweenDays(customDate,today);
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int position = (day -week)/7;
        return  Integer.MAX_VALUE / 2 +position;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static  class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mode;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }
}
