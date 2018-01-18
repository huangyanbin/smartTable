package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.LeftTopDrawFormat;
import com.bin.david.form.data.format.sequence.ISequenceFormat;
import com.bin.david.form.utils.DrawUtils;


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
    private Rect tempRect; //临时使用

    public YSequence() {
        rect = new Rect();
        tempRect = new Rect();
    }

    @Override
    public void onMeasure(Rect scaleRect, Rect showRect, TableConfig config) {
        this.scaleRect = scaleRect;
        int scaleWidth = (int) (width*(config.getZoom()>1?1:config.getZoom()));
        boolean fixed = config.isFixedYSequence();
        rect.top = scaleRect.top;
        rect.bottom = scaleRect.bottom;
        rect.left = fixed ?showRect.left:scaleRect.left;
        rect.right = rect.left+scaleWidth;
        if(fixed){
            scaleRect.left += scaleWidth;
            showRect.left +=scaleWidth;
            clipWidth = scaleWidth;
        }else{
            int disX = showRect.left - scaleRect.left;
            clipWidth=Math.max(0, scaleWidth-disX);
            showRect.left +=clipWidth;
            scaleRect.left += scaleWidth;
        }
    }

    @Override
    public void onDraw(Canvas canvas, Rect showRect, TableData<T> tableData, TableConfig config) {
        format = tableData.getYSequenceFormat();
        float hZoom = (config.getZoom()>1?1:config.getZoom());
        int totalSize = tableData.getLineSize();
        TableInfo info = tableData.getTableInfo();
        int topHeight = info.getTopHeight(hZoom);
        float top = rect.top + topHeight;
        int showLeft = showRect.left-clipWidth;
        boolean isFixTop = config.isFixedXSequence();
        int showTop = isFixTop ?(showRect.top+topHeight):showRect.top;
        int num = 0;
        float tempTop= top;
        boolean isFixedTitle = config.isFixedTitle();
        boolean isFixedCount = config.isFixedCountRow();
        if(isFixedTitle){
            int clipHeight;
            if(isFixTop){
                clipHeight = info.getTopHeight(hZoom);
            }else{
                int disY = showRect.top - scaleRect.top;
                clipHeight= Math.max(0, topHeight-disY);
            }
            tempTop = showRect.top + clipHeight;
        }

        tempRect.set(showLeft,(int)tempTop-topHeight,showRect.left,(int)tempTop);
        drawLeftAndTop(canvas,showRect,tempRect,config);

        canvas.save();
        canvas.clipRect(showLeft,showTop,
                showRect.left,showRect.bottom);
        DrawUtils.fillBackground(canvas, showLeft,showTop,
                showRect.left,showRect.bottom,config.getYSequenceBackgroundColor(),config.getPaint());
        if(config.isShowColumnTitle()) {
            for (int i = 0; i < info.getMaxLevel(); i++) {
                num++;
                float bottom = tempTop + info.getTitleHeight();
                if (DrawUtils.isVerticalMixRect(showRect, (int) top, (int) bottom)) {
                    tempRect.set(rect.left, (int) tempTop, rect.right, (int) bottom);
                    draw(canvas,tempRect, format.format(num), num, config);
                }
                tempTop = bottom;
                top += info.getTitleHeight();
            }
        }
        int tempBottom =  showRect.bottom;
        if(tableData.isShowCount() && isFixedCount){
            int bottom = showRect.bottom;
            tempBottom = bottom-info.getCountHeight();
            tempRect.set(rect.left, tempBottom,
                    rect.right, bottom);
            draw(canvas,tempRect,format.format(num +totalSize+1),num +totalSize+1, config);
        }
        if(isFixedTitle || isFixedCount){
            canvas.save();
            canvas.clipRect(showLeft,tempTop,showRect.left,tempBottom);
        }
        for(int i = 0; i < totalSize;i++){
            num++;
            float bottom = top+info.getLineHeightArray()[i]*config.getZoom();
            if(showRect.bottom >= rect.top) {
                if (DrawUtils.isVerticalMixRect(showRect, (int)top,  (int)bottom)) {
                    tempRect.set(rect.left, (int)top, rect.right, (int)bottom);
                    draw(canvas, tempRect,format.format(num),num, config);
                }
            }else{
                break;
            }
            top = bottom;
        }
        if(tableData.isShowCount() && !isFixedCount){
            num++;
            float bottom = top+info.getCountHeight();
            if(DrawUtils.isVerticalMixRect(showRect,(int)top,(int)bottom)) {
                tempRect.set(rect.left, (int)top, rect.right, (int)bottom);
                draw(canvas,rect, format.format(num),num, config);
            }
        }
        if(isFixedTitle || isFixedCount){
            canvas.restore();
        }
        canvas.restore();

    }

    /**
     * 绘制左上角空隙
     * @param canvas
     * @param rect
     * @param config
     */
    private void drawLeftAndTop(Canvas canvas,Rect showRect,Rect rect,TableConfig config){
        canvas.save();
        canvas.clipRect(rect.left,showRect.top,
                showRect.left,rect.bottom);
        Paint paint = config.getPaint();
        if(config.getLeftAndTopBackgroundColor() !=0){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(config.getLeftAndTopBackgroundColor());
            canvas.drawRect(rect,paint);
        }
        config.getSequenceGridStyle().fillPaint(paint);
        canvas.drawRect(rect,paint);
        LeftTopDrawFormat format = config.getLeftTopDrawFormat();
        if( format!=null){
            format.setImageSize(rect.width(),rect.height());
            config.getLeftTopDrawFormat().draw(canvas,null,null,null,rect,0,config);
        }
        canvas.restore();
    }

    private void draw(Canvas canvas,Rect rect,String text,int position,TableConfig config){
        Paint paint= config.getPaint();
        ICellBackgroundFormat<Integer> backgroundFormat = config.getYSequenceBgFormat();
        int textColor =TableConfig.INVALID_COLOR;
        if(backgroundFormat != null){
            backgroundFormat.drawBackground(canvas,rect,position,config.getPaint());
            textColor =  backgroundFormat.getTextColor(position);
        }
        config.getSequenceGridStyle().fillPaint(paint);
        canvas.drawRect(rect,paint);
        config.getYSequenceStyle().fillPaint(paint);

        if(textColor != TableConfig.INVALID_COLOR){
            paint.setColor(textColor);
        }
        float hZoom = (config.getZoom()>1?1:config.getZoom());
        paint.setTextSize(paint.getTextSize()*hZoom);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text,rect.centerX(), DrawUtils.getTextCenterY(rect.centerY(),paint) ,paint);
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
