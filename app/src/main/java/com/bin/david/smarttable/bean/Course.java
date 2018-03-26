package com.bin.david.smarttable.bean;

/**
 * Created by huang on 2018/3/21.
 */

public class Course {

    private String name;
    private int period;

    public Course(String name, int period) {
        this.name = name;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
