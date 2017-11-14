package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by huang on 2017/11/14.
 */

public interface IBackgroundFormat<T> {

    void drawBackground(Canvas canvas, int left, int top, int right, int bottom, Paint paint);

    int getBackGroundColor();

    boolean isDraw(T t);
}
