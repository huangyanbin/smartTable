package com.bin.david.form.data.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.bin.david.form.utils.DensityUtils;


/**
 * Created by huang on 2017/9/27.
 */

public class FontStyle implements IStyle{

    public static int defaultFontSize = 12;
    public static int defaultFontColor = Color.parseColor("#636363");
    private int textSize;
    private int textColor;

    public static void setDefaultTextSize(int defaultTextSize){
        defaultFontSize = defaultTextSize;
    }

    public static void setDefaultTextSpSize(Context context,int defaultTextSpSize){
        defaultFontSize = DensityUtils.sp2px(context,defaultTextSpSize);
    }
    public static void setDefaultTextColor(int defaultTextColor){
        defaultFontColor = defaultTextColor;
    }

    public FontStyle() {
    }

    public FontStyle(int textSize, int textColor) {
        this.textSize = textSize;
        this.textColor = textColor;
    }
    
    public FontStyle(Context context, int sp, int textColor) {
        this.textSize = DensityUtils.sp2px(context,sp);
        this.textColor = textColor;
    }
    
    


    public int getTextSize() {
        if(textSize == 0){
            return defaultFontSize;
        }
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextSpSize(Context context,int sp){
        this.setTextSize(DensityUtils.sp2px(context,sp));
    }

    public int getTextColor() {
        if(textColor == 0){
            return defaultFontColor;
        }
        return textColor;
    }

    public FontStyle setTextColor(int textColor) {
        
        this.textColor = textColor;
        return this;
    }



    @Override
    public void fillPaint(Paint paint){
        paint.setColor(getTextColor());
        paint.setTextSize(getTextSize());
        paint.setStyle(Paint.Style.FILL);
    }
}
