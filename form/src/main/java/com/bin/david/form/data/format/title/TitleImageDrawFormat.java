package com.bin.david.form.data.format.title;

import android.graphics.Canvas;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.exception.TableException;

/**
 * Created by huang on 2017/10/30.
 */

public abstract class TitleImageDrawFormat extends ImageResTitleDrawFormat {

    public static final int LEFT =0;
    public static final int TOP =1;
    public static final int RIGHT =2;
    public static final int BOTTOM =3;

   private TitleDrawFormat textDrawFormat;
    private int drawPadding;
   private int direction;
   private int verticalPadding;
   private int horizontalPadding;

    public TitleImageDrawFormat(int imageWidth, int imageHeight, int drawPadding) {
       this(imageWidth,imageHeight,LEFT,drawPadding);

    }

    public TitleImageDrawFormat(int imageWidth, int imageHeight, int direction, int drawPadding) {
        super(imageWidth, imageHeight);
        textDrawFormat = new TitleDrawFormat();
        this.direction = direction;
        this.drawPadding = drawPadding;
        if(direction >BOTTOM || direction <LEFT){
            throw  new TableException("Please set the direction less than 3 greater than 0");
        }

    }

    @Override
    public int measureWidth(Column column, TableConfig config) {
        int textWidth = textDrawFormat.measureWidth(column, config);
        horizontalPadding = config.getHorizontalPadding();
        if(direction == LEFT || direction == RIGHT) {
            return getImageWidth() + textWidth+drawPadding;
        }else {
            return Math.max(super.measureWidth(column,config),textWidth);
        }
    }

    @Override
    public int measureHeight( TableConfig config) {
        int imgHeight = super.measureHeight(config);
        int textHeight = textDrawFormat.measureHeight(config);
        verticalPadding = config.getVerticalPadding();
        if(direction == TOP || direction == BOTTOM) {
            return getImageHeight() + textHeight+drawPadding;
        }else {
            return Math.max(imgHeight,textHeight);
        }
    }

    @Override
    public void draw(Canvas c, Column column,  int left, int top, int right, int bottom,TableConfig config) {
        setDrawBackground(true);
        drawBackground(c,column,left,top,right,bottom,config);
        setDrawBackground(false);
        if(getBitmap(column) == null){
            textDrawFormat.draw(c,column,left,top,right,bottom,config);
            return;
        }
        int width,imgLeft,imgRight,textWidth,height,imgTop,imgBottom,textHeight;
        switch (direction){
            case LEFT:
                    width = (int) (measureWidth(column,config)*config.getZoom());
                    imgLeft = (int) (left+(right -left- width)/2 +horizontalPadding*config.getZoom());
                   imgRight = (int) (imgLeft+getImageWidth()*config.getZoom());
                    super.draw(c,column,imgLeft,top,imgRight,bottom,config);
                     textWidth = (int) ((textDrawFormat.measureWidth(column,config) -2*horizontalPadding)*config.getZoom());
                    textDrawFormat.draw(c,column,imgRight+drawPadding,top,imgRight+drawPadding+textWidth,bottom,config);

                   break;
            case RIGHT:
                width = (int) (measureWidth(column,config)*config.getZoom());
                 imgRight = (int) (right-(right -left- width)/2-horizontalPadding*config.getZoom());
                imgLeft = (int) (imgRight - getImageWidth()*config.getZoom());
                super.draw(c,column,imgLeft,top,imgRight,bottom,config);
                textWidth = (int) ((textDrawFormat.measureWidth(column,config) -2*horizontalPadding)*config.getZoom());
                textDrawFormat.draw(c,column,imgLeft-drawPadding
                        - textWidth,top,imgLeft-drawPadding,bottom,config);

                break;
            case TOP:
                height = (int) (measureHeight(config)*config.getZoom());
                imgTop = (int) (top+(top -bottom- height)/2 +verticalPadding*config.getZoom());
                imgBottom = (int) (imgTop+ getImageHeight()*config.getZoom());
                textDrawFormat.draw(c,column,left,imgTop,right,imgBottom,config);
                textHeight = (int) ((textDrawFormat.measureHeight(config) -2*verticalPadding)*config.getZoom());
                super.draw(c,column,left,imgBottom+drawPadding,right,imgBottom+drawPadding+textHeight,config);
                break;
            case BOTTOM:
                height = (int) (measureHeight(config)*config.getZoom());
                imgBottom = (int) (bottom-(bottom -top- height)/2- verticalPadding*config.getZoom());
                imgTop = (int) (imgBottom - getImageHeight()*config.getZoom());
                textDrawFormat.draw(c,column,left,imgTop,right,imgBottom,config);
                textHeight = (int) ((textDrawFormat.measureHeight(config) -2*verticalPadding)*config.getZoom());
                super.draw(c,column,left,imgTop-drawPadding-textHeight,right,imgTop-drawPadding,config);
                break;

        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
