package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/10/30.
 */

public class TextDrawFormat<T> implements IDrawFormat<T> {

    //避免多次计算
    private int height;
    private CellInfo<T> cellInfo = new CellInfo<>();
    private boolean isDrawBg =true;

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
    public void draw(Canvas c, Column<T> column,T t, String value, int left, int top, int right, int bottom, int position, TableConfig config) {
        cellInfo.set(column,t,value,position);
        boolean isDrawBg = drawBackground(c,cellInfo,left,top,right,bottom,config);
        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        IBackgroundFormat<CellInfo> backgroundFormat = config.getContentBackgroundFormat();
        if(isDrawBg && backgroundFormat.getTextColor(cellInfo) != TableConfig.INVALID_COLOR){
            paint.setColor( backgroundFormat.getTextColor(cellInfo));
        }
        paint.setTextSize(paint.getTextSize()*config.getZoom());
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawText(value,(right +left)/2, DrawUtils.getTextCenterY((bottom+top)/2,paint) ,paint);
    }

    @Override
    public boolean drawBackground(Canvas c, CellInfo<T> cellInfo, int left, int top, int right, int bottom,TableConfig config) {
        IBackgroundFormat<CellInfo> backgroundFormat = config.getContentBackgroundFormat();
        if(isDrawBg && backgroundFormat != null && backgroundFormat.isDraw(cellInfo)){
            backgroundFormat.drawBackground(c,left,top,right,bottom,config.getPaint());
            return true;
        }
        return false;
    }

    public boolean isDrawBg() {
        return isDrawBg;
    }

    public void setDrawBg(boolean drawBg) {
        isDrawBg = drawBg;
    }
}
