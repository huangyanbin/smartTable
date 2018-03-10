package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2017/11/14.
 * 通用绘制Rect格子背景绘制
 */

public abstract class BaseCellBackgroundFormat<T> implements ICellBackgroundFormat<T> {

    @Override
    public void drawBackground(Canvas canvas, Rect rect, T t,Paint paint) {
        int color = getBackGroundColor(t);
        if(color != TableConfig.INVALID_COLOR) {
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rect, paint);
        }
    }

    /**
     * 获取背景颜色
     */
      public abstract   int getBackGroundColor(T t);

    /**
     * 默认字体颜色不跟随背景变化，
     * 当有需要多种字体颜色，请重写该方法
     * @param t
     * @return
     */
    @Override
    public int getTextColor(T t) {
        return TableConfig.INVALID_COLOR;
    }
}
