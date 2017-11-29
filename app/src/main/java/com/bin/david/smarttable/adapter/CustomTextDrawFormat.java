package com.bin.david.smarttable.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;

import com.bin.david.calendar.CalendarView;
import com.bin.david.calendar.CustomDate;
import com.bin.david.calendar.DateUtil;
import com.bin.david.calendar.TextDrawFormat;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.R;

/**
 * Created by huang on 2017/11/22.
 */

public class CustomTextDrawFormat extends TextDrawFormat {

    public static final int TODAY =1;
    public static final int CURRENT_MONTH_DAY = 2;
    public static final int OTHER_MONTH_DAY = 4;
    public static final int CLICK_DAY = 3;
    private int progress = 100;
    private CalendarView calendarView;



    private Context mContext;

    public CustomTextDrawFormat(Context context) {
        this.mContext = context;
        setDrawLunar(true);
        setInterval(DensityUtils.dp2px(context,7));
    }

    private CustomDate clickDate;

    @Override
    public void onDrawBackground(Canvas canvas, int type, Rect rect, Paint paint) {
        if(type == CLICK_DAY) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(mContext, R.color.selectColor));
            int r = Math.min(rect.width(), rect.height()) / 2 *progress/100;
            canvas.drawCircle(rect.centerX(), rect.centerY(), r, paint);
        }
    }

    @Override
    public void onDrawBefore(Canvas canvas, int type, Rect rect, Paint paint) {
        if(type == CLICK_DAY){
            paint.setColor(ContextCompat.getColor(mContext,R.color.white));
        }else if(type == OTHER_MONTH_DAY){
            paint.setColor(ContextCompat.getColor(mContext, R.color.cal_buckle_text_color));
        }
    }

    @Override
    public void onDrawLunarBefore(Canvas canvas, int type, Rect rect,int dayLevel,Paint paint) {
        paint.setTextSize(DensityUtils.sp2px(mContext, 9));
        if(type == CLICK_DAY) {
            paint.setColor(ContextCompat.getColor(mContext,R.color.white));
        }else{
            if(dayLevel == TextDrawFormat.FESTIVAL){
                paint.setColor(ContextCompat.getColor(mContext, R.color.arc1));
            }else if(dayLevel == TextDrawFormat.SOLAR){
                paint.setColor(ContextCompat.getColor(mContext, R.color.arc22));
            }else {
                paint.setColor(ContextCompat.getColor(mContext, R.color.arc_text));
            }
        }
    }



    @Override
    public boolean isDraw(int type) {
        return true;
    }

    @Override
    public int getDateType(CalendarView calendarView, CalendarView.Cell cell) {

        CustomDate showDate = calendarView.getShowDate();
        CustomDate compareDate = cell.getDate();
        if(clickDate != null && compareDate.isSameDay(clickDate)){
            return CLICK_DAY;
        }
        if(showDate.isSameMonth(compareDate)){
            return CURRENT_MONTH_DAY;
        }
        if(DateUtil.isToday(compareDate)){
            return TODAY;
        }
        return OTHER_MONTH_DAY;
    }

    @Override
    public String getContent(CalendarView.Cell cell, int type) {
        if(type == TODAY){
            return "今";
        }
        return super.getContent(cell, type);
    }

    @Override
    public void onDraw(final CalendarView calendarView) {
        if(this.calendarView == calendarView && progress == 0){
                ValueAnimator animator = ValueAnimator.ofInt(30,100).setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        progress = (int) animation.getAnimatedValue();
                        calendarView.invalidate();

                    }
                });
                animator.start();

        }
    }

    @Override
    public void onClick(CalendarView calendarView, CalendarView.Cell cell) {
        super.onClick(calendarView, cell);
        this.calendarView = calendarView;
        progress = 0;
        clickDate = cell.getDate();

    }
}
