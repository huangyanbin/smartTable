package com.bin.david.form.data.column;


import com.bin.david.form.data.ArrayStructure;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by huang on 2018/2/1.
 * 用于解析数组Column
 */

public class ArrayColumn<T> extends Column<T> {

    public static final int ARRAY = 1;
    public static final int LIST = 2;
    private ColumnNode node;
    private ArrayStructure structure;
    /**
     * 数组类型
     */
    private int arrayType;
    /**
     * 是否查询到Array object 内部
     */
    private boolean isThoroughArray = false;

    public ArrayColumn(String columnName, String fieldName) {
        this(columnName, fieldName,true,null,null);
    }
    public ArrayColumn(String columnName, String fieldName,boolean isThoroughArray) {
        this(columnName, fieldName,isThoroughArray,null,null);
    }

    public ArrayColumn(String columnName, String fieldName,boolean isThoroughArray, IFormat<T> format) {
        this(columnName, fieldName, isThoroughArray,format,null);
    }

    public ArrayColumn(String columnName, String fieldName,boolean isThoroughArray, IDrawFormat<T> drawFormat) {
        this(columnName, fieldName,isThoroughArray, null,drawFormat);
    }

    public ArrayColumn(String columnName, String fieldName,boolean isThoroughArray, IFormat<T> format, IDrawFormat<T> drawFormat) {
        super(columnName, fieldName, format, drawFormat);
        structure = new ArrayStructure();
        this.isThoroughArray = isThoroughArray;
    }


    /**
     * 填充数据
     * @param objects 对象列表
     * @return 返回需要合并的单元
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Override
    public void fillData(List<Object> objects) throws NoSuchFieldException, IllegalAccessException {
        structure.clear();
        structure.setMaxLevel(getLevel());
        if(getCountFormat() != null){
            getCountFormat().clearCount();
        }
        if (objects.size() > 0) {
            String[] fieldNames = getFieldName().split("\\.");
            if (fieldNames.length > 0) {
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object child= objects.get(k);
                    getFieldData(fieldNames,0,child,0,true);
                }
            }

        }
    }



    /**
     * 添加数据
     * @param objects 对象列表
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public void addData(List<Object> objects, int startPosition,boolean isFoot) throws NoSuchFieldException, IllegalAccessException {
        if (objects.size() > 0) {
            String[] fieldNames = getFieldName().split("\\.");
            if (fieldNames.length >0) {
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object child= objects.get(isFoot ? k:(size-1-k));
                    getFieldData(fieldNames,0,child,0,true);
                }
            }
        }
    }

    /**
     * 递归解析
     * @param fieldNames
     * @param start
     * @param child
     * @param level
     * @param isFoot
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected void getFieldData(String[] fieldNames,int start,Object child,int level,boolean isFoot) throws NoSuchFieldException, IllegalAccessException {

        for (int i = start; i < fieldNames.length; i++) {
            if (child == null) {
                addData(null,isFoot);
                countColumnValue(null);
                structure.putNull(level,isFoot);
                break;
            }
            Class childClazz = child.getClass();
            Field childField = childClazz.getDeclaredField(fieldNames[i]);
            childField.setAccessible(true);
            child = childField.get(child);
            if(!isList(child)) {
                if (i == fieldNames.length - 1) {
                    if(child == null){
                        structure.putNull(level,isFoot);
                    }
                    T t = (T) child;
                    addData(t, true);
                    countColumnValue(t);
                }
            }else{
               level++;
              if(child.getClass().isArray()){
                  T[] data = (T[]) child;
                  arrayType = ARRAY;
                  for (Object d : data) {
                      if (i == fieldNames.length - 1) {
                          addData((T)d, true);
                      } else {
                          getFieldData(fieldNames, i + 1, d,level,true);
                      }
                  }
                  structure.put(level-1,data.length,isFoot);
              }else {
                  List data = (List) child;
                  arrayType = LIST;
                  for (Object d : data) {
                      if (i == fieldNames.length - 1) {
                          T t = (T) d;
                          addData(t, true);
                      } else {
                          getFieldData(fieldNames, i + 1, d,level,true);
                      }

                  }
                  structure.put(level-1,data.size(),isFoot);
              }
              break;
            }
        }
    }

    /**
     * 判断是否是数组或者List
     * @param o
     * @return
     */
    public static boolean isList(Object o){
        return o !=null && (o instanceof List  || o.getClass().isArray());
    }


    /**
     * 获取当前层级
     * @return
     */
    public int getLevel(){
        return  ColumnNode.getLevel(node,0)-1;
    }

    public ColumnNode getNode() {
        return node;
    }

    /**
     * 设置Array结构节点
     * @param node  结构节点
     */
    public void setNode(ColumnNode node) {
        this.node = node;
    }

    /**
     * 获取Array类型
     */
    public int getArrayType() {
        return arrayType;
    }

    public void setArrayType(int arrayType) {
        this.arrayType = arrayType;
    }

    /**
     * 获取Array结构
     * @return Array结构
     */
    public ArrayStructure getStructure() {
        return structure;
    }
    /**
     * 设置Array结构
     */
    public void setStructure(ArrayStructure structure) {
        this.structure = structure;
    }

    /**
     * 是否继续查询Array每个对象内部
     * @return 是否继续
     */
    public boolean isThoroughArray() {
        return isThoroughArray;
    }
    /**
     * 设置是否继续查询Array每个对象内部
     */
    public void setThoroughArray(boolean thoroughArray) {
        isThoroughArray = thoroughArray;
    }

    /**
     * 获取Position所占格子
     * @return 格子数
     */
    @Override
    public int getSeizeCellSize(TableInfo tableInfo, int position){
        return structure.getCellSizes().get(position);
    }



}
