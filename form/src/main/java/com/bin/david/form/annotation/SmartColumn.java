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
     * @return
     */
    String name() default "";

    /**
     * id 越小越在前面
     * @return
     */
    int id() default 0;

    String parent() default "";

    /**
     * 对齐
     */
    Paint.Align align() default Paint.Align.CENTER;
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

    boolean autoCount() default false;
    boolean fixed() default false;


}
