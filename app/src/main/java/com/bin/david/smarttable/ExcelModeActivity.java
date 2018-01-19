package com.bin.david.smarttable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.draw.BitmapDrawFormat;
import com.bin.david.form.data.format.selected.BaseSelectFormat;
import com.bin.david.form.data.format.selected.IDrawOver;
import com.bin.david.form.data.format.tip.MultiLineBubbleTip;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.CellRange;
import com.bin.david.form.data.format.draw.LeftTopDrawFormat;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;
import com.bin.david.smarttable.adapter.SheetAdapter;
import com.bin.david.smarttable.bean.ImagePoint;
import com.bin.david.smarttable.utils.DrawHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;


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
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.RGB;

public class ExcelModeActivity extends AppCompatActivity {

    private SmartTable<Cell> table;
    private SheetAsyncTask sheetTask;
    private ExcelAsyncTask excelTask;
    private RecyclerView recyclerView;
    private String fileName = "c.xls";
    private  List<CellRange> cellRanges;
    private Set<ImagePoint> imgPointSet;
    //使用缓存
    private LruCache<ImagePoint,Bitmap> cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        table = (SmartTable<Cell>) findViewById(R.id.table);
        table.getConfig().setFixedYSequence(true);
        table.getConfig().setFixedXSequence(true);
        table.getConfig().setShowTableTitle(false);
        int backgroundColor = ContextCompat.getColor(this,R.color.arc_bg);
        int xyGridColor = ContextCompat.getColor(this,R.color.excel_bg); //x,y序列网格颜色
        //配置
        table.getConfig().setHorizontalPadding(DensityUtils.dp2px(this,10))
                .setColumnTitleHorizontalPadding(DensityUtils.dp2px(this,5))
                .setXSequenceBackgroundColor(backgroundColor)
                .setYSequenceBackgroundColor(backgroundColor)
                .setLeftAndTopBackgroundColor(backgroundColor)
                .setSequenceGridStyle(new LineStyle().setColor(xyGridColor))
                .setLeftTopDrawFormat(new LeftTopDrawFormat() { //设置左上角三角形
                    @Override
                    protected int getResourceID() {
                        return R.mipmap.excel_triangle;
                    }

                    @Override
                    protected Context getContext() {
                        return ExcelModeActivity.this;
                    }
                });
        //设置表格背景颜色
        table.getConfig().setContentBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                Cell cell = (Cell) cellInfo.data;
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

        });
        table.setZoom(true,3,0.5f);
        //绘制选中区域
        table.setSelectFormat(new BaseSelectFormat());
        table.getProvider().setDrawOver(new IDrawOver() {
            @Override
            public void draw(Canvas canvas, Rect showRect, TableConfig config) {
                if(imgPointSet.size() >0){
                    for(ImagePoint point :imgPointSet){
                        if(table.getProvider().getGridDrawer().maybeContain(point.row,point.col)) {
                            Bitmap bitmap = cache.get(point);
                            int[] location = table.getProvider().getPointLocation(point.row,point.col);
                            int[] size = table.getProvider().getPointSize((int)Math.ceil(point.row),(int)Math.ceil(point.col));
                            int width = (int) (size[0]*point.width);
                            int height = (int)(size[1]*point.height);
                            Rect imgBitmap = new Rect(location[0],location[1],location[0]+width,location[1]+height);
                            DrawHelper.drawBitmap(canvas,imgBitmap,bitmap,config);
                        }
                    }
                }
            }
        });
        //增加批注
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));
        MultiLineBubbleTip<Column> tip = new MultiLineBubbleTip<Column>(this,R.mipmap.round_rect,R.mipmap.triangle,fontStyle) {
            @Override
            public boolean isShowTip(Column column, int position) {
                Cell cell = (Cell) column.getDatas().get(position);
                if(cell !=null && cell.getCellFeatures()!=null){
                    String comment = cell.getCellFeatures().getComment();
                    if(comment !=null && comment.length()>0){
                        return true;
                    }
                }
                return false;
            }


            @Override
            public String[] format(Column column, int position) {
                Cell cell = (Cell) column.getDatas().get(position);
                String comment = cell.getCellFeatures().getComment();
                return comment.split("\n");
            }
        };
        tip.setColorFilter(ContextCompat.getColor(this,R.color.column_bg));
        tip.setAlpha(0.9f);
        table.getProvider().setTip(tip);
        sheetTask = new SheetAsyncTask();
        sheetTask.execute();
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


    //读取sheet
    public class SheetAsyncTask extends AsyncTask<Void,Void,List<String>>{

        @Override
        protected List<String> doInBackground(Void... voids) {

            try {
                List<String> list = new ArrayList<>();
                InputStream is = getAssets().open(fileName);
                Workbook workbook = Workbook.getWorkbook(is);
                int sheetNum = workbook.getNumberOfSheets();
                for(int i = 0;i < sheetNum;i++){
                    Sheet sheet = workbook.getSheet(i);
                    list.add(sheet.getName());
                }
                workbook.close();
                return list;
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> sheets) {
            recyclerView.setHasFixedSize(true);
            if(sheets!=null && sheets.size() >0) {
                final SheetAdapter sheetAdapter = new SheetAdapter(sheets);
                sheetAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        sheetAdapter.setSelectPosition(position);
                        excelTask = new ExcelAsyncTask();
                        excelTask.execute(position);
                    }
                });
                recyclerView.setAdapter(sheetAdapter);
                excelTask = new ExcelAsyncTask();
                excelTask.execute(0);
            }


        }
    }


    public class ExcelAsyncTask extends AsyncTask<Integer,Void,Cell[][]>{

        @Override
        protected Cell[][] doInBackground(Integer... position) {

            try {
                //这里可以优化
                cache.evictAll();
                imgPointSet.clear();
                int maxRow, maxColumn;
                cellRanges = null;
                InputStream is = getAssets().open(fileName);
                Workbook workbook = Workbook.getWorkbook(is);
                Sheet sheet = workbook.getSheet(position[0]);
                Range[] ranges = sheet.getMergedCells();
                if(ranges !=null) {
                    cellRanges = new ArrayList<>();
                    for (int i = 0;i < ranges.length;i++) {
                        Range range =ranges[i];
                        CellRange cellRange = new CellRange(range.getTopLeft().getRow(),
                                range.getBottomRight().getRow(),
                                range.getTopLeft().getColumn(),range.getBottomRight().getColumn());
                        cellRanges.add(cellRange);
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

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Cell[][] data) {
            if(data ==null || data.length==0) {
                data = new Cell[26][50]; //美观
            }
            //设置字体
            ArrayTableData<Cell> tableData = ArrayTableData.create(table, "Excel表", data, new TextDrawFormat<Cell>(){


                //Excel 因为每格的大小都不一样，所以需要重新计算高度和宽度
                @Override
                public int measureWidth(Column<Cell> column, TableConfig config) {
                    int maxWidth = 0;
                    int count = column.getDatas().size();
                    for(int i = 0;i < count;i++){
                        Cell cell = column.getDatas().get(i);
                        if(cell !=null) {
                            CellFormat cellFormat = cell.getCellFormat();
                            if (cellFormat != null) {
                                Font font = cellFormat.getFont();
                                int fontSize = (int) (font.getPointSize() * 1.7f); //增加字体，效果更好看
                                config.getPaint().setTextSize(DensityUtils.sp2px(ExcelModeActivity.this, fontSize));
                                int width =  (int) config.getPaint().measureText(column.format(i));
                                if(width > maxWidth){
                                    maxWidth = width;
                                }
                            }
                        }
                    }
                    return maxWidth;
                }

                @Override
                public int measureHeight(Column<Cell> column, int position, TableConfig config) {
                    Cell cell = column.getDatas().get(position);
                    if(cell !=null) {
                        CellFormat cellFormat = cell.getCellFormat();
                        if (cellFormat != null) {
                            Alignment alignment = cellFormat.getAlignment();
                            config.getPaint().setTextAlign(alignment == Alignment.LEFT ? Paint.Align.LEFT :
                                    alignment == Alignment.RIGHT ? Paint.Align.RIGHT
                                            : Paint.Align.CENTER);
                            Font font = cellFormat.getFont();
                            int fontSize = (int) (font.getPointSize() * 1.7f); //增加字体，效果更好看
                            config.getPaint().setTextSize(DensityUtils.sp2px(ExcelModeActivity.this, fontSize) );
                            return DrawUtils.getTextHeight(config.getPaint());
                        }
                    }
                    return super.measureHeight(column, position, config);
                }


                @Override
                public void setTextPaint(TableConfig config, Cell cell, Paint paint) {

                    super.setTextPaint(config,cell,paint);
                    if(cell !=null) {
                        CellFormat cellFormat = cell.getCellFormat();
                        if(cellFormat !=null) {
                            Alignment alignment = cellFormat.getAlignment();
                            paint.setTextAlign(alignment == Alignment.LEFT ? Paint.Align.LEFT :
                                    alignment == Alignment.RIGHT ? Paint.Align.RIGHT
                                            : Paint.Align.CENTER);
                            Font font = cellFormat.getFont();
                            int size = (int) (font.getPointSize() * 1.7f); //增加字体，效果更好看
                            paint.setTextSize(DensityUtils.sp2px(ExcelModeActivity.this, size) * config.getZoom());
                            Colour colour = font.getColour();
                            RGB rgb = colour.getDefaultRGB();

                            paint.setColor(Color.rgb(rgb.getRed(), rgb.getGreen(), rgb.getBlue()));
                        }

                    }
                }
            });
            if(cellRanges !=null) {
                tableData.setUserCellRange(cellRanges); //设置自定义规则
            }
            tableData.setFormat(new IFormat<Cell>() {
                @Override
                public String format(Cell cell) {
                    if(cell !=null) {
                        return cell.getContents();
                    }
                    return "";
                }
            });
            table.setTableData(tableData);


        }
    }

    @Override
    protected void onDestroy() {
        if(sheetTask !=null && !sheetTask.isCancelled()){
            sheetTask.cancel(true);
        }
        if(excelTask !=null && !excelTask.isCancelled()){
            excelTask.cancel(true);
        }
        super.onDestroy();
    }

}
