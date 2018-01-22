package com.bin.david.form.data.format.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;

/**
 * Created by huang on 2017/10/30.
 * 多行文字格式化
 */

public class MultiLineDrawFormat<T> extends TextDrawFormat<T> {

    private int width;
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 多行文字格式化构造方法
     *
     * @param width 指定宽度 px
     */
    public  MultiLineDrawFormat(int width) {
        this.width = width;
    }

    /**
     * 多行文字格式化构造方法
     *
     * @param dpWidth 指定宽度 dp
     */
    public  MultiLineDrawFormat(Context context,int dpWidth) {
        this.width = DensityUtils.dp2px(context,dpWidth);
    }

    @Override
    public int measureWidth(Column<T> column,int position, TableConfig config) {

        return width;
    }

    @Override
    public int measureHeight(Column<T> column, int position, TableConfig config) {
        config.getContentStyle().fillPaint(textPaint);
        StaticLayout sl = new StaticLayout(column.format(position), textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        return sl.getHeight();
    }

    @Override
    public void draw(Canvas c, Column<T> column, T t, String value, Rect rect, int position, TableConfig config) {
        CellInfo<T> cellInfo = getCellInfo();
        cellInfo.set(column, t, value, position);
        drawBackground(c, cellInfo, rect, config);
        setTextPaint(config, t, textPaint);
        if(column.getTextAlign() !=null) {
            textPaint.setTextAlign(column.getTextAlign());
        }
        int hPadding = (int) (config.getHorizontalPadding()*config.getZoom());
        int vPadding = (int) (config.getVerticalPadding()*config.getZoom());
        int realWidth =rect.width() - 2*hPadding;
        StaticLayout staticLayout = new StaticLayout(column.format(position), textPaint, realWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        c.save();
        c.translate(DrawUtils.getTextCenterX(rect.left+hPadding,rect.right-hPadding,textPaint), rect.top+vPadding);
        staticLayout.draw(c);
        c.restore();
    }
}



