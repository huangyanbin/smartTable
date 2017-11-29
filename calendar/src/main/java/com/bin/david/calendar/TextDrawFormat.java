package com.bin.david.calendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;


/**
 * Created by huang on 2017/11/22.
 */

public abstract class TextDrawFormat implements CalendarView.IDrawFormat {

    private boolean isDrawLunar;
    private int interval = 5;
    public static final int FESTIVAL = 2; //节日

    public static final int SOLAR = 1; //节气

    public static final int DAY  = 0; //普通日子

    @Override
    public void onClick(CalendarView calendarView,CalendarView.Cell cell) {

    }


    @Override
    public void onDraw(CalendarView calendarView, Canvas canvas, CalendarView.Cell cell, Rect rect, Paint paint) {
        int type = getDateType(calendarView,cell);
        onDrawBackground(canvas,type,rect,paint);
        paint.setTextSize(calendarView.getDefaultTextSize());
        paint.setColor(calendarView.getDefaultTextColor());
        onDrawBefore(canvas,type,rect,paint);
        if(isDraw(type)) {
            drawText(calendarView, canvas,type, cell, rect, paint);
        }
        onDrawOver(canvas,type,rect,paint);
    }

    //绘制背景
    public void onDrawBackground(Canvas canvas,int type,Rect rect, Paint paint){

    }

    //绘制公历之前 可以设置公历字体和颜色
    public void onDrawBefore(Canvas canvas,int type,Rect rect, Paint paint){

    }

    //绘制农历之前 可以用来绘制背景和改变字体大小颜色
    public void onDrawLunarBefore(Canvas canvas,int type,Rect rect,int dayLevel,Paint paint){

    }
    //绘制之后
    public void onDrawOver(Canvas canvas,int type,Rect rect, Paint paint){

    }

    public abstract boolean isDraw(int type);

    public  String getContent(CalendarView.Cell cell,int type){
       return cell.getDate().day + "";
    }



    private void drawText(CalendarView calendarView,Canvas canvas,int type, CalendarView.Cell cell, Rect rect, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        if(isDrawLunar) {
            //绘制公历
            CustomDate date = cell.getDate();
            canvas.drawText(getContent(cell,getDateType(calendarView,cell)), rect.centerX(),
                    CalendarView.getTextCenterY(rect.centerY()-interval, paint), paint);
            int textHeight = CalendarView.getTextHeight(paint);
            //绘制农历
            int dayLevel = DAY;
            String lunar = LunarCalendar.getSolarCalendar(date.month,date.day);
            if(!TextUtils.isEmpty(lunar)){
                dayLevel = FESTIVAL;
            }else {
                 lunar = LunarCalendar.getTermString(date.year, date.month - 1, date.day);
                if (!TextUtils.isEmpty(lunar)) {
                    dayLevel = SOLAR;
                }else{
                     lunar = LunarCalendar.getLunar(date.year,date.month,date.day);
                }
            }
            onDrawLunarBefore(canvas,type,rect,dayLevel,paint);
            canvas.drawText(lunar,
                    rect.centerX(),
                    CalendarView.getTextCenterY(rect.centerY()+textHeight/2, paint), paint);
        }else{
            canvas.drawText(getContent(cell,getDateType(calendarView,cell)), rect.centerX(),
                    CalendarView.getTextCenterY(rect.centerY(), paint), paint);
        }
    }

    public boolean isDrawLunar() {
        return isDrawLunar;
    }

    public void setDrawLunar(boolean drawLunar) {
        isDrawLunar = drawLunar;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public void onDraw(CalendarView calendarView) {

    }
}

