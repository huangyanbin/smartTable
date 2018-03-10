package com.bin.david.form.data.format.draw;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
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
   private Rect rect;

    public TextImageDrawFormat(int imageWidth, int imageHeight,int drawPadding) {
       this(imageWidth,imageHeight,LEFT,drawPadding);

    }

    public TextImageDrawFormat(int imageWidth, int imageHeight,int direction,int drawPadding) {
        super(imageWidth, imageHeight);
        textDrawFormat = new TextDrawFormat<>();
        this.rect = new Rect();
        this.direction = direction;
        this.drawPadding = drawPadding;
        if(direction >BOTTOM || direction <LEFT){
            throw  new TableException("Please set the direction less than 3 greater than 0");
        }

    }

    @Override
    public int measureWidth(Column<T>column, int position,TableConfig config) {
        int textWidth = textDrawFormat.measureWidth(column,position, config);
        if(direction == LEFT || direction == RIGHT) {
            return getImageWidth() + textWidth+drawPadding;
        }else {
            return Math.max(super.measureWidth(column,position,config),textWidth);
        }
    }

    @Override
    public int measureHeight(Column<T> column,int position, TableConfig config) {
        int imgHeight = super.measureHeight(column,position,config);
        int textHeight = textDrawFormat.measureHeight(column,position,config);

        if(direction == TOP || direction == BOTTOM) {
            return getImageHeight() + textHeight+drawPadding;
        }else {
            return Math.max(imgHeight,textHeight);
        }
    }

    @Override
    public void draw(Canvas c, Rect rect, CellInfo<T> cellInfo, TableConfig config) {

        if(getBitmap(cellInfo.data,cellInfo.value,cellInfo.row) == null){
            textDrawFormat.draw(c,rect,cellInfo,config);
            return;
        }
        int imgWidth = (int) (getImageWidth()*config.getZoom());
        int imgHeight = (int) (getImageHeight()*config.getZoom());
        rect.left +=config.getHorizontalPadding();
        rect.right-=config.getHorizontalPadding();
        rect.top +=config.getVerticalPadding();
        rect.bottom -=config.getVerticalPadding();
        switch (direction){
            case LEFT:
                this.rect.set(rect.left+(imgWidth+drawPadding),rect.top,rect.right,rect.bottom);
                textDrawFormat.draw(c,this.rect,cellInfo,config);
                int imgRight = (rect.right+rect.left)/2- textDrawFormat.measureWidth(cellInfo.column,cellInfo.row,config)/2+drawPadding;
                this.rect.set(imgRight-imgWidth,rect.top,imgRight,rect.bottom);
                super.draw(c,this.rect,cellInfo,config);
                break;
            case RIGHT:
                this.rect.set(rect.left,rect.top,rect.right-(imgWidth+drawPadding),rect.bottom);
                textDrawFormat.draw(c,this.rect,cellInfo,config);
                int imgLeft = (rect.right+rect.left)/2+ textDrawFormat.measureWidth(cellInfo.column,cellInfo.row,config)/2 + drawPadding;
                this.rect.set(imgLeft,rect.top,imgLeft+imgWidth,rect.bottom);
                super.draw(c,this.rect,cellInfo,config);
                break;
            case TOP:
                this.rect.set(rect.left,rect.top+(imgHeight+drawPadding)/2,rect.right,rect.bottom);
                textDrawFormat.draw(c,this.rect,cellInfo,config);
                int imgBottom = (rect.top+rect.bottom)/2- textDrawFormat.measureHeight(cellInfo.column,cellInfo.row,config)/2+drawPadding;
                this.rect.set(rect.left,imgBottom -imgHeight,rect.right,imgBottom);
                super.draw(c,this.rect,cellInfo,config);
                break;
            case BOTTOM:
                this.rect.set(rect.left,rect.top,rect.right,rect.bottom-(imgHeight+drawPadding)/2);
                textDrawFormat.draw(c,this.rect,cellInfo,config);
                int imgTop = (rect.top+rect.bottom)/2+ textDrawFormat.measureHeight(cellInfo.column,cellInfo.row,config)/2-drawPadding ;
                this.rect.set(rect.left,imgTop,rect.right,imgTop +imgHeight);
                super.draw(c,this.rect,cellInfo,config);
                break;

        }
    }
}
