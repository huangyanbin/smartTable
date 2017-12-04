package com.bin.david.form.data;

/**
 * Created by huang on 2017/11/2.
 */

public class ColumnInfo {

    public int width;
    public int height;
    public int left;
    public int top;
    public String value;
    public Column column;
    private ColumnInfo parent;

    public ColumnInfo() {

    }

    //获取顶层
    public ColumnInfo getTopParent(){

        return getParent(this);
    }

    /**
     * 递归查找顶层
     * @param column
     * @return
     */
    private ColumnInfo getParent(ColumnInfo column){
        if(column.getParent() != null){
            return getParent(column.getParent());
        }
        return column;
    }

    public ColumnInfo getParent() {
        return parent;
    }

    public void setParent(ColumnInfo parent) {
        this.parent = parent;
    }
}
