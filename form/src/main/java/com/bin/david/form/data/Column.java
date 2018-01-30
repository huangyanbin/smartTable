package com.bin.david.form.data;

import android.graphics.Paint;

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

    private IFormat<T> format;
    private IDrawFormat<T> drawFormat;
    private String fieldName;
    private List<T> datas;
    private boolean isFixed;
    private int width;
    private int level;
    private Comparator<T> comparator;
    private ICountFormat<T,? extends Number> countFormat;
    private boolean isReverseSort;
    private OnColumnItemClickListener<T> onColumnItemClickListener;
    private Paint.Align textAlign;
    private boolean isAutoCount =false;
    private boolean isAutoMerge = false; //是否自动合并单元格
    private int maxMergeCount = Integer.MAX_VALUE;
    private int id;
    private boolean isParent;
    private List<int[]> ranges; //合并数据

    /**列构造方法
     * 用于构造组合列
     * @param columnName 列名
     * @param children 子列
     */
    public Column(String columnName, List<Column> children) {
        this.columnName = columnName;
        this.children = children;
        isParent = true;
    }
    /**列构造方法
     * 用于构造组合列
     * @param columnName 列名
     * @param children 子列
     */
    public Column(String columnName, Column... children) {
        this(columnName, Arrays.asList(children));
    }
    /**列构造方法
     * 用于构造子列
     * @param columnName 列名
     * @param fieldName 需要解析的反射字段
     */
    public Column(String columnName, String fieldName) {
        this(columnName, fieldName, null, null);
    }

    /**列构造方法
     * 用于构造子列
     * @param columnName 列名
     * @param fieldName 需要解析的反射字段
     * @param format 文字格式化
     */
    public Column(String columnName, String fieldName, IFormat<T> format) {
        this(columnName, fieldName, format, null);
    }
    /**列构造方法
     * 用于构造子列
     * @param columnName 列名
     * @param fieldName 需要解析的反射字段
     * @param drawFormat 绘制格式化
     */
    public Column(String columnName, String fieldName, IDrawFormat<T> drawFormat) {
        this(columnName, fieldName, null, drawFormat);
    }

    /**列构造方法
     * 用于构造子列
     * @param columnName 列名
     * @param fieldName 需要解析的反射字段
     * @param format 文字格式化
     * @param drawFormat 绘制格式化
     */
    public Column(String columnName, String fieldName, IFormat<T> format, IDrawFormat<T> drawFormat) {
        this.columnName = columnName;
        this.format = format;
        this.fieldName = fieldName;
        //默认给一个TextDrawFormat
        this.drawFormat = (drawFormat == null ? new TextDrawFormat<T>() : drawFormat);
        datas = new ArrayList<>();
    }



    /**
     * 获取列名
     * @return 列名
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 设置列名
     * @param columnName 列名
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 获取文字格式化
     * @return 文字格式化
     */
    public IFormat<T> getFormat() {
        return format;
    }
    /**
     * 设置文字格式化
     */
    public void setFormat(IFormat<T> format) {
        this.format = format;
    }
    /**
     * 获取反射name
     */
    public String getFieldName() {
        return fieldName;
    }
    /**
     * 设置反射name
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    /**
     * 设置子列
     */
    public void setChildren(List<Column> children) {
        this.children = children;
    }

    /**
     * 获取绘制格式化
     * @return 绘制格式化
     */
    public IDrawFormat<T> getDrawFormat() {
        return drawFormat;
    }
    /**
     * 设置绘制格式化
     */
    public void setDrawFormat(IDrawFormat<T> drawFormat) {
        this.drawFormat = drawFormat;
    }
    /**
     * 是否是父列 组合列
     */
    public boolean isParent() {
        return isParent;
    }
    /**
     * 设置是否是父列 组合列
     */
    public void setParent(boolean parent) {
        isParent = parent;
    }

    /**
     * 获取需要解析的数据
     * @return 数据
     */
    public List<T> getDatas() {
        return datas;
    }
    /**
     * 设置需要解析的数据
     * 直接设置数据，不需要反射获取值
     */
    public void setDatas(List<T> datas) {
        this.datas = datas;
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
                return getFieldValue(field, o,true);
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
                    return getFieldValue(childField, child,true);
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
         * @return 返回需要合并的单元
         * @throws NoSuchFieldException
         * @throws IllegalAccessException
         */

    public void fillData(List<Object> objects, TableInfo tableInfo, TableConfig config) throws NoSuchFieldException, IllegalAccessException {
        if(countFormat != null){
            countFormat.clearCount();
        }
        if (objects.size() > 0) {
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
                        addData(null,true);
                        setRowHeight(config, lineHeightArray, k,null);
                        continue;
                    }
                    if (fieldNames.length == 0 || fieldNames.length == 1) {
                        T t = getFieldValue(field, o,true);
                        setRowHeight(config, lineHeightArray, k,t);
                        continue;
                    }
                    for (int i = 0; i < fieldNames.length; i++) {
                        if (child == null) {
                            addData(null,true);
                            setRowHeight(config, lineHeightArray, k,null);
                            break;
                        }
                        Class childClazz = child.getClass();
                        Field childField = childClazz.getDeclaredField(fieldNames[i]);
                        if (childField == null) {
                            addData(null,true);
                            setRowHeight(config, lineHeightArray, k,null);
                            break;
                        }
                        if (i == fieldNames.length - 1) {
                            T t = getFieldValue(childField, child,true);
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

    public List<int[]> parseRanges(){
        if(isAutoMerge && maxMergeCount> 1 &&datas != null) {
            if(ranges != null){
                ranges.clear();
            }else{
                ranges = new ArrayList<>();
            }
            int size = datas.size();
            String perVal = null;
            int rangeStartPosition= -1;
            int rangeCount = 0;
            for (int i = 0; i < size; i++) {
                String val = format(datas.get(i));
                if(perVal !=null && val !=null
                        && val.length() != 0 && val.equals(perVal)){
                    if(rangeCount < maxMergeCount && rangeStartPosition ==-1){
                        rangeStartPosition = i-1;
                        rangeCount++;
                    }else{
                        rangeCount =0;
                    }
                }else{
                    if(rangeStartPosition !=-1){
                        int[] range = {rangeStartPosition, i-1};
                        ranges.add(range);
                        rangeStartPosition =-1;
                        rangeCount =0;
                    }
                }
                perVal = val;
            }
        }
        return ranges;
    }


    public void parseData(TableInfo tableInfo, TableConfig config){
        if(datas != null) {
            int size = datas.size();
            int[] lineHeightArray = tableInfo.getLineHeightArray();
            for (int i = 0; i < size; i++) {
                setRowHeight(config, lineHeightArray, i,null);
            }
        }
    }

    public String format(int position){
       if(position >=0 && position< datas.size()){
          return format(datas.get(position));
       }
       return "";
    }

    public String format(T t){
        String value;
        if (format != null) {
            value = format.format(t);
        } else {
            value = t == null ? "" : t.toString();
        }
        return value;
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
        int height = drawFormat.measureHeight(this, position, config)
                +2*config.getVerticalPadding();
        if (height > lineHeightArray[position]) {
            lineHeightArray[position] = height;
        }
    }

    /**
     * 填充数据
     * @param objects 对象列表
     * @param tableInfo 表格信息
     * @param config 配置
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public void addData(List<Object> objects, TableInfo tableInfo, TableConfig config,int startPosition,boolean isFoot) throws NoSuchFieldException, IllegalAccessException {
        if(objects.size()+ startPosition == datas.size()){
            return;
        }
        if (objects.size() > 0) {
            int[] lineHeightArray = tableInfo.getLineHeightArray();
            Object firstObject = objects.get(0);
            Class clazz = firstObject.getClass();
            String[] fieldNames = fieldName.split("\\.");
            String firstFieldName = fieldNames.length == 0 ? fieldName : fieldNames[0];
            Field field = clazz.getDeclaredField(firstFieldName);
            if (field != null) {
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object o = objects.get(isFoot ? k:(size-1-k));
                    Object child = o;
                    if (o == null) {
                        addData(null,isFoot);
                        setRowHeight(config, lineHeightArray, k+startPosition,null);
                        continue;
                    }
                    if (fieldNames.length == 0 || fieldNames.length == 1) {
                        T t = getFieldValue(field, o,isFoot);

                        setRowHeight(config, lineHeightArray, k+startPosition,t);
                        continue;
                    }
                    for (int i = 0; i < fieldNames.length; i++) {
                        if (child == null) {
                            addData(null,isFoot);
                            setRowHeight(config, lineHeightArray, k+startPosition,null);
                            break;
                        }
                        Class childClazz = child.getClass();
                        Field childField = childClazz.getDeclaredField(fieldNames[i]);
                        if (childField == null) {
                            addData(null,isFoot);
                            setRowHeight(config, lineHeightArray, k+startPosition,null);
                            break;
                        }
                        if (i == fieldNames.length - 1) {
                            T t = getFieldValue(childField, child,isFoot);
                            setRowHeight(config, lineHeightArray, k+startPosition,t);
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
     * 动态添加数据
     * @param t 数据
     * @param isFoot 是否添加到尾部
     */
    private void addData(T t,boolean isFoot){
        if(isFoot) {
            datas.add(t);
        }else {
            datas.add(0,t);
        }

    }


    /**
     * 反射得到值
     *
     * @param field 成员变量
     * @param o     对象
     * @throws IllegalAccessException
     */
    private T getFieldValue(Field field, Object o,boolean isFoot) throws IllegalAccessException {
        field.setAccessible(true);
        T t = (T) field.get(o);
        addData(t, isFoot);

        return t;
    }

    /**
     * 获取等级 如果上面没有parent 则为1，否则等于parent 递归+1
     * @return
     */
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 列的宽度
     * @return 宽度
     */
    public int getWidth() {
        return width;
    }
    /**
     * 设置列的宽度
     */
    public void setWidth(int width) {
        this.width = width;
    }


    /**
     * 统计总数
     * @return 最长值
     */
    public  String getTotalNumString(){
        if(countFormat != null){
            return countFormat.getCountString();
        }
        return "";
    }
    /**
     * 获取子列列表
     */
    public List<Column> getChildren() {
        return children;
    }

    /**
     * 添加子列
     * @param column
     */
    public void addChildren(Column column) {
        children.add(column);
    }

    /**
     * 获取用于排序比较器
     * @return 排序比较器
     */
    public Comparator<T> getComparator() {
        return comparator;
    }
    /**
     * 设置用于排序比较器
     */
    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * 获取统计格式化
     * @return 统计格式化
     */
    public ICountFormat<T, ? extends Number> getCountFormat() {
        return countFormat;
    }
    /**
     * 设置统计格式化
     */
    public void setCountFormat(ICountFormat<T, ? extends Number> countFormat) {
        this.countFormat = countFormat;
    }


    /**
     * 获取列ID
     * @return ID
     */
    public int getId() {
        return id;
    }
    /**
     * 设置列ID
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * 比较
     */
    @Override
    public int compareTo(Column o) {
        return  this.id - o.getId();
    }

    /**
     * 判断是否开启自动统计
     * @return 是否开启自动统计
     */
    public boolean isAutoCount() {
        return isAutoCount;
    }
    /**
     * 设置开启自动统计
     */
    public void setAutoCount(boolean autoCount) {
        isAutoCount = autoCount;
    }

    /**
     * 判断是否反序
     * @return 是否反序
     */
    public boolean isReverseSort() {
        return isReverseSort;
    }
    /**
     * 设置是否反序
     */
    public void setReverseSort(boolean reverseSort) {
        isReverseSort = reverseSort;
    }

    /**
     * 获取点击列监听
     * @return 点击列监听
     */
    public OnColumnItemClickListener<T> getOnColumnItemClickListener() {
        return onColumnItemClickListener;
    }
    /**
     * 设置点击列监听
     */
    public void setOnColumnItemClickListener(OnColumnItemClickListener<T> onColumnItemClickListener) {
        this.onColumnItemClickListener = onColumnItemClickListener;
    }


    /**
     * 判断是否固定
     * @return 是否固定
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * 设置是否固定
     */
    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    /**
     * 获取字体位置
     * @return Align
     */
    public Paint.Align getTextAlign() {
        return textAlign;
    }
    /**
     * 设置字体位置
     */
    public void setTextAlign(Paint.Align textAlign) {
        this.textAlign = textAlign;
    }

    /**
     * 是否自动合并
     */
    public boolean isAutoMerge() {
        return isAutoMerge;
    }
    /**
     * 设置是否自动合并
     *
     */
    public void setAutoMerge(boolean autoMerge) {
        isAutoMerge = autoMerge;
    }
    /**
     * 是否最大合并数量
     */
    public int getMaxMergeCount() {
        return maxMergeCount;
    }
    /**
     * 设置是否最大合并数量
     */
    public void setMaxMergeCount(int maxMergeCount) {
        this.maxMergeCount = maxMergeCount;
    }

}
