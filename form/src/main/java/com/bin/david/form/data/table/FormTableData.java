package com.bin.david.form.data.table;

import android.graphics.Paint;
import android.util.Log;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.form.IForm;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2018/4/10.
 */

public class FormTableData<T extends IForm> extends ArrayTableData<T>{


    public static<T extends IForm> FormTableData<T> create(SmartTable table,
                                                           String tableName,
                                                           int spanSize,
                                                           T[][] data){
        T[][] newArray= (T[][]) Array.newInstance(data.getClass().getComponentType(),data.length);
        int[][] exitArray = new int[data.length][spanSize];
        List<CellRange> cellRanges = new ArrayList<>();
        for(int i = 0; i < data.length;i++){
            T[] rowData = data[i];
            int spanWidthSize =0;
            for(int j = 0; j <rowData.length;j++){
                T t = rowData[j];
                createArrayRow(spanSize, newArray, i, rowData);
                while(exitArray[i][spanWidthSize] ==1){
                    spanWidthSize++;
                }
                newArray[i][spanWidthSize] = t;
                if(t.getSpanHeightSize() >1){
                    for(int row =i; row <i + t.getSpanHeightSize();row++){ //行
                        for(int col = spanWidthSize; col< spanWidthSize+t.getSpanWidthSize();col++){ //列
                           exitArray[row][col] = 1;
                        }
                    }
                }
                if(t.getSpanWidthSize() >1 || t.getSpanHeightSize() >1){
                    cellRanges.add(new CellRange(i,i+t.getSpanHeightSize()-1,
                            spanWidthSize,spanWidthSize+t.getSpanWidthSize()-1));
                }
                spanWidthSize +=t.getSpanWidthSize();
            }
        }
        newArray = ArrayTableData.transformColumnArray(newArray);
        FormTableData tableData =  createTableData(table,tableName,newArray,new TextDrawFormat<T>(){
            @Override
            public void setTextPaint(TableConfig config, CellInfo<T> cellInfo, Paint paint) {
                super.setTextPaint(config, cellInfo, paint);
                paint.setTextAlign(cellInfo.data == null? Paint.Align.CENTER:cellInfo.data.getAlign());

            }
        });
        tableData.setUserCellRange(cellRanges);
        return tableData;
    }


    private static <T extends IForm> void createArrayRow(int spanSize, T[][] newArray, int i, T[] rowData) {
        if(newArray[i] == null) {
            newArray[i] = (T[]) Array.newInstance(rowData.getClass().getComponentType(),spanSize);
        }
    }


    private static<T extends IForm> FormTableData<T> createTableData(SmartTable table, String tableName, T[][] data, IDrawFormat<T> drawFormat){
        table.getConfig().setShowColumnTitle(false);
        List<Column<T>> columns = new ArrayList<>();
        for (T[] dataArray : data) {
            Column<T> column = new Column<>("", null, drawFormat);
            column.setDatas(Arrays.asList(dataArray));
            columns.add(column);
        }
        ArrayList<T> arrayList = new ArrayList<>(Arrays.asList(data[0]));
        FormTableData<T> tableData =  new FormTableData<>(tableName,arrayList,columns);
        tableData.setData(data);
        return tableData;
    }
    /**
     * 二维数组的构造方法
     *
     * @param tableName 表名
     * @param t         数据
     * @param columns   列
     */
    private FormTableData(String tableName, List<T> t, List<Column<T>> columns) {
        super(tableName, t, columns);
    }
}
