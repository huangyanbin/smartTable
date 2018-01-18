package com.bin.david.form.data;

/**
 * Created by huang on 2017/11/15.
 * 单元格数据
 */

public class CellInfo<T> {
    /**
     * 数据
     */
    public T data;
    /**
     * 所在列位置
     */
    public int position;

    /**
     * 所在列
     */
    public Column<T> column;
    /**
     * 显示的值
     */
    public String value;

    public void set(Column<T> column,T t,String value, int position){
        this.column = column;
        this.value= value;
        this.data = t;
        this.position = position;
    }

}
