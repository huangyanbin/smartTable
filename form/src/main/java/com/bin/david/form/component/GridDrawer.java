package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.table.TableData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huang on 2018/1/16.
 * 网格绘制
 * 主要为了解决合并单元格问题
 */

public class GridDrawer<T>{

    private Path path;
    private ArrayList<Integer> horizontalGrids;
    private ArrayList<Integer> verticalGrids;
    private Rect tableShowRect;
    private int hMinPosition;
    private int hMaxPosition;
    private int vMinPosition;
    private int vMaxPosition;
    private boolean isHasHData;
    private boolean isHasVData;
    private List<CellRange> cellRanges;
    private Set<Integer> set;
    private TableData<T> tableData;

    public GridDrawer(){
        path = new Path();
        tableShowRect = new Rect();
        horizontalGrids = new ArrayList<>();
        verticalGrids = new ArrayList<>();
        set = new HashSet<>();
    }

    public void reset(){
        horizontalGrids.clear();
        verticalGrids.clear();
        tableShowRect.set(-1,-1,-1,-1);
        isHasHData = false;
        isHasVData = false;
        set.clear();
    }

    public void addHorizontalGrid(int position, int left, int right, int y) {
        if(!isHasHData) {
            tableShowRect.left = left;
            tableShowRect.right = right;
            hMinPosition = position;
            hMaxPosition = position;
            isHasHData = true;
        }else {
            hMaxPosition = position;
        }
        horizontalGrids.add(y);
    }

    /**
     * 添加垂直网格位置
     * @param position
     * @param top
     * @param bottom
     * @param x
     */
    public void addVerticalGrid(int position, int top, int bottom, int x) {
        if(!isHasVData) {
            tableShowRect.top = top;
            tableShowRect.bottom = bottom;
            vMinPosition = position;
            vMaxPosition = position;
            isHasVData = true;
        }else {
            vMaxPosition = position;
        }
        verticalGrids.add(x);

    }

    /**
     * 绘制网格
     * @param canvas
     * @param config
     */
    public void drawGrid(Canvas canvas,TableConfig config){
        Paint paint = config.getPaint();
        drawHGrid(canvas, paint);
        drawVGrid(canvas, paint);
    }

    private void drawHGrid(Canvas canvas, Paint paint) {
        if(cellRanges ==null || cellRanges.size()==0) {
            for (int hGrid : horizontalGrids) {
                path.rewind();
                path.moveTo(tableShowRect.left, hGrid);
                path.lineTo(tableShowRect.right, hGrid);
                canvas.drawPath(path, paint);
            }
        }else{
            for (int k =0;k < horizontalGrids.size();k++) {
                int hGrid = horizontalGrids.get(k);
                int currentPosition = k+hMinPosition;
                path.rewind();
                path.moveTo(tableShowRect.left, hGrid);
                boolean isLineTo = false;
                set.clear();
                for(CellRange address: cellRanges){
                    if(address.getLastRow() > currentPosition && address.getFirstRow()<= currentPosition){
                        if(address.getLastCol() >= vMinPosition-1 || address.getFirstCol()<= vMaxPosition+1) {
                            for (int i = address.getFirstCol(); i <= address.getLastCol(); i++) {
                                set.add(i);
                            }
                        }
                    }
                }
                if(set.size() >0) {
                    for (int i = vMinPosition; i <= vMaxPosition; i++) {
                        if (set.contains(i)) {
                            if (!isLineTo) {
                                path.lineTo(verticalGrids.get(i - vMinPosition), hGrid);
                                isLineTo = true;
                            }
                        } else {
                            if (isLineTo) {
                                path.moveTo(verticalGrids.get(i - vMinPosition), hGrid);
                                isLineTo = false;
                            }
                        }
                    }
                }
                if(!isLineTo) {
                    path.lineTo(tableShowRect.right, hGrid);
                }
                canvas.drawPath(path, paint);
            }
        }
    }

    private void drawVGrid(Canvas canvas, Paint paint) {
        if(cellRanges ==null || cellRanges.size()==0) {
            for(int vGrid :verticalGrids){
                path.rewind();
                path.moveTo(vGrid, tableShowRect.top);
                path.lineTo(vGrid, tableShowRect.bottom);
                canvas.drawPath(path, paint);
            }
        }else{
            for (int k =0;k < verticalGrids.size();k++) {
                int vGrid = verticalGrids.get(k);
                int currentPosition = k+vMinPosition-1;
                path.rewind();
                path.moveTo(vGrid, tableShowRect.top);
                boolean isLineTo = false;
                set.clear();
                for(CellRange address: cellRanges){
                    if(address.getLastCol() > currentPosition && address.getFirstCol()<= currentPosition){
                        if(address.getLastRow() >= hMinPosition-1 || address.getFirstRow()<= hMaxPosition+1) {
                            for (int i = address.getFirstRow(); i <= address.getLastRow(); i++) {
                                set.add(i);
                            }
                        }
                    }
                }
                if(set.size() >0) {
                    for (int i = hMinPosition; i <= hMaxPosition; i++) {
                        if (set.contains(i) && i - hMinPosition-1 >=0) {
                            if (!isLineTo) {
                                path.lineTo(vGrid, horizontalGrids.get(i - hMinPosition-1));
                                isLineTo = true;
                            }
                        } else {
                            if (isLineTo && i - hMinPosition-1 >=0) {
                                path.moveTo(vGrid,horizontalGrids.get(i - hMinPosition-1));
                                isLineTo = false;
                            }
                        }
                    }
                }
                if(!isLineTo) {
                    path.lineTo(vGrid, tableShowRect.bottom);
                }
                canvas.drawPath(path, paint);
            }
        }
    }



    public void setTableData(TableData<T> tableData) {
        this.tableData = tableData;
        this.cellRanges = tableData.getCellRangeAddresses();

    }

    //矫正格子大小
    public Rect correctCellRect(int row, int col, Rect rect,float zoom){
        if(cellRanges !=null){
           for(CellRange range:cellRanges){
               if(range.contain(row,col)){
                   if(range.isLeftAndTop(row,col)){
                       List<Column> childColumns = tableData.getChildColumns();
                       int[] lineHeights =  tableData.getTableInfo().getLineHeightArray();
                       int width = 0,height = 0;
                       for(int i = col;i<= range.getLastCol();i++){
                           width += childColumns.get(i).getWidth()*zoom;
                       }
                       for(int i = row;i<= range.getLastRow();i++){
                           height += lineHeights[i]*zoom;
                       }
                       rect.right=rect.left+width;
                       rect.bottom=rect.top+height;
                       return rect;
                   }
                   return  null;
               }
           }
        }
        return rect;

    }
}
