package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.selected.ISelectFormat;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.matrix.MatrixHelper;

/**
 * Created by huang on 2018/1/12.
 * 选中操作
 * 这个类用于处理选中操作
 * 暂时只做比较简单点击效果
 */

public class SelectionOperation implements MatrixHelper.OnInterceptListener {
    /**
     * 选中区域
     */
    private static final int INVALID = -1; //无效坐标
    private Rect selectionRect;
    private ISelectFormat selectFormat;
    private int selectRow = INVALID;
    private int selectColumn = INVALID;
    private boolean isShow;

    void reset(){
        isShow = false;
    }

    SelectionOperation() {
        this.selectionRect = new Rect();
    }

    void setSelectionRect(int selectColumn,int selectRow, Rect rect){
        this.selectRow = selectRow;
        this.selectColumn = selectColumn;
        selectionRect.set(rect);
        isShow = true;
    }

    boolean isSelectedPoint( int selectColumn,int selectRow){

       return  selectRow == this.selectRow  && selectColumn == this.selectColumn;
    }

    void checkSelectedPoint(int selectColumn,int selectRow, Rect rect){

         if(isSelectedPoint(selectColumn,selectRow)){

             selectionRect.set(rect);
             isShow = true;
         }
    }





    public void draw(Canvas canvas, Rect showRect, TableConfig config){

        if(selectFormat !=null && isShow){
          selectFormat.draw(canvas,selectionRect,showRect,config);
        }
    }

    public ISelectFormat getSelectFormat() {
        return selectFormat;
    }

    void setSelectFormat(ISelectFormat selectFormat) {
        this.selectFormat = selectFormat;
    }

    @Override
    public boolean isIntercept(MotionEvent e1, float distanceX, float distanceY) {
        return false;
    }


}
