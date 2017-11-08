package com.bin.david.form.data;

import com.bin.david.form.data.format.sequence.ISequenceFormat;
import com.bin.david.form.data.format.sequence.LetterSequenceFormat;
import com.bin.david.form.data.format.sequence.NumberSequenceFormat;
import com.bin.david.form.data.format.title.ITitleDrawFormat;
import com.bin.david.form.data.format.title.TitleDrawFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2017/10/30.
 */

public class TableData<T> {

    private String tableName;
    private List<Column> columns;
    private List<T> t;
    private List<Column> childColumns;
    private TableInfo tableInfo = new TableInfo();
    private List<ColumnInfo> columnInfos;
    private Column sortColumn;
    private boolean showCount; //显示统计
    private ITitleDrawFormat titleDrawFormat;
    private ISequenceFormat XSequenceFormat;
    private ISequenceFormat YSequenceFormat;

    public TableData(String tableName,List<T> t,List<Column> columns) {
        this(tableName,t,columns,null);
    }

    public TableData(String tableName,List<T> t,List<Column> columns,ITitleDrawFormat titleDrawFormat) {
        this.tableName = tableName;
        this.columns = columns;
        this.t = t;
        tableInfo.setLineSize(t.size());
        childColumns = new ArrayList<>();
        columnInfos = new ArrayList<>();
        this.titleDrawFormat = titleDrawFormat == null?new TitleDrawFormat() :titleDrawFormat;
    }

    public TableData(String tableName,List<T> t, Column... columns) {
        this(tableName,t,Arrays.asList(columns));
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<T> getT() {
        return t;
    }

    public void setT(List<T> t) {
        this.t = t;
    }


    public List<Column> getChildColumns() {
        return childColumns;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public void setChildColumns(List<Column> childColumns) {
        this.childColumns = childColumns;
    }

    public Column getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(Column sortColumn) {
        this.sortColumn = sortColumn;
    }

    public boolean isShowCount() {
        return showCount;
    }

    /**
     * 是否显示统计总数
     * @param showCount 显示统计总数
     */
    public void setShowCount(boolean showCount) {
        this.showCount = showCount;
    }

    public ITitleDrawFormat getTitleDrawFormat() {
        return titleDrawFormat;
    }

    public void setTitleDrawFormat(ITitleDrawFormat titleDrawFormat) {
        this.titleDrawFormat = titleDrawFormat;
    }

    public ISequenceFormat getXSequenceFormat() {
        if(XSequenceFormat == null){
            XSequenceFormat = new LetterSequenceFormat();
        }
        return XSequenceFormat;
    }

    public void setXSequenceFormat(ISequenceFormat XSequenceFormat) {
        this.XSequenceFormat = XSequenceFormat;
    }

    public ISequenceFormat getYSequenceFormat() {
        if(YSequenceFormat == null){
            YSequenceFormat = new NumberSequenceFormat();
        }
        return YSequenceFormat;
    }

    public void setYSequenceFormat(ISequenceFormat YSequenceFormat) {
        this.YSequenceFormat = YSequenceFormat;
    }

    public Column getColumnByID(int id){
        List<Column> columns = getChildColumns();
        for(Column column :columns){
            if(column.getId() == id){
                return column;
            }
        }
        return null;
    }



    public Column getColumnByFieldName(String fieldName){
        List<Column> columns = getChildColumns();
        for(Column column :columns){
            if(column.getFieldName().equals( fieldName)){
                return column;
            }
        }
        return null;
    }
}
