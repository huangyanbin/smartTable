package com.bin.david.smarttable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.ColumnInfo;
import com.bin.david.form.data.PageTableData;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.count.ICountFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.tip.MultiLineBubbleTip;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.bean.UserInfo;
import com.bin.david.smarttable.view.BaseCheckDialog;
import com.bin.david.smarttable.view.BaseDialog;
import com.bin.david.smarttable.view.QuickChartDialog;
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PagerModeActivity extends AppCompatActivity implements View.OnClickListener{

    private SmartTable<UserInfo> table;
    private BaseCheckDialog<TableStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    private PageTableData<UserInfo> tableData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        quickChartDialog = new QuickChartDialog();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15)); //设置全局字体大小
        table = (SmartTable<UserInfo>) findViewById(R.id.table);
        final List<UserInfo> testData = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i <500; i++) {
            testData.add(new UserInfo("用户"+i, random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("测试"+i)));
        }

        final Column<String> nameColumn = new Column<>("姓名", "name");
        nameColumn.setAutoCount(true);
        final Column<Integer> ageColumn = new Column<>("年龄", "age");
        ageColumn.setAutoCount(true);
        Column<String> column4 = new Column<>("测试多重查询", "childData.child");
        column4.setAutoCount(true);
        final IFormat<Long> format =  new IFormat<Long>() {
            @Override
            public String format(Long aLong) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(aLong);
                return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            }
        };
        final Column<Long> timeColumn = new Column<>("时间", "time",format);
        timeColumn.setCountFormat(new ICountFormat<Long, Long>() {
            private long maxTime;
            @Override
            public void count(Long aLong) {
                if(aLong > maxTime){
                    maxTime = aLong;
                }
            }

            @Override
            public Long getCount() {
                return maxTime;
            }

            @Override
            public String getCountString() {
                return format.format(maxTime);
            }

            @Override
            public void clearCount() {
                maxTime =0;
            }
        });
        int size = DensityUtils.dp2px(this,15);
        Column<Boolean> column5 = new Column<>("勾选1", "isCheck", new ImageResDrawFormat<Boolean>(size,size) {
            @Override
            protected Context getContext() {
                return PagerModeActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.check;
                }
                return 0;
            }
        });
        Column<Boolean> column6 = new Column<>("勾选2", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.LEFT,10) {
            @Override
            protected Context getContext() {
                return PagerModeActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.clock_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column7 = new Column<>("勾选3", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return PagerModeActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column8 = new Column<>("勾选4", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.TOP,10) {
            @Override
            protected Context getContext() {
                return PagerModeActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.brush_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column9 = new Column<>("勾选5", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.BOTTOM,10) {
            @Override
            protected Context getContext() {
                return PagerModeActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.collection_fill;
                }
                return 0;
            }
        });
        Column totalColumn1 = new Column("总项1",nameColumn,ageColumn);
        Column totalColumn2 = new Column("总项2",nameColumn,ageColumn,timeColumn);
        Column totalColumn = new Column("总项",nameColumn,totalColumn1,totalColumn2,timeColumn);

       tableData = new PageTableData<>("测试",testData,nameColumn,column4,column5,column6,column7,column8,column9,totalColumn,totalColumn1,totalColumn2,timeColumn);


        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size,size, TitleImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return PagerModeActivity.this;
            }

            @Override
            protected int getResourceID(Column column) {
                if(!column.isParent()){
                    if(tableData.getSortColumn() == column){
                        setDirection(TextImageDrawFormat.RIGHT);
                        if(column.isReverseSort()){
                            return R.mipmap.sort_up;
                        }
                        return R.mipmap.sort_down;

                    }else{
                        setDirection(TextImageDrawFormat.LEFT);
                        if(column == nameColumn){
                            return R.mipmap.name;
                        }else if(column == ageColumn){
                            return R.mipmap.age;
                        }else if(column == timeColumn){
                            return R.mipmap.update;
                        }
                    }
                    return 0;
                }
                setDirection(TextImageDrawFormat.LEFT);
                int level = tableData.getTableInfo().getMaxLevel()-column.getLevel();
                if(level ==0){
                    return R.mipmap.level1;
                }else if(level ==1){
                    return R.mipmap.level2;
                }
                return 0;
            }
        });
        ageColumn.setOnColumnItemClickListener(new OnColumnItemClickListener<Integer>() {
            @Override
            public void onClick(Column<Integer> column, String value, Integer integer, int position) {
                Toast.makeText(PagerModeActivity.this,"点击了"+value,Toast.LENGTH_SHORT).show();
            }
        });
        tableData.setShowCount(true);
        table.getConfig().setCountBackgroundColor(getResources().getColor(R.color.windows_bg))
                .setShowXSequence(false).setShowYSequence(false);
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));
        MultiLineBubbleTip<Column> tip = new MultiLineBubbleTip<Column>(this,R.mipmap.round_rect,R.mipmap.triangle,fontStyle) {
            @Override
            public boolean isShowTip(Column column, int position) {
                if(column == nameColumn){
                    return true;
                }
                return false;
            }


            @Override
            public String[] format(Column column, int position) {
                UserInfo data = testData.get(position);
                String[] strings = {"批注","姓名："+data.getName(),"年龄："+data.getAge()};
                return strings;
            }
        };
        tip.setColorFilter(Color.parseColor("#FA8072"));
        tip.setAlpha(0.8f);
        table.getProvider().setTip(tip);
        table.setSortColumn(ageColumn,false);
        ageColumn.setComparator(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1- o2;
            }
        });
        table.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                if(!columnInfo.column.isParent()) {

                    if(columnInfo.column == ageColumn){
                        showChartDialog(tableData.getTableName(),nameColumn.getDatas(),ageColumn.getDatas());
                    }else{
                        table.setSortColumn(columnInfo.column, !columnInfo.column.isReverseSort());
                    }
                }
                Toast.makeText(PagerModeActivity.this,"点击了"+columnInfo.column.getColumnName(),Toast.LENGTH_SHORT).show();
            }
        });
        table.getConfig().setTableTitleStyle(new FontStyle(this,15,getResources().getColor(R.color.arc1)));
        table.getConfig().setContentBackgroundFormat(new BaseBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor() {
                return ContextCompat.getColor(PagerModeActivity.this,R.color.content_bg);
            }
            @Override
            public boolean isDraw(CellInfo cellInfo) {
               return cellInfo.position%2 ==0;
            }
        });

        tableData.setPageSize(9);
        table.setTableData(tableData);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left:
                tableData.setCurrentPage(tableData.getCurrentPage()-1);
                table.notifyDataChanged();
                break;
            case R.id.right:
                tableData.setCurrentPage(tableData.getCurrentPage()+1);
                table.notifyDataChanged();
                break;
        }


    }



    /**
     * 测试是否可以兼容之前smartChart
     * @param tableName
     * @param chartYDataList
     * @param list
     */
    private void showChartDialog(String tableName,List<String> chartYDataList,List<Integer> list ){
        View chartView = View.inflate(this,R.layout.dialog_chart,null);
        LineChart lineChart = (LineChart) chartView.findViewById(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        com.daivd.chart.data.style.FontStyle.setDefaultTextSpSize(this,12);
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        ArrayList<String> ydataList = new ArrayList<>();
        for(int i = 0;i <30;i++){
            String value = chartYDataList.get(i);
            ydataList.add(value);
        }
        for(int i = 0;i <30;i++){
            int value = list.get(i);
            tempList1.add(Double.valueOf(value));
        }
        LineData columnData1 = new LineData(tableName,"", IAxis.AxisDirection.LEFT,getResources().getColor(R.color.arc1),tempList1);
        ColumnDatas.add(columnData1);
        ChartData<LineData> chartData2 = new ChartData<>("Area Chart",ydataList,ColumnDatas);
        lineChart.getChartTitle().setDirection(IComponent.TOP);
        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis =  lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChart.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(this,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(true);
        lineChart.getProvider().setCross(new VerticalCross());
        lineChart.getProvider().setShowText(true);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new BubbleMarkView(this));

        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题样式
        com.daivd.chart.data.style.FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this,15);
        LevelLine levelLine = new LevelLine(30);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(this,1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        Point legendPoint = (Point) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.SQUARE);
        lineChart.getProvider().setArea(true);
        lineChart.getHorizontalAxis().setRotateAngle(90);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(400);
        BaseDialog dialog = new  BaseDialog.Builder(this).setFillWidth(true).setContentView(chartView).create();
        dialog.show();
    }

}
