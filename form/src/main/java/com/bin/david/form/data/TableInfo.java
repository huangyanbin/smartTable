package com.bin.david.form.data;

import android.graphics.Rect;

/**
 * Created by huang on 2017/11/1.
 */

public class TableInfo {

    private int topHeight;
    private int titleHeight;
    private int yAxisWidth;
    private int countHeight;
    private Rect tableRect;
    private int maxLevel = 1;
    private int columnSize;
    private int lineSize;
    private int[] lineHeightArray;
    private float zoom =1;

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getTopHeight() {
        return (int) (topHeight*zoom);
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }

    public int getTitleHeight() {
        return (int) (titleHeight*zoom);
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }



    public Rect getTableRect() {
        return tableRect;
    }

    public void setTableRect(Rect tableRect) {
        this.tableRect = tableRect;
    }

    public int getyAxisWidth() {
        return (int) (zoom*yAxisWidth);
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
        lineHeightArray = new int[lineSize];
    }

    public int getCountHeight() {
        return (int) (zoom*countHeight);
    }

    public void setCountHeight(int countHeight) {
        this.countHeight = countHeight;
    }



    public int[] getLineHeightArray() {
        return lineHeightArray;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void setyAxisWidth(int yAxisWidth) {
        this.yAxisWidth = yAxisWidth;
    }
}
