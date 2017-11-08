package com.bin.david.form.core;

import android.graphics.Paint;

import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;

/**
 * Created by huang on 2017/11/1.
 */

public class TableConfig {

    public static final FontStyle defaultFontStyle = new FontStyle();
    public static final LineStyle defaultGridStyle = new LineStyle();
    public static final int INVALID_COLOR = -1;
    private FontStyle contentStyle;
    private FontStyle YSequenceStyle;
    private FontStyle XSequenceStyle;
    private FontStyle columnTitleStyle;
    private FontStyle tableTitleStyle;
    private FontStyle countStyle; //总和
    private LineStyle columnTitleGridStyle;
    private LineStyle gridStyle;
    private int verticalPadding = 10;
    private int horizontalPadding= 40;
    private int YSequenceBackgroundColor=INVALID_COLOR;

    private int XSequenceBackgroundColor =INVALID_COLOR;
    private int columnTitleBackgroundColor =INVALID_COLOR;
    private int contentBackgroundColor=INVALID_COLOR;
    private int countBackgroundColor=INVALID_COLOR;

    //固定左侧
    private boolean fixedYSequence = false;
    //固定顶部
    private boolean fixedXSequence = false;
    //固定标题
    private boolean fixedTitle = true;
    //固定第一列
    private boolean fixedFirstColumn = true;
    private boolean fixedCountRow = true;
    private Paint paint;
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

    public void setCountBackgroundColor(int countBackgroundColor) {
        this.countBackgroundColor = countBackgroundColor;
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

    public float getZoom() {
        return zoom;
    }

     void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
