package com.bin.david.form.data;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by huang on 2017/11/1.
 */

public class TableInfo {

    private int topHeight;
    private int titleHeight;
    private int tableTitleHeight;
    private int yAxisWidth;
    private int countHeight;
    private Rect tableRect;
    private int maxLevel = 1;
    private int columnSize;
    private int[] lineHeightArray;
    private float zoom =1;

    /**
     * 获取最大层级
     * @return 最大层级
     */
    public int getMaxLevel() {
        return maxLevel;
    }
    /**
     * 设置最大层级
     * 该方法提供用于表格递归
     * @return 最大层级
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * 获取列总数
     * @return 列总数
     */
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


    public void setLineSize(int lineSize) {
        lineHeightArray = new int[lineSize];
    }

    /**
     * 动态添加列，数组重新创建Copy
     * @param count 添加数量
     */
    public void addLine(int count){
        int size = lineHeightArray.length;
        int[] tempArray = new int[size+count];
        //数组复制

        System.arraycopy(lineHeightArray,0,tempArray,0,size);
        lineHeightArray = tempArray;
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

    /**
     * 获取缩放值
     * @return 缩放值
     */
    public float getZoom() {
        return zoom;
    }
    /**
     * 设置缩放值
     */
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void setyAxisWidth(int yAxisWidth) {
        this.yAxisWidth = yAxisWidth;
    }

    public int getTableTitleHeight() {
        return tableTitleHeight;
    }

    public void setTableTitleHeight(int tableTitleHeight) {
        this.tableTitleHeight = tableTitleHeight;
    }
}
