package com.bin.david.form.component;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.bin.david.form.data.Column;
import com.bin.david.form.data.ColumnInfo;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.format.tip.ITip;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.TableClickObserver;
import com.bin.david.form.utils.DrawUtils;

import java.util.List;

/**
 * Created by huang on 2017/11/1.
 * 表格内容绘制
 */

public class TableProvider<T> implements TableClickObserver {

    private Path path;
    private Rect scaleRect;
    private Rect showRect;
    private Rect orignRect;
    private TableConfig config;
    private PointF clickPoint;
    private ColumnInfo clickColumnInfo;
    private boolean isClickPoint;
    private PointF tipPoint = new PointF();
    private Column tipColumn;
    private int tipPosition;
    private OnColumnClickListener onColumnClickListener;
    private TableData<T> tableData;
    private ITip<Column, ?> tip;
    private Rect clipRect;

    public TableProvider() {
       path = new Path();
        clickPoint = new PointF(-1, -1);
        orignRect = new Rect();
        clipRect = new Rect();
    }

    public void onDraw(Canvas canvas, Rect scaleRect, Rect showRect,
                       TableData<T> tableData, TableConfig config) {
        isClickPoint = false;
        clickColumnInfo = null;
        tipColumn = null;
        orignRect.set(showRect);
        this.scaleRect = scaleRect;
        this.showRect = showRect;
        this.config = config;
        this.tableData = tableData;
        canvas.save();
        canvas.clipRect(this.showRect);
        if (config.isFixedTitle()) {
            drawTitle(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(this.showRect);
        } else {
            drawTitle(canvas);
        }
        drawCount(canvas);
        drawContent(canvas);
        canvas.restore();
        if (isClickPoint && clickColumnInfo != null) {
            onColumnClickListener.onClick(clickColumnInfo);
        }
        if (tipColumn != null) {
            drawTip(canvas, tipPoint.x, tipPoint.y, tipColumn, tipPosition);
        }
    }

    private void drawCount(Canvas canvas) {
        if (tableData.isShowCount()) {

            int left = scaleRect.left;
            int bottom = config.isFixedCountRow() ? showRect.bottom : scaleRect.bottom;
            int countHeight = tableData.getTableInfo().getCountHeight();
            int top = bottom - countHeight;
            int backgroundColor = config.getCountBackgroundColor();
            if(backgroundColor != TableConfig.INVALID_COLOR) {
                DrawUtils.fillBackground(canvas, left, top, showRect.right,
                        bottom, backgroundColor, config.getPaint());
            }
            List<ColumnInfo> childColumnInfos = tableData.getChildColumnInfos();
            if (DrawUtils.isVerticalMixRect(showRect, top, bottom)) {
                List<Column> columns = tableData.getChildColumns();
                int columnSize = columns.size();
                boolean isPerColumnFixed = false;
                clipRect.set(showRect);
                int clipCount = 0;
                for (int i = 0; i < columnSize; i++) {
                    Column column = columns.get(i);
                    int tempLeft = left;

                    int width = (int) (column.getWidth()*config.getZoom());
                    if(childColumnInfos.get(i).getTopParent().column.isFixed()){
                        if(left < clipRect.left) {
                            left = clipRect.left;
                            clipRect.left += width;
                            isPerColumnFixed = true;
                        }
                    }else if(isPerColumnFixed){
                        canvas.save();
                        clipCount++;
                        canvas.clipRect(clipRect.left, showRect.bottom - countHeight,
                                showRect.right, showRect.bottom);
                    }
                    drawCountText(canvas, column,left, top, left+width,
                            bottom, column.getTotalNumString(), config);
                    left = tempLeft;
                    left +=width;
                }
                for(int i = 0;i < clipCount;i++){
                    canvas.restore();
                }
               /* if (config.isFixedFirstColumn()) {
                    canvas.restore();
                }*/
            }
        }
    }

    private void drawTitle(Canvas canvas) {
        int dis = showRect.top - scaleRect.top;
        TableInfo tableInfo = tableData.getTableInfo();
        int titleHeight = tableInfo.getTitleHeight() * tableInfo.getMaxLevel();
        int clipHeight = config.isFixedTitle() ? titleHeight : Math.max(0, titleHeight - dis);
        DrawUtils.fillBackground(canvas, showRect.left, showRect.top, showRect.right,
                showRect.top + clipHeight, config.getColumnTitleBackgroundColor(), config.getPaint());
        clipRect.set(showRect);
        List<ColumnInfo> columnInfoList = tableData.getColumnInfos();
        float zoom = config.getZoom();
        boolean isPerColumnFixed = false;
        int clipCount = 0;
        ColumnInfo parentColumnInfo = null;
        for (ColumnInfo info : columnInfoList) {
            int left = (int) (info.left*zoom + scaleRect.left);
            //根据top ==0是根部，根据最根部的Title判断是否需要固定
            if (info.top == 0 && info.column.isFixed()) {
                if (left < clipRect.left) {
                    parentColumnInfo = info;
                    left = clipRect.left;
                    fillColumnTitle(canvas, info, left);
                    clipRect.left += info.width * zoom;
                    isPerColumnFixed = true;
                    continue;
                }
                //根部需要固定，同时固定所有子类
            }else if(isPerColumnFixed && info.top != 0){
                    left = (int) (clipRect.left - info.width * zoom);
                    left += (info.left -parentColumnInfo.left);
            }else if(isPerColumnFixed){
                canvas.save();
                canvas.clipRect(clipRect.left, showRect.top, showRect.right,
                        showRect.top + clipHeight);
                isPerColumnFixed = false;
                clipCount++;
            }
            fillColumnTitle(canvas, info, left);
        }
        for(int i = 0;i < clipCount;i++){
            canvas.restore();
        }
        if (config.isFixedTitle()) {
            scaleRect.top += titleHeight;
            showRect.top += titleHeight;
        } else {
            showRect.top += clipHeight;
            scaleRect.top += titleHeight;
        }

    }

    private void fillColumnTitle(Canvas canvas, ColumnInfo info, int left) {

        int top = (int)(info.top*config.getZoom())
                + (config.isFixedTitle() ? showRect.top : scaleRect.top);
        int right = (int) (left + info.width *config.getZoom());
        int bottom = (int) (top + info.height*config.getZoom());

        if (DrawUtils.isMixRect(showRect, left, top, right, bottom)) {
            if (!isClickPoint && onColumnClickListener != null) {
                if (DrawUtils.isClick(left, top, right, bottom, clickPoint)) {
                    isClickPoint = true;
                    clickColumnInfo = info;
                    clickPoint.set(-1, -1);
                }
            }
            Paint paint = config.getPaint();
           tableData.getTitleDrawFormat().draw(canvas, info.column, left, top, right, bottom, config);
            config.getColumnTitleGridStyle().fillPaint(paint);
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }


    private void drawContent(Canvas canvas) {
        int top;
        int left = scaleRect.left;
        Paint paint = config.getPaint();
        List<Column> columns = tableData.getChildColumns();
        clipRect.set(showRect);
        TableInfo info = tableData.getTableInfo();
        int columnSize = columns.size();
        int dis = config.isFixedCountRow() ? info.getCountHeight()
                : showRect.bottom + info.getCountHeight() - scaleRect.bottom;
        int fillBgBottom = showRect.bottom - Math.max(dis, 0);
        DrawUtils.fillBackground(canvas, showRect.left, showRect.top, showRect.right,
                fillBgBottom, config.getContentBackgroundColor(), config.getPaint());
        if (config.isFixedCountRow()) {
            canvas.save();
            canvas.clipRect(showRect.left, showRect.top, showRect.right, showRect.bottom - info.getCountHeight());
        }
        List<ColumnInfo> childColumnInfo = tableData.getChildColumnInfos();
        boolean isPerFixed = false;
        int clipCount = 0;
        for (int i = 0; i < columnSize; i++) {
            top = scaleRect.top;
            Column column = columns.get(i);
            int width = (int) (column.getWidth()*config.getZoom());
            List<String> values = column.getValues();
            int tempLeft = left;
            //根据根部标题是否固定
            Column topColumn = childColumnInfo.get(i).getTopParent().column;
            if (topColumn.isFixed()) {
                isPerFixed = false;
                if(tempLeft < clipRect.left){
                    left = clipRect.left;
                    clipRect.left +=width;
                    isPerFixed = true;
                }
            }else if(isPerFixed){
                canvas.save();
                canvas.clipRect(clipRect);
                isPerFixed = false;
               clipCount++;
            }
            int right = left + width;
            if (left < showRect.right) {
                for (int j = 0; j < values.size(); j++) {
                    String value = values.get(j);
                    int bottom = (int) (top + info.getLineHeightArray()[j]*config.getZoom());
                    if (top < showRect.bottom) {
                        if (right > showRect.left && bottom > showRect.top) {
                            Object data = column.getDatas().get(j);
                            if (DrawUtils.isClick(left, top, right, bottom, clickPoint)) {
                                tipPoint.x = (left + right) / 2;
                                tipPoint.y = (top + bottom) / 2;
                                tipColumn = column;
                                tipPosition = j;
                                if (!isClickPoint && column.getOnColumnItemClickListener() != null) {
                                    column.getOnColumnItemClickListener().onClick(column, value, data, j);
                                }
                                isClickPoint = true;
                                clickPoint.set(-1, -1);
                            }
                            column.getDrawFormat().draw(canvas,column, data, value, left, top, right, bottom, j, config);
                        }
                    } else {
                        break;
                    }
                    if (i == 0) {
                        config.getGridStyle().fillPaint(paint);
                        drawHorizontalGrid(canvas, Math.max(scaleRect.left, showRect.left),
                                top, Math.min(scaleRect.right, showRect.right), bottom, paint);
                    }
                    top = bottom;
                }
                config.getGridStyle().fillPaint(paint);
                drawVerticalGrid(canvas, left, Math.max(scaleRect.top, showRect.top)
                        , right, showRect.bottom, paint);
                left = tempLeft + width;
            } else {
                break;
            }
        }
        for(int i = 0;i < clipCount;i++){
            canvas.restore();
        }
        if (config.isFixedCountRow()) {
            canvas.restore();
        }
    }

    private void drawHorizontalGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {
        path.rewind();
        path.moveTo(left, bottom);
        path.lineTo(right, bottom);
        canvas.drawPath(path, paint);
    }

    private void drawVerticalGrid(Canvas canvas, int left, int top, int right, int bottom, Paint paint) {
        path.rewind();
        path.moveTo(left, top);
        path.lineTo(left, bottom);
        path.rewind();
        path.moveTo(right, top);
        path.lineTo(right, bottom);
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制提示
     */
    void drawTip(Canvas canvas, float x, float y, Column c, int position) {
        if (tip != null) {
            tip.drawTip(canvas, x, y, orignRect, c, position);
        }
    }

    private void drawCountText(Canvas canvas,Column column, int left, int top, int right, int bottom, String text, TableConfig config) {
        Paint paint = config.getPaint();
       /* path.rewind();
        path.moveTo(left, top);
        path.lineTo(left, bottom);
        path.lineTo(right, bottom);
        path.lineTo(right, top);
        path.close();*/
        //绘制背景
        IBackgroundFormat<Column> backgroundFormat = config.getCountBgFormat();
        boolean isDrawBg = false;
        if(backgroundFormat != null&& backgroundFormat.isDraw(column)){
            backgroundFormat.drawBackground(canvas,left,top,right,bottom,config.getPaint());
            isDrawBg = true;
        }
        config.getGridStyle().fillPaint(paint);
        canvas.drawRect(left,top,right,bottom, paint);
        config.getCountStyle().fillPaint(paint);
        //字体颜色跟随背景变化
        if(isDrawBg && backgroundFormat.getTextColor(column) != TableConfig.INVALID_COLOR){
            paint.setColor(backgroundFormat.getTextColor(column));
        }
        paint.setTextSize(paint.getTextSize()*config.getZoom());
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, (right + left) / 2, DrawUtils.getTextCenterY((bottom + top) / 2, paint), paint);
    }


    @Override
    public void onClick(float x, float y) {
        clickPoint.x = x;
        clickPoint.y = y;
    }

    public OnColumnClickListener getOnColumnClickListener() {
        return onColumnClickListener;
    }

    public void setOnColumnClickListener(OnColumnClickListener onColumnClickListener) {
        this.onColumnClickListener = onColumnClickListener;
    }

    public ITip<Column, ?> getTip() {
        return tip;
    }

    public void setTip(ITip<Column, ?> tip) {
        this.tip = tip;
    }


}
