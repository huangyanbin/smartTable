package com.bin.david.form.data;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.count.DecimalCountFormat;
import com.bin.david.form.data.format.count.ICountFormat;
import com.bin.david.form.data.format.count.NumberCountFormat;
import com.bin.david.form.data.format.count.StringCountFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.LetterUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huang on 2017/10/31.
 */

public class Column<T> implements Comparable<Column> {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 子类
     */
    private List<Column> children;

    private Column parent;

    private IFormat<T> format;
    private IDrawFormat<T> drawFormat;


    private String fieldName;

    private List<T> datas;

    private List<String> values;
    private boolean isFixed;
    private int maxValueLength = -1; //最长的长度
    private String longestValue = ""; //最长的值
    private int width;
    private int level;
    private Comparator<T> comparator;
    private ICountFormat<T,? extends Number> countFormat;
    private boolean isReverseSort;
    private OnColumnItemClickListener<T> onColumnItemClickListener;

    private boolean isAutoCount =false;
    private int id;

    private boolean isParent;

    public Column(String columnName, List<Column> children) {
        this.columnName = columnName;
        this.children = children;
        isParent = true;
    }

    public Column(String columnName, Column... children) {
        this(columnName, Arrays.asList(children));
    }

    public Column(String columnName, String fieldName) {
        this(columnName, fieldName, null, null);
    }

    public Column(String columnName, String fieldName, IFormat<T> format) {
        this(columnName, fieldName, format, null);
    }

    public Column(String columnName, String fieldName, IDrawFormat<T> format) {
        this(columnName, fieldName, null, format);
    }

    public Column(String columnName, String fieldName, IFormat<T> format, IDrawFormat<T> drawFormat) {
        this.columnName = columnName;
        this.format = format;
        this.fieldName = fieldName;
        //默认给一个TextDrawFormat
        this.drawFormat = (drawFormat == null ? new TextDrawFormat<T>() : drawFormat);
        datas = new ArrayList<>();
        values = new ArrayList<>();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


    public IFormat<T> getFormat() {
        return format;
    }

    public void setFormat(IFormat<T> format) {
        this.format = format;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setChildren(List<Column> children) {
        this.children = children;
    }

    public IDrawFormat<T> getDrawFormat() {
        return drawFormat;
    }

    public void setDrawFormat(IDrawFormat<T> drawFormat) {
        this.drawFormat = drawFormat;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }



    /**
     * 获取数据
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public T getData(Object o) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = o.getClass();
        String[] fieldNames = fieldName.split("\\.");
        String firstFieldName = fieldNames.length == 0 ? fieldName : fieldNames[0];
        Field field = clazz.getDeclaredField(firstFieldName);
        if (field != null) {
            Object child = o;
            if (fieldNames.length == 0 || fieldNames.length == 1) {
                return getFieldValue(field, o);
            }
            for (int i = 0; i < fieldNames.length; i++) {
                if (child == null) {
                    return null;
                }
                Class childClazz = child.getClass();
                Field childField = childClazz.getDeclaredField(fieldNames[i]);
                if (childField == null) {
                    return null;
                }
                if (i == fieldNames.length - 1) {
                    return getFieldValue(childField, child);
                } else {
                    field.setAccessible(true);
                    child = field.get(child);
                }
            }

        }
        return  null;
    }

        /**
         * 填充数据
         * @param objects 对象列表
         * @param tableInfo 表格信息
         * @param config 配置
         * @throws NoSuchFieldException
         * @throws IllegalAccessException
         */

    public void fillData(List<Object> objects, TableInfo tableInfo, TableConfig config) throws NoSuchFieldException, IllegalAccessException {
        if(countFormat != null){
            countFormat.clearCount();
        }
        if (objects != null && objects.size() > 0) {
            int[] lineHeightArray = tableInfo.getLineHeightArray();
            Object firstObject = objects.get(0);
            Class clazz = firstObject.getClass();
            String[] fieldNames = fieldName.split("\\.");
            String firstFieldName = fieldNames.length == 0 ? fieldName : fieldNames[0];
            Field field = clazz.getDeclaredField(firstFieldName);
            if (field != null) {
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object o = objects.get(k);
                    Object child = o;
                    if (o == null) {
                        datas.add(null);
                        values.add("");
                        setRowHeight(config, lineHeightArray, k,null);
                        continue;
                    }
                    if (fieldNames.length == 0 || fieldNames.length == 1) {
                        T t = getFieldValue(field, o);
                        setRowHeight(config, lineHeightArray, k,t);
                        continue;
                    }
                    for (int i = 0; i < fieldNames.length; i++) {
                        if (child == null) {
                            datas.add(null);
                            values.add("");
                            setRowHeight(config, lineHeightArray, k,null);
                            break;
                        }
                        Class childClazz = child.getClass();
                        Field childField = childClazz.getDeclaredField(fieldNames[i]);
                        if (childField == null) {
                            datas.add(null);
                            values.add("");
                            setRowHeight(config, lineHeightArray, k,null);
                            break;
                        }
                        if (i == fieldNames.length - 1) {
                            T t = getFieldValue(childField, child);
                            setRowHeight(config, lineHeightArray, k,t);
                        } else {
                            field.setAccessible(true);
                            child = field.get(child);
                        }
                    }

                }
            }
        }
    }

    /**
     * 设置每行的高度
     * 以及计算总数
     *
     * @param config          配置
     * @param lineHeightArray 储存高度数组
     * @param position        位置
     */
    private void setRowHeight(TableConfig config, int[] lineHeightArray, int position,T t) {
        if(t != null && isAutoCount && countFormat ==null){
            if(LetterUtils.isBasicType(t)){
                if(LetterUtils.isNumber(this)) {
                    countFormat = new NumberCountFormat<>();
                }else{
                    countFormat = new DecimalCountFormat<>();
                }
            }else{
                countFormat = new StringCountFormat<>(this);
            }
        }
        if(countFormat != null){
            countFormat.count(t);
        }
        int height = drawFormat.measureHeight(this, position, config);
        if (height > lineHeightArray[position]) {
            lineHeightArray[position] = height;
        }
    }

    /**
     * 反射得到值
     *
     * @param field 成员变量
     * @param o     对象
     * @throws IllegalAccessException
     */
    private T getFieldValue(Field field, Object o) throws IllegalAccessException {
        field.setAccessible(true);
        T t = (T) field.get(o);
        datas.add(t);
        String value;
        if (format != null) {
            value = format.format(t);
        } else {
            value = t == null ? "" : t.toString();
        }
        if (value.length() > maxValueLength) {
            maxValueLength = value.length();
            longestValue = value;
        }
        values.add(value);
        return t;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMaxValueLength() {
        return maxValueLength;
    }

    public void setMaxValueLength(int maxValueLength) {
        this.maxValueLength = maxValueLength;
    }

    public String getLongestValue() {
        return longestValue;
    }

    public  String getTotalNumString(){
        if(countFormat != null){
            return countFormat.getCountString();
        }
        return "";
    }

    public List<Column> getChildren() {
        return children;
    }

    public void addChildren(Column column) {
        children.add(column);
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public ICountFormat<T, ? extends Number> getCountFormat() {
        return countFormat;
    }

    public void setCountFormat(ICountFormat<T, ? extends Number> countFormat) {
        this.countFormat = countFormat;
    }

    public void setLongestValue(String longestValue) {
        this.longestValue = longestValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Column o) {
        return  this.id - o.getId();
    }

    public boolean isAutoCount() {
        return isAutoCount;
    }

    public void setAutoCount(boolean autoCount) {
        isAutoCount = autoCount;
    }

    public boolean isReverseSort() {
        return isReverseSort;
    }

    public void setReverseSort(boolean reverseSort) {
        isReverseSort = reverseSort;
    }

    public OnColumnItemClickListener<T> getOnColumnItemClickListener() {
        return onColumnItemClickListener;
    }

    public void setOnColumnItemClickListener(OnColumnItemClickListener<T> onColumnItemClickListener) {
        this.onColumnItemClickListener = onColumnItemClickListener;
    }



    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }


}
