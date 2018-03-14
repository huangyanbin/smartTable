package com.bin.david.form.data.format.grid;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;

/**
 * Created by huang on 2018/3/9.
 * 简单的绘制边界
 */

public  class SimpleGridFormat implements IGridFormat {


    @Override
    public void drawContentGrid(Canvas canvas, int col, int row, Rect rect, CellInfo cellInfo, Paint paint) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void drawXSequenceGrid(Canvas canvas, int col, Rect rect, Paint paint) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void drawYSequenceGrid(Canvas canvas, int row, Rect rect, Paint paint) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void drawCountGrid(Canvas canvas, int col, Rect rect, Column column, Paint paint) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void drawColumnTitleGrid(Canvas canvas, Rect rect, Column column, int col, Paint paint) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void drawTableBorderGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {
        canvas.drawRect(left,top,right,bottom,paint);
    }

    @Override
    public void drawLeftAndTopGrid(Canvas canvas, Rect rect, Paint paint) {
        canvas.drawRect(rect,paint);
    }
}
