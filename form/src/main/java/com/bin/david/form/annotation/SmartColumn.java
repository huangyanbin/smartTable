package com.bin.david.form.annotation;


import android.graphics.Paint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by huang on 2017/11/4.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SmartColumn {

    /**
     * 名称
     */
    String name() default "";

    /**
     * id 越小越在前面
     */
    int id() default 0;

    /**
     * 父列名称
     */
    String parent() default "";

    /**
     * 对齐
     */
    Paint.Align align() default Paint.Align.CENTER;

    /**
     * 列标题对齐
     */
    Paint.Align titleAlign() default Paint.Align.CENTER;
    /**
     * 设置是否查询下一级
     * @return 是否查询下一级
     */
    ColumnType type() default ColumnType.Own;
    /**
     * 设置是否自动合并
     * @return 是否自动合并
     */
    boolean autoMerge() default false;
    /**
     * 设置自动合并最大数量
     * @return 自动合并最大数量
     */
    int maxMergeCount() default -1;
    /**
     * 开启自动统计
     */
    boolean autoCount() default false;
    /**
     * 是否固定该列
     */
    boolean fixed() default false;


    /**
     * 是否开启快速显示
     * 当列的字体大小不变且为单行，可以开启快速显示
     */
    boolean fast() default false;

    /**
     * 最小宽度
     */
    int minWidth() default 0;

    /**
     * 最小高度
     */
    int minHeight() default 0;

    /**
     * 指定宽度
     * @return
     */
    int width() default 0;


}
