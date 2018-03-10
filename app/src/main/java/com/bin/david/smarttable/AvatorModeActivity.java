package com.bin.david.smarttable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.title.TitleDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;


public class AvatorModeActivity extends AppCompatActivity {

    private SmartTable<Integer> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable<Integer>) findViewById(R.id.table);
        Integer[][] data = new Integer[20][];
        //构造假数据
        int avatorID = 0;
        int avatorID2 = 0;
        for(int i = 0;i <20; i++){
            Integer[] column = new Integer[10];
            for(int j= 0;j <10; j++){
                if(i ==0 || i == 19){
                    if(j == 9){
                        column[j] = 0;
                    }
                }else if(i ==1 || i == 18){
                    if(j == 9 || j ==8){
                        column[j] = 0;
                    }
                }else if(i == 2 || i == 17){
                    if(j != 0){
                        column[j] =0;
                    }
                }else{
                    if(i >4 && i <9 && j >2&& j <9) {
                        avatorID++;
                        column[j] = avatorID;
                    } else if(i >0 && i <5 && j >0&& j <9) {
                        avatorID2++;
                        column[j] = avatorID2;
                    }else {
                        column[j] = 0;
                    }
                }
            }
            data[i] = column;
        }

        FontStyle fontStyle = new FontStyle(this,10,ContextCompat.getColor(this,R.color.arc_text));
        table.getConfig().setColumnTitleStyle(fontStyle);
        table.getConfig().setHorizontalPadding(10);
        table.getConfig().setVerticalPadding(10);
        LineStyle lineStyle = new LineStyle();
        lineStyle.setColor(ContextCompat.getColor(this,android.R.color.transparent));
        table.getConfig().setContentGridStyle(lineStyle);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setFixedYSequence(true);
        table.setZoom(true,1,0.5f);
        table.getConfig().setTableGridFormat(new BaseGridFormat(){
            @Override
            protected boolean isShowYSequenceHorizontalLine(int row) {
                return false;
            }

            @Override
            protected boolean isShowYSequenceVerticalLine(int row) {
                return false;
            }
        });
        Bitmap bmp_9 = BitmapFactory.decodeResource(getResources(), R.mipmap.set_bg);
       final NinePatch ninePatch  = new NinePatch(bmp_9, bmp_9.getNinePatchChunk(), null);

        table.getConfig().setYSequenceBackground(new IBackgroundFormat() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                ninePatch.draw(canvas, rect);
               // DrawUtils.drawPatch(canvas,AvatorModeActivity.this,R.mipmap.set_bg,rect);
            }
        });
        table.getConfig().setYSequenceCellBgFormat(new ICellBackgroundFormat<Integer>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Integer position, Paint paint) {

            }

            @Override
            public int getTextColor(Integer integer) {
                return ContextCompat.getColor(AvatorModeActivity.this,R.color.white);
            }
        });
        int size = DensityUtils.dp2px(this,30);


        final ArrayTableData<Integer> tableData = ArrayTableData.create(table, "头像表", data,
                new ImageResDrawFormat<Integer>(size,size) {
            @Override
            protected Context getContext() {
                return AvatorModeActivity.this;
            }



            @Override
            protected int getResourceID(Integer status, String value, int position) {
                if(status == null){return 0;}
                if(status >0 && status <=20){
                        return getResources().getIdentifier("avator_"+status, "mipmap", getPackageName());
                }
                return 0;
            }
        });
        table.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>(){

            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {
                if(cellInfo.data != null && (Integer)cellInfo.data == 0){
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(ContextCompat.getColor(AvatorModeActivity.this,R.color.cal_buckle_color));
                    canvas.drawCircle(rect.centerX(),rect.centerY(),Math.min(rect.width(),rect.height())/2-15,paint);
                }
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return 0;
            }

        });

        tableData.setOnItemClickListener(new ArrayTableData.OnItemClickListener<Integer>() {
            @Override
            public void onClick(Column column, String value, Integer checked, int col, int row) {
                if(checked != null) {
                    Toast.makeText(AvatorModeActivity.this, "列:" + col + " 行：" + row + "数据：" + value, Toast.LENGTH_SHORT).show();
                    tableData.getData()[col][row] = checked == 1?0:1;
                }
                table.invalidate();
            }
        });
        table.setTableData(tableData);


    }

    public void onClick(View view){

    }
}
