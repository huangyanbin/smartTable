package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/9/29.
 * 绘制标题
 */

public class TableTitle extends PercentComponent<String> implements ITableTitle {

    private static final float MAX_PERCENT =0.4f;


    @Override
    public void setPercent(float percent) {
        if(percent > MAX_PERCENT){
            percent = MAX_PERCENT;
        }
        super.setPercent(percent);
    }

    @Override
    public void draw(Canvas canvas,Rect showRect, String tableName, TableConfig config){
        Paint paint = config.getPaint();
        config.getTableTitleStyle().fillPaint(paint);
        Rect rect = getRect();
        int startY = rect.centerY();
        int startX = rect.centerX();
        Path path = new Path();
        switch (direction) {
            case TOP:
            case BOTTOM:
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(tableName, startX, DrawUtils.getTextCenterY(startY,paint), paint);
                break;
            case LEFT:
            case RIGHT:
                int textWidth = (int)paint.measureText(tableName);
                path.moveTo(startX,rect.top);
                path.lineTo(startX,rect.bottom);
                canvas.drawTextOnPath(tableName,path,(rect.height()-textWidth)/2,0,paint);
                break;
        }
    }


}
