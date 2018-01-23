package com.bin.david.smarttable.excel;

import android.content.Context;
import android.graphics.Canvas;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/1/23.
 */

public class POIExcel2Table extends BaseExcel2Table<Cell> {

    private int textColor;


    @Override
    public void initTableConfig(Context context, SmartTable<Cell> table) {
        super.initTableConfig(context, table);
        textColor = ContextCompat.getColor(context, R.color.arc_temp);
        table.getProvider().setDrawOver(new IDrawOver() {
            @Override
            public void draw(Canvas canvas, Rect showRect, TableConfig config) {

            }
        });
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
        int maxRow, maxColumn;
        Workbook workbook = getWorkBook(context,fileName);
        Sheet sheet = workbook.getSheetAt(position);
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

        maxRow = sheet.getLastRowNum()+1;

        Cell[][] data = new Cell[maxRow][];
        for (int i = 0; i < maxRow; i++) {
            Row row = sheet.getRow(i);
            maxColumn = row.getPhysicalNumberOfCells();
            Cell[] rows = new Cell[maxColumn];
            for(int j = 0;j < maxColumn;j++){
                Cell cell = row.getCell(j);
                if(cell !=null){
                    rows[j] = cell;
                }else{
                    rows[j] = null;
                }
            }
            data[i] = rows;
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

    @Override
    protected int getTextColor(Context context,Cell cell) {
        return textColor;
    }

    @Override
    protected int getBackgroundColor(Context context, Cell cell) {
        if( cell.getCellStyle() !=null) {
            return cell.getCellStyle().getFillForegroundColor();
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

    public static Workbook getWorkBook(Context context,String fileName)throws Exception {

        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is =context.getAssets().open(fileName);
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(is);
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
