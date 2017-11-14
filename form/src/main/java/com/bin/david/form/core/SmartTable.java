package com.bin.david.form.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bin.david.form.annotation.AnnotationParser;
import com.bin.david.form.component.IComponent;
import com.bin.david.form.component.ITableTitle;
import com.bin.david.form.component.TableProvider;
import com.bin.david.form.component.TableTitle;
import com.bin.david.form.component.XSequence;
import com.bin.david.form.component.YSequence;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnTableChangeListener;
import com.bin.david.form.matrix.MatrixHelper;

import java.util.List;

/**
 * Created by huang on 2017/10/30.
 * 表格
 */

public class SmartTable<T> extends View  implements OnTableChangeListener {

    private XSequence<T> xAxis;
    private YSequence<T> yAxis;
    private ITableTitle tableTitle;
    private Rect showRect;
    private Rect tableRect;
    private TableConfig config;
    private TableParser<T> parser;
    private TableData<T> tableData;
    private TableProvider<T> provider;
    private TableMeasurer<T> measurer;
    private AnnotationParser<T> annotationParser;
    protected Paint paint;
    private MatrixHelper matrixHelper;
    public SmartTable(Context context) {
        super(context);
        init();
    }

    public SmartTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        FontStyle.setDefaultTextSize(14);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        showRect = new Rect();
        tableRect = new Rect();
         config = new TableConfig();
         xAxis = new XSequence<>();
         yAxis = new YSequence<>();
        parser = new TableParser<>();
        provider = new TableProvider<>();
        config.setPaint(paint);
        measurer = new TableMeasurer<>();
        tableTitle = new TableTitle();
        tableTitle.setDirection(IComponent.TOP);
        matrixHelper = new MatrixHelper(getContext());
        matrixHelper.setOnTableChangeListener(this);
        matrixHelper.register(provider);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        showRect.set(0,0,getWidth(),getHeight());
        if(tableData != null) {
            Rect rect = tableData.getTableInfo().getTableRect();
            if(rect != null) {
                measurer.measureTableTitle(tableData,tableTitle,showRect);
                tableRect.set(rect);
                Rect scaleRect = matrixHelper.getZoomProviderRect(showRect,tableRect,measurer.getHeadHeight(tableData));
                tableTitle.computeRect(scaleRect,showRect,config);
                tableTitle.draw(canvas,showRect, tableData.getTableName(), config);
                config.getGridStyle().fillPaint(paint);
                canvas.drawRect(showRect,paint);
                yAxis.computeRect(scaleRect,showRect,config);
                yAxis.draw(canvas,showRect, tableData, config);
                xAxis.computeRect(scaleRect,showRect,config);
                xAxis.draw(canvas, showRect,tableData, config);
                provider.draw(canvas, scaleRect,showRect,tableData, config);
            }
        }

    }

    public TableConfig getConfig() {
        return config;
    }

    public void setData(List<T> data){
        if(annotationParser == null){
            annotationParser = new AnnotationParser<>();
        }
        TableData<T> tableData = annotationParser.parse(data);
        if(tableData != null) {
            setTableData(tableData);
        }
    }


    public void setTableData(TableData<T> tableData){
        this.tableData = tableData;
        config.setPaint(paint);
        parser.parse(tableData,config);
        TableInfo info = measurer.measure(tableData,config);
        xAxis.setHeight(info.getTopHeight());
        yAxis.setWidth(info.getyAxisWidth());
        invalidate();
    }

    /**
     *将触摸事件交给Iouch处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return matrixHelper.handlerTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        matrixHelper.onDisallowInterceptEvent(this,event);
        return super.dispatchTouchEvent(event);
    }




    @Override
    public void onTableChanged(float scale, float translateX, float translateY) {
        if(tableData != null) {
            config.setZoom(scale);
            tableData.getTableInfo().setZoom(scale);
            invalidate();
        }
    }

    public OnColumnClickListener getOnColumnClickListener() {
        return provider.getOnColumnClickListener();
    }

    public void setOnColumnClickListener(OnColumnClickListener onColumnClickListener) {
        this.provider.setOnColumnClickListener(onColumnClickListener);
    }

    public void setSortColumn(Column column,boolean isReverse){
        if(tableData != null&& column !=null){
            column.setReverseSort(isReverse);
            tableData.setSortColumn(column);
            setTableData(tableData);
            invalidate();
        }
    }

    public Rect getShowRect() {
        return showRect;
    }


    public TableProvider<T> getProvider() {
        return provider;
    }

    public TableData<T> getTableData() {
        return tableData;
    }

    public boolean isZoom() {
        return matrixHelper.isCanZoom();
    }

    public void setZoom(boolean zoom,int maxScale) {

        matrixHelper.setMaxZoom(maxScale);
        matrixHelper.setCanZoom(zoom);
        invalidate();

    }
}

