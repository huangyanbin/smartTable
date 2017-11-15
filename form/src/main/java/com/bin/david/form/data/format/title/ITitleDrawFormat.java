package com.bin.david.form.data.format.title;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2017/11/6.
 */

public interface ITitleDrawFormat {

    /**
     *测量宽
     */
    int measureWidth(Column column, TableConfig config);

    /**
     *测量高
     */
    int measureHeight(TableConfig config);

    /**
     * 绘制
     * @param c 画笔
     * @param column 列信息
     * @param left 左
     * @param top 顶
     * @param right 右
     * @param bottom 底
     */
    void draw(Canvas c, Column column,int left, int top, int right, int bottom, TableConfig config);

    /**
     * 重写可以绘制背景
     */
     boolean drawBackground(Canvas c, Column column, int left, int top, int right, int bottom, TableConfig config);
}
