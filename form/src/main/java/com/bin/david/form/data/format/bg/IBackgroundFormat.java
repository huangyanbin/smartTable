package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by huang on 2018/3/9.
 */

public interface IBackgroundFormat {

    /**
     * 绘制背景
     */
    void drawBackground(Canvas canvas, Rect rect,  Paint paint);
}
