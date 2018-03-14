package com.bin.david.smarttable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
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
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.selected.IDrawOver;
import com.bin.david.form.data.format.title.TitleDrawFormat;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.utils.BitmapDrawer;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;


public class SeatModeActivity extends AppCompatActivity {

    private SmartTable<Integer> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable<Integer>) findViewById(R.id.table);
        Integer[][] data = new Integer[20][];
        //构造假数据
        for(int i = 0;i <20; i++){
            Integer[] column = new Integer[10];
            for(int j= 0;j <10; j++){
                 if(i ==0 || i == 19){
                    if(i ==0 && j == 9){
                        column[j] = 1;
                    }else{
                        column[j] = null;
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
                    if(i >8 && i <12 && j >6&& j <9) {
                        column[j] = 1;
                    }else {
                        column[j] = 0;
                    }
                }
            }
            data[i] = column;
        }

        FontStyle fontStyle = new FontStyle(this,10,ContextCompat.getColor(this,R.color.arc_text));
        LineStyle lineStyle = new LineStyle();
        lineStyle.setColor(ContextCompat.getColor(this,R.color.dedede));
        lineStyle.setEffect(new DashPathEffect(new float[] {5, 5}, 0));
        table.getConfig().setColumnTitleStyle(fontStyle);
        table.getConfig().setHorizontalPadding(10)
                .setVerticalPadding(10)
                .setSequenceHorizontalPadding(0)
                .setSequenceVerticalPadding(0)
                .setContentGridStyle(lineStyle)
                .setShowXSequence(false)
                .setFixedYSequence(true);

        table.setZoom(true,2,0.5f);
        table.getConfig().setTableGridFormat(new BaseGridFormat(){
            @Override
            protected boolean isShowYSequenceHorizontalLine(int row) {
                return false;
            }

            @Override
            protected boolean isShowYSequenceVerticalLine(int row) {
                return false;
            }

            @Override
            protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
                return false;
            }

            @Override
            protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
                return col == 10;
            }

            @Override
            public void drawTableBorderGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {

            }
        });
        table.getConfig().setYSequenceBackground(new IBackgroundFormat() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                DrawUtils.drawPatch(canvas,SeatModeActivity.this,R.mipmap.set_bg,rect);
            }
        });
        table.getConfig().setYSequenceCellBgFormat(new ICellBackgroundFormat<Integer>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Integer position, Paint paint) {

            }

            @Override
            public int getTextColor(Integer integer) {
                return ContextCompat.getColor(SeatModeActivity.this,R.color.white);
            }
        });
        final int size = DensityUtils.dp2px(this,20);
        ImageResDrawFormat<Integer> format =  new ImageResDrawFormat<Integer>(size,size) {

            @Override
            protected Context getContext() {
                return SeatModeActivity.this;
            }



            @Override
            protected int getResourceID(Integer status, String value, int position) {
                if(status == null){return 0;}
                if(status == -1){
                    return R.mipmap.a9m;
                }
                switch (status){
                    case 0:
                        return R.mipmap.seat;
                    case 1:
                        return R.mipmap.seat_selected;
                }
                return 0;
            }

        };
        final ArrayTableData<Integer> tableData = ArrayTableData.create(table, "", data,format);

        tableData.setOnItemClickListener(new ArrayTableData.OnItemClickListener<Integer>() {
            @Override
            public void onClick(Column column, String value, Integer checked, int col, int row) {
                if(checked != null) {
                    Toast.makeText(SeatModeActivity.this, "列:" + col + " 行：" + row + "数据：" + value, Toast.LENGTH_SHORT).show();
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
