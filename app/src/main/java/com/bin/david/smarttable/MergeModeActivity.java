package com.bin.david.smarttable;

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
import com.bin.david.smarttable.bean.MergeInfo;

import java.util.ArrayList;
import java.util.List;

public class MergeModeActivity extends AppCompatActivity {

    private SmartTable<MergeInfo> table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
       
        List<MergeInfo> list = new ArrayList<>();
        for(int i = 0;i <50; i++) {
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
            list.add(new MergeInfo("li", 23, System.currentTimeMillis(),false,null));
        }
        table = (SmartTable<MergeInfo>) findViewById(R.id.table);
        table.setData(list);
        table.getConfig().setShowTableTitle(false);
        table.setZoom(true,2,0.2f);
        table.getConfig().setContentBackgroundFormat(new BaseBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor() {
                return ContextCompat.getColor(MergeModeActivity.this,R.color.selectColor);
            }

            @Override
            public boolean isDraw(CellInfo cellInfo) {
               return "name".equals(cellInfo.column.getFieldName())
                       && cellInfo.position%2 ==1;
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return ContextCompat.getColor(MergeModeActivity.this,R.color.white);
            }
        });
    }
    public void onClick(View view) {
        table.back();
    }
}
