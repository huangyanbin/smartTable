package com.bin.david.smarttable.excel;

import android.content.Context;

import com.bin.david.form.core.SmartTable;


/**
 * Created by huang on 2018/1/23.
 * excel 转换的接口
 */

public interface IExcel2Table<T> {
    /**
     * 初始化表格配置
     * @param context
     * @param table
     */
    void initTableConfig(Context context,SmartTable<T> table);

    /**
     * 回调
     * @param excelCallback
     */
    void setCallback(ExcelCallback excelCallback);

    /**
     * 加载Sheet
     * @param context
     * @param fileName
     */
    void loadSheetList(Context context,String fileName);

    /**
     * 加载表格Sheet内容
     * @param context
     * @param position
     */
    void loadSheetContent(Context context, int position);

    /**
     * 清理数据
     */
    void clear();

    void setIsAssetsFile(boolean isAssetsFile);


}
