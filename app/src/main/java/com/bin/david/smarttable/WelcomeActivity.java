package com.bin.david.smarttable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.view.QuickChartDialog;

/**
 * Created by huang on 2018/2/8.
 * 欢迎页
 */

public class WelcomeActivity extends AppCompatActivity {
    private SmartTable<Integer> table;

    private QuickChartDialog quickChartDialog;
    ArrayTableData<Integer> tableData;
    ValueAnimator valueAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weclome);
        table = (SmartTable<Integer>) findViewById(R.id.table);
        quickChartDialog = new QuickChartDialog();
        Integer[][] data = {{0,1,2},{3,4,5},{6,7,8}};

        table.getConfig()
                .setHorizontalPadding(0)
                .setVerticalPadding(0)
                .setColumnTitleHorizontalPadding(0)
                .setColumnTitleVerticalPadding(0)
                .setShowXSequence(false)
                .setShowYSequence(false)
                .setShowTableTitle(false)
                .setGridStyle(new LineStyle(-1,ContextCompat.getColor(this,android.R.color.transparent)));
        tableData= ArrayTableData.create(table, "动画", data, null);
        table.setTableData(tableData);
        startAnim0();
    }




    public void onClick(View view){
        quickChartDialog.showDialog(this, TableStyle.ANIM, new String[]{"翻转","单翻转"}, new QuickChartDialog.OnCheckChangeAdapter() {
            @Override
            public void onItemClick(String s, int position) {
                switch (position){
                    case 0:
                        startAnim0();
                        break;
                    case 1:
                        startAnim1();
                        break;
                }
            }
        });
    }
    private int rotateAngle0=0;
    private void startAnim0(){
        final int size = DensityUtils.dp2px(this,40);
        IDrawFormat<Integer> format = new IDrawFormat<Integer>() {
            @Override
            public int measureWidth(Column<Integer> column, int position, TableConfig config) {
                return size;
            }

            @Override
            public int measureHeight(Column<Integer> column, int position, TableConfig config) {
                return size;
            }

            @Override
            public void draw(Canvas c, Column<Integer> column, Integer integer, String value, Rect rect, int position, TableConfig config) {
                Paint paint = config.getPaint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(WelcomeActivity.this, R.color.arc1));
                c.save();
                c.clipRect(rect);
                c.rotate(rotateAngle0,rect.centerX(),rect.centerY());
                c.drawRect(rect.left+5,rect.top+5,rect.right-5,rect.bottom-5,paint);
                c.restore();
            }
        };
        tableData.setDrawFormat(format);
        if(valueAnimator != null){
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofInt(0,180).setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotateAngle0 = (int) animation.getAnimatedValue();
                table.invalidate();
            }
        });
        valueAnimator.start();
    }
    private int rotateAngle1=0;
    private int rotatePosition =0;

    private void startAnim1(){
        final int size = DensityUtils.dp2px(this,40);
       final  int[] points = new int[]{0,1,2,5,8,7,6,3};
        IDrawFormat<Integer> format = new IDrawFormat<Integer>() {
            @Override
            public int measureWidth(Column<Integer> column, int position, TableConfig config) {
                return size;
            }

            @Override
            public int measureHeight(Column<Integer> column, int position, TableConfig config) {
                return size;
            }

            @Override
            public void draw(Canvas c, Column<Integer> column, Integer val, String value, Rect rect, int position, TableConfig config) {

                Paint paint = config.getPaint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(WelcomeActivity.this, R.color.arc1));
                c.save();
                if(val == points[rotatePosition]) {
                    c.rotate(rotateAngle1, rect.centerX(), rect.centerY());
                }
                c.drawRect(rect.left+10,rect.top+10,rect.right-10,rect.bottom-10,paint);
                c.restore();
            }
        };
        tableData.setDrawFormat(format);
        if(valueAnimator != null){
            valueAnimator.cancel();
        }
         valueAnimator = ValueAnimator.ofInt(0,180).setDuration(1000);
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                rotatePosition++;
                if(rotatePosition ==8){
                    rotatePosition = 0;
                }
                valueAnimator.setStartDelay(50);
                valueAnimator.start();
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                rotateAngle1 = (int) animation.getAnimatedValue();

                table.invalidate();
            }
        });
        valueAnimator.start();
    }

}
