package com.bin.david.form.core;

import android.graphics.Paint;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.grid.BaseAbstractGridFormat;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.grid.IGridFormat;
import com.bin.david.form.data.format.draw.LeftTopDrawFormat;
import com.bin.david.form.data.format.grid.SimpleGridFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;

/**
 * Created by huang on 2017/11/1.
 * 表格配置
 * 表格90%配置都在这里
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

    public int dp10;
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
    private LineStyle contentGridStyle;
    /**
     * 上下padding(为了表格的美观，暂只支持统一的padding)
     */
    private int verticalPadding = 10;

    /**
     * 增加列序列上下padding
     */
    private int sequenceVerticalPadding =10;
    /**
     * 文字左边偏移
     */
    private int textLeftOffset = 0;
    /**
     * 增加列序列左右padding
     */
    private int sequenceHorizontalPadding =40;

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
     * 组标题背景
     */
    private IBackgroundFormat columnTitleBackground;
    /**
     * 内容背景
     */
    private IBackgroundFormat contentBackground;
    /**
     * 统计行背景
     */
    private IBackgroundFormat countBackground;
    /**
     *
     * 左侧序号列背景
     *
     */
    private IBackgroundFormat YSequenceBackground;
    /**
     * 顶部序号列背景
     */
    private IBackgroundFormat XSequenceBackground;

    /**
     * 网格格式化
     */
    private IGridFormat tableGridFormat = new SimpleGridFormat();



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
     * 内容格子进行背景格式化
     */
    private ICellBackgroundFormat<CellInfo> contentCellBackgroundFormat;
    /**
     * 标题格子背景格式化
     */
    private ICellBackgroundFormat<Column> columnCellBackgroundFormat;
    /**
     * 顶部序号格子背景格式化
     */
    private ICellBackgroundFormat<Integer> XSequenceCellBgFormat;
    /**
     * 左序号格子背景格式化
     */
    private ICellBackgroundFormat<Integer> YSequenceCellBgFormat;
    /**
     * 统计行格子背景格式化
     */
    private ICellBackgroundFormat<Column> countBgCellFormat;
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

    public LineStyle getContentGridStyle() {
        if(contentGridStyle == null){
            return defaultGridStyle;
        }
        return contentGridStyle;
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



    public TableConfig setCountStyle(FontStyle countStyle) {
        this.countStyle = countStyle;
        return this;
    }

    public TableConfig setContentGridStyle(LineStyle contentGridStyle) {
        this.contentGridStyle = contentGridStyle;
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

    public ICellBackgroundFormat<CellInfo> getContentCellBackgroundFormat() {
        return contentCellBackgroundFormat;
    }

    public TableConfig setContentCellBackgroundFormat(ICellBackgroundFormat<CellInfo> contentCellBackgroundFormat) {
        this.contentCellBackgroundFormat = contentCellBackgroundFormat;
        return this;
    }

    public ICellBackgroundFormat<Column> getColumnCellBackgroundFormat() {
        return columnCellBackgroundFormat;
    }

    public TableConfig setColumnCellBackgroundFormat(ICellBackgroundFormat<Column> columnCellBackgroundFormat) {
        this.columnCellBackgroundFormat = columnCellBackgroundFormat;
        return this;
    }

    public ICellBackgroundFormat<Integer> getXSequenceCellBgFormat() {
        return XSequenceCellBgFormat;
    }

    public TableConfig setXSequenceCellBgFormat(ICellBackgroundFormat<Integer> XSequenceCellBgFormat) {
        this.XSequenceCellBgFormat = XSequenceCellBgFormat;
        return this;
    }

    public ICellBackgroundFormat<Integer> getYSequenceCellBgFormat() {
        return YSequenceCellBgFormat;
    }

    public TableConfig setYSequenceCellBgFormat(ICellBackgroundFormat<Integer> YSequenceCellBgFormat) {
        this.YSequenceCellBgFormat = YSequenceCellBgFormat;
        return this;
    }

    public ICellBackgroundFormat<Column> getCountBgCellFormat() {
        return countBgCellFormat;
    }

    public TableConfig setCountBgCellFormat(ICellBackgroundFormat<Column> countBgCellFormat) {
        this.countBgCellFormat = countBgCellFormat;
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

    public IBackgroundFormat getYSequenceBackground() {
        return YSequenceBackground;
    }

    public TableConfig setYSequenceBackground(IBackgroundFormat YSequenceBackground) {
        this.YSequenceBackground = YSequenceBackground;
        return this;
    }

    public int getColumnTitleVerticalPadding() {
        return columnTitleVerticalPadding;
    }

    public TableConfig setColumnTitleVerticalPadding(int columnTitleVerticalPadding) {
        this.columnTitleVerticalPadding = columnTitleVerticalPadding;
        return this;
    }

    public IBackgroundFormat getColumnTitleBackground() {
        return columnTitleBackground;
    }

    public TableConfig setColumnTitleBackground(IBackgroundFormat columnTitleBackground) {
        this.columnTitleBackground = columnTitleBackground;
        return this;

    }

    public IBackgroundFormat getContentBackground() {
        return contentBackground;
    }

    public TableConfig setContentBackground(IBackgroundFormat contentBackground) {
        this.contentBackground = contentBackground;
        return this;
    }

    public IBackgroundFormat getCountBackground() {
        return countBackground;
    }

    public TableConfig setCountBackground(IBackgroundFormat countBackground) {
        this.countBackground = countBackground;
        return this;
    }

    public IBackgroundFormat getXSequenceBackground() {
        return XSequenceBackground;
    }

    public TableConfig setXSequenceBackground(IBackgroundFormat XSequenceBackground) {
        this.XSequenceBackground = XSequenceBackground;
        return this;
    }

    public IGridFormat getTableGridFormat() {
        return tableGridFormat;
    }

    public TableConfig setTableGridFormat(IGridFormat tableGridFormat) {
        this.tableGridFormat = tableGridFormat;
        return this;
    }

    public int getSequenceVerticalPadding() {
        return sequenceVerticalPadding;
    }

    public TableConfig setSequenceVerticalPadding(int sequenceVerticalPadding) {
        this.sequenceVerticalPadding = sequenceVerticalPadding;
        return this;
    }

    public int getSequenceHorizontalPadding() {
        return sequenceHorizontalPadding;
    }

    public TableConfig setSequenceHorizontalPadding(int sequenceHorizontalPadding) {
        this.sequenceHorizontalPadding = sequenceHorizontalPadding;
        return this;
    }

    public int getTextLeftOffset() {
        return textLeftOffset;
    }

    public TableConfig setTextLeftOffset(int textLeftOffset) {
        this.textLeftOffset = textLeftOffset;
        return this;
    }
}
