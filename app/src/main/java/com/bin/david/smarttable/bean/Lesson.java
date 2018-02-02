package com.bin.david.smarttable.bean;

import com.bin.david.form.annotation.SmartColumn;

/**
 * Created by huang on 2018/2/1.
 */

public class Lesson {
    @SmartColumn(id = 3,name = "课程名称")
    private String name;
    private  boolean isFav;

    public Lesson(String name, boolean isFav) {
        this.name = name;
        this.isFav = isFav;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }


}
