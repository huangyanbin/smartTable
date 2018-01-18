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
import com.bin.david.smarttable.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class AnnotationModeActivity extends AppCompatActivity {

    private SmartTable<UserInfo> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
       
        List<UserInfo> list = new ArrayList<>();
        for(int i = 0;i <100; i++) {
            list.add(new UserInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new UserInfo("li", 23, System.currentTimeMillis(),false,null));
        }
        table = (SmartTable<UserInfo>) findViewById(R.id.table);
        table.setData(list);
        table.getConfig().setShowTableTitle(false);
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
        table.back();
    }
}
