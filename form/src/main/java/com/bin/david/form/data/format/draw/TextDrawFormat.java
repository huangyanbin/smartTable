package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/10/30.
 */

public class TextDrawFormat<T> implements IDrawFormat<T> {

    //避免多次计算
    private int height;

    @Override
    public int measureWidth(Column<T>column, TableConfig config) {

        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        return (int) paint.measureText(column.getLongestValue())
                +2*config.getHorizontalPadding();
    }

    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        if(height == 0){
            Paint paint = config.getPaint();
            config.getContentStyle().fillPaint(paint);
            height = DrawUtils.getTextHeight(config.getContentStyle(),config.getPaint())
                    + 2*config.getVerticalPadding();
        }
        return height;
    }

    @Override
    public void draw(Canvas c, T t, String value, int left, int top, int right, int bottom, int position, TableConfig config) {
        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        paint.setTextSize(paint.getTextSize()*config.getZoom());
        drawBackground(c,left,top,right,bottom,paint);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawText(value,(right +left)/2, DrawUtils.getTextCenterY((bottom+top)/2,paint) ,paint);
    }

    /**
     * 重写可以绘制背景
     */
    public void drawBackground(Canvas c, int left, int top, int right, int bottom, Paint paint){

    }
}
