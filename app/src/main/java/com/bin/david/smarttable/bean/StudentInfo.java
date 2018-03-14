package com.bin.david.smarttable.bean;

/**
 * Created by huang on 2018/3/11.
 */

public class StudentInfo {

    private String name;
    private String url;

    public StudentInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
