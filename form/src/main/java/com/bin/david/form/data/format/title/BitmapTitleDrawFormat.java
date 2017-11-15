package com.bin.david.form.data.format.title;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.bg.IBackgroundFormat;

/**
 * Created by huang on 2017/10/30.
 */

public abstract class BitmapTitleDrawFormat implements ITitleDrawFormat {

    private int imageWidth;
    private int imageHeight;
    private Rect imgRect;
    private Rect drawRect;
    private boolean isDrawBackground  = true;


    public BitmapTitleDrawFormat(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        imgRect = new Rect();
        drawRect = new Rect();
    }



    @Override
    public int measureWidth(Column column, TableConfig config) {
        return  imageWidth +2*config.getHorizontalPadding();
    }

    @Override
    public int measureHeight(TableConfig config) {

        return imageHeight + 2*config.getVerticalPadding();
    }

    /**
     * 获取bitmap
     * @return Bitmap 占位图
     */
    protected abstract Bitmap getBitmap(Column column);

    @Override
    public void draw(Canvas c, Column column, int left, int top, int right, int bottom,TableConfig config) {
        Paint paint = config.getPaint();
        drawBackground(c,column,left,top,right,bottom,config);
        Bitmap bitmap = getBitmap(column);
        if(bitmap != null) {
            paint.setStyle(Paint.Style.FILL);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            imgRect.set(0,0,width,height);
            float scaleX = (float)width/imageWidth;
            float scaleY = (float)height/imageHeight;
            if(scaleX >1 || scaleY >1){
                if(scaleX > scaleY){
                    width = (int) (width/scaleX);
                    height = imageHeight;
                }else{
                    height = (int) (height/scaleY);
                    width = imageWidth;
                }
            }
            width= (int) (width*config.getZoom());
            height = (int) (height*config.getZoom());
            int disX= (right-left-width)/2;
            int disY= (bottom-top-height)/2;
            drawRect.left = left+disX;
            drawRect.top = top+ disY;
            drawRect.right = right - disX;
            drawRect.bottom = bottom - disY;
            c.drawBitmap(bitmap, imgRect, drawRect, paint);
        }
    }

    @Override
    public boolean drawBackground(Canvas c, Column column, int left, int top, int right, int bottom, TableConfig config) {
        IBackgroundFormat<Column> backgroundFormat = config.getColumnBackgroundFormat();
        if(isDrawBackground && backgroundFormat != null && backgroundFormat.isDraw(column)){
            backgroundFormat.drawBackground(c,left,top,right,bottom,config.getPaint());
            return true;
        }
        return false;
    }


    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public boolean isDrawBackground() {
        return isDrawBackground;
    }

    public void setDrawBackground(boolean drawBackground) {
        isDrawBackground = drawBackground;
    }
}
