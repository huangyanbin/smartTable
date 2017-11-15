package com.bin.david.smarttable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.UserData;

import java.util.ArrayList;
import java.util.List;

public class AnnotationModeActivity extends AppCompatActivity {

    private SmartTable<UserData> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
       
        List<UserData> list = new ArrayList<>();
        for(int i = 0;i <100; i++) {
            list.add(new UserData("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new UserData("li", 23, System.currentTimeMillis(),false,null));
        }
        table = (SmartTable<UserData>) findViewById(R.id.table);
        table.setData(list);
        table.getConfig().setContentBackgroundFormat(new BaseBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor() {
                return ContextCompat.getColor(AnnotationModeActivity.this,R.color.selectColor);
            }

            @Override
            public boolean isDraw(CellInfo cellInfo) {
               return "name".equals(cellInfo.column.getFieldName())
                       && cellInfo.position%2 ==1;
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return ContextCompat.getColor(AnnotationModeActivity.this,R.color.white);
            }
        });
    }
    public void onClick(View view) {
        table.back();
    }
}
