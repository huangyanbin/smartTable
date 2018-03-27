package com.bin.david.form.data.column;

import android.graphics.Paint;

import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.count.DecimalCountFormat;
import com.bin.david.form.data.format.count.ICountFormat;
import com.bin.david.form.data.format.count.NumberCountFormat;
import com.bin.david.form.data.format.count.StringCountFormat;
import com.bin.david.form.data.format.draw.FastTextDrawFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.MultiLineDrawFormat;
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

    public static final String INVAL_VALUE = "";
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
    private int computeWidth;
    private int level;
    private Comparator<T> comparator;
    private ICountFormat<T,? extends Number> countFormat;
    private boolean isReverseSort;
    private OnColumnItemClickListener<T> onColumnItemClickListener;
    private Paint.Align textAlign;
    private Paint.Align titleAlign;
    private boolean isAutoCount =false;
    private boolean isAutoMerge = false; //是否自动合并单元格
    private int maxMergeCount = Integer.MAX_VALUE;
    private int id;
    private boolean isParent;
    private List<int[]> ranges; //合并数据
    private boolean isFast;
    private int minWidth;
    private int minHeight;
    private int width;



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
        this.drawFormat =drawFormat;
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
        if(drawFormat== null){
            drawFormat = isFast ? new FastTextDrawFormat<T>() : new TextDrawFormat<T>();
        }
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
        String[] fieldNames = fieldName.split("\\.");
        if (fieldNames.length >0) {
            Object child = o;
            for (int i = 0; i < fieldNames.length; i++) {
                if (child == null) {
                    return null;
                }
                Class childClazz = child.getClass();
                Field childField = childClazz.getDeclaredField(fieldNames[i]);
                if (childField == null) {
                    return null;
                }
                childField.setAccessible(true);
                if (i == fieldNames.length - 1) {
                    return (T) childField.get(child);

                } else {
                    child = childField.get(child);
                }
            }

        }
        return  null;
    }

    /**
     * 填充数据
     * @param objects 对象列表
     * @return 返回需要合并的单元
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public void fillData(List<Object> objects) throws NoSuchFieldException, IllegalAccessException {
        if(countFormat != null){
            countFormat.clearCount();
        }
        if (objects.size() > 0) {
            String[] fieldNames = fieldName.split("\\.");
            if (fieldNames.length>  0) {
                Field[] fields = new Field[fieldNames.length];
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object child= objects.get(k);
                    for (int i = 0; i < fieldNames.length; i++) {
                        if (child == null) {
                            addData(null,true);
                            countColumnValue(null);
                            break;
                        }
                        Field childField;
                        if(fields[i] != null){
                            childField = fields[i];
                        }else {
                            Class childClazz = child.getClass();
                            childField = childClazz.getDeclaredField(fieldNames[i]);
                            childField.setAccessible(true);
                            fields[i] = childField;
                        }
                        if (childField == null) {
                            addData(null,true);
                            countColumnValue(null);
                            break;
                        }
                        if (i == fieldNames.length - 1) {
                            T t = (T) childField.get(child);
                            addData(t, true);
                            countColumnValue(t);
                        } else {
                            child = childField.get(child);
                        }
                    }

                }
            }
        }

    }


    /**
     * 填充数据
     * @param objects 对象列表
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public void addData(List<Object> objects, int startPosition,boolean isFoot) throws NoSuchFieldException, IllegalAccessException {
        if(objects.size()+ startPosition == datas.size()){
            return;
        }
        if (objects.size() > 0) {
            String[] fieldNames = fieldName.split("\\.");
            if (fieldNames.length >0) {
                int size = objects.size();
                for (int k = 0; k < size; k++) {
                    Object child= objects.get(isFoot ? k:(size-1-k));
                    for (int i = 0; i < fieldNames.length; i++) {
                        if (child == null) {
                            addData(null,isFoot);
                            countColumnValue(null);
                            break;
                        }
                        Class childClazz = child.getClass();
                        Field childField = childClazz.getDeclaredField(fieldNames[i]);
                        if (childField == null) {
                            addData(null,isFoot);
                            countColumnValue(null);
                            break;
                        }
                        childField.setAccessible(true);
                        if (i == fieldNames.length - 1) {
                            T t = (T) childField.get(child);
                            addData(t, isFoot);
                            countColumnValue(t);
                        } else {
                            child = childField.get(child);
                        }
                    }

                }
            }
        }
    }




    public String format(int position){
       if(position >=0 && position< datas.size()){
          return format(datas.get(position));
       }
       return INVAL_VALUE;
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
            int rangeCount = 1;
            for (int i = 0; i < size; i++) {
                String val = format(datas.get(i));
                if(rangeCount < maxMergeCount && perVal !=null && val !=null
                        && val.length() != 0 && val.equals(perVal)){
                    if(rangeStartPosition ==-1){
                        rangeStartPosition = i-1;
                    }
                    rangeCount++;
                    //修复最后一列没有合并
                    if(i == size-1){
                        int[] range = {rangeStartPosition, i};
                        ranges.add(range);
                        rangeStartPosition =-1;
                        rangeCount =1;
                    }
                }else{
                    if(rangeStartPosition !=-1){
                        int[] range = {rangeStartPosition, i-1};
                        ranges.add(range);
                        rangeStartPosition =-1;
                        rangeCount =1;
                    }
                }
                perVal = val;
            }
        }
        return ranges;
    }

    public String format(T t){
        String value;
        if (format != null) {
            value = format.format(t);
        } else {
            value = t == null ? INVAL_VALUE : t.toString();
        }
        return value;
    }

    /**
     * 统计数据
     *
     */
    protected void countColumnValue(T t) {
        if(t != null && isAutoCount && countFormat ==null){
            if(LetterUtils.isBasicType(t)){
                if(LetterUtils.isNumber(t)) {
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
    }


    /**
     * 动态添加数据
     * @param t 数据
     * @param isFoot 是否添加到尾部
     */
    protected void addData(T t,boolean isFoot){
        if(isFoot) {
            datas.add(t);
        }else {
            datas.add(0,t);
        }

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
     * 获取列的计算的宽度
     * @return 宽度
     */
    public int getComputeWidth() {
        return computeWidth;
    }
    /**
     * 设置列的计算宽度
     */
    public void setComputeWidth(int computeWidth) {
        this.computeWidth = computeWidth;
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

    /**
     * 是否快速显示
     * 当所显示为单行，且列字体大小不变，可以使用isFast来更快加载
     * @return 是否快速显示
     */
    public boolean isFast() {
        return isFast;
    }

    /**
     * 设置是否快速显示
     * 当所显示为单行，且列字体大小不变，可以使用isFast来更快加载
     */
    public void setFast(boolean fast) {
        isFast = fast;
        drawFormat = isFast ? new FastTextDrawFormat<T>() : new TextDrawFormat<T>();
    }

    /**
     * 获取Position所占格子
     * @return 格子数
     */
    public int getSeizeCellSize(TableInfo tableInfo,int position){
        return tableInfo.getArrayLineSize()[position];
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }



    public Paint.Align getTitleAlign() {
        return titleAlign;
    }

    /**
     * 设置标题对齐方式
     * @param titleAlign
     */
    public void setTitleAlign(Paint.Align titleAlign) {
        this.titleAlign = titleAlign;
    }

    /**
     * 设置列的宽度
     * @param width
     */
    public void setWidth(int width) {
        if(width >0) {
            this.width = width;
            this.setDrawFormat(new MultiLineDrawFormat<T>(width));
        }
    }

    /**
     * 获取列的宽度
     * @param
     */
    public int getWidth() {
        if(width == 0){
            return computeWidth;
        }
        return width;
    }

    public List<int[]> getRanges() {
        return ranges;
    }

    public void setRanges(List<int[]> ranges) {
        this.ranges = ranges;
    }
}
