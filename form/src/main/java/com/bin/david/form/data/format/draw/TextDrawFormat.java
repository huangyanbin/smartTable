package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.utils.DrawUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by huang on 2017/10/30.
 */

public class TextDrawFormat<T> implements IDrawFormat<T> {

    private CellInfo<T> cellInfo = new CellInfo<>();
    private boolean isDrawBg = true;
    private Map<String,SoftReference<String[]>> valueMap; //避免产生大量对象

    public TextDrawFormat() {
        valueMap = new HashMap<>();
    }

    @Override
    public int measureWidth(Column<T>column, int position, TableConfig config) {
        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        return DrawUtils.getMultiTextWidth(paint,getSplitString(column.format(position)));
    }

    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        return DrawUtils.getMultiTextHeight(paint,getSplitString(column.format(position)));
    }

    @Override
    public void draw(Canvas c, Column<T> column, T t, String value, Rect rect, int position, TableConfig config) {
        cellInfo.set(column,t,value,position);
        drawBackground(c,cellInfo,rect,config);
        Paint paint = config.getPaint();
        setTextPaint(config,t, paint);
        if(column.getTextAlign() !=null) {
            paint.setTextAlign(column.getTextAlign());
        }
        drawText(c, value, rect, paint);
    }

    protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
        DrawUtils.drawMultiText(c,paint,rect,getSplitString(value));
    }


    public void setTextPaint(TableConfig config,T t, Paint paint) {
        config.getContentStyle().fillPaint(paint);
        ICellBackgroundFormat<CellInfo> backgroundFormat = config.getContentBackgroundFormat();
        if(backgroundFormat!=null && backgroundFormat.getTextColor(cellInfo) != TableConfig.INVALID_COLOR){
            paint.setColor(backgroundFormat.getTextColor(cellInfo));
        }
        paint.setTextSize(paint.getTextSize()*config.getZoom());

    }

    public void drawBackground(Canvas c, CellInfo<T> cellInfo, Rect rect,TableConfig config) {
        ICellBackgroundFormat<CellInfo> backgroundFormat = config.getContentBackgroundFormat();
        if(isDrawBg && backgroundFormat != null){
            backgroundFormat.drawBackground(c,rect,cellInfo,config.getPaint());
        }
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

    protected String[] getSplitString(String val){
        String[] values = null;
        if(valueMap.get(val)!=null){
            values= valueMap.get(val).get();
        }
        if(values == null){
            values = val.split("\n");
            valueMap.put(val, new SoftReference<>(values));
        }
        return values;
    }
}
