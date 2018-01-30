package com.bin.david.smarttable;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.Student;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.view.QuickChartDialog;

import java.util.ArrayList;
import java.util.List;

public class AnnotationModeActivity extends AppCompatActivity {

    private SmartTable<Student> table;
    private QuickChartDialog quickChartDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        quickChartDialog = new QuickChartDialog();
        List<Student> list = new ArrayList<>();
        for(int i = 0;i <50; i++) {
            list.add(new Student("楼夕", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new Student("黄柳", 23, System.currentTimeMillis(),false,null));
        }
        table = (SmartTable<Student>) findViewById(R.id.table);
        table.setData(list);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        table.setZoom(true,2,0.2f);
        //设置单个格子背景颜色
        table.getConfig().setContentBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if("name".equals(cellInfo.column.getFieldName())
                        && cellInfo.position%2 ==1) {
                    return ContextCompat.getColor(AnnotationModeActivity.this, R.color.selectColor);
                }else{
                    return TableConfig.INVALID_COLOR;
                }
            }
            //根据背景颜色设置字体颜色
            @Override
            public int getTextColor(CellInfo cellInfo) {
                if("name".equals(cellInfo.column.getFieldName())
                        && cellInfo.position%2 ==1) {
                    return ContextCompat.getColor(AnnotationModeActivity.this, R.color.white);
                }else{
                    return super.getTextColor(cellInfo);
                }
            }
        });
    }

    public void onClick(View view) {
        fling(TableStyle.FLING);
    }

    //飞滚
    private void fling(TableStyle item) {
        quickChartDialog.showDialog(this, item, new String[]{"Left","Top","Right","Bottom"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getMatrixHelper().flingLeft(200);
                } else if (position == 1) {
                    table.getMatrixHelper().flingTop(200);
                }else if(position ==2){
                    table.getMatrixHelper().flingRight(200);
                }else {
                    table.getMatrixHelper().flingBottom(2000);
                }
                table.invalidate();
            }
        });
    }


}
