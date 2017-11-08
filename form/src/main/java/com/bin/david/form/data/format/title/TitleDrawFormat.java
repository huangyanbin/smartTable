package com.bin.david.form.data.format.title;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/10/30.
 */

public class TitleDrawFormat implements ITitleDrawFormat {


    @Override
    public int measureWidth(Column column, TableConfig config) {
        Paint paint = config.getPaint();
        config.getColumnTitleStyle().fillPaint(paint);
        return (int) (paint.measureText(column.getColumnName())
                        +2*config.getHorizontalPadding());
    }


    @Override
    public int measureHeight(TableConfig config) {
        Paint paint = config.getPaint();
        config.getColumnTitleStyle().fillPaint(paint);
        return DrawUtils.getTextHeight(config.getColumnTitleStyle(),config.getPaint())
                + 2*config.getVerticalPadding();
    }

    @Override
    public void draw(Canvas c, Column column, int left, int top, int right, int bottom, TableConfig config) {
        Paint paint = config.getPaint();
        drawBackground(c,left,top,right,bottom,paint);
        config.getColumnTitleStyle().fillPaint(paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(paint.getTextSize()*config.getZoom());
        c.drawText(column.getColumnName(),(right +left)/2, DrawUtils.getTextCenterY((bottom+top)/2,paint) ,paint);
    }

    @Override
    public void drawBackground(Canvas c, int left, int top, int right, int bottom, Paint paint) {

    }
}
