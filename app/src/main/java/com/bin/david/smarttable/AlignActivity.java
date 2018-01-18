package com.bin.david.smarttable;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.selected.BaseSelectFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.bean.TanBean;
import com.bin.david.smarttable.bean.UserInfo;
import com.bin.david.smarttable.view.BaseCheckDialog;
import com.bin.david.smarttable.view.QuickChartDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 字体Align
 */
public class AlignActivity extends AppCompatActivity implements View.OnClickListener{

    private SmartTable<UserInfo> table;
    private BaseCheckDialog<TableStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        quickChartDialog = new QuickChartDialog();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15)); //设置全局字体大小
        table = (SmartTable<UserInfo>) findViewById(R.id.table);
        final List<UserInfo> testData = new ArrayList<>();
        Random random = new Random();
        List<TanBean> tanBeans = TanBean.initDatas();
        //测试 从其他地方获取url
        int urlSize = tanBeans.size();
        for(int i = 0;i <100; i++) {
            UserInfo userData = new UserInfo("用户"+i, random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("测试"+i));
            userData.setUrl(tanBeans.get(i%urlSize).getUrl());
            testData.add(userData);
        }
        List<Column> columns = new ArrayList<>();
        for(int i = 0; i<150;i++){
            columns.add( new Column<>("姓名"+i, "name"));
        }



        final TableData<UserInfo> tableData = new TableData<>("测试",testData,columns);
        tableData.setShowCount(true);

        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));

        table.getConfig().setTableTitleStyle(new FontStyle(this,15,getResources().getColor(R.color.arc1)));
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if(cellInfo.position%2 == 0) {
                    return ContextCompat.getColor(AlignActivity.this, R.color.content_bg);
                }else{
                    return TableConfig.INVALID_COLOR; //返回无效颜色，不会绘制
                }
            }
        };
        table.setSelectFormat(new BaseSelectFormat());
        table.getConfig().setContentBackgroundFormat(backgroundFormat);
        table.setTableData(tableData);

    }

    @Override
    public void onClick(View view) {
        changedStyle();
    }

    private void changedStyle() {

        if (chartDialog == null) {
            chartDialog = new BaseCheckDialog<>("表格设置", new BaseCheckDialog.OnCheckChangeListener<TableStyle>() {
                @Override
                public String getItemText(TableStyle chartStyle) {
                    return chartStyle.value;
                }

                @Override
                public void onItemClick(TableStyle item, int position) {
                    switch (item) {
                        case FIXED_TITLE:
                            fixedTitle(item);
                            break;
                        case FIXED_X_AXIS:
                            fixedXAxis(item);
                            break;
                        case FIXED_Y_AXIS:
                           fixedYAxis(item);
                            break;
                        case FIXED_FIRST_COLUMN:
                            fixedFirstColumn(item);
                            break;
                        case FIXED_COUNT_ROW:
                            fixedCountRow(item);
                            break;
                        case ZOOM:
                            zoom(item);
                            break;
                        case ALIGN:
                            align(item);
                            break;


                    }
                }
            });
        }
        ArrayList<TableStyle> items = new ArrayList<>();

        items.add(TableStyle.FIXED_X_AXIS);
        items.add(TableStyle.FIXED_Y_AXIS);
        items.add(TableStyle.FIXED_TITLE);
        items.add(TableStyle.FIXED_FIRST_COLUMN);
        items.add(TableStyle.FIXED_COUNT_ROW);
        items.add(TableStyle.ZOOM);
        items.add(TableStyle.ALIGN);
        chartDialog.show(this, true, items);
    }

    private void align(TableStyle item){
        quickChartDialog.showDialog(this, item, new String[]{"左", "中","右"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    FontStyle.setDefaultTextAlign(Paint.Align.LEFT);
                } else if (position == 1) {
                    FontStyle.setDefaultTextAlign(Paint.Align.CENTER);
                }else{
                    FontStyle.setDefaultTextAlign(Paint.Align.RIGHT);
                }
                table.invalidate();
            }
        });
    }

    private void zoom(TableStyle item) {
        quickChartDialog.showDialog(this, item, new String[]{"缩放", "不缩放"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.setZoom(true,4,0.2f);
                } else if (position == 1) {
                    table.setZoom(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedXAxis(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                   table.getConfig().setFixedXSequence(true);
                } else if (position == 1) {
                    table.getConfig().setFixedXSequence(false);
                }
               table.invalidate();
            }
        });
    }

    private void fixedYAxis(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedYSequence(true);
                } else if (position == 1) {
                    table.getConfig().setFixedYSequence(false);
                }
                table.invalidate();
            }
        });
    }
    private void fixedTitle(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedTitle(true);
                } else if (position == 1) {
                    table.getConfig().setFixedTitle(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedFirstColumn(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedFirstColumn(true);
                } else if (position == 1) {
                    table.getConfig().setFixedFirstColumn(false);
                }
                table.invalidate();
            }
        });
    }
    private void fixedCountRow(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedCountRow(true);
                } else if (position == 1) {
                    table.getConfig().setFixedCountRow(false);
                }
                table.invalidate();
            }
        });
    }



}
