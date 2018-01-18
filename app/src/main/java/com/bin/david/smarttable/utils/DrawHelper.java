package com.bin.david.smarttable.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2018/1/18.
 */

public class DrawHelper {

    /**
     * 绘制图片
     * @param c 画布
     * @param rect 绘制大小
     * @param bitmap 位图
     * @param config 配置
     */
        public static void drawBitmap(Canvas c, Rect rect, Bitmap bitmap, TableConfig config) {
            if (bitmap != null) {
                config.getPaint().setStyle(Paint.Style.FILL);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                Rect imgRect= new Rect(0,0,width,height);
                float scaleX = (float) width / rect.width();
                float scaleY = (float) height / rect.height();
                if (scaleX > 1 || scaleY > 1) {
                    if (scaleX > scaleY) {
                        width = (int) (width / scaleX);
                        height = rect.height();
                    } else {
                        height = (int) (height / scaleY);
                        width = rect.width();
                    }
                }
                width = (int) (width * config.getZoom());
                height = (int) (height * config.getZoom());
                int disX = (rect.right - rect.left - width) / 2;
                int disY = (rect.bottom - rect.top - height) / 2;
                Rect drawRect = new Rect();
                drawRect.left = rect.left + disX;
                drawRect.top = rect.top + disY;
                drawRect.right = rect.right - disX;
                drawRect.bottom = rect.bottom - disY;
                c.drawBitmap(bitmap, imgRect, drawRect, config.getPaint());
            }
        }

}
