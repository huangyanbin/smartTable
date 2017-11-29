package com.bin.david.smarttable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bin.david.smarttable.adapter.ItemAdapter;
import com.bin.david.smarttable.bean.MainItem;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<MainItem> items = new ArrayList<>();
        items.add(new MainItem(ParseModeActivity.class,"解析模式"));
        items.add(new MainItem(AnnotationModeActivity.class,"注解模式"));
        items.add(new MainItem(NetHttpActivity.class,"网络模式"));
        items.add(new MainItem(PagerModeActivity.class,"分页模式"));
        items.add(new MainItem(ManyActivity.class,"测试150列"));
       items.add(new MainItem(TableListActivity.class,"测试手势冲突"));
        items.add(new MainItem(TanTanActivity.class,"探探效果"));
        items.add(new MainItem(CalendarMonthActivity.class,"RecyclerView日历(月模式)"));
        items.add(new MainItem(CalendarWeekActivity.class,"RecyclerView日历(星期模式)"));
        itemAdapter = new ItemAdapter(items);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.openLoadAnimation();
        itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               MainItem mainItem = (MainItem) adapter.getData().get(position);
                Intent i = new Intent(MainActivity.this,mainItem.clazz);
                startActivity(i);
            }
        });
    }



}
