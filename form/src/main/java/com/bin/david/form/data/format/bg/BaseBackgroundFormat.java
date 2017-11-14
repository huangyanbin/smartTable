package com.bin.david.form.data.format.bg;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by huang on 2017/11/14.
 */

public abstract class BaseBackgroundFormat<T> implements IBackgroundFormat<T> {

    @Override
    public void drawBackground(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {
        paint.setColor(getBackGroundColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(left,top,right,bottom,paint);
    }




}
