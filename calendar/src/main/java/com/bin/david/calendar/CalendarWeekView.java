package com.bin.david.calendar;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class CalendarWeekView extends CalendarView {


    public CalendarWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CalendarWeekView(Context context) {
        super(context);
    }


    public CalendarWeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShowDate(CustomDate showDate) {
        mShowDate = showDate;
        fillWeekDate();
        invalidate();
    }


    @Override
    public void measureClickCell(int col, int row) {
        if (mShowDate != null) {
            if (col >= TOTAL_COL || row >= TOTAL_ROW)
                return;
            Cell cell = cells[row][col];
            if (cell == null || cell.getDate() == null) {
                return;
            }
            geCalendarDraw().onClick(this, cell);
            if (getOnClickListener() != null) {
                getOnClickListener().onClick(this, cell);
            }
            fillWeekDate();
            invalidate();
        }
    }

    @Override
    public int getTotalRow() {
        return 1;
    }


    private void fillWeekDate() {

        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month);
        cells[0] = new Cell[TOTAL_COL];
        for (int j = 0; j < TOTAL_COL; j++) {
            int year = mShowDate.year, month = mShowDate.month;
            int monthDay = mShowDate.day + j;
            if (monthDay > currentMonthDays) {
                month += 1;
                monthDay -= currentMonthDays;
            }
            Cell cell = cells[0][j];
            if (cell != null && cell.getDate() != null) {
                cell.getDate().update(year, month, monthDay, j);
                cell.update(cell.getDate(), 0, j);
            } else {
                CustomDate date = new CustomDate(year, month, monthDay, j);
                cells[0][j] = new Cell(date, 0, j);
            }

        }

    }

}
