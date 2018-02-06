package com.bin.david.smarttable.bean;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.util.List;

/**
 * Created by huang on 2017/11/1.
 */
@SmartTable(name="学生信息列表",count = true)
public class Student {
    @SmartColumn(id =1,name = "姓名",autoCount = true)
    private String name;
    @SmartColumn(id=8,name="年龄",autoCount = true)
    private int age;
    @SmartColumn(id =7,name="更新时间")
    private long time;
    @SmartColumn(type= ColumnType.Child)
    private ChildData childData;
    @SmartColumn(id =6,name="选中")
    private boolean isCheck;
    private String url;
    @SmartColumn(type = ColumnType.ArrayChild)
    private List<Lesson> lessons;

    public Student(String name, int age, long time, boolean isCheck, ChildData childData) {
        this.name = name;
        this.age = age;
        this.time = time;
        this.childData = childData;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ChildData getChildData() {
        return childData;
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}
