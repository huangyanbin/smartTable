package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

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
        return (int) paint.measureText(column.getLongestValue());
    }

    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        if(height == 0){
            Paint paint = config.getPaint();
            config.getContentStyle().fillPaint(paint);
            height = DrawUtils.getTextHeight(config.getContentStyle(),config.getPaint());
        }
        return height;
    }

    @Override
    public void draw(Canvas c, Column<T> column, T t, String value, Rect rect, int position, TableConfig config) {
        cellInfo.set(column,t,value,position);
        boolean isDrawBg = drawBackground(c,cellInfo,rect,config);
        Paint paint = config.getPaint();
        setTextPaint(config, isDrawBg, paint);
        if(column.getTextAlign() !=null) {
            paint.setTextAlign(column.getTextAlign());
        }
        c.drawText(value,DrawUtils.getTextCenterX(rect.left,rect.right,paint), DrawUtils.getTextCenterY((rect.bottom+rect.top)/2,paint) ,paint);
    }


    protected void setTextPaint(TableConfig config, boolean isDrawBg, Paint paint) {
        config.getContentStyle().fillPaint(paint);
        IBackgroundFormat<CellInfo> backgroundFormat = config.getContentBackgroundFormat();
        if(isDrawBg && backgroundFormat.getTextColor(cellInfo) != TableConfig.INVALID_COLOR){
            paint.setColor(backgroundFormat.getTextColor(cellInfo));
        }
        paint.setTextSize(paint.getTextSize()*config.getZoom());

    }

    public boolean drawBackground(Canvas c, CellInfo<T> cellInfo, Rect rect,TableConfig config) {
        IBackgroundFormat<CellInfo> backgroundFormat = config.getContentBackgroundFormat();
        if(isDrawBg && backgroundFormat != null && backgroundFormat.isDraw(cellInfo)){
            backgroundFormat.drawBackground(c,rect,config.getPaint());
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

    public CellInfo<T> getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(CellInfo<T> cellInfo) {
        this.cellInfo = cellInfo;
    }
}
