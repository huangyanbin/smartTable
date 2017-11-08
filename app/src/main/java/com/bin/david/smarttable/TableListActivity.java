package com.bin.david.smarttable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bin.david.smarttable.adapter.TableListAdapter;

import java.util.ArrayList;

/**
 * Created by huang on 2017/10/18.
 */

public class TableListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TableListAdapter itemAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> items = new ArrayList<>();
        for(int i = 0;i <100;i++){
            items.add(i+"");
        }
        itemAdapter = new TableListAdapter(items);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.openLoadAnimation();

    }
}
