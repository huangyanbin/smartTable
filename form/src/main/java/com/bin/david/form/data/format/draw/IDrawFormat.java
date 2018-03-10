package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2017/10/30.
 * 绘制格式化
 */

public interface IDrawFormat<T>  {

    /**
     *测量宽
     */
    int measureWidth(Column<T> column, int position, TableConfig config);

    /**
     *测量高
     */
    int measureHeight(Column<T> column,int position, TableConfig config);


    void draw(Canvas c, Rect rect, CellInfo<T> cellInfo, TableConfig config);




}
