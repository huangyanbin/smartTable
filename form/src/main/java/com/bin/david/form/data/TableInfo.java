package com.bin.david.form.data;

import android.graphics.Rect;

import com.bin.david.form.data.column.ArrayColumn;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnNode;

/**
 * Created by huang on 2017/11/1.
 */

public class TableInfo {

    private int topHeight;
    private int titleHeight;
    private int tableTitleSize;
    private int yAxisWidth;
    private int countHeight;
    private int titleDirection;
    private Rect tableRect;
    private int maxLevel = 1;
    private int columnSize;
    private int[] lineHeightArray;
    private float zoom =1;
    private Cell[][] rangeCells;
    private int lineSize;
    private ColumnNode topNode;
    private int[] arrayLineSize;
    private boolean isHasArrayColumn; //含有数组列暂不支持合并

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
        rangeCells = new Cell[lineSize][columnSize];

    }

    public int getTopHeight() {
        return topHeight;
    }

    public int getTopHeight(float zoom) {
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
        return yAxisWidth;
    }


    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
        this.lineHeightArray = new int[lineSize];

    }

    /**
     * 动态添加列，数组重新创建Copy
     * @param count 添加数量
     */
    public void addLine(int count,boolean isFoot){
        lineSize += count;
        int size = lineHeightArray.length;
        int[] tempArray = new int[size+count];
        //数组复制
        if(isFoot) {
            System.arraycopy(lineHeightArray, 0, tempArray, 0, size);
        }else{
            System.arraycopy(lineHeightArray, 0, tempArray, count, size);
        }
        lineHeightArray = tempArray;
        if(!isHasArrayColumn) {
            if (size == rangeCells.length) {
                Cell[][] tempRangeCells = new Cell[size + count][columnSize];
                for (int i = 0; i < size; i++) {
                    tempRangeCells[i + (isFoot ? 0 : count)] = rangeCells[i];
                }
                rangeCells = tempRangeCells;
            }
        }
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

    public int getTableTitleSize() {
        return tableTitleSize;
    }

    public void setTableTitleSize(int tableTitleSize) {
        this.tableTitleSize = tableTitleSize;
    }

    public int getTitleDirection() {
        return titleDirection;
    }

    public void setTitleDirection(int titleDirection) {
        this.titleDirection = titleDirection;
    }

    public Cell[][] getRangeCells() {
        return rangeCells;
    }

    public void clear(){
        rangeCells = null;
        lineHeightArray = null;
        tableRect = null;
        topNode = null;
    }

    public ColumnNode getTopNode() {
        return topNode;
    }

    public void setTopNode(ColumnNode topNode) {
        this.topNode = topNode;
        if( this.topNode !=null){
            isHasArrayColumn = true;
            rangeCells = null;
        }
    }

    /**
     * 统计表格行数
     */
    public void countTotalLineSize(ArrayColumn bottomColumn){
       if(topNode !=null) {
           arrayLineSize = new int[lineSize];
           int totalSize = 0;
           for (int i = 0; i < lineSize; i++) {
               arrayLineSize[i]= bottomColumn.getStructure().getLevelCellSize(-1,i);
               totalSize+= arrayLineSize[i];
           }
           lineHeightArray = new int[totalSize];
            rangeCells = null;
       }
    }

    /**
     * 所有每列Position位置所占格子数
     * @param column 列
     * @param position 位置
     * @return 格子数
     */
    public int getSeizeCellSize(Column column, int position){
        if(topNode !=null) {
           return column.getSeizeCellSize(this,position);
        }
        return 1;
    }


    public int[] getArrayLineSize() {
        return arrayLineSize;
    }

}
