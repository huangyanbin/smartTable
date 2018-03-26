package com.bin.david.smarttable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.sequence.ISequenceFormat;
import com.bin.david.form.data.format.sequence.NumberSequenceFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.StudentInfo;


public class DesktopModeActivity extends AppCompatActivity {

    private SmartTable<StudentInfo> table;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,13));
        table = (SmartTable<StudentInfo>) findViewById(R.id.table);
        StudentInfo[][] data = new StudentInfo[6][];
        for(int i = 0;i <6; i++){
            StudentInfo[] column = new StudentInfo[6];
            for(int j= 0;j <6; j++){
                    if(i >1 && i <5 && j >2&& j <5) {
                        column[j] = new StudentInfo("王小san");
                    } else {
                        column[j] = null;
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
        table.getConfig().setContentGridStyle(lineStyle)
                .setShowXSequence(false).setFixedYSequence(true)
                .setVerticalPadding(0)
                .setHorizontalPadding(0);
        table.setZoom(false);
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
        final int dp10  = DensityUtils.dp2px(this,10);
        table.getConfig().setYSequenceBackground(new IBackgroundFormat() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                rect.bottom -= dp10*1.5;
                ninePatch.draw(canvas, rect);
            }
        });
        table.getConfig().setYSequenceCellBgFormat(new ICellBackgroundFormat<Integer>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Integer position, Paint paint) {

            }

            @Override
            public int getTextColor(Integer integer) {
                return ContextCompat.getColor(DesktopModeActivity.this,R.color.white);
            }
        });

        final ArrayTableData<StudentInfo> tableData = ArrayTableData.create(table, "头像表", data,
                new DesktopDrawFormat(DesktopModeActivity.this));


        tableData.setOnItemClickListener(new ArrayTableData.OnItemClickListener<StudentInfo>() {

            public void onClick(Column column, String value, StudentInfo checked, int col, int row) {
                if(checked == null) {
                    Toast.makeText(DesktopModeActivity.this, "列:" + col + " 行：" + row + "数据：" + value, Toast.LENGTH_SHORT).show();
                    tableData.getData()[col][row] = new StudentInfo("王小二");
                }else{
                    tableData.getData()[col][row] = null;
                }
                table.invalidate();
            }
        });

        tableData.setYSequenceFormat(new NumberSequenceFormat() {
            @Override
            public String format(Integer integer) {
                return (6-integer+1)+"";
            }
        });
        table.setTableData(tableData);
        table.setYSequenceRight(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                table.getMatrixHelper().flingBottom(100);
            }
        },50);




    }

    public void onClick(View view){

    }
}
