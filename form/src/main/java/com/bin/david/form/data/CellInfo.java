package com.bin.david.form.data;

/**
 * Created by huang on 2017/11/15.
 */

public class CellInfo<T> {

    public T t;

    public int position;


    public Column<T> column;

    public String value;

    public void set(Column<T> column,T t,String value, int position){
        this.column = column;
        this.value= value;
        this.t = t;
        this.position = position;
    }





}
