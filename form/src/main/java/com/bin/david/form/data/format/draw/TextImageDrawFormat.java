package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.exception.TableException;

/**
 * Created by huang on 2017/10/30.
 */

public abstract class TextImageDrawFormat<T> extends ImageResDrawFormat<T> {

    public static final int LEFT =0;
    public static final int TOP =1;
    public static final int RIGHT =2;
    public static final int BOTTOM =3;

   private TextDrawFormat<T> textDrawFormat;
    private int drawPadding;
   private int direction;
   private int verticalPadding;
   private int horizontalPadding;

    public TextImageDrawFormat(int imageWidth, int imageHeight,int drawPadding) {
       this(imageWidth,imageHeight,LEFT,drawPadding);

    }

    public TextImageDrawFormat(int imageWidth, int imageHeight,int direction,int drawPadding) {
        super(imageWidth, imageHeight);
        textDrawFormat = new TextDrawFormat<>();
        this.direction = direction;
        this.drawPadding = drawPadding;
        if(direction >BOTTOM || direction <LEFT){
            throw  new TableException("Please set the direction less than 3 greater than 0");
        }

    }

    @Override
    public int measureWidth(Column<T>column, TableConfig config) {
        int textWidth = textDrawFormat.measureWidth(column, config);
        horizontalPadding = config.getHorizontalPadding();
        if(direction == LEFT || direction == RIGHT) {
            return getImageWidth() + textWidth+drawPadding;
        }else {
            return Math.max(super.measureWidth(column,config),textWidth);
        }
    }

    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        int imgHeight = super.measureHeight(column,position,config);
        int textHeight = textDrawFormat.measureHeight(column,position,config);
        verticalPadding = config.getVerticalPadding();
        if(direction == TOP || direction == BOTTOM) {
            return getImageHeight() + textHeight+drawPadding;
        }else {
            return Math.max(imgHeight,textHeight);
        }
    }

    @Override
    public void draw(Canvas c,Column<T> column, T t, String value, int left, int top, int right, int bottom, int position, TableConfig config) {
        setDrawBg(true);
        cellInfo.set(column,t,value,position);
        drawBackground(c,cellInfo,left,top,right,bottom,config);
        setDrawBg(false);
        textDrawFormat.setDrawBg(false);
        if(getBitmap(t,value,position) == null){
            textDrawFormat.draw(c,column,t,value,left,top,right,bottom,position,config);
            return;
        }
        int imgWidth = (int) (getImageWidth()*config.getZoom());
        int imgHeight = (int) (getImageHeight()*config.getZoom());
        switch (direction){
            case LEFT:
                textDrawFormat.draw(c,column,t,value,left+(imgWidth+drawPadding)/2,top,right,bottom,position,config);
                super.draw(c,column,t,value,left+horizontalPadding,top,left+horizontalPadding+imgWidth,bottom,position,config);
                break;
            case RIGHT:
                textDrawFormat.draw(c,column,t,value,left,top,right-(imgWidth+drawPadding)/2,bottom,position,config);
                super.draw(c,column,t,value,right-horizontalPadding-imgWidth,top,right-horizontalPadding,bottom,position,config);
                break;
            case TOP:
                textDrawFormat.draw(c,column,t,value,left,top+(imgHeight+drawPadding)/2,right,bottom,position,config);
                super.draw(c,column,t,value,left,top+verticalPadding,right,top+verticalPadding+imgHeight,position,config);
                break;
            case BOTTOM:
                textDrawFormat.draw(c,column,t,value,left,top,right,bottom-(imgHeight+drawPadding)/2,position,config);
                super.draw(c,column,t,value,left,bottom-verticalPadding-imgHeight,right,bottom-verticalPadding,position,config);
                break;

        }
    }
}
