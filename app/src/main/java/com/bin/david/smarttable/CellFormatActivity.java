package com.bin.david.smarttable;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.PageTableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.CompanyCellData;
import com.bin.david.smarttable.bean.CompanyInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * author: DongYonghui
 * email: 648731994@qq.com
 * created on: 2022/3/11 10:08
 * description:演示自定义单元格样式
 */
public class CellFormatActivity extends AppCompatActivity {
    private CheckBox mFixFirstCheckBox;
    private SmartTable<CompanyInfo> table;
    private PageTableData<CompanyInfo> companyInfoPageTableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell_format);

        //初始化View
        mFixFirstCheckBox = (CheckBox) findViewById(R.id.mFixFirstCheckBox);
        table = (SmartTable<CompanyInfo>) findViewById(R.id.table);

        //初始化表格
        initTable();

        //固定列设置
        mFixFirstCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (null != companyInfoPageTableData && null != companyInfoPageTableData.getColumnByID(1))
                    companyInfoPageTableData.getColumnByID(1).setFixed(isChecked);
                if (null != companyInfoPageTableData && null != companyInfoPageTableData.getColumnByID(2))
                    companyInfoPageTableData.getColumnByID(2).setFixed(isChecked);
                if (null != table)
                    table.invalidate();
            }
        });
    }

    private void initTable() {

        //添加表格数据
        final List<CompanyInfo> companyInfos = new ArrayList<>();
        addTestData(companyInfos);
        companyInfoPageTableData = table.setData(companyInfos);

        //标题样式设置
        FontStyle titleStyle = new FontStyle();
        titleStyle.setTextColor(Color.BLACK);
        titleStyle.setTextSize(DensityUtils.sp2px(this, 16));
        //设置需要加粗的标题集合
        HashSet<String> boldTitlesSet = new HashSet<>();
        boldTitlesSet.add("单位");
        boldTitlesSet.add("一季度小计");
        boldTitlesSet.add("二季度小计");
        boldTitlesSet.add("三季度小计");
        boldTitlesSet.add("四季度小计");
        boldTitlesSet.add("总计");

        int titleBgColor = Color.parseColor("#4DE0E0E0");
        int titleLineColor = Color.parseColor("#1AB3B3B3");

        //标题线条样式
        LineStyle titleLineStyle = new LineStyle();
        titleLineStyle.setColor(titleLineColor);


        table.setZoom(true, 5, 0.2f);
        table.getConfig()
                //设置padding
                .setVerticalPadding(DensityUtils.dp2px(this, 5))
                .setColumnTitleVerticalPadding(DensityUtils.dp2px(this, 10))
                //设置标题字体样式
                .setColumnTitleStyle(titleStyle)
                //设置标题需要加粗的集合
                .setBoldTitlesSet(boldTitlesSet)
                //设置标题背景
                .setColumnTitleBackground(new BaseBackgroundFormat(titleBgColor))
                //设置标题线条颜色
                .setColumnTitleGridStyle(titleLineStyle)
//                .setColumnTitleVerticalPadding(DensityUtils.dp2px(this,5))
                //因此表的总标题和X Y坐标轴
                .setShowTableTitle(false)
                .setShowXSequence(false)
                .setShowYSequence(false);
    }

    private void addTestData(List<CompanyInfo> companyInfos) {
        for (int i = 0; i < 15; i++) {
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.companyName = new CompanyCellData("建安集团", true);
            companyInfo.companySectionInfo = new CompanyCellData[]{new CompanyCellData("财政"), new CompanyCellData("企业"), new CompanyCellData("转贷")};
            companyInfo.dataOfMonth1 = new CompanyCellData[]{new CompanyCellData("--"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth2 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("--"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth3 = new CompanyCellData[]{new CompanyCellData("--"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth3Total = new CompanyCellData[]{new CompanyCellData("588832"), new CompanyCellData("66688"), new CompanyCellData("989832")};
            companyInfo.dataOfMonth4 = new CompanyCellData[]{new CompanyCellData("--"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth5 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("--")};
            companyInfo.dataOfMonth6 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth6Total = new CompanyCellData[]{new CompanyCellData("588832"), new CompanyCellData("66688"), new CompanyCellData("989832")};
            companyInfo.dataOfMonth7 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth8 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth9 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth9Total = new CompanyCellData[]{new CompanyCellData("588832"), new CompanyCellData("66688"), new CompanyCellData("989832")};
            companyInfo.dataOfMonth10 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth11 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth12 = new CompanyCellData[]{new CompanyCellData("23630"), new CompanyCellData("23630"), new CompanyCellData("23630")};
            companyInfo.dataOfMonth12Total = new CompanyCellData[]{new CompanyCellData("588832"), new CompanyCellData("66688"), new CompanyCellData("989832")};
            companyInfo.dataOfMonthAllTotal = new CompanyCellData[]{new CompanyCellData("588832"), new CompanyCellData("66688"), new CompanyCellData("989832")};

            companyInfos.add(companyInfo);

            companyInfo = new CompanyInfo();
            companyInfo.companyName = new CompanyCellData("小计(建安)", true);
            companyInfo.dataOfMonth1 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth2 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth3 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth4 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth5 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth6 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth7 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth8 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth9 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth10 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth11 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth12 = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonthAllTotal = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth3Total = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth6Total = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth9Total = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.dataOfMonth12Total = new CompanyCellData[]{new CompanyCellData("88888", true)};
            companyInfo.isTotalData = true;
            companyInfos.add(companyInfo);
        }
    }

}
