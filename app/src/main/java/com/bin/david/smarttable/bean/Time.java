package com.bin.david.smarttable.bean;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */

public class Time {

    private String time;
    private List<Lesson> lessons;

    public Time() {
    }

    public Time(String time, List<Lesson> lessons) {
        this.time = time;
        this.lessons = lessons;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
