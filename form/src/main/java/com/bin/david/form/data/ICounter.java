package com.bin.david.form.data;

/**
 * Created by huang on 2017/11/3.
 * 统计器
 */

public interface ICounter<T,N extends Number> {

     N  count(T t);
}
