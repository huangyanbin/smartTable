package com.bin.david.smarttable.bean;

import android.graphics.Paint;

import com.bin.david.form.data.form.IForm;

/**
 * Created by huang on 2018/4/10.
 */

public class Form implements IForm {

    private int spanWidthSize =1;
    private int spanHeightSize = 1;
    private String name;
    public static Form  Empty = new Form("");
    private Paint.Align align;
    public Form(int spanWidthSize, int spanHeightSize, String name) {
        this.spanWidthSize = spanWidthSize;
        this.spanHeightSize = spanHeightSize;
        this.name = name;
    }

    public Form(int spanWidthSize, int spanHeightSize, String name, Paint.Align align) {
        this.spanWidthSize = spanWidthSize;
        this.spanHeightSize = spanHeightSize;
        this.name = name;
        this.align = align;
    }

    public Form(String name) {
        this.name = name;
    }

    public Form(String name, Paint.Align align) {
        this.name = name;
        this.align = align;
    }

    public void setAlign(Paint.Align align) {
        this.align = align;
    }

    public void setSpanWidthSize(int spanWidthSize) {
        this.spanWidthSize = spanWidthSize;
    }

    public void setSpanHeightSize(int spanHeightSize) {
        this.spanHeightSize = spanHeightSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getSpanWidthSize() {
        return spanWidthSize;
    }

    @Override
    public int getSpanHeightSize() {
        return spanHeightSize;
    }

    @Override
    public Paint.Align getAlign() {
        if(align == null) {
            return Paint.Align.CENTER;
        }else {
            return align;
        }
    }




}
