package com.bin.david.form.data.format.title;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.column.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;

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
        return  imageWidth ;
    }

    @Override
    public int measureHeight(TableConfig config) {

        return imageHeight;
    }

    /**
     * 获取bitmap
     * @return Bitmap 占位图
     */
    protected abstract Bitmap getBitmap(Column column);

    @Override
    public void draw(Canvas c, Column column, Rect rect,TableConfig config) {
        Paint paint = config.getPaint();
        drawBackground(c,column,rect,config);
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
            int disX= (rect.right-rect.left-width)/2;
            int disY= (rect.bottom-rect.top-height)/2;
            drawRect.left = rect.left+disX;
            drawRect.top = rect.top+ disY;
            drawRect.right = rect.right - disX;
            drawRect.bottom = rect.bottom - disY;
            c.drawBitmap(bitmap, imgRect, drawRect, paint);
        }
    }

    public boolean drawBackground(Canvas c, Column column, Rect rect, TableConfig config) {
        ICellBackgroundFormat<Column> backgroundFormat = config.getColumnCellBackgroundFormat();
        if(isDrawBackground && backgroundFormat != null){
            backgroundFormat.drawBackground(c,rect,column,config.getPaint());
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
