package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;

/**
 * Created by huang on 2017/10/30.
 * 左上角格式化
 */

public abstract class LeftTopDrawFormat extends ImageResDrawFormat<String> {


    public LeftTopDrawFormat() {
        super(20, 20);
    }

    @Override
    protected int getResourceID(String s, String value, int position) {
        return getResourceID();
    }

    protected abstract int getResourceID();

   public void setImageSize(int w,int h){
       setImageWidth(w);
       setImageHeight(h);
   }


    @Override
    public void draw(Canvas c, Rect rect, CellInfo<String> cellInfo , TableConfig config) {
       //为了保持三角形不变形，不跟随缩放
       float zoom = config.getZoom();
        config.setZoom(zoom>1?1:zoom);
        super.draw(c,  rect, cellInfo, config);
        config.setZoom(zoom);
    }
}
