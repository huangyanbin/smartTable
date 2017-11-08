package com.bin.david.form.component;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

import com.bin.david.form.data.Column;
import com.bin.david.form.data.ColumnInfo;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.TableData;
import com.bin.david.form.data.TableInfo;
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

    public TableProvider() {
        path = new Path();
        clickPoint = new PointF(-1, -1);
        orignRect = new Rect();
    }

    public void draw(Canvas canvas, Rect scaleRect, Rect showRect,
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
            int right;
            int bottom = config.isFixedCountRow() ? showRect.bottom : scaleRect.bottom;
            int countHeight = tableData.getTableInfo().getCountHeight();
            int top = bottom - countHeight;
            int backgroundColor = config.getCountBackgroundColor();
            DrawUtils.fillBackground(canvas, left, top, showRect.right,
                    bottom, backgroundColor, config.getPaint());
            if (DrawUtils.isVerticalMixRect(showRect, top, bottom)) {
                List<Column> columns = tableData.getChildColumns();
                int columnSize = columns.size();
                Column firstColumn = tableData.getChildColumns().get(0);
                boolean isFixedFirstColumn = false;
                for (int i = 0; i < columnSize; i++) {
                    Column column = columns.get(i);
                    right = (int) (left + column.getWidth()*config.getZoom());
                    if (config.isFixedFirstColumn() && !isFixedFirstColumn
                            && firstColumn == column) {
                        isFixedFirstColumn = true;
                        int tempRight = showRect.left + column.getWidth();
                        drawText(canvas, showRect.left, top, tempRight,
                                bottom, column.getTotalNumString(), config);
                        canvas.save();
                        canvas.clipRect(tempRight, showRect.bottom - countHeight, showRect.right, showRect.bottom);
                        left = right;
                        continue;
                    }
                    drawText(canvas, left, top, right, bottom, column.getTotalNumString(), config);
                    left = right;

                }
                if (config.isFixedFirstColumn()) {
                    canvas.restore();
                }
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

        List<ColumnInfo> columnInfoList = tableData.getColumnInfos();
        Column firstColumn = tableData.getChildColumns().get(0);
        boolean isFixedFirstColumn = false;
        float zoom = config.getZoom();
        for (ColumnInfo info : columnInfoList) {
            if (config.isFixedFirstColumn() && !isFixedFirstColumn
                    && firstColumn == info.column) {
                isFixedFirstColumn = true;
                int left = (int) (info.left*zoom + showRect.left);
                fillColumnTitle(canvas, info, left);
                canvas.save();
                canvas.clipRect(left + info.width*zoom, showRect.top, showRect.right,
                        showRect.top + clipHeight);
                continue;
            }
            int left = (int) (info.left*zoom + scaleRect.left);
            fillColumnTitle(canvas, info, left);
        }
        if (config.isFixedFirstColumn()) {
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
            path.rewind();
            path.moveTo(left, top);
            path.lineTo(left, bottom);
            path.lineTo(right, bottom);
            path.lineTo(right, top);
            path.close();
            config.getColumnTitleGridStyle().fillPaint(paint);
            canvas.drawPath(path, paint);
            tableData.getTitleDrawFormat().draw(canvas, info.column, left, top, right, bottom, config);
        }
    }


    private void drawContent(Canvas canvas) {
        int top;
        int left = scaleRect.left;
        Paint paint = config.getPaint();
        List<Column> columns = tableData.getChildColumns();
        boolean isFixedFirst = config.isFixedFirstColumn();
        TableInfo info = tableData.getTableInfo();
        int columnSize = columns.size();
        int firstWidth = 0;
        int dis = config.isFixedCountRow() ? info.getCountHeight()
                : showRect.bottom + info.getCountHeight() - scaleRect.bottom;
        int fillBgBottom = showRect.bottom - Math.max(dis, 0);
        DrawUtils.fillBackground(canvas, showRect.left, showRect.top, showRect.right,
                fillBgBottom, config.getContentBackgroundColor(), config.getPaint());
        if (config.isFixedCountRow()) {
            canvas.save();
            canvas.clipRect(showRect.left, showRect.top, showRect.right, showRect.bottom - info.getCountHeight());
        }
        for (int i = 0; i < columnSize; i++) {
            top = scaleRect.top;
            Column column = columns.get(i);
            int width = (int) (column.getWidth()*config.getZoom());
            List<String> values = column.getValues();
            int tempLeft = left;
            if (i == 0 && isFixedFirst) {
                left = showRect.left;
                firstWidth = width;
            }
            if (i == 1 && isFixedFirst) {
                canvas.save();
                showRect.left += firstWidth;
                canvas.clipRect(showRect);
            }
            int right = left + width;
            if (left < showRect.right) {
                config.getGridStyle().fillPaint(paint);
                drawVerticalGrid(canvas, left, Math.max(scaleRect.top, showRect.top)
                        , right, showRect.bottom, paint);
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
                            column.getDrawFormat().draw(canvas, data, value, left, top, right, bottom, j, config);
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
                left = tempLeft + width;
            } else {
                break;
            }
        }
        if (columnSize > 1 && isFixedFirst) {
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

    private void drawText(Canvas canvas, int left, int top, int right, int bottom, String text, TableConfig config) {
        Paint paint = config.getPaint();
        path.rewind();
        path.moveTo(left, top);
        path.lineTo(left, bottom);
        path.lineTo(right, bottom);
        path.lineTo(right, top);
        path.close();
        config.getColumnTitleGridStyle().fillPaint(paint);
        canvas.drawPath(path, paint);
        config.getColumnTitleStyle().fillPaint(paint);
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
