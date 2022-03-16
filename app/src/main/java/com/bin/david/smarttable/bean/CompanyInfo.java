package com.bin.david.smarttable.bean;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.bin.david.form.core.ITableCellFormatData;

/**
 * author: DongYonghui
 * email: 648731994@qq.com
 * created on: 2022/3/11 10:08
 * description:
 */
@SmartTable(name = "到期债务")
public class CompanyInfo implements ITableCellFormatData {
    @SmartColumn(id = 1, name = "单位", fixed = true)
    public CompanyCellData companyName;
    @SmartColumn(id = 2, name = "类别", type = ColumnType.ArrayOwn, fixed = true)
    public CompanyCellData[] companySectionInfo;

    @SmartColumn(id = 3, name = "1月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth1;
    @SmartColumn(id = 4, name = "2月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth2;
    @SmartColumn(id = 5, name = "3月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth3;
    @SmartColumn(id = 5, name = "一季度小计", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth3Total;
    @SmartColumn(id = 6, name = "4月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth4;
    @SmartColumn(id = 7, name = "5月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth5;
    @SmartColumn(id = 8, name = "6月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth6;
    @SmartColumn(id = 8, name = "二季度小计", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth6Total;
    @SmartColumn(id = 9, name = "7月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth7;
    @SmartColumn(id = 10, name = "8月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth8;
    @SmartColumn(id = 11, name = "9月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth9;
    @SmartColumn(id = 11, name = "三季度小计", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth9Total;
    @SmartColumn(id = 12, name = "10月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth10;
    @SmartColumn(id = 13, name = "11月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth11;
    @SmartColumn(id = 14, name = "12月", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth12;
    @SmartColumn(id = 15, name = "四季度小计", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonth12Total;
    @SmartColumn(id = 15, name = "总计", type = ColumnType.ArrayOwn)
    public CompanyCellData[] dataOfMonthAllTotal;

    /**
     * 是否是合计
     */
    public boolean isTotalData;

    @Override
    public boolean isBold() {
        return isTotalData;
    }
}
