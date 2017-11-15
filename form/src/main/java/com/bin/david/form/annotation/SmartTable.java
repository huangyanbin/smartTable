package com.bin.david.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by huang on 2017/11/4.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SmartTable {

    String name() default "";
    boolean count() default false;
    int pageSize() default Integer.MAX_VALUE;
    int currentPage() default 0;
}
