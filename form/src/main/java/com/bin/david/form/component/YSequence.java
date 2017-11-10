package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.sequence.ISequenceFormat;
import com.bin.david.form.utils.DrawUtils;

import java.util.List;

/**
 * Created by huang on 2017/10/30.
 * 左边竖向排版
 */

public class YSequence<T> implements IComponent<TableData<T>> {

    private Rect rect;
    private int width;
    private int clipWidth;
    private Rect scaleRect;
    private ISequenceFormat format;

    public YSequence() {
        rect = new Rect();
    }

    @Override
    public void computeRect(Rect scaleRect,Rect showRect,TableConfig config) {
        this.scaleRect = scaleRect;
        int scaleWidth = (int) (width*config.getZoom());
        boolean fixed = config.isFixedYSequence();
        rect.top = scaleRect.top;
        rect.bottom = scaleRect.bottom;
        rect.left = fixed ?showRect.left:scaleRect.left;
        rect.right = rect.left+scaleWidth;
        if(fixed){
            scaleRect.left += scaleWidth;
            showRect.left +=scaleWidth;
        }else{
            int disX = showRect.left - scaleRect.left;
            clipWidth=Math.max(0, scaleWidth-disX);
            showRect.left +=clipWidth;
            scaleRect.left += scaleWidth;
        }
    }

    @Override
    public void draw(Canvas canvas,Rect showRect, TableData<T> tableData, TableConfig config) {
        format = tableData.getYSequenceFormat();
        List<T> tList = tableData.getT();
        int totalSize = tList.size();
        TableInfo info = tableData.getTableInfo();
        int top = rect.top + info.getTopHeight();
        canvas.save();
        int showLeft = showRect.left-clipWidth;
        boolean isFixTop = config.isFixedXSequence();
        canvas.clipRect(showLeft,isFixTop ?(showRect.top+info.getTopHeight()):showRect.top,
                showRect.left,showRect.bottom);
        DrawUtils.fillBackground(canvas, showLeft,isFixTop ?(showRect.top+info.getTopHeight()):showRect.top,
                showRect.left,showRect.bottom,config.getYSequenceBackgroundColor(),config.getPaint());
        int num = 0;
        //drawVerticalGrid(canvas,showRect,config);


        int tempTop= top;
        boolean isFixedTitle = config.isFixedTitle();
        boolean isFixedCount = config.isFixedCountRow();
        if(isFixedTitle){
            int clipHeight;
            if(isFixTop){
                clipHeight = info.getTopHeight();
            }else{
                int disY = showRect.top - scaleRect.top;
                clipHeight= Math.max(0, info.getTopHeight()-disY);
            }
            tempTop = showRect.top + clipHeight;
        }
     /*   path.rewind();
        path.moveTo(rect.left,tempTop);
        path.lineTo(rect.right,tempTop);
        canvas.drawPath(path,config.getPaint());*/
        for(int i = 0; i <info.getMaxLevel(); i++){
            num++;
            int bottom = tempTop+info.getTitleHeight();
            if(DrawUtils.isVerticalMixRect(showRect,top,bottom)) {

                draw(canvas, rect.left, tempTop, rect.right, bottom,  format.format(num), config);
            }
            tempTop = bottom;
            top +=info.getTitleHeight();
        }
        int tempBottom =  showRect.bottom;
        if(tableData.isShowCount() && isFixedCount){
            int bottom = showRect.bottom;
            tempBottom = bottom-info.getCountHeight();
            draw(canvas, rect.left, tempBottom,
                    rect.right, bottom,  format.format(num +totalSize+1), config);
        }
        if(isFixedTitle || isFixedCount){
            canvas.save();
            canvas.clipRect(showLeft,tempTop,showRect.left,tempBottom);
        }
        for(int i = 0; i < totalSize;i++){
            num++;
            int bottom = (int) (top+info.getLineHeightArray()[i]*config.getZoom());
            if(showRect.bottom >= rect.top) {
                if (DrawUtils.isVerticalMixRect(showRect, top,  bottom)) {
                    draw(canvas, rect.left, top, rect.right, bottom, format.format(num), config);
                }
            }else{
                break;
            }
            top = bottom;
        }
        if(tableData.isShowCount() && !isFixedCount){
            num++;
            int bottom = top+info.getCountHeight();
            if(DrawUtils.isVerticalMixRect(showRect,top,bottom)) {
                draw(canvas, rect.left, top, rect.right, bottom, format.format(num), config);
            }
        }
        if(isFixedTitle || isFixedCount){
            canvas.restore();
        }
        canvas.restore();

    }
/*    private void drawVerticalGrid(Canvas canvas,Rect showRect,TableConfig config){
        Paint paint =  config.getPaint();
        config.getGridStyle().fillPaint(paint);
        path.rewind();
        path.moveTo(rect.left,showRect.top >rect.top?showRect.top :rect.top);
        path.lineTo(rect.left,showRect.bottom <rect.bottom?showRect.bottom :rect.bottom);
        canvas.drawPath(path,paint);
        path.rewind();
        path.moveTo(rect.right,showRect.top >rect.top?showRect.top :rect.top);
        path.lineTo(rect.right,showRect.bottom <rect.bottom?showRect.bottom :rect.bottom);
        canvas.drawPath(path,paint);
    }*/

    private void draw(Canvas canvas,int left,int top, int right,int bottom,String text,TableConfig config){
        Paint paint= config.getPaint();
        config.getGridStyle().fillPaint(paint);
        canvas.drawRect(left,top,right,bottom,paint);
       /* path.reset();
        path.moveTo(left,bottom);
        path.lineTo(right,bottom);
        canvas.drawPath(path,paint);*/
        config.getYSequenceStyle().fillPaint(paint);
        paint.setTextSize(paint.getTextSize()*config.getZoom());
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text,(right +left)/2, DrawUtils.getTextCenterY((bottom+top)/2,paint) ,paint);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Rect getRect() {
        return rect;
    }

}
