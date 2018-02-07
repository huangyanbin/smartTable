package com.bin.david.smarttable.bean;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.bin.david.smarttable.bean.Week;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */
@SmartTable(name = "课程表")
public class CollegeStudent {
    @SmartColumn(id = 1,name ="姓名")
    private String name;

    @SmartColumn(type = ColumnType.ArrayChild)
    private List<Week> weeks;
    private int age;

    public CollegeStudent(String name, int age, List<Week> weeks) {
        this.name = name;
        this.age = age;
        this.weeks = weeks;
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

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
