package com.bin.david.smarttable.bean;

import com.bin.david.form.core.ITableCellFormatData;

/**
 * author: DongYonghui
 * email: 648731994@qq.com
 * created on: 2022/3/11 10:08
 * description:
 */
public class CompanyCellData implements ITableCellFormatData {
    public String name;
    public boolean isBold;

    public CompanyCellData() {
    }

    public CompanyCellData(String name) {
        this.name = name;
    }

    public CompanyCellData(String name, boolean isBold) {
        this.name = name;
        this.isBold = isBold;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isBold() {
        return isBold;
    }
}
