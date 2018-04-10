package com.bin.david.form.core;

import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.component.IComponent;
import com.bin.david.form.component.ITableTitle;
import com.bin.david.form.data.column.ArrayColumn;
import com.bin.david.form.data.Cell;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.utils.DrawUtils;

import java.util.List;

/**表格测量器
 * Created by huang on 2017/11/2.
 */

public class TableMeasurer<T> {

    private  boolean isReMeasure; //是否重新计算

    public TableInfo measure(TableData<T> tableData, TableConfig config){
        isReMeasure = true;
        TableInfo tableInfo = tableData.getTableInfo();
        int width = getTableWidth(tableData,config);
        int height = getTableHeight(tableData,config);
        tableInfo.setTableRect(new Rect(0,0,width,height));
        measureColumnSize(tableData);
        return tableInfo;
    }



    public void measureTableTitle(TableData<T> tableData,ITableTitle tableTitle,Rect showRect){
        TableInfo tableInfo = tableData.getTableInfo();
        Rect tableRect = tableInfo.getTableRect();
        if(isReMeasure) {
            isReMeasure = false;
            int size = tableTitle.getSize();
            tableInfo.setTitleDirection(tableTitle.getDirection());
            tableInfo.setTableTitleSize(size);
            if (tableTitle.getDirection() == IComponent.TOP ||
                    tableTitle.getDirection() == IComponent.BOTTOM) {
                int height = size;
                tableRect.bottom += height;
                reSetShowRect(showRect,tableRect);
            } else {
                int width = size;
                tableRect.right += width;
                reSetShowRect(showRect,tableRect);
            }
        }else {
            reSetShowRect(showRect,tableRect);
        }

    }

    /**
     * 重新计算显示大小
     * @param showRect
     * @param tableRect
     */
    public void reSetShowRect(Rect showRect,Rect tableRect){
        if(showRect.bottom > tableRect.bottom){
            showRect.bottom = tableRect.bottom;
        }
        if(showRect.right > tableRect.right){
            showRect.right = tableRect.right;
        }
    }
    /**
     * 添加table高度
     * @param tableData
     * @return
     */
    public void addTableHeight(TableData<T> tableData,TableConfig config){
        /*TableInfo tableInfo = tableData.getTableInfo();
        Rect tableRect = tableInfo.getTableRect();
        int[] lineArray = tableInfo.getLineHeightArray();
        for(int i = startPosition;i<lineArray.length;i++){
           tableRect.bottom+= lineArray[i];
        }*/
        TableInfo tableInfo = tableData.getTableInfo();
        int width = getTableWidth(tableData,config);
        int height = getTableHeight(tableData,config);
        tableInfo.setTableRect(new Rect(0,0,width,height));
    }



    /**
     * 计算table高度
     * @param tableData
     * @param config
     * @return
     */
    private int getTableHeight(TableData<T> tableData,TableConfig config){
        Paint paint = config.getPaint();
        int topHeight = 0;
        if(config.isShowXSequence()) {
             topHeight = DrawUtils.getTextHeight(config.getXSequenceStyle(), paint)
                    + 2 * config.getSequenceVerticalPadding();
        }
        int titleHeight = config.isShowColumnTitle()?(tableData.getTitleDrawFormat().measureHeight(config)
                +2*config.getColumnTitleVerticalPadding()): 0;
        TableInfo tableInfo = tableData.getTableInfo();
        tableInfo.setTitleHeight(titleHeight);
        tableInfo.setTopHeight(topHeight);
        int totalContentHeight = 0;
        for(int height :tableInfo.getLineHeightArray()){
            totalContentHeight+=height;
        }
        int totalTitleHeight = titleHeight*tableInfo.getMaxLevel();
        int totalHeight = topHeight +totalTitleHeight+totalContentHeight;
        if(tableData.isShowCount()) {
            int countHeight = DrawUtils.getTextHeight(config.getCountStyle(), paint)
                    + 2 * config.getVerticalPadding();
            tableInfo.setCountHeight(countHeight);
            totalHeight+=countHeight;
        }
        return totalHeight;
    }

    /**
     * 计算table宽度
     * @param tableData
     * @param config
     * @return
     */
    private int getTableWidth(TableData<T> tableData,TableConfig config){
        int totalWidth= 0;
        Paint paint = config.getPaint();
        config.getYSequenceStyle().fillPaint(paint);
        int totalSize = tableData.getLineSize();
        if(config.isShowYSequence()) {
            int yAxisWidth = (int) paint.measureText(tableData.getYSequenceFormat().format(totalSize)
                    + 2 * config.getSequenceHorizontalPadding());
            tableData.getTableInfo().setyAxisWidth(yAxisWidth);
            totalWidth+=yAxisWidth;
        }

        int columnPos =0;
        int contentWidth = 0;
        int[] lineHeightArray = tableData.getTableInfo().getLineHeightArray();
        TableInfo tableInfo = tableData.getTableInfo();
        int currentPosition,size;
        for(Column column:tableData.getChildColumns()){
            float columnNameWidth =tableData.getTitleDrawFormat().measureWidth(column,config)
                    +config.getColumnTitleHorizontalPadding()*2;
            int columnWidth =0;
             size = column.getDatas().size();
            currentPosition=0;
            boolean isArrayColumn = column instanceof ArrayColumn;
            Cell[][] rangeCells = tableData.getTableInfo().getRangeCells();
            for(int position = 0;position < size;position++) {
                int width = column.getDrawFormat().measureWidth(column, position, config);
                measureRowHeight(config, lineHeightArray, column, currentPosition, position);
                int skipPosition = tableInfo.getSeizeCellSize(column, position);
                currentPosition += skipPosition;
                /**
                 *Todo 为了解决合并单元宽度过大问题
                 */
                if (!isArrayColumn) {
                    if(rangeCells !=null) {
                        Cell cell = rangeCells[position][columnPos];
                        if (cell != null) {
                            if (cell.row != Cell.INVALID && cell.col != Cell.INVALID) {
                                cell.width = width;
                                width = width / cell.col;
                            } else if (cell.realCell != null) {
                                width = cell.realCell.width / cell.realCell.col;
                            }

                        }
                    }
                }

                if (columnWidth < width) {
                    columnWidth = width;
                }
            }
            int width = (int) (Math.max(columnNameWidth,columnWidth + 2 * config.getHorizontalPadding()));
            if(tableData.isShowCount()) {
                int totalCountWidth = column.getCountFormat() != null ?
                        (int) paint.measureText(column.getTotalNumString()) : 0;
                width = Math.max(totalCountWidth+2*config.getHorizontalPadding(), width);
            }
            width = Math.max(column.getMinWidth(),width);
            column.setComputeWidth(width);
            contentWidth+=width;
            columnPos++;
        }
        int minWidth = config.getMinTableWidth();
        //计算出来的宽度大于最小宽度
        if(minWidth ==-1 || minWidth- totalWidth < contentWidth){
            totalWidth += contentWidth;
        }else{
            minWidth -=totalWidth;
            float widthScale = ((float) minWidth)/contentWidth;
            for(Column column:tableData.getChildColumns()){
                column.setComputeWidth((int)(widthScale*column.getComputeWidth()));
            }
            totalWidth+=minWidth;
        }
        return totalWidth;
    }


    /**
     * 测量行高
     * @param config
     * @param lineHeightArray
     * @param column
     * @param position
     */
    private void measureRowHeight(TableConfig config, int[] lineHeightArray, Column column, int currentPosition,int position) {

       int height =0;
        if(column.getRanges() != null && column.getRanges().size() >0){
            //如果有合并的情况，将合并的高度分散到各个格子里面去
          for(int i = 0; i < column.getRanges().size();i++){
              int[] range = (int[]) column.getRanges().get(i);
              if(range !=null && range.length ==2){
                  if(range[0] <= position && range[1] >=position){
                      height = (column.getDrawFormat().measureHeight(column,range[0],config) +
                              2*config.getVerticalPadding())/(range[1]- range[0]+1);
                  }
              }
          }
        }
       /* if(tableData.getUserCellRange() != null && tableData.getUserCellRange().size() >0){
            //如果有合并的情况，将合并的高度分散到各个格子里面去
            for(int i = 0; i < column.getRanges().size();i++){
                int[] range = (int[]) column.getRanges().get(i);
                if(range !=null && range.length ==2){
                    if(range[0] <= position && range[1] >=position){
                        height = (column.getDrawFormat().measureHeight(column,range[0],config) +
                                2*config.getVerticalPadding())/(range[1]- range[0]+1);
                    }
                }
            }
        }*/
        if(height == 0){
             height = column.getDrawFormat().measureHeight(column,position,config) +
                    2*config.getVerticalPadding();
        }
        height = Math.max(column.getMinHeight(),height);
        if (height > lineHeightArray[currentPosition]) {
            lineHeightArray[currentPosition] = height;
        }
    }

    /**
     * 测量列的Rect
     * @param tableData
     */
    private void measureColumnSize(TableData<T> tableData){
        List<Column> columnList = tableData.getColumns();
        int left = 0;
        int maxLevel =tableData.getTableInfo().getMaxLevel();
        tableData.getColumnInfos().clear();
        tableData.getChildColumnInfos().clear();
        for(int i = 0;i < columnList.size();i++){
            int top = 0;
            Column column = columnList.get(i);
            ColumnInfo columnInfo = getColumnInfo(tableData,column,null,left,top,maxLevel);
            left += columnInfo.width;
        }
    }

    public ColumnInfo getColumnInfo(TableData<T> tableData,Column column,ColumnInfo parent,int left,int top,int overLevel){
        TableInfo tableInfo = tableData.getTableInfo();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.value = column.getColumnName();
        columnInfo.column = column;
        columnInfo.setParent(parent);
        tableData.getColumnInfos().add(columnInfo);
        if(!column.isParent()){
            columnInfo.width = column.getComputeWidth();
            columnInfo.top = top;
            columnInfo.height = tableInfo.getTitleHeight()*overLevel;
            tableData.getChildColumnInfos().add(columnInfo);
            columnInfo.left = left;
            return columnInfo;
        }else{
            List<Column> children = column.getChildren();
            int size = children.size();
            int level = column.getLevel();
            int height= (level == 2 ?overLevel-1:1)*tableInfo.getTitleHeight();
            overLevel = level ==2?1:overLevel-1;
            columnInfo.left = left;
            columnInfo.top = top;
            columnInfo.height = height;
            top+= height;
            int width =0;
            for(int i= 0;i < size;i++){
                Column child = children.get(i);
                ColumnInfo childInfo = getColumnInfo(tableData,child,columnInfo,left,top,overLevel);
                width+=childInfo.width;
                left += childInfo.width;
            }
            columnInfo.width = width;
        }
        return columnInfo;
    }




}
