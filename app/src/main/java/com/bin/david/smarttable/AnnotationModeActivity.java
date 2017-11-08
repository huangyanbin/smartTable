package com.bin.david.smarttable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
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
        setContentView(R.layout.activity_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
       
        List<UserData> list = new ArrayList<>();
        for(int i = 0;i <100; i++) {
            list.add(new UserData("huang", 18, System.currentTimeMillis(),true,new ChildData("测试1")));
            list.add(new UserData("li", 23, System.currentTimeMillis(),false,null));
        }
        table = findViewById(R.id.table);
        table.setData(list);
    }
    public void onClick(View view) {
        Intent intent = new Intent(this,ParseModeActivity.class);
        startActivity(intent);
    }
}
