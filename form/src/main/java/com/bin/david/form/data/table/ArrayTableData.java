package com.bin.david.form.data.table;


import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.listener.OnColumnItemClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2018/1/14.
 * 数组TableData
 */

public class ArrayTableData<T> extends TableData<T> {

    private  T[][] data;
    private List<Column<T>> arrayColumns;


    /**
     * 提供将数组[col][row]转换成数组[row][col]
     * 因为平时我们提供的二维数组可能是以行作为一组。
     * @param rowArray 数组[row][col]
     * @return 数组[col][row]
     */
    public static<T> T[][]  transformColumnArray(T[][] rowArray){
        T[][] newData = null;
        T[] row= null;
        if(rowArray != null){
            int maxLength = 0;
            for(T[] t :rowArray){
                if(t !=null && t.length > maxLength){
                    maxLength = t.length;
                    row= t;
                }
            }
            if(row !=null) {
                newData = (T[][]) Array.newInstance(rowArray.getClass().getComponentType(),maxLength);
                for (int i = 0; i < rowArray.length; i++) { //转换一下
                    for (int j = 0; j < rowArray[i].length; j++) {
                        if(newData[j] == null) {
                            newData[j] = (T[]) Array.newInstance(row.getClass().getComponentType(),rowArray.length);
                        }
                        newData[j][i] = rowArray[i][j];
                    }
                }
            }

        }
        return newData;
    }

    /**
     * 创建二维数组表格数据
     * 如果数据不是数组[row][col]，可以使用transformColumnArray方法转换
     * @param tableName 表名
     * @param titleNames 列名
     * @param data 数据 数组[row][col]
     * @param drawFormat 数据格式化
     * @return 创建的二维数组表格数据
     */
    public static<T> ArrayTableData<T> create(String tableName,String[] titleNames, T[][] data, IDrawFormat<T> drawFormat){
        List<Column<T>> columns = new ArrayList<>();
        for(int i = 0;i <data.length;i++){
            T[] dataArray = data[i];
            Column<T> column = new Column<>(titleNames == null?"":titleNames[i], null,drawFormat);
            column.setDatas(Arrays.asList(dataArray));
            columns.add(column);
        }
        ArrayList<T> arrayList = new ArrayList<>(Arrays.asList(data[0]));
        ArrayTableData<T> tableData =  new ArrayTableData<>(tableName,arrayList,columns);
        tableData.setData(data);
        return tableData;
    }

    /**
     * 创建不需要显示列名的二维数组表格数据
     * 如果数据不是数组[row][col]，可以使用transformColumnArray方法转换
     * @param tableName 表名
     * @param data 数据 数组[row][col]
     * @param drawFormat 数据格式化
     * @return 创建的二维数组表格数据
     */
    public static<T> ArrayTableData<T> create(SmartTable table,String tableName, T[][] data, IDrawFormat<T> drawFormat){
        table.getConfig().setShowColumnTitle(false);
        return create(tableName,null,data,drawFormat);
    }

    /**
     * 设置默认格式化
     * @param format
     */
    public void setFormat(IFormat<T> format){
        for(Column<T> column:arrayColumns){


            column.setFormat(format);
        }
    }
    /**
     * 设置绘制格式化
     * @param format
     */
    public void setDrawFormat(IDrawFormat<T> format){
        for(Column<T> column:arrayColumns){
            column.setDrawFormat(format);
        }
    }

    /**
     * 设置最小宽度
     * @param minWidth
     */
    public void setMinWidth(int minWidth){
        for(Column<T> column:arrayColumns){
            column.setMinWidth(minWidth);
        }
    }

    /**
     * 设置最小高度
     * @param minHeight
     */
    public void setMinHeight(int minHeight){
        for(Column<T> column:arrayColumns){
            column.setMinHeight(minHeight);
        }
    }


    /**
     * 二维数组的构造方法
     * @param tableName 表名
     * @param t 数据
     * @param columns 列
     */
    protected ArrayTableData(String tableName, List<T> t, List<Column<T>> columns) {
        super(tableName, t, new ArrayList<Column>(columns));
        this.arrayColumns = columns;
    }
    /**
     * 获取当前的列
     */
    public List<Column<T>> getArrayColumns() {
        return arrayColumns;
    }


    /**
     * 获取二维数组数据
     */
    public T[][] getData() {
        return data;
    }

    /**
     * 设置二维数组数据
     * @param data
     */
    public void setData(T[][] data) {
        this.data = data;
    }





}
