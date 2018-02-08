package com.bin.david.form.data.column;

/**
 * Created by huang on 2017/11/2.
 * 列信息类
 * 用于保存计算出来列Column信息
 */

public class ColumnInfo {
    /**
     * 列宽度
     */
    public int width;
    /**
     * 列高度
     */
    public int height;
    /**
     * 列左边
     */
    public int left;
    /**
     * 列顶部
     */
    public int top;
    /**
     * 值
     */
    public String value;
    /**
     * 列
     */
    public Column column;
    /**
     * 父列数据
     * 便于递归查找
     */
    private ColumnInfo parent;

    public ColumnInfo() {

    }

    /**
     * 获取最顶层列信息
     *
     * @return 最顶层列信息
     */
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

    /**
     * 获取父列
     * @return 父列
     */
    public ColumnInfo getParent() {
        return parent;
    }
    /**
     * 设置父列
     */
    public void setParent(ColumnInfo parent) {
        this.parent = parent;
    }
}
