package com.bin.david.form.component;


import android.graphics.Rect;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.Cell;
import com.bin.david.form.data.table.TableData;

import java.util.List;

/**
 * Created by huang on 2018/1/16.
 * 网格绘制
 * 主要为了解决合并单元格问题
 */

public class GridDrawer<T>{


    private TableData<T> tableData;
    private Cell[][] rangePoints;

    public GridDrawer(){

    }

    public void setTableData(TableData<T> tableData) {
        this.tableData = tableData;
        this.rangePoints = tableData.getTableInfo().getRangeCells();

    }


    //矫正格子大小
    public Rect correctCellRect(int row, int col, Rect rect, float zoom) {
        if(rangePoints != null && rangePoints.length >row){
            Cell point = rangePoints[row][col];
            if (point != null) {
                if (point.col != Cell.INVALID && point.row != Cell.INVALID) {
                    List<Column> childColumns = tableData.getChildColumns();
                    int[] lineHeights = tableData.getTableInfo().getLineHeightArray();
                    int width = 0, height = 0;
                    for (int i = col; i < Math.min(childColumns.size(), col + point.col); i++) {
                        width += childColumns.get(i).getComputeWidth();
                    }
                    for (int i = row; i < Math.min(lineHeights.length, row + point.row); i++) {
                        height += lineHeights[i];
                     }
                    rect.right = (int) (rect.left + width*zoom);
                    rect.bottom = (int) (rect.top + height*zoom);
                    return rect;
                }
                return null;
            }
        }
        return rect;
    }


}
