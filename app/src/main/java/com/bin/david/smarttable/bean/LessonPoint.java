package com.bin.david.smarttable.bean;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;

import java.util.List;

/**
 * Created by huang on 2018/2/2.
 */

public class LessonPoint {

    @SmartColumn(id=4,name="知识点")
    private String name;
    @SmartColumn(id=4,name="More",type = ColumnType.ArrayOwn)
    private List<String> more;
    public LessonPoint(String name) {
        this.name = name;
    }
}
