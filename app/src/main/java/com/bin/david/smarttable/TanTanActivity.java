package com.bin.david.smarttable;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.bin.david.smarttable.adapter.TanTanAdapter;
import com.bin.david.smarttable.bean.TanBean;
import com.bin.david.smarttable.layoutManager.TanTanLayoutManager;

import java.util.List;

/**
 * Created by huang on 2017/11/9.
 */

public class TanTanActivity extends AppCompatActivity{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new TanTanLayoutManager());
        final List<TanBean> tanBeans = TanBean.initDatas();
        final TanTanAdapter tanTanAdapter = new TanTanAdapter(tanBeans);
        recyclerView.setAdapter(tanTanAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                 int swipeFlags = ItemTouchHelper.START| ItemTouchHelper.END;
                return makeMovementFlags(0,swipeFlags);

            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                 TanBean bean= tanBeans.get(0);
                 tanBeans.remove(bean);
                 tanBeans.add(bean);
                 viewHolder.itemView.setRotation(0);
                tanTanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                float proportion  = dX*2/recyclerView.getWidth() ;
                if(proportion >1){
                    proportion = 1;
                }else if(proportion <-1){
                    proportion = -1;
                }
                for(int i = 0;i < recyclerView.getChildCount();i++){
                    View child = recyclerView.getChildAt(i);
                    int level = TanTanLayoutManager.MAX_SHOW_COUNT - i -1;
                    if(level == 0){
                        child.setRotation(15*proportion);
                    }else if(level <TanTanLayoutManager.MAX_SHOW_COUNT -1 ){
                        Log.e("huang","level---"+level+"proportion"+proportion);
                        float scalex = 1-TanTanLayoutManager.SCALE*(level-Math.abs(proportion));
                        Log.e("huang","scaleX"+scalex);
                        child.setScaleX(scalex);
                        child.setTranslationY(TanTanLayoutManager.MAX_TRANY*(level-Math.abs(proportion)));
                    }/*else{
                        float scalex = 1-TanTanLayoutManager.SCALE*(level-1-Math.abs(proportion)-Math.abs(proportion));
                        Log.e("huang","level--------"+level+"proportion"+proportion);
                        child.setScaleX(scalex);
                        child.setTranslationY(TanTanLayoutManager.MAX_TRANY*(level-1-Math.abs(proportion)-Math.abs(proportion)));
                    }*/
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}
