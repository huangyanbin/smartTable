package com.bin.david.form.core;

import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.component.IComponent;
import com.bin.david.form.component.ITableTitle;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.ColumnInfo;
import com.bin.david.form.data.TableData;
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

    public int getHeadHeight(TableData<T> tableData){
        TableInfo tableInfo = tableData.getTableInfo();
        return tableInfo.getTopHeight()+tableInfo.getTableTitleHeight()
                +tableInfo.getTitleHeight()*tableInfo.getMaxLevel();
    }

    public void measureTableTitle(TableData<T> tableData,ITableTitle tableTitle,Rect showRect){
        TableInfo tableInfo = tableData.getTableInfo();
        Rect tableRect = tableInfo.getTableRect();
        if(isReMeasure) {
            isReMeasure = false;
            int size = tableTitle.getSize();
            tableInfo.setTableTitleHeight(size);
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
                    + 2 * config.getVerticalPadding();
        }
        int titleHeight = tableData.getTitleDrawFormat().measureHeight(config);
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
        for(Column column:tableData.getChildColumns()){
            float columnNameWidth =tableData.getTitleDrawFormat().measureWidth(column,config);
            int contentWidth =column.getDrawFormat().measureWidth(column,config);
            int width = (int) (Math.max(columnNameWidth,contentWidth));
            if(tableData.isShowCount()) {
                int totalCountWidth = column.getCountFormat() != null ?
                        (int) paint.measureText(column.getTotalNumString()) : 0;
                width = Math.max(totalCountWidth+2*config.getHorizontalPadding(), width);
            }
            column.setWidth(width);
            totalWidth+=width;
        }
        config.getYSequenceStyle().fillPaint(paint);
        int totalSize = tableData.getT().size();
        if(config.isShowYSequence()) {
            int yAxisWidth = (int) paint.measureText(tableData.getYSequenceFormat().format(totalSize)
                    + 2 * config.getHorizontalPadding());
            tableData.getTableInfo().setyAxisWidth(yAxisWidth);
            totalWidth+=yAxisWidth;
        }
        return totalWidth;
    }

    private void measureColumnSize(TableData<T> tableData){
        List<Column> columnList = tableData.getColumns();
        int left = 0;
        int maxLevel =tableData.getTableInfo().getMaxLevel();
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
            columnInfo.width = column.getWidth();
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
