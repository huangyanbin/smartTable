package com.bin.david.form.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bin.david.form.data.Column;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.ColumnInfo;
import com.bin.david.form.data.TableData;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
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
    private Rect clipRect;

    public XSequence() {
        this.rect = new Rect();
        clipRect = new Rect();
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
        clipRect.set(showRect);
        boolean isPerColumnFixed = false;
        int clipCount = 0;
        List<ColumnInfo> childColumnInfos = tableData.getChildColumnInfos();
        for(int i = 0;i < columnSize;i++){
            Column column = columns.get(i);
            int width = (int) (column.getWidth()*config.getZoom());
            int right = left + width;
            if(childColumnInfos.get(i).getTopParent().column.isFixed()){
                if(left < clipRect.left) {
                    isPerColumnFixed = true;
                    showTextNum(canvas, showRect, config, clipRect.left, i, clipRect.left + width);
                    clipRect.left += width;
                    left += width;
                    continue;
                }
            }else if(isPerColumnFixed){
                    isPerColumnFixed = false;
                    clipCount++;
                    canvas.save();
                    canvas.clipRect(clipRect.left,rect.top,showRect.right,rect.bottom);
            }
            if(showRect.right >= left) {
                left = showTextNum(canvas, showRect, config, left, i, right);
            }else {
                break;
            }
        }
        for(int i = 0;i < clipCount;i++){
            canvas.restore();
        }
        canvas.restore();
    }

    private int showTextNum(Canvas canvas, Rect showRect, TableConfig config, int left, int i, int right) {
        if(DrawUtils.isMixHorizontalRect(showRect,left,right)) {
            String text = format.format(i+1);
            draw(canvas, left, rect.top,right, rect.bottom,text,i, config);
        }
        left = right;
        return left;
    }

    private void draw(Canvas canvas,int left,int top, int right,int bottom,String text,int position,TableConfig config){
        Paint paint= config.getPaint();
        config.getGridStyle().fillPaint(paint);
        canvas.drawRect(left,top,right,bottom,paint);
        //绘制背景
        IBackgroundFormat<Integer> backgroundFormat = config.getXSequenceBgFormat();
        boolean isDrawBg = false;
        if(backgroundFormat != null&& backgroundFormat.isDraw(position)){
            backgroundFormat.drawBackground(canvas,left,top,right,bottom,config.getPaint());
            isDrawBg = true;
        }
        config.getXSequenceStyle().fillPaint(paint);
        //字体颜色跟随背景变化
        if(isDrawBg && backgroundFormat.getTextColor(position) != TableConfig.INVALID_COLOR){
            paint.setColor(backgroundFormat.getTextColor(position));
        }
        //字体缩放
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
