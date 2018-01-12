package com.bin.david.smarttable.bean;

import android.graphics.Paint;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by huang on 2017/11/4.
 */
@SmartTable(name = "PM2.5")
public class PM25 {


    /**
     * aqi : 27
     * area : 上海
     * pm10 : 27
     * pm10_24h : 58
     * position_name : 徐汇上师大
     * primary_pollutant : null
     * quality : 优
     * station_code : 1144A
     * time_point : 2017-11-04T13:00:00Z
     */

    private int aqi;
    @SmartColumn(id =1,name="地区",align = Paint.Align.LEFT)
    private String area;
    @SmartColumn(parent = "PM",id =3,name="PM10",align = Paint.Align.RIGHT)
    private int pm10;
    @SmartColumn(parent = "PM",id =2,name="PM10 24小时")
    private int pm10_24h;
    @SmartColumn(id=4,name = "地址",fixed = true)
    private String position_name;
    @SmartColumn(id=6,name="首要污染物")
    private Object primary_pollutant;
    @SmartColumn(id=5,name = "空气质量",fixed = true)
    private String quality;
    @SmartColumn(id=3,name = "坐标代码",fixed = true)
    private String station_code;
    @SmartColumn(id=7,name = "更新时间")
    private String time_point;

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getPm10_24h() {
        return pm10_24h;
    }

    public void setPm10_24h(int pm10_24h) {
        this.pm10_24h = pm10_24h;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public Object getPrimary_pollutant() {
        return primary_pollutant;
    }

    public void setPrimary_pollutant(Object primary_pollutant) {
        this.primary_pollutant = primary_pollutant;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getStation_code() {
        return station_code;
    }

    public void setStation_code(String station_code) {
        this.station_code = station_code;
    }

    public String getTime_point() {
        return time_point;
    }

    public void setTime_point(String time_point) {
        this.time_point = time_point;
    }
}
