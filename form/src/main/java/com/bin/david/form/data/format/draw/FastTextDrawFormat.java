package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/10/30.
 * 快速字体格式化
 * 当解析数目大，且字体大小和单行时，这个更快可以测量出来，节省加载时间
 */

public class FastTextDrawFormat<T> extends TextDrawFormat<T> {


    private int height;
    private int width;
    private int maxLengthValue;

    @Override
    public int measureWidth(Column<T>column, int position, TableConfig config) {
        String value = column.format(position);
        if(value.length() >maxLengthValue){
            maxLengthValue = value.length();
            Paint paint = config.getPaint();
            config.getContentStyle().fillPaint(paint);
            width = (int) paint.measureText(value);
        }
        return width;
    }

    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        if(height ==0) {
            Paint paint = config.getPaint();
            config.getContentStyle().fillPaint(paint);
            height =  DrawUtils.getTextHeight(paint);
        }
        return height;
    }



    protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
        DrawUtils.drawSingleText(c,paint,rect,value);
    }



}
