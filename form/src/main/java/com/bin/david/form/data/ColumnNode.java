package com.bin.david.form.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/2/1.
 * */

public class ColumnNode {

    private String name;
    private List<ColumnNode> children;
    private ArrayColumn arrayColumn;
    private ColumnNode parent;

    public ColumnNode(String name, ColumnNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public ColumnNode(String name, ColumnNode parent, ArrayColumn arrayColumn) {
        this(name,parent);
        this.arrayColumn = arrayColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnNode> getChildren() {
        return children;
    }


    public ArrayColumn getArrayColumn() {
        return arrayColumn;
    }

    public void setArrayColumn(ArrayColumn arrayColumn) {
        this.arrayColumn = arrayColumn;
    }

    public ColumnNode getParent() {
        return parent;
    }

    public void setParent(ColumnNode parent) {
        this.parent = parent;
    }

    public int getTotalLine(int position){
        int maxLineSize = 1;
        for(ColumnNode child :children){
            int childLineSize = child.getTotalLine(position);
            Log.e("huang","childLineSize"+childLineSize);
            if(maxLineSize < childLineSize){
                maxLineSize = childLineSize;
            }
        }
        if(arrayColumn !=null){
            int count = arrayColumn.getLineCount(position);
            maxLineSize = Math.max(count,maxLineSize);
        }
        Log.e("huang","maxLineSize"+maxLineSize);
        return maxLineSize;
    }






}
