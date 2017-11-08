package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2017/10/30.
 * 绘制格式化
 */

public interface IDrawFormat<T>  {

    /**
     *测量宽
     */
    int measureWidth(Column<T> column, TableConfig config);

    /**
     *测量高
     */
    int measureHeight(Column<T> column,int position, TableConfig config);

    /**
     * 绘制
     * @param c 画笔
     * @param t 对象
     * @param value 值
     * @param left 左
     * @param top 顶
     * @param right 右
     * @param bottom 底
     * @param position 位置
     * @param paint 画笔
     */
    void draw(Canvas c,T t,String value,int left,int top,int right,int bottom,int position,TableConfig config);
}
