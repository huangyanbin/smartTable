package com.bin.david.smarttable.bean;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;

import java.util.List;

/**
 * Created by huang on 2018/2/1.
 */

public class Lesson {
    @SmartColumn(id = 3,name = "课程名称")
    private String name;
    @SmartColumn(id = 5,name="是否喜欢")
    private  boolean isFav;
    @SmartColumn(type = ColumnType.ArrayChild)
    private LessonPoint[] lessonPoints;
    //@SmartColumn(type = ColumnType.ArrayOwn)
    private List<String> test;

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

    public List<String> getTest() {
        return test;
    }

    public void setTest(List<String> test) {
        this.test = test;
    }

    public LessonPoint[] getLessonPoints() {
        return lessonPoints;
    }

    public void setLessonPoints(LessonPoint[] lessonPoints) {
        this.lessonPoints = lessonPoints;
    }
}
