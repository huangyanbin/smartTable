package com.bin.david.form.data;


import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/2/1.
 * 用于解析数组Column
 */

public class ArrayColumn<T> extends Column<T> {

    public static final int ARRAY = 1;
    public static final int LIST = 2;
    private int[] lastPositionArray;
    private List<Integer> perSizeList;
    private ColumnNode node;
    private int arrayType;


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
        perSizeList = new ArrayList<>();

    }


    @Override
    public void fillData(List<Object> objects) throws NoSuchFieldException, IllegalAccessException {
        if(getCountFormat() != null){
            getCountFormat().clearCount();
        }
        perSizeList.clear();
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
        int level = ColumnNode.getNodeLevel(node,1);
        int tempLevel;
        for (int i = start; i < fieldNames.length; i++) {
            if (child == null) {
                addData(null,true);
                countColumnValue(null);
                recordPerSizeList(1);
                break;
            }
            tempLevel = level;
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
               tempLevel--;
              if(child.getClass().isArray()){
                  T[] data = (T[]) child;
                  arrayType = ARRAY;
                  for (Object d : data) {
                      if (i == fieldNames.length - 1) {
                          addData((T)d, true);
                      } else {
                          getFieldData(fieldNames, i + 1, d);
                      }
                  }
                  if(tempLevel == 0){
                      recordPerSizeList(data.length);
                  }
              }else {
                  List data = (List) child;
                  arrayType = LIST;
                  for (Object d : data) {
                      if (i == fieldNames.length - 1) {
                          T t = (T) d;
                          addData(t, true);
                      } else {
                          getFieldData(fieldNames, i + 1, d);
                      }
                      if(tempLevel == 0){
                          recordPerSizeList(data.size());
                      }
                  }
              }
              break;
            }
        }
    }

    private void recordPerSizeList(int size){
        int perListSize = perSizeList.size();
        if( perListSize== 0){
            perSizeList.add(size-1);
        }else{
            int per = perSizeList.get(perListSize-1);
            perSizeList.add(per+size-1);
        }
    }



    private boolean isList(Object o){
        return o !=null && (o instanceof List  || o.getClass().isArray());
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

    public int getArrayType() {
        return arrayType;
    }

    public void setArrayType(int arrayType) {
        this.arrayType = arrayType;
    }

    public List<Integer> getPerSizeList() {
        return perSizeList;
    }

    public int[] getPerStartAndEnd(int position){
        int end=  perSizeList.get(position);
        int start =0;
        if(position>=0){
            perSizeList.get(position-1);
        }
        return new int[]{start,end};
    }

    public void setPerSizeList(List<Integer> perSizeList) {
        this.perSizeList = perSizeList;
    }
}
