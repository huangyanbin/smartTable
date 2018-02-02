package com.bin.david.form.data;

import android.util.Log;

import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by huang on 2018/2/1.
 * 用于解析数组Column
 */

public class ArrayColumn<T> extends Column<T> {

    private int[] lastPositionArray;
    private ColumnNode node;

    public ArrayColumn(String columnName, String fieldName) {
        this(columnName, fieldName,null,null);
    }

    public ArrayColumn(String columnName, String fieldName, IFormat<T> format) {
        this(columnName, fieldName, format,null);
    }

    public ArrayColumn(String columnName, String fieldName, IDrawFormat<T> drawFormat) {
        this(columnName, fieldName, null,drawFormat);
    }

    public ArrayColumn(String columnName, String fieldName, IFormat<T> format, IDrawFormat<T> drawFormat) {
        super(columnName, fieldName, format, drawFormat);

    }


    @Override
    public void fillData(List<Object> objects) throws NoSuchFieldException, IllegalAccessException {
        if(getCountFormat() != null){
            getCountFormat().clearCount();
        }
        if (objects.size() > 0) {
            lastPositionArray = new int[objects.size()];
            String[] fieldNames = getFieldName().split("\\.");
            if (fieldNames.length > 0) {
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object child= objects.get(k);
                    getFieldData(fieldNames,0,child);
                    lastPositionArray[k] = getDatas().size()-1;
                }
            }

        }
    }

    private void getFieldData( String[] fieldNames,int start,Object child) throws NoSuchFieldException, IllegalAccessException {

        for (int i = start; i < fieldNames.length; i++) {
            if (child == null) {
                addData(null,true);
                countColumnValue(null);
                break;
            }
            Class childClazz = child.getClass();
            Field childField = childClazz.getDeclaredField(fieldNames[i]);
            childField.setAccessible(true);
            child = childField.get(child);
            if(!isList(child)) {
                if (i == fieldNames.length - 1) {
                    T t = (T) child;
                    addData(t, true);
                    countColumnValue(t);
                }
            }else{
              List data = (List) child;
              for(Object d :data){
                  if (i == fieldNames.length - 1) {
                      T t = (T) d;
                      addData(t,true);
                  }else {
                      getFieldData(fieldNames, i + 1, d);
                  }
              }
              break;
            }
        }
    }



    private boolean isList(Object o){
        return o !=null && o instanceof List ;
    }

    public int getLineCount(int position){
        if(lastPositionArray == null){
            return 1;
        }
        int size;
        if(position == 0){
            size = lastPositionArray[position]+1;
        }else{
            size = lastPositionArray[position] - lastPositionArray[position-1];
        }
        return size;
    }

    public ColumnNode getNode() {
        return node;
    }

    public void setNode(ColumnNode node) {
        this.node = node;
    }

    public int[] getLastPositionArray() {
        return lastPositionArray;
    }

    public void setLastPositionArray(int[] lastPositionArray) {
        this.lastPositionArray = lastPositionArray;
    }
}
