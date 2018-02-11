package com.bin.david.smarttable.excel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.format.selected.IDrawOver;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.smarttable.R;
import com.bin.david.smarttable.utils.ExcelHelper;
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.axis.VerticalAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.core.base.BaseChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.point.Point;
import com.daivd.chart.provider.component.tip.MultiLineBubbleTip;
import com.daivd.chart.utils.DensityUtils;

import org.apache.poi.hssf.usermodel.HSSFChart;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressBase;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/1/23.
 */

public class POIExcel2Table extends BaseExcel2Table<Cell> {

    private int textColor;
    private ChartData<LineData> chartData;


    @Override
    public void initTableConfig(final Context context, SmartTable<Cell> table) {
        super.initTableConfig(context, table);
        textColor = ContextCompat.getColor(context, R.color.arc_temp);

    }

    @Override
    public List<String> getSheetName(Context context, String fileName) throws Exception {
        List<String> list = new ArrayList<>();
        Workbook workbook = getWorkBook(context,fileName);
        int sheetNum = workbook.getNumberOfSheets();
        for(int i = 0;i < sheetNum;i++){
            Sheet sheet = workbook.getSheetAt(i);
            list.add(sheet.getSheetName());
        }
        workbook.close();
        return list;
    }

    @Override
    protected Cell[][] readExcelCell(Context context, String fileName, int position) throws Exception {
        chartData = null;
        int maxRow, maxColumn;
        HSSFWorkbook workbook = getWorkBook(context,fileName);
        HSSFSheet sheet = workbook.getSheetAt(position);
        List<CellRangeAddress> ranges = sheet.getMergedRegions();
        if(ranges !=null) {
            int size = ranges.size();
            for (int i = 0;i < size;i++) {
                CellRangeAddress range =ranges.get(i);
                CellRange cellRange = new CellRange(range.getFirstRow(),
                        range.getLastRow(),
                        range.getFirstColumn(),range.getLastColumn());
                getRanges().add(cellRange);
            }
        }
        HSSFChart[] charts = HSSFChart.getSheetCharts(sheet);
        maxRow = sheet.getLastRowNum()+1;
        int addRow = charts.length*40;
        Cell[][] data = new Cell[maxRow+addRow][];
        for (int i = 0; i < maxRow+addRow; i++) {
            if(i < maxRow) {
                Row row = sheet.getRow(i);
                maxColumn = row.getPhysicalNumberOfCells();
                Cell[] rows = new Cell[maxColumn];
                for (int j = 0; j < maxColumn; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        rows[j] = cell;
                    } else {
                        rows[j] = null;
                    }
                }
                data[i] = rows;
            }else{
                Cell[] rows = new Cell[20];
                data[i] = rows;
            }
        }

        for (HSSFChart chart : charts) {
            //poi获取excel图表标题
            String title = chart.getChartTitle();
            HSSFChart.HSSFChartType type = chart.getType();
            //poi获取excel图表系列
            HSSFChart.HSSFSeries[] series = chart.getSeries();
            List<LineData> ColumnDatas = new ArrayList<>();
            List<String> chartYDataList = new ArrayList<>();
            for (HSSFChart.HSSFSeries se : series) {
                //poi获取excel图表的值区域
                ArrayList<Double> values = new ArrayList<>();
                CellRangeAddressBase valueRange = se.getValuesCellRange();
                for(int j = valueRange.getFirstColumn(); j <= valueRange.getLastColumn();j++){
                    for(int i = valueRange.getFirstRow(); i <= valueRange.getLastRow();i++){
                        Cell cell = data[i][j];
                        if(cell !=null){
                            double d = cell.getNumericCellValue();
                            if(d !=0) {
                                values.add(d);
                                chartYDataList.add(i+ "");
                            }
                        }
                     }
                }
                final LineData columnData1 = new LineData("系列1", "", IAxis.AxisDirection.LEFT,
                        context.getResources().getColor(R.color.arc23), values);
                ColumnDatas.add(columnData1);
                chartData = new ChartData<>(title== null|| title.length() ==0 ?"图表标题":title, chartYDataList, ColumnDatas);
            }

        }
        workbook.close();
        //将行二维数组转换成列的二维数组
        return  ArrayTableData.transformColumnArray(data);
    }

    @Override
    protected int getFontSize(Context context,Cell cell) {
        int fontSize = cell.getCellStyle().getFontIndex();
        return fontSize >0 ?fontSize:12;
    }

    /**
     * 实验固定图表
     * @param context
     */
    @Override
    public void loadDataSuc(Context context) {
        final LineChart lineChart = new LineChart(context);
        Resources res = context.getResources();
        FontStyle.setDefaultTextSpSize(context, 12);
        lineChart.getProvider().setShowText(false);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis = lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis = lineChart.getHorizontalAxis();
        VerticalAxis rightAxis = lineChart.getRightVerticalAxis();
        rightAxis.setStartZero(true);
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(false);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(context, 1);
        DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(context, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(context, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        VerticalCross cross = new VerticalCross();
        LineStyle crossStyle = cross.getCrossStyle();
        crossStyle.setWidth(context, 1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        //设置显示点
        Point point = new Point();
        point.getPointStyle().setShape(PointStyle.CIRCLE);
        //设置显示点的样式
        lineChart.getProvider().setPoint(point);
        lineChart.getProvider().setDrawLine(false);
        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题方向
        lineChart.getChartTitle().setDirection(IComponent.TOP);
        //设置标题比例
        lineChart.getChartTitle().setPercent(0.2f);
        //设置标题样式
        FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(context, 15);

        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        lineChart.getLegend().setPercent(0.2f);
        lineChart.setFirstAnim(false);
        if(chartData !=null) {
            lineChart.setChartData(chartData);
        }
        smartTable.getProvider().setDrawOver(new IDrawOver() {
            @Override
            public void draw(Canvas canvas, Rect scaleRect,Rect showRect, TableConfig config) {
                if(chartData !=null) {

                    //暂时写死图表位置 如果能读取出图表位置就可以通过位置计算出来
                    int w = (int)(smartTable.getWidth()*smartTable.getConfig().getZoom()*0.9);
                    int h = (int)(smartTable.getHeight()*smartTable.getConfig().getZoom()*0.75);
                    setLineChart(lineChart,w,h);

                    int padding = (int) (smartTable.getConfig().getZoom()*50);
                    canvas.translate(scaleRect.right-w -padding, scaleRect.top+padding);
                    Paint paint = config.getPaint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(0,0,w,h,paint);
                    lineChart.draw(canvas);


                }
            }
        });

    }

    private void setLineChart(LineChart lineChart,int width,int height) {
        Class clazz = BaseChart.class;
        try {
            Field field1 =  clazz.getDeclaredField("width");
            Field field2= clazz.getDeclaredField("height");

            field1.setAccessible(true);
            field2.setAccessible(true);
            field1.set(lineChart,width);
            field2.set(lineChart,height);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getTextColor(Context context,Cell cell) {
        return textColor;
    }

    @Override
    protected int getBackgroundColor(Context context, Cell cell) {
        if( cell.getCellStyle() !=null) {
            return cell.getCellStyle().getFillBackgroundColor();
        }
        return TableConfig.INVALID_COLOR;
    }

    @Override
    protected String getFormat(Cell cell) {
        return ExcelHelper.getCellValue(cell);
    }

    @Override
    protected Paint.Align getAlign(Cell cell) {
        HorizontalAlignment alignment = cell.getCellStyle().getAlignmentEnum();
        return alignment == HorizontalAlignment.LEFT ? Paint.Align.LEFT :
                alignment == HorizontalAlignment.RIGHT ? Paint.Align.RIGHT
                        : Paint.Align.CENTER;
    }

    @Override
    protected boolean hasComment(Cell cell) {
        return false;
    }

    public  HSSFWorkbook getWorkBook(Context context,String fileName)throws Exception {

        //创建Workbook工作薄对象，表示整个excel
        HSSFWorkbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = getInputStream(context,fileName);
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }
        } catch (IOException e) {
        }
        return workbook;
    }

    @Override
    protected String getComment(Cell cell) {
        return null;
    }

    @Override
    public Cell[][] getEmptyTableData() {
        return new Cell[26][50];
    }
}
