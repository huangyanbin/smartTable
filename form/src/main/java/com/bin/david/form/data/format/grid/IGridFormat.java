package com.bin.david.form.data.format.grid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;

/**
 * Created by huang on 2018/3/9.
 * 表格网格绘制格式化
 */

public interface IGridFormat {

    /**
     * 绘制内容格子网格
     * @param canvas 画布
     * @param col 列
     * @param row 行
     * @param rect 方位
     * @param cellInfo 格子信息
     * @param paint 画笔
     */
    void drawContentGrid(Canvas canvas, int col, int row, Rect rect, CellInfo cellInfo, Paint paint);
    /**
     * 绘制X序列格子网格
     * @param canvas 画布
     * @param col 列
     * @param rect 方位
     * @param paint 画笔
     */
    void drawXSequenceGrid(Canvas canvas,int col, Rect rect, Paint paint);
    /**
     * 绘制Y序列格子网格
     * @param canvas 画布
     * @param row 行
     * @param rect 方位
     * @param paint 画笔
     */
    void drawYSequenceGrid(Canvas canvas,int row, Rect rect, Paint paint);
    /**
     * 绘制统计行格子网格
     * @param canvas 画布
     * @param col 列
     * @param rect 方位
     * @param paint 画笔
     */
    void drawCountGrid(Canvas canvas, int col, Rect rect, Column column, Paint paint);

    /**
     * 绘制列标题网格
     * @param canvas 画布
     * @param rect 方位
     * @param paint 画笔
     */
    void drawColumnTitleGrid(Canvas canvas, Rect rect, Column column,int col, Paint paint);
    /**
     * 绘制表格边框网格
     * @param canvas 画布
     * @param rect 方位
     * @param paint 画笔
     */
     void drawTableBorderGrid(Canvas canvas, int left,int top,int right,int bottom,Paint paint);
    /**
     * 绘制左上角空隙网格
     * @param canvas 画布
     * @param rect 方位
     * @param paint 画笔
     */
     void drawLeftAndTopGrid(Canvas canvas, Rect rect,Paint paint);
}
