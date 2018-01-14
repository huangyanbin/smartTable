package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2017/11/14.
 */

public abstract class BaseBackgroundFormat<T> implements IBackgroundFormat<T> {

    @Override
    public void drawBackground(Canvas canvas, Rect rect, T t,Paint paint) {
        paint.setColor(getBackGroundColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom,paint);
    }

    /**
     * 获取背景颜色
     */
      public abstract   int getBackGroundColor();

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
