package com.bin.david.smarttable.bean;

import com.bin.david.smarttable.bean.Week;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */

public class CollegeStudent {
    private String name;
    private int age;
    private List<Week> weeks;

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
