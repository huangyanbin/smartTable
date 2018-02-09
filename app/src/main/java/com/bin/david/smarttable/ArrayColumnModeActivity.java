package com.bin.david.smarttable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.ArrayColumn;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.CollegeStudent;
import com.bin.david.smarttable.bean.Lesson;
import com.bin.david.smarttable.bean.LessonPoint;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.bean.DayTime;
import com.bin.david.smarttable.bean.Week;
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
import java.util.List;

public class ArrayColumnModeActivity extends AppCompatActivity implements View.OnClickListener{

    private SmartTable<CollegeStudent> table;
    private BaseCheckDialog<TableStyle> chartDialog;
    private QuickChartDialog quickChartDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        quickChartDialog = new QuickChartDialog();
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15)); //设置全局字体大小
        table = (SmartTable<CollegeStudent>) findViewById(R.id.table);
        final List<CollegeStudent> students  = new ArrayList<>();
        List<Lesson> lessons2 = new ArrayList<>();
        Lesson lesson1 = new Lesson("软件",true);
        lesson1.setLessonPoints(new LessonPoint[]{new LessonPoint("软件工程"),new LessonPoint("离散数学")});
        Lesson lesson2 = new Lesson("生物",true);
        lesson2.setLessonPoints(new LessonPoint[]{new LessonPoint("医学构造"),new LessonPoint("生物科技")});
        //lesson2.setTest(texts);
        lessons2.add(lesson1);
        lessons2.add(lesson2);
        lessons2.add(new Lesson("微积分",false));
        for(int i = 0; i < 1;i++){
            List<Week> weeks = new ArrayList<>();
            for (int j = 0; j< 7;j++){
                List<DayTime> times = new ArrayList<>();
                for(int k =0;k < 3; k++){
                    DayTime time = new DayTime(k==0?"上午": k==1?"下午":"晚上",lessons2);
                    times.add(time);
                }
                Week week = new Week("星期"+(j+1),times);
                weeks.add(week);
            }
            CollegeStudent student = new CollegeStudent("学生"+i,(int)(20+Math.random()*10),weeks);
            students.add(student);
        }
        Column<String> studentNameColumn = new Column<>("姓名","name");
        //Column<Integer> studentAgeColumn = new Column<>("年龄","age");
        ArrayColumn<String> weekNameColumn = new ArrayColumn<>("星期","weeks.name");
        ArrayColumn<String> timeNameColumn = new ArrayColumn<>("时间","weeks.times.time");
        ArrayColumn<String> lessonNameColumn = new ArrayColumn<>("课程","weeks.times.lessons.name");

        ArrayColumn<String> pointNameColumn = new ArrayColumn<>("知识点","weeks.times.lessons.lessonPoints.name");
       ArrayColumn<Boolean> lessonFavColumn = new ArrayColumn<>("是否喜欢","weeks.times.lessons.isFav");
       int imgSize = DensityUtils.dp2px(this,20);
       timeNameColumn.setDrawFormat(new TextImageDrawFormat<String>(imgSize,imgSize,TextImageDrawFormat.RIGHT,10) {
           @Override
           protected Context getContext() {
               return ArrayColumnModeActivity.this;
           }

           @Override
           protected int getResourceID(String s, String value, int position) {
               if(value.equals("上午")){
                   return R.mipmap.morning;
               }else if(value.equals("下午")){
                   return R.mipmap.noon;
               }
               return R.mipmap.night ;
           }
       });
       lessonFavColumn.setDrawFormat(new ImageResDrawFormat<Boolean>(imgSize,imgSize) {
           @Override
           protected Context getContext() {
               return ArrayColumnModeActivity.this;
           }

           @Override
           protected int getResourceID(Boolean val, String value, int position) {
               if(val){
                   return R.mipmap.check;
               }
               return TableConfig.INVALID_COLOR;
           }
       });
        TableData<CollegeStudent> tableData = new TableData<>("课程表",students,studentNameColumn,
                weekNameColumn,timeNameColumn,lessonNameColumn,pointNameColumn,lessonFavColumn);
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
        chartDialog.show(this, true, items);
    }

    private void zoom(TableStyle item) {
        quickChartDialog.showDialog(this, item, new String[]{"缩放", "不缩放"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.setZoom(true,3,1);
                } else if (position == 1) {
                    table.setZoom(false,3,1);
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
