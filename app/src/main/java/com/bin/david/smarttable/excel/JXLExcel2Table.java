package com.bin.david.smarttable.excel;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.LruCache;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.format.selected.IDrawOver;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.smarttable.bean.ImagePoint;
import com.bin.david.smarttable.utils.DrawHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Cell;
import jxl.Image;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.drawing.Chart;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.RGB;

/**
 * Created by huang on 2018/1/23.
 */

public class JXLExcel2Table extends BaseExcel2Table<Cell> {

    private Set<ImagePoint> imgPointSet;
    //使用缓存
    private LruCache<ImagePoint,Bitmap> cache;

    @Override
    public void initTableConfig(Context context, final SmartTable<Cell> table) {
        super.initTableConfig(context, table);
        table.getProvider().setDrawOver(new IDrawOver() {
            @Override
            public void draw(Canvas canvas,Rect scaleRect, Rect showRect, TableConfig config) {
                if(imgPointSet.size() >0){
                    for(ImagePoint point :imgPointSet){
                       /* if(table.getProvider().getGridDrawer().maybeContain(point.row,point.col)) {*/
                            Bitmap bitmap = cache.get(point);
                            int[] location = table.getProvider().getPointLocation(point.row,point.col);
                            int[] size = table.getProvider().getPointSize((int)Math.ceil(point.row),(int)Math.ceil(point.col));
                            int width = (int) (size[0]*point.width);
                            int height = (int)(size[1]*point.height);
                            Rect imgBitmap = new Rect(location[0],location[1],location[0]+width,location[1]+height);
                            DrawHelper.drawBitmap(canvas,imgBitmap,bitmap,config);
                        /*}*/
                    }
                }
            }
        });
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 16;
        imgPointSet = new HashSet<>();
        cache = new LruCache<ImagePoint,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(ImagePoint key,Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;// KB
            }
        };
    }



    @Override
    public List<String> getSheetName(Context context,String fileName)throws Exception {

            List<String> list = new ArrayList<>();
            InputStream is = context.getAssets().open(fileName);
            Workbook workbook = Workbook.getWorkbook(is);
            int sheetNum = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Sheet sheet = workbook.getSheet(i);
                list.add(sheet.getName());
            }
            workbook.close();
            return list;
    }

    @Override
    protected Cell[][] readExcelCell(Context context,String fileName, int position)throws Exception {
        //这里可以优化
        cache.evictAll();
        imgPointSet.clear();
        int maxRow, maxColumn;
        InputStream is = context.getAssets().open(fileName);
        Workbook workbook = Workbook.getWorkbook(is);
        Sheet sheet = workbook.getSheet(position);
        Range[] ranges = sheet.getMergedCells();
        if(ranges !=null) {
            for (int i = 0;i < ranges.length;i++) {
                Range range =ranges[i];
                CellRange cellRange = new CellRange(range.getTopLeft().getRow(),
                        range.getBottomRight().getRow(),
                        range.getTopLeft().getColumn(),range.getBottomRight().getColumn());
                getRanges().add(cellRange);
            }
        }
        maxRow = sheet.getRows();
        maxColumn =  sheet.getColumns();
        int imageCount = sheet.getNumberOfImages();//获得第一个sheet中的图片数目
        for(int i = 0;i < imageCount;i++) {
            Image img = sheet.getDrawing(i);//取第一个sheet中的第i个图片（插入时间上的第i个）
            byte[] bytes = img.getImageData();//从图片中取出数据
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImagePoint point = new ImagePoint(img.getColumn()-1,img.getRow()-1);//jxl从1开始
            point.height =  img.getHeight();
            point.width =img.getWidth();
            cache.put(point,bitmap);
            imgPointSet.add(point);
        }
        Cell[][] data = new Cell[maxRow][];
        for (int i = 0; i < maxRow; i++) {
            Cell[] rows = new Cell[maxColumn];
            for(int j = 0;j < maxColumn;j++){
                Cell cell = sheet.getCell(j, i);
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
        CellFormat cellFormat = cell.getCellFormat();
        if (cellFormat != null) {
            Font font = cellFormat.getFont();
            return font.getPointSize();
        }
        return 13;
    }

    @Override
    protected int getTextColor(Context context,Cell cell) {
        CellFormat cellFormat = cell.getCellFormat();
        if(cellFormat !=null) {
              Font font = cellFormat.getFont();
            Colour colour = font.getColour();
            RGB rgb = colour.getDefaultRGB();
            return Color.rgb(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
        }
        return Color.GRAY;
    }

    @Override
    protected int getBackgroundColor(Context context, Cell cell) {
        if(cell !=null) {
            CellFormat cellFormat = cell.getCellFormat();
            if(cellFormat !=null) {
                Colour colour = cellFormat.getBackgroundColour();
                RGB rgb = colour.getDefaultRGB();
                return Color.rgb(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
            }
        }
        return TableConfig.INVALID_COLOR;
    }

    @Override
    protected String getFormat(Cell cell) {
        return cell.getContents();
    }

    @Override
    protected Paint.Align getAlign(Cell cell) {
        CellFormat cellFormat = cell.getCellFormat();
        if(cellFormat !=null) {
            Alignment alignment = cellFormat.getAlignment();
            return alignment == Alignment.LEFT ? Paint.Align.LEFT :
                    alignment == Alignment.RIGHT ? Paint.Align.RIGHT
                            : Paint.Align.CENTER;
        }
        return Paint.Align.CENTER;
    }

    @Override
    protected boolean hasComment(Cell cell) {
        if(cell !=null && cell.getCellFeatures()!=null){
            String comment = cell.getCellFeatures().getComment();
            if(comment !=null && comment.length()>0){
                return true;
            }
        }
        return false;
    }

    @Override
    protected String getComment(Cell cell) {

        return cell.getCellFeatures().getComment();
    }

    @Override
    public Cell[][] getEmptyTableData() {
        return new Cell[26][50]; //美观
    }
}
