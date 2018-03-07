package com.bin.david.form.data.column;

import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;

import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2018/2/1.
 * 用于解析Map Column
 */

public class MapColumn<T> extends ArrayColumn<T> {

    private boolean isArrayColumn;


    public MapColumn(String columnName, String fieldName) {
        super(columnName, fieldName);
    }

    public MapColumn(String columnName, String fieldName, boolean isThoroughArray) {
        super(columnName, fieldName, isThoroughArray);
    }

    public MapColumn(String columnName, String fieldName, boolean isThoroughArray, IFormat<T> format) {
        super(columnName, fieldName, isThoroughArray, format);
    }

    public MapColumn(String columnName, String fieldName, boolean isThoroughArray, IDrawFormat<T> drawFormat) {
        super(columnName, fieldName, isThoroughArray, drawFormat);
    }

    public MapColumn(String columnName, String fieldName, boolean isThoroughArray, IFormat<T> format, IDrawFormat<T> drawFormat) {
        super(columnName, fieldName, isThoroughArray, format, drawFormat);
    }




    protected void getFieldData(String[] fieldNames,int start,Object child,int level,boolean isFoot) throws NoSuchFieldException, IllegalAccessException {

        for (int i = start; i < fieldNames.length; i++) {
            if (child == null) {
                addData(null, isFoot);
                countColumnValue(null);
                getStructure().putNull(level,isFoot);
                break;
            }
            if (child instanceof Map) {
                child = ((Map) child).get(fieldNames[i]);
                if (!isList(child)) {
                    if (i == fieldNames.length - 1) {
                        if (child == null) {
                            getStructure().putNull(level,isFoot);
                        }
                        T t = (T) child;
                        addData(t, true);
                        countColumnValue(t);
                    }
                } else {
                    level++;
                    if (child.getClass().isArray()) {
                        isArrayColumn = true;
                        T[] data = (T[]) child;
                        setArrayType(ARRAY);
                        for (Object d : data) {
                            if (i == fieldNames.length - 1) {
                                addData((T) d, true);
                            } else {
                                getFieldData(fieldNames, i + 1, d, level, true);
                            }
                        }
                        getStructure().put(level - 1, data.length,isFoot);
                    } else {
                        List data = (List) child;
                        setArrayType(LIST);
                        isArrayColumn = true;
                        for (Object d : data) {
                            if (i == fieldNames.length - 1) {
                                T t = (T) d;
                                addData(t, true);
                            } else {
                                getFieldData(fieldNames, i + 1, d, level, true);
                            }

                        }
                        getStructure().put(level - 1, data.size(),isFoot);
                    }
                    break;
                }
            }
        }
    }





    @Override
    public int getSeizeCellSize(TableInfo tableInfo, int position) {
        if(isArrayColumn) {
            return super.getSeizeCellSize(tableInfo, position);
        }else {
            if(tableInfo.getArrayLineSize() == null){
                return 1;
            }
            return tableInfo.getArrayLineSize()[position];
        }
    }
}
