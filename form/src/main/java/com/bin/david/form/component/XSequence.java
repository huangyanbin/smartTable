package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.TableData;
import com.bin.david.form.data.format.sequence.ISequenceFormat;
import com.bin.david.form.utils.DrawUtils;

import java.util.List;

/**
 * Created by huang on 2017/10/30.
 * 顶部横向排版
 */

public class XSequence<T> implements IComponent<TableData<T>>{

    private Rect rect;
    private int height;
    private int clipHeight;
    private ISequenceFormat format;

    public XSequence() {
        this.rect = new Rect();
    }

    @Override
    public void onMeasure(Rect scaleRect, Rect showRect, TableConfig config) {
        boolean fixed = config.isFixedXSequence();
        int scaleHeight = (int) (config.getZoom() *height);
        rect.top = fixed ? showRect.top: scaleRect.top;
        rect.bottom = rect.top+ scaleHeight;
        rect.left = scaleRect.left;
        rect.right = scaleRect.right;
        if(fixed){
            scaleRect.top += scaleHeight;
            showRect.top +=scaleHeight;
            clipHeight = scaleHeight;
        }else{
            int dis = showRect.top - scaleRect.top;
            clipHeight=Math.max(0, scaleHeight-dis);
            showRect.top +=clipHeight;
            scaleRect.top += scaleHeight;
        }

    }

    @Override
    public void onDraw(Canvas canvas, Rect showRect, TableData<T> tableData, TableConfig config) {

        format = tableData.getXSequenceFormat();
        List<Column> columns = tableData.getChildColumns();
        int columnSize = columns.size();
        int left  = rect.left;
        int showTop = showRect.top-clipHeight;
        canvas.save();
        canvas.clipRect(showRect.left,showTop,showRect.right, showRect.top);
        DrawUtils.fillBackground(canvas, showRect.left, showTop, showRect.right, showRect.top,
                config.getXSequenceBackgroundColor(),config.getPaint());
        //测试
        //config.getPaint().setColor(Color.BLACK);
        //canvas.drawRect(showRect.left,clipTop,showRect.right,clipTop+rect.height(),config.getPaint());
        for(int i = 0;i < columnSize;i++){
            Column column = columns.get(i);
            int width = (int) (column.getWidth()*config.getZoom());
            int right = left + width;
            if(config.isFixedFirstColumn() && i ==0){
                showTextNum(canvas, showRect, config, showRect.left, 0, showRect.left+width);
                left +=width;
                canvas.save();
                canvas.clipRect(showRect.left+width,rect.top,showRect.right,rect.bottom);
                continue;
            }
            if(showRect.right >= left) {
                left = showTextNum(canvas, showRect, config, left, i, right);
            }else {
                break;
            }
        }
        if(config.isFixedFirstColumn()){
            canvas.restore();
        }
        canvas.restore();
    }

    private int showTextNum(Canvas canvas, Rect showRect, TableConfig config, int left, int i, int right) {
        if(DrawUtils.isMixHorizontalRect(showRect,left,right)) {
            String text = format.format(i+1);
            draw(canvas, left, rect.top,right, rect.bottom, text, config);
        }
        left = right;
        return left;
    }

    private void draw(Canvas canvas,int left,int top, int right,int bottom,String text,TableConfig config){
        Paint paint= config.getPaint();
        config.getGridStyle().fillPaint(paint);
        canvas.drawRect(left,top,right,bottom,paint);
        config.getXSequenceStyle().fillPaint(paint);
        paint.setTextSize(paint.getTextSize()*config.getZoom());
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text,(right +left)/2, DrawUtils.getTextCenterY((bottom+top)/2,paint) ,paint);
    }


    public Rect getRect() {
        return rect;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
