package com.bin.david.calendar;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class CalendarMonthView extends CalendarView {

    private static final int INVALID = -1;

    public CalendarMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CalendarMonthView(Context context) {
        super(context);
    }


    public CalendarMonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShowDate(CustomDate showDate){
        mShowDate = showDate;
        fillMonthDate();
        invalidate();
    }


    @Override
    public void measureClickCell(int col, int row) {
        if(mShowDate != null) {
            if (col >= TOTAL_COL || row >= TOTAL_ROW)
                return;
            Cell cell = cells[row][col];
            if (cell == null || cell.getDate() == null) {
                return;
            }
            geCalendarDraw().onClick(this,cell);
            if(getOnClickListener() != null){
                getOnClickListener().onClick(this,cell);
            }
            fillMonthDate();
            invalidate();
        }
    }

    @Override
    public int getTotalRow() {
        return 6;
    }


    private void fillMonthDate() {

        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month - 1);
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month);
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year, mShowDate.month);
        int day = 0;
        for (int i = 0; i < TOTAL_ROW; i++) {
            if(cells[i] == null){
                cells[i] = new Cell[TOTAL_COL];
            }
            for (int j = 0; j < TOTAL_COL; j++) {
                int position = j + i * TOTAL_COL;
                int year = INVALID,month = INVALID,monthDay = INVALID;
                if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
                    day++;
                    year = mShowDate.year;
                    month = mShowDate.month;
                    monthDay = day;
                } else if (position < firstDayWeek) {
                    year = mShowDate.year;
                    month = mShowDate.month-1;
                    monthDay = lastMonthDays - (firstDayWeek - position - 1);
                } else if (position >= firstDayWeek + currentMonthDays) {
                    year = mShowDate.year;
                    month = mShowDate.month + 1;
                    monthDay = position - firstDayWeek - currentMonthDays + 1;
                }

                Cell cell = cells[i][j];
                if(year == INVALID){
                    cell.update(null,i,j);
                }else {
                    if (cell != null && cell.getDate() != null) {
                        cell.getDate().update(year,month,monthDay,j);
                        cell.update(cell.getDate(),i,j);
                    } else {
                        CustomDate date = new CustomDate(year,month,monthDay,j);
                        cells[i][j] = new Cell(date, i, j);
                    }
                }
            }
        }
    }

}
