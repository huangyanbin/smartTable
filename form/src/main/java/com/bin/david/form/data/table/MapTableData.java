package com.bin.david.form.data.table;


import com.bin.david.form.data.column.ArrayColumn;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.MapColumn;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2018/1/14.
 * Map表格数据
 * 可用于Json数据展示
 */

public class MapTableData extends TableData<Object> {

    private FilterColumnIntercept mIntercept;

    /**
     * 创建Map表格数据
     *
     * @param tableName 表格名
     * @param mapList   Map数组
     */
    public static MapTableData create(String tableName, List<Object> mapList) {
        return create(tableName, mapList, null);
    }

    /**
     * 创建Map表格数据
     *
     * @param tableName 表格名
     * @param mapList   Map数组
     * @param keyFormat map中key格式化
     */
    public static MapTableData create(String tableName, List<Object> mapList, IFormat<String> keyFormat) {
        if (mapList != null) {
            List<Column> columns = new ArrayList<>();
            getMapColumn(columns, Column.INVAL_VALUE, Column.INVAL_VALUE, mapList, keyFormat);
            return new MapTableData(tableName, mapList, columns);
        }
        return null;
    }

    /**
     * 获取Map中所有字段
     * 暂时只支持Map中List数据解析 不支持数组[]
     */
    private static void getMapColumn(List<Column> columns, String fieldName, String parentKey, List<Object> mapList, IFormat<String> keyFormat) {
        if (mapList != null && mapList.size() > 0) {
            Object o = mapList.get(0);
            if (o != null) {
                if (o instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) o;
                    //暂时只能解析json每个层级一个Array
                    boolean isOneArray = true;
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Object val = entry.getValue();
                        if (ArrayColumn.isList(val)) {
                            if (isOneArray) {
                                List<Object> list = ((List) val);
                                getMapColumn(columns, fieldName + key + ".", key, list, keyFormat);
                                isOneArray = false;
                            }
                        } else {
                            String columnName = keyFormat == null ? key : keyFormat.format(key);
                            MapColumn<Object> column = new MapColumn<>(columnName, fieldName + key);
                            columns.add(column);
                        }
                    }
                } else {
                    String columnName = keyFormat == null ? parentKey : keyFormat.format(parentKey);
                    MapColumn<Object> column = new MapColumn<>(columnName, fieldName, false);
                    columns.add(column);
                }
            }
        }
    }


    private MapTableData(String tableName, List t, List<Column> columns) {
        super(tableName, t, columns);
    }

    /**
     * 设置绘制样式
     *
     * @param drawFormat
     */
    public void setDrawFormat(IDrawFormat drawFormat) {
        for (Column column : getColumns()) {
            column.setDrawFormat(drawFormat);
        }
    }

    /**
     * 设置格式化
     */
    public void setFormat(IFormat format) {
        for (Column column : getColumns()) {
            column.setFormat(format);
        }
    }

    /**
     * 设置最小宽度
     * @param minWidth
     */
    public void setMinWidth(int minWidth){
        for (Column column : getColumns()) {
            column.setMinWidth(minWidth);
        }
    }

    /**
     * 设置最小高度
     * @param minHeight
     */
    public void setMinHeight(int minHeight){
        for (Column column : getColumns()) {
            column.setMinHeight(minHeight);
        }
    }

    /**
     * 过滤列拦截器
     * 拦截则不会表格显示出来该列
     */
    public interface FilterColumnIntercept {
        /**
         * 是否拦截
         *
         * @param column     列
         * @param columnName 列名
         * @return 是否拦截
         */
        boolean onIntercept(Column column, String columnName);
    }

    /**
     * 获取过滤拦截器
     *
     * @return 过滤拦截器
     */
    public FilterColumnIntercept getFilterColumnIntercept() {
        return mIntercept;
    }

    /**
     * 设置过滤拦截器
     * 拦截则不会表格显示出来该列
     */
    public void setFilterColumnIntercept(FilterColumnIntercept intercept) {
        this.mIntercept = intercept;
        if (mIntercept != null) {
            for (int i = getColumns().size() - 1; i >= 0; i--) {
                Column column = getColumns().get(i);
                if (mIntercept.onIntercept(column, column.getColumnName())) {
                    getColumns().remove(i);
                }
            }
        }
    }



}
