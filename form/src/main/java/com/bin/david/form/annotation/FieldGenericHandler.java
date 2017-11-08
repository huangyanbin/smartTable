package com.bin.david.form.annotation;

import com.bin.david.form.data.Column;

/**
 * Created by huang on 2017/11/4.
 */

public class FieldGenericHandler {

    public Column<?> getGenericColumn(Class<?> clazz, String name, String fieldName){
        Column<?> column = new Column<>(name,fieldName);
        return column;
    }
}
