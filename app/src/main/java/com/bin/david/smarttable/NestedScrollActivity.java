package com.bin.david.smarttable;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.TanBean;
import com.bin.david.smarttable.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NestedScrollActivity extends AppCompatActivity {
    private SmartTable<UserInfo> table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15)); //设置全局字体大小
        table = (SmartTable<UserInfo>) findViewById(R.id.table);
        final List<UserInfo> testData = new ArrayList<>();
        Random random = new Random();
        List<TanBean> tanBeans = TanBean.initDatas();
        //测试 从其他地方获取url
        int urlSize = tanBeans.size();
        for(int i = 0;i <500; i++) {
            UserInfo userData = new UserInfo("用户"+i, random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("测试"+i));
            userData.setUrl(tanBeans.get(i%urlSize).getUrl());
            testData.add(userData);
        }
        List<Column> columns = new ArrayList<>();
        for(int i = 0; i<150;i++){
            Column column = new Column<>("姓名"+i, "name");
            column.setFast(true);
            columns.add(column);
        }



        final TableData<UserInfo> tableData = new TableData<>("测试",testData,columns);
        tableData.setShowCount(true);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));
        table.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));

        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));

        table.getConfig().setTableTitleStyle(new FontStyle(this,15,getResources().getColor(R.color.arc1)));

        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor( CellInfo cellInfo) {
                if(cellInfo.row %2 == 0) {
                    return ContextCompat.getColor(NestedScrollActivity.this, R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }


        };
        ICellBackgroundFormat<Integer> backgroundFormat2 = new BaseCellBackgroundFormat<Integer>() {
            @Override
            public int getBackGroundColor(Integer position) {
                if(position%2 == 0){
                    return ContextCompat.getColor(NestedScrollActivity.this,R.color.arc1);
                }
                return TableConfig.INVALID_COLOR;

            }

            @Override
            public int getTextColor(Integer position) {
                if(position%2 == 0) {
                    return ContextCompat.getColor(NestedScrollActivity.this, R.color.white);
                }
                return TableConfig.INVALID_COLOR;
            }
        };
        table.getConfig().setContentCellBackgroundFormat(backgroundFormat)
                .setYSequenceCellBgFormat(backgroundFormat2);
        table.getConfig().setFixedYSequence(true);
        table.setTableData(tableData);
        table.getMatrixHelper().flingRight(200);
    }
}
