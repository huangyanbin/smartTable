package com.bin.david.form.data.format.title;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.column.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/10/30.
 */

public class TitleDrawFormat implements ITitleDrawFormat {

    private boolean isDrawBg;

    @Override
    public int measureWidth(Column column, TableConfig config) {
        Paint paint = config.getPaint();
        config.getColumnTitleStyle().fillPaint(paint);
        return (int) (paint.measureText(column.getColumnName()));
    }


    @Override
    public int measureHeight(TableConfig config) {
        Paint paint = config.getPaint();
        config.getColumnTitleStyle().fillPaint(paint);
        return DrawUtils.getTextHeight(config.getColumnTitleStyle(),config.getPaint());
    }

    @Override
    public void draw(Canvas c, Column column, Rect rect, TableConfig config) {
        Paint paint = config.getPaint();
        boolean isDrawBg =drawBackground(c,column,rect,config);
        config.getColumnTitleStyle().fillPaint(paint);
        ICellBackgroundFormat<Column> backgroundFormat = config.getColumnCellBackgroundFormat();

        paint.setTextSize(paint.getTextSize()*config.getZoom());
        if(isDrawBg && backgroundFormat.getTextColor(column) != TableConfig.INVALID_COLOR){
            paint.setColor(backgroundFormat.getTextColor(column));
        }
        drawText(c, column, rect, paint);
    }

    private void drawText(Canvas c, Column column, Rect rect, Paint paint) {
        if(column.getTitleAlign() !=null) { //如果列设置Align ，则使用列的Align
            paint.setTextAlign(column.getTitleAlign());
        }
        c.drawText(column.getColumnName(), DrawUtils.getTextCenterX(rect.left,rect.right,paint), DrawUtils.getTextCenterY((rect.bottom+rect.top)/2,paint) ,paint);
    }


    public boolean drawBackground(Canvas c, Column column, Rect rect,  TableConfig config) {
        ICellBackgroundFormat<Column> backgroundFormat = config.getColumnCellBackgroundFormat();
        if(isDrawBg && backgroundFormat != null ){
            backgroundFormat.drawBackground(c,rect,column,config.getPaint());
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
