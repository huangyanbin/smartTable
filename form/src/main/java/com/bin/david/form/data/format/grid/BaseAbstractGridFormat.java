package com.bin.david.form.data.format.grid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;

/**
 * Created by huang on 2018/3/9.
 * 通用绘制网格格式化抽象类
 */

public abstract class BaseAbstractGridFormat implements IGridFormat {

    private Path path = new Path();

    /**
     * 是否绘制内容格子垂直方向
     * @param col 列
     * @param row 行
     * @param cellInfo 格子信息
     * @return 是否绘制
     */
    protected abstract boolean isShowVerticalLine(int col, int row,CellInfo cellInfo);
    /**
     * 是否绘制内容格子横向方向
     * @param col 列
     * @param row 行
     * @param cellInfo 格子信息
     * @return 是否绘制
     */
    protected abstract boolean isShowHorizontalLine(int col, int row,CellInfo cellInfo);

    /**
     * 是否绘制统计行垂直方向
     * @param col 列
     * @return 是否绘制
     */
    protected  boolean isShowCountVerticalLine(int col,Column column){
        return true;
    }

    /**
     * 是否绘制统计行横向方向
     * @param col 列
     * @return 是否绘制
     */
    protected  boolean isShowCountHorizontalLine(int col,Column column){
        return true;
    }

    /**
     * 是否绘制列标题垂直方向
     * @param col 列
     * @return 是否绘制
     */
    protected  boolean isShowColumnTitleVerticalLine(int col,Column column){
        return true;
    }

    /**
     * 是否绘制列标题横向方向
     * @param col 列
     * @return 是否绘制
     */
    protected  boolean isShowColumnTitleHorizontalLine(int col,Column column){
        return true;
    }

    /**
     * 是否绘制X序号行垂直方向
     * @param col 列
     * @return 是否绘制
     */
    protected  boolean isShowXSequenceVerticalLine(int col){
        return true;
    }
    /**
     * 是否绘制X序号行横向方向
     * @param col 列
     * @return 是否绘制
     */
    protected  boolean isShowXSequenceHorizontalLine(int col){
        return true;
    }

    /**
     * 是否绘制Y序号行垂直方向
     * @param row 行
     * @return 是否绘制
     */
    protected  boolean isShowYSequenceVerticalLine(int row){
        return true;
    }
    /**
     * 是否绘制Y序号行横向方向
     * @param row 行
     * @return 是否绘制
     */
    protected  boolean isShowYSequenceHorizontalLine(int row){
        return true;
    }

    /**
     * 绘制内容格子网格
     * @param canvas 画布
     * @param col 列
     * @param row 行
     * @param rect 方位
     * @param cellInfo 格子信息
     * @param paint 画笔
     */

    @Override
    public void drawContentGrid(Canvas canvas, int col, int row, Rect rect, CellInfo cellInfo, Paint paint) {
       drawGridPath(canvas,rect,paint,
               isShowHorizontalLine(col,row,cellInfo),
               isShowVerticalLine(col,row,cellInfo));
    }





    /**
     * 绘制X序列网格
     * @param canvas 画布
     * @param col 列
     * @param rect 方位
     * @param paint 画笔
     */
    @Override
    public void drawXSequenceGrid(Canvas canvas, int col, Rect rect, Paint paint) {
        drawGridPath(canvas,rect,paint,
                isShowXSequenceHorizontalLine(col),
                isShowXSequenceVerticalLine(col));
    }


    /**
     * 绘制Y序列网格
     * @param canvas 画布
     * @param row 行
     * @param rect 方位矩形
     * @param paint 画笔
     */
    @Override
    public void drawYSequenceGrid(Canvas canvas, int row, Rect rect, Paint paint) {
        drawGridPath(canvas,rect,paint,
                isShowYSequenceHorizontalLine(row),
                isShowYSequenceVerticalLine(row));
    }

    /**
     * 绘制统计行网格
     * @param canvas 画布
     * @param rect 方位矩形
     * @param paint 画笔
     */
    @Override
    public void drawCountGrid(Canvas canvas, int col, Rect rect, Column column, Paint paint) {
        drawGridPath(canvas,rect,paint,
                isShowCountHorizontalLine(col,column),
                isShowCountVerticalLine(col,column));
    }

    /**
     *  绘制列标题网格
     * @param canvas 画布
     * @param rect 方位矩形
     * @param column 列对象
     * @param paint 画笔
     */
    @Override
    public void drawColumnTitleGrid(Canvas canvas, Rect rect, Column column, int col,Paint paint) {
        drawGridPath(canvas,rect,paint,
                isShowColumnTitleHorizontalLine(col,column),
                isShowColumnTitleVerticalLine(col,column));
    }
    /**
     *  绘制表格边框网格
     * @param canvas 画布
     *  @param left  左
     *   @param  top 上
     *    @param   right 右
     *  @param  bottom 底
     * @param paint 画笔
     */
    @Override
    public void drawTableBorderGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {
        canvas.drawRect(left,top,right,bottom,paint);
    }

    /**
     *  绘制表格左上角空隙网格
     * @param canvas 画布
     * @param paint 画笔
     */
    public void drawLeftAndTopGrid(Canvas canvas, Rect rect,Paint paint){
        canvas.drawRect(rect,paint);
    }

    /**
     * 绘制网格路径
     * @param canvas 画布
     * @param rect 方位
     * @param paint 画笔
     */
    protected void drawGridPath(Canvas canvas, Rect rect, Paint paint,boolean isShowHorizontal,
                                boolean isShowVertical) {
        path.rewind();
        if(isShowHorizontal) {
            path.moveTo(rect.left, rect.top);
            path.lineTo(rect.right, rect.top);
        }
        if(isShowVertical) {
            if(!isShowHorizontal){
                path.moveTo(rect.right, rect.top);
            }
            path.lineTo(rect.right, rect.bottom);
        }
        if(isShowHorizontal || isShowVertical)
            canvas.drawPath(path,paint);
    }
}
