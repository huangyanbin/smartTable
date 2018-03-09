package com.bin.david.smarttable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;

public class SeatModeActivity extends AppCompatActivity {

    private SmartTable<Integer> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable<Integer>) findViewById(R.id.table);
        Integer[][] data = new Integer[20][];
        //构造假数据
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
        table.getConfig().setColumnTitleStyle(fontStyle);
        table.getConfig().setHorizontalPadding(10);
        table.getConfig().setVerticalPadding(10);
        LineStyle lineStyle = new LineStyle();
        lineStyle.setColor(ContextCompat.getColor(this,android.R.color.transparent));
        table.getConfig().setGridStyle(lineStyle);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setFixedYSequence(true);//暂时有问题 ，后面修复
        table.setZoom(true,1,0.5f);
        final  int roundSize = DensityUtils.dp2px(SeatModeActivity.this,5);
        table.getConfig().setYSequenceBgFormat(new ICellBackgroundFormat<Integer>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Integer position, Paint paint) {

                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(SeatModeActivity.this,R.color.seat_y_bg));
                canvas.drawRect(rect,paint);
            }

            @Override
            public int getTextColor(Integer integer) {
                return ContextCompat.getColor(SeatModeActivity.this,R.color.white);
            }
        });
        int size = DensityUtils.dp2px(this,20);
        final ArrayTableData<Integer> tableData = ArrayTableData.create(table, "选座表", data,
                new ImageResDrawFormat<Integer>(size,size) {
            @Override
            protected Context getContext() {
                return SeatModeActivity.this;
            }

            @Override
            public void draw(Canvas c, Column<Integer> column, Integer integer, String value, Rect rect, int position, TableConfig config) {
                super.draw(c, column, integer, value, rect, position, config);
                Paint paint = config.getPaint();
                paint.setColor(ContextCompat.getColor(SeatModeActivity.this,R.color.cal_sign_color));
                paint.setTextSize(DensityUtils.sp2px(SeatModeActivity.this,13));
                DrawUtils.drawSingleText(c,paint,rect,value);
            }

            @Override
            protected int getResourceID(Integer status, String value, int position) {
                if(status == null){return 0;}
                switch (status){
                    case 0:
                        return R.mipmap.seat;
                    case 1:
                        return R.mipmap.seat_selected;
                }
                return 0;
            }


        });

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
