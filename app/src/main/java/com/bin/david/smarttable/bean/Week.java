package com.bin.david.smarttable.bean;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */

public class Week {
    private String name;
    private List<Time> times;

    public Week(String name, List<Time> times) {
        this.name = name;
        this.times = times;
    }
}
