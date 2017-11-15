package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by huang on 2017/11/14.
 */

public interface IBackgroundFormat<T> {
    /**
     * 绘制背景
     */
    void drawBackground(Canvas canvas, int left, int top, int right, int bottom, Paint paint);

    /**
     * 获取背景颜色
     */
    int getBackGroundColor();

    /**
     * 是否绘制
     */
    boolean isDraw(T t);

    /**
     *当背景颜色改变字体也需要跟随变化
     */
    int getTextColor(T t);

}
