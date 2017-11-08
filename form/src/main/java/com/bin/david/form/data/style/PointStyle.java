package com.bin.david.form.data.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.bin.david.form.utils.DensityUtils;


/**
 * Created by huang on 2017/9/27.
 */

public class PointStyle implements IStyle {

    public static final int CIRCLE = 0;
    public static final int SQUARE = 1;
    public static final int RECT = 2;
    private float width;
    private int color;
    private int shape;
    private Paint.Style style;
    private static  float defaultPointSize = 10f;
    private static  int defaultPointColor = Color.parseColor("#888888");
    private  boolean isDraw = true;
    public PointStyle() {
    }

    public PointStyle(float width, int color) {
        this.width = width;
        this.color = color;
    }
    public PointStyle(Context context, float dp, int color) {
        this.width = DensityUtils.dp2px(context,dp);
        this.color = color;
    }
    public static void setDefaultPointSize(float width){
        defaultPointSize = width;
    }

    public static void setDefaultLineSize(Context context,float dp){
        defaultPointSize = DensityUtils.dp2px(context,dp);
    }

    public static void setDefaultPointColor(int color){
        defaultPointColor = color;
    }

    public float getWidth() {
        if(width == 0){
            return defaultPointSize;
        }
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    public void setWidth(Context context,int dp) {
        this.width = DensityUtils.dp2px(context,dp);
    }

    public int getColor() {
        if(color == 0){
            return defaultPointColor;
        }
        return color;
    }

    public void setColor(int color) {

        this.color = color;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public Paint.Style getStyle() {
        if(style == null){
            return Paint.Style.FILL;
        }
        return style;
    }

    public void setStyle(Paint.Style style) {
        this.style = style;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    @Override
    public void fillPaint(Paint paint){
        paint.setColor(getColor());
        paint.setStyle(getStyle());
        paint.setStrokeWidth(getWidth());
    }
}
