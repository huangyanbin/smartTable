package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2017/11/14.
 * 通用绘制背景绘制
 */

public  class BaseBackgroundFormat implements IBackgroundFormat {
    private int backgroundColor;

    public BaseBackgroundFormat(int backgroundColor){
        this.backgroundColor= backgroundColor;
    }

    @Override
    public void drawBackground(Canvas canvas, Rect rect,Paint paint) {
        if(backgroundColor != TableConfig.INVALID_COLOR) {
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rect, paint);
        }
    }



}
