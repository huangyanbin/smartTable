package com.bin.david.smarttable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.utils.DensityUtils;

public class ArrayModeActivity extends AppCompatActivity {

    private SmartTable<Integer> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable<Integer>) findViewById(R.id.table);
        String[] week = {"日","一","二","三","四","五","六"};
        Integer[][] infos = {{0,1,2,1,1,0,1,1,0,1,1,2,3}, {4,2,1,1,0,1,1,0,1,1,2,2,3},
                {2,2,0,1,2,4,1,0,1,3,0,1,1},{2,1,1,0,1,4,0,1,1,2,2,0,3},
                {0,1,2,4,1,0,1,4,0,1,1,2,2}, {1,0,1,3,2,2,0,1,2,1,1,0,4},
                {3,1,2,4,0,1,2,1,1,0,1,1,0}};
        FontStyle fontStyle = new FontStyle(this,10,ContextCompat.getColor(this,R.color.arc_text));
        table.getConfig().setColumnTitleStyle(fontStyle);
        table.getConfig().setHorizontalPadding(0);

        table.getConfig().setVerticalPadding(0);
        table.getConfig().setGridStyle(new LineStyle());

        final ArrayTableData<Integer> tableData = ArrayTableData.create("日程表",week,infos,new IDrawFormat<Integer>(){

            @Override
            public int measureWidth(Column<Integer> column, int position,TableConfig config) {
                return DensityUtils.dp2px(ArrayModeActivity.this,50);
            }

            @Override
            public int measureHeight(Column<Integer> column, int position, TableConfig config) {
                return DensityUtils.dp2px(ArrayModeActivity.this,50);
            }

            @Override
            public void draw(Canvas c, Column<Integer> column, Integer integer, String value, Rect rect, int position, TableConfig config) {
                Paint paint = config.getPaint();
                int color;
                switch (integer){
                    case 1:
                        color =R.color.github_con_1;
                        break;
                    case 2:
                        color =R.color.github_con_2;
                        break;
                    case 3:
                        color =R.color.github_con_3;
                        break;
                    case 4:
                        color =R.color.github_con_4;
                        break;
                    default:
                        color =R.color.github_con_0;
                        break;
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(ArrayModeActivity.this, color));
                c.drawRect(rect.left+5,rect.top+5,rect.right-5,rect.bottom-5,paint);
            }
        });
        tableData.setOnItemClickListener(new ArrayTableData.OnItemClickListener<Integer>() {
            @Override
            public void onClick(Column column, String value, Integer o, int col, int row) {
                tableData.getArrayColumns().get(col).getDatas().get(row);
                Toast.makeText(ArrayModeActivity.this,"列:"+col+ " 行："+row + "数据："+value,Toast.LENGTH_SHORT).show();
            }
        });
        table.setTableData(tableData);
    }




}
