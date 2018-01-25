package com.bin.david.form.core;

import android.graphics.Paint;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.LeftTopDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;

/**
 * Created by huang on 2017/11/1.
 */

public class TableConfig {
    /**
     * 默认字体样式
     */
    private static final FontStyle defaultFontStyle = new FontStyle();
    /**
     * 默认网格样式
     */
    private static final LineStyle defaultGridStyle = new LineStyle();
    /**
     * 无效值
     */
    public static final int INVALID_COLOR = 0;
    /**
     * 内容字体样式
     */
    private FontStyle contentStyle;
    /**
     * 左侧序号列字体样式
     */
    private FontStyle YSequenceStyle;
    /**
     * 顶部序号列字体样式
     */
    private FontStyle XSequenceStyle;
    /**
     * 列标题字体样式
     */
    private FontStyle columnTitleStyle;
    /**
     * 表格标题字体样式
     */
    private FontStyle tableTitleStyle;
    /**
     * 统计行字体样式
     */
    private FontStyle countStyle; //总和
    /**
     * 列标题网格样式
     */
    private LineStyle columnTitleGridStyle;
    /**
     * 序列网格样式
     */
    private LineStyle SequenceGridStyle;
    /**
     * 表格网格
     */
    private LineStyle gridStyle;
    /**
     * 上下padding(为了表格的美观，暂只支持统一的padding)
     */
    private int verticalPadding = 10;

    /**
     * 增加列标题上下padding
     */
    private int columnTitleVerticalPadding =10;
    /**
     * 增加列标题左右padding
     */
    private int columnTitleHorizontalPadding =40;
    /**
     * 左右padding(为了表格的美观，暂只支持统一的padding)
     */
    private int horizontalPadding= 40;
    /**
     * 左侧序号列背景
     */
    private int YSequenceBackgroundColor=INVALID_COLOR;
    /**
     * 顶部序号列背景
     */
    private int XSequenceBackgroundColor =INVALID_COLOR;
    /**
     * 组标题背景
     */
    private int columnTitleBackgroundColor =INVALID_COLOR;
    /**
     * 内容背景
     */
    private int contentBackgroundColor=INVALID_COLOR;
    /**
     * 统计行背景
     */
    private int countBackgroundColor=INVALID_COLOR;

    /**
     * 是否显示顶部序号列
     */
    private boolean isShowXSequence = true;
    /**
     * 是否显示左侧序号列
     */
    private boolean isShowYSequence = true;
    /**
     * 是否显示表格标题
     */
    private boolean isShowTableTitle = true;
    /**
     * 是否显示列标题
     */
    private boolean isShowColumnTitle = true;
    /**
     * 格子进行背景格式化
     */
    private ICellBackgroundFormat<CellInfo> contentBackgroundFormat;
    /**
     * 标题格子背景格式化
     */
    private ICellBackgroundFormat<Column> columnBackgroundFormat;
    /**
     * 顶部序号背景格式化
     */
    private ICellBackgroundFormat<Integer> XSequenceBgFormat;
    /**
     * 左序号背景格式化
     */
    private ICellBackgroundFormat<Integer> YSequenceBgFormat;

    private ICellBackgroundFormat<Column> countBgFormat;
    /**
     * 是否固定左侧
     *
     */
    private boolean fixedYSequence = false;
    /**
     * 固定顶部
     *
     */
    private boolean fixedXSequence = false;
    /**
     * 固定标题
     *
     */
    private boolean fixedTitle = true;
    /**
     * 固定第一列 （作废）
     *
     */
    private boolean fixedFirstColumn = true;
    /**
     * 是否固定统计行
     */
    private boolean fixedCountRow = true;
    /**
     * 左上角空隙背景颜色
     */
    private int leftAndTopBackgroundColor;
    /**
     * 左上角资源设置
     */
    private LeftTopDrawFormat leftTopDrawFormat;

    private int minTableWidth =-1;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 缩放值
     */
    private  float zoom = 1;

    public FontStyle getContentStyle() {
        if(contentStyle == null){
            return defaultFontStyle;
        }
        return contentStyle;
    }

    public TableConfig setContentStyle(FontStyle contentStyle) {
        this.contentStyle = contentStyle;
        return this;
    }

    public FontStyle getYSequenceStyle() {
        if(YSequenceStyle == null){
            return defaultFontStyle;
        }
        return YSequenceStyle;
    }

    public TableConfig setYSequenceStyle(FontStyle YSequenceStyle) {
        this.YSequenceStyle = YSequenceStyle;
        return this;
    }

    public FontStyle getXSequenceStyle() {
        if(XSequenceStyle == null){
            return defaultFontStyle;
        }
        return XSequenceStyle;
    }

    public TableConfig setXSequenceStyle(FontStyle XSequenceStyle) {
        this.XSequenceStyle = XSequenceStyle;
        return this;
    }

    public FontStyle getColumnTitleStyle() {
        if(columnTitleStyle == null){
            return defaultFontStyle;
        }
        return columnTitleStyle;
    }

    public TableConfig setColumnTitleStyle(FontStyle columnTitleStyle) {
        this.columnTitleStyle = columnTitleStyle;
        return this;
    }

    public int getVerticalPadding() {
        return verticalPadding;
    }

    public TableConfig setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
        return this;
    }

    public int getHorizontalPadding() {
        return horizontalPadding;
    }

    public TableConfig setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
        return this;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public LineStyle getGridStyle() {
        if(gridStyle == null){
            return defaultGridStyle;
        }
        return gridStyle;
    }

    public LineStyle getColumnTitleGridStyle() {
        if(columnTitleGridStyle == null){
            return defaultGridStyle;
        }
        return columnTitleGridStyle;
    }

    public  TableConfig setColumnTitleGridStyle(LineStyle columnTitleGridStyle) {
        this.columnTitleGridStyle = columnTitleGridStyle;
        return this;
    }

    public boolean isFixedYSequence() {
        return fixedYSequence;
    }

    public TableConfig setFixedYSequence(boolean fixedYSequence) {
        this.fixedYSequence = fixedYSequence;
        return this;
    }

    public boolean isFixedXSequence() {
        return fixedXSequence;
    }

    public TableConfig setFixedXSequence(boolean fixedXSequence) {
        this.fixedXSequence = fixedXSequence;
        return this;
    }

    public boolean isFixedTitle() {
        return fixedTitle;
    }

    public TableConfig setFixedTitle(boolean fixedTitle) {
        this.fixedTitle = fixedTitle;
        return this;
    }


    public boolean isFixedFirstColumn() {
        return fixedFirstColumn;
    }

    /**你可以使用Column.setFixed(boolean isFixed) 来固定任何一列
     * 此方法作废
     * @param fixedFirstColumn
     * @return
     */
    @Deprecated
    public TableConfig setFixedFirstColumn(boolean fixedFirstColumn) {
        this.fixedFirstColumn = fixedFirstColumn;
        return this;
    }

    public FontStyle getCountStyle() {
        if(contentStyle == null){
            return defaultFontStyle;
        }
        return countStyle;
    }

    public int getYSequenceBackgroundColor() {
        return YSequenceBackgroundColor;
    }

    public TableConfig setYSequenceBackgroundColor(int YSequenceBackgroundColor) {
        this.YSequenceBackgroundColor = YSequenceBackgroundColor;
        return this;
    }

    public int getXSequenceBackgroundColor() {
        return XSequenceBackgroundColor;
    }

    public TableConfig setXSequenceBackgroundColor(int XSequenceBackgroundColor) {
        this.XSequenceBackgroundColor = XSequenceBackgroundColor;
        return this;
    }

    public int getColumnTitleBackgroundColor() {
        return columnTitleBackgroundColor;
    }

    public TableConfig setColumnTitleBackgroundColor(int columnTitleBackgroundColor) {
        this.columnTitleBackgroundColor = columnTitleBackgroundColor;
        return this;
    }

    public int getColumnTitleVerticalPadding() {
        return columnTitleVerticalPadding;
    }

    public TableConfig setColumnTitleVerticalPadding(int columnTitleVerticalPadding) {
        this.columnTitleVerticalPadding = columnTitleVerticalPadding;
        return this;
    }

    public int getContentBackgroundColor() {
        return contentBackgroundColor;
    }

    public TableConfig setContentBackgroundColor(int contentBackgroundColor) {
        this.contentBackgroundColor = contentBackgroundColor;
        return this;
    }

    public TableConfig setCountStyle(FontStyle countStyle) {
        this.countStyle = countStyle;
        return this;
    }

    public TableConfig setGridStyle(LineStyle gridStyle) {
        this.gridStyle = gridStyle;
        return this;
    }

    public int getCountBackgroundColor() {
        return countBackgroundColor;
    }

    public TableConfig setCountBackgroundColor(int countBackgroundColor) {
        this.countBackgroundColor = countBackgroundColor;
        return this;
    }

    public boolean isFixedCountRow() {
        return fixedCountRow;
    }

    public TableConfig setFixedCountRow(boolean fixedCountRow) {
        this.fixedCountRow = fixedCountRow;
        return this;
    }

    public FontStyle getTableTitleStyle() {
        if(tableTitleStyle == null){
            return defaultFontStyle;
        }
        return tableTitleStyle;
    }

    public TableConfig setTableTitleStyle(FontStyle tableTitleStyle) {
        this.tableTitleStyle = tableTitleStyle;
        return this;
    }

    public boolean isShowXSequence() {
        return isShowXSequence;
    }

    public TableConfig setShowXSequence(boolean showXSequence) {
        isShowXSequence = showXSequence;
        return this;
    }

    public boolean isShowYSequence() {
        return isShowYSequence;
    }

    public TableConfig setShowYSequence(boolean showYSequence) {
        isShowYSequence = showYSequence;
        return this;
    }

    public ICellBackgroundFormat<CellInfo> getContentBackgroundFormat() {
        return contentBackgroundFormat;
    }

    public TableConfig setContentBackgroundFormat(ICellBackgroundFormat<CellInfo> contentBackgroundFormat) {
        this.contentBackgroundFormat = contentBackgroundFormat;
        return this;
    }

    public ICellBackgroundFormat<Column> getColumnBackgroundFormat() {
        return columnBackgroundFormat;
    }

    public TableConfig setColumnBackgroundFormat(ICellBackgroundFormat<Column> columnBackgroundFormat) {
        this.columnBackgroundFormat = columnBackgroundFormat;
        return this;
    }

    public ICellBackgroundFormat<Integer> getXSequenceBgFormat() {
        return XSequenceBgFormat;
    }

    public TableConfig setXSequenceBgFormat(ICellBackgroundFormat<Integer> XSequenceBgFormat) {
        this.XSequenceBgFormat = XSequenceBgFormat;
        return this;
    }

    public ICellBackgroundFormat<Integer> getYSequenceBgFormat() {
        return YSequenceBgFormat;
    }

    public TableConfig setYSequenceBgFormat(ICellBackgroundFormat<Integer> YSequenceBgFormat) {
        this.YSequenceBgFormat = YSequenceBgFormat;
        return this;
    }

    public ICellBackgroundFormat<Column> getCountBgFormat() {
        return countBgFormat;
    }

    public TableConfig setCountBgFormat(ICellBackgroundFormat<Column> countBgFormat) {
        this.countBgFormat = countBgFormat;
        return this;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public int getColumnTitleHorizontalPadding() {
        return columnTitleHorizontalPadding;
    }

    public TableConfig setColumnTitleHorizontalPadding(int columnTitleHorizontalPadding) {
        this.columnTitleHorizontalPadding = columnTitleHorizontalPadding;
        return this;
    }

    public boolean isShowTableTitle() {
        return isShowTableTitle;
    }

    public TableConfig setShowTableTitle(boolean showTableTitle) {
        isShowTableTitle = showTableTitle;
        return this;
    }

    public boolean isShowColumnTitle() {

        return isShowColumnTitle;
    }

    public int getLeftAndTopBackgroundColor() {
        return leftAndTopBackgroundColor;
    }

    public TableConfig setLeftAndTopBackgroundColor(int leftAndTopBackgroundColor) {
        this.leftAndTopBackgroundColor = leftAndTopBackgroundColor;
        return this;
    }

    public LeftTopDrawFormat getLeftTopDrawFormat() {
        return leftTopDrawFormat;
    }

    public void setLeftTopDrawFormat(LeftTopDrawFormat leftTopDrawFormat) {
        this.leftTopDrawFormat = leftTopDrawFormat;
    }

    public TableConfig setShowColumnTitle(boolean showColumnTitle) {
        isShowColumnTitle = showColumnTitle;
        return this;
    }

    public LineStyle getSequenceGridStyle() {
        if(SequenceGridStyle == null){
            return defaultGridStyle;
        }
        return SequenceGridStyle;
    }

    public TableConfig setSequenceGridStyle(LineStyle sequenceGridStyle) {
        SequenceGridStyle = sequenceGridStyle;
        return this;
    }



    public TableConfig setMinTableWidth(int minTableWidth) {
        this.minTableWidth = minTableWidth;
        return this;
    }

    public int getMinTableWidth() {
        return minTableWidth;
    }
}
