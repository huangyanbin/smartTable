package com.bin.david.smarttable.layoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

/**
 * Created by huang on 2017/11/9.
 */

public class TanTanLayoutManager extends LayoutManager {

    public static final int MAX_SHOW_COUNT = 4;
    public static final float SCALE = 0.05f;
    public static final int MAX_TRANSLATION_Y = 20;
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {

        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if(itemCount < MAX_SHOW_COUNT){
            return;
        }
        for(int position = MAX_SHOW_COUNT -1; position>=0; position--){
            View child = recycler.getViewForPosition(position);
            addView(child);
            measureChildWithMargins(child,0,0);
            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);
            int left = (getWidth() - width/2)/2;
            int right = left + width/2;
            int top = (getHeight() - height/2)/2;
            int bottom = top+ height/2;
            layoutDecoratedWithMargins(child,left,top,right,bottom);
            int level = position;
            if(level ==0){
                child.setScaleX(1);
                child.setTranslationY(0);
            }else if(level < MAX_SHOW_COUNT-1){
                child.setScaleX(1-SCALE*level);
                child.setTranslationY(MAX_TRANSLATION_Y *level);
            }else{
                child.setScaleX(1-SCALE*(level-1));
                child.setTranslationY(MAX_TRANSLATION_Y *(level-1));
            }

        }
    }
}
