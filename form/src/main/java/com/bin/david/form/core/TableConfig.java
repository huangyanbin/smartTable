package com.bin.david.form.core;

import android.graphics.Paint;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
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
     * 表格网格
     */
    private LineStyle gridStyle;
    /**
     * 上下padding(为了表格的美观，暂只支持统一的padding)
     */
    private int verticalPadding = 10;
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
     * 格子进行背景格式化
     */
    private IBackgroundFormat<CellInfo> contentBackgroundFormat;
    /**
     * 标题格子背景格式化
     */
    private IBackgroundFormat<Column> columnBackgroundFormat;
    /**
     * 顶部序号背景格式化
     */
    private IBackgroundFormat<Integer> XSequenceBgFormat;
    /**
     * 左序号背景格式化
     */
    private IBackgroundFormat<Integer> YSequenceBgFormat;

    private IBackgroundFormat<Column> countBgFormat;
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
     * 固定第一列
     *
     */
    private boolean fixedFirstColumn = true;
    /**
     * 是否固定统计行
     */
    private boolean fixedCountRow = true;
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

    public IBackgroundFormat<CellInfo> getContentBackgroundFormat() {
        return contentBackgroundFormat;
    }

    public TableConfig setContentBackgroundFormat(IBackgroundFormat<CellInfo> contentBackgroundFormat) {
        this.contentBackgroundFormat = contentBackgroundFormat;
        return this;
    }

    public IBackgroundFormat<Column> getColumnBackgroundFormat() {
        return columnBackgroundFormat;
    }

    public TableConfig setColumnBackgroundFormat(IBackgroundFormat<Column> columnBackgroundFormat) {
        this.columnBackgroundFormat = columnBackgroundFormat;
        return this;
    }

    public IBackgroundFormat<Integer> getXSequenceBgFormat() {
        return XSequenceBgFormat;
    }

    public TableConfig setXSequenceBgFormat(IBackgroundFormat<Integer> XSequenceBgFormat) {
        this.XSequenceBgFormat = XSequenceBgFormat;
        return this;
    }

    public IBackgroundFormat<Integer> getYSequenceBgFormat() {
        return YSequenceBgFormat;
    }

    public TableConfig setYSequenceBgFormat(IBackgroundFormat<Integer> YSequenceBgFormat) {
        this.YSequenceBgFormat = YSequenceBgFormat;
        return this;
    }

    public IBackgroundFormat<Column> getCountBgFormat() {
        return countBgFormat;
    }

    public TableConfig setCountBgFormat(IBackgroundFormat<Column> countBgFormat) {
        this.countBgFormat = countBgFormat;
        return this;
    }

    public float getZoom() {
        return zoom;
    }

     void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
