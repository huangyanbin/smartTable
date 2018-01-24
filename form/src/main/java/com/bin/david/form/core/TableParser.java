package com.bin.david.form.core;

import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.exception.TableException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huang on 2017/10/31.
 * 表格解析器
 */

public class TableParser<T> {



    /**
     * 解析数据
     */
    public List<Column> parse(TableData<T> tableData, TableConfig config){

        tableData.getChildColumns().clear();
        tableData.getColumnInfos().clear();
        int maxLevel = getChildColumn(tableData);
        TableInfo tableInfo =  tableData.getTableInfo();
        tableInfo.setColumnSize(tableData.getChildColumns().size());
        tableInfo.setMaxLevel(maxLevel);
        tableData.clearCellRangeAddresses();
        if(tableData instanceof ArrayTableData){
            for (Column column : tableData.getChildColumns()) {
                column.parseData(tableInfo,config);
            }
        }else {
            sort(tableData);
            try {
                List<T> dataList = tableData.getT();
                int i = 0;
                for (Column column : tableData.getChildColumns()) {
                    column.getDatas().clear();
                    column.fillData(dataList, tableInfo, config);
                    List<int[]> ranges = column.parseRanges();
                    if(ranges !=null && ranges.size()>0){
                        for(int[] range:ranges){
                            tableData.addCellRange(new CellRange(range[0],range[1],i,i));
                        }
                    }
                    i++;
                }
            } catch (NoSuchFieldException e) {
                throw new TableException(
                        "NoSuchFieldException :Please check whether field name is correct!");
            } catch (IllegalAccessException e) {
                throw new TableException(
                        "IllegalAccessException :Please make sure that access objects are allowed!");
            }
        }
       return tableData.getColumns();
    }

    /**
     * 添加数据
     */
    public void addData(TableData<T> tableData, List<T> addData,boolean isFoot, TableConfig config){

        try {

            int size = tableData.getLineSize();
            if(isFoot) {
                tableData.getT().addAll(addData);
            }else{
                tableData.getT().addAll(0,addData);
            }
            tableData.setLineSize(tableData.getT().size());
            TableInfo tableInfo =  tableData.getTableInfo();
            tableInfo.addLine(addData.size());
            tableData.clearCellRangeAddresses();
            int i =0;
            for (Column column : tableData.getChildColumns()) {
                column.addData(addData,tableInfo,config,size,isFoot);
                List<int[]> ranges = column.parseRanges();
                if(ranges !=null && ranges.size()>0){
                    for(int[] range:ranges){
                        tableData.addCellRange(new CellRange(range[0],range[1],i,i));
                    }
                }
                i++;
            }
        }catch (NoSuchFieldException e){
            throw new TableException(
                    "NoSuchFieldException :Please check whether field name is correct!");
        }catch (IllegalAccessException e){
            throw new TableException(
                    "IllegalAccessException :Please make sure that access objects are allowed!");
        }
    }


    /**
     * 排序
     * @param tableData 表格数据
     * @return
     */
    public List<Column> sort(TableData<T> tableData){

        final Column sortColumn = tableData.getSortColumn();
        if(sortColumn !=null) {
            List<T> dataList = tableData.getT();
            Collections.sort(dataList, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {

                    try {
                        if(o1 == null){
                            return sortColumn.isReverseSort() ?1:-1;
                        }
                        if(o2 == null){
                            return sortColumn.isReverseSort() ?-1:1;
                        }
                        Object data = sortColumn.getData(o1);
                        Object compareData = sortColumn.getData(o2);
                        if(data == null){
                            return sortColumn.isReverseSort() ?1:-1;
                        }
                        if(compareData == null){
                            return sortColumn.isReverseSort() ?-1:1;
                        }
                        int compare;
                        if(sortColumn.getComparator() != null){
                            compare=  sortColumn.getComparator().compare(data,compareData);
                            return sortColumn.isReverseSort()?-compare:compare;
                        }else {
                            if(data instanceof Comparable){
                                compare= ((Comparable) data).compareTo(compareData);
                                return sortColumn.isReverseSort()?-compare:compare;
                            }
                            return 0;
                        }
                    } catch (NoSuchFieldException e){
                        throw new TableException(
                                "NoSuchFieldException :Please check whether field name is correct!");
                    }catch (IllegalAccessException e){
                        throw new TableException(
                                "IllegalAccessException :Please make sure that access objects are allowed!");
                    }
                }
            });
        }
        return tableData.getColumns();
    }


    private int  getChildColumn(TableData<T> tableData){
        int maxLevel = 0;
        for (Column column : tableData.getColumns()) {
                int level = getColumnLevel(tableData,column,0);
                if(level >maxLevel){
                    maxLevel = level;
                }
        }
        return maxLevel;
    }

    /**
     * 得到列的层级
     * @param tableData 表格数据
     * @param column 列
     * @param level 层级
     * @return
     */
    private int getColumnLevel(TableData<T> tableData,Column column,int level){
        level++;
        if(column.isParent()){
            List<Column> children = column.getChildren();
            int maxLevel =0;
            for(Column child :children){
                int childLevel = getColumnLevel(tableData,child,level);
                if(maxLevel < childLevel){
                    maxLevel = childLevel;
                    column.setLevel(maxLevel);
                }
            }
            level = maxLevel;
            return level;
        }else{
           tableData.getChildColumns().add(column);
           return level;
        }
    }



}
