package com.bin.david.form.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.style.FontStyle;

/**
 * Created by huang on 2017/11/1.
 */

public class DrawUtils {

    public static int getTextHeight(FontStyle style,Paint paint){
        style.fillPaint(paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    public static int getTextHeight(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    public static float getTextCenterY(int centerY,Paint paint){
       return centerY-((paint.descent() + paint.ascent()) / 2);
    }

    public static float getTextCenterX(int left,int right,Paint paint){
        Paint.Align align = paint.getTextAlign();
        if(align == Paint.Align.RIGHT){
            return right;
        }else if(align == Paint.Align.LEFT){
            return left;
        }else{
            return (right +left)/2;
        }
    }

    public static boolean isMixRect(Rect rect,int left,int top,int right,int bottom){

        return rect.bottom>= top && rect.right >= left && rect.top <bottom && rect.left< right;
    }

    public static boolean isClick(int left, int top, int right, int bottom, PointF clickPoint){
        return clickPoint.x >= left && clickPoint.x <=right && clickPoint.y>=top && clickPoint.y <=bottom;
    }

    public static boolean isClick(Rect rect, PointF clickPoint){
        return rect.contains((int)clickPoint.x,(int)clickPoint.y);
    }
    public static void fillBackground(Canvas canvas,int left, int top, int right, int bottom,int bgColor,Paint paint){
        if(bgColor != TableConfig.INVALID_COLOR) {
            paint.setColor(bgColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    public static boolean isMixHorizontalRect(Rect rect,int left,int right){

        return   rect.right >= left  && rect.left<= right;
    }
    public static boolean isVerticalMixRect(Rect rect,int top,int bottom){

        return rect.bottom>= top  && rect.top <=bottom;
    }
}
