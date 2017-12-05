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
import com.bin.david.form.data.PageTableData;
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
    private TableProvider<T> provider;
    private Rect showRect;
    private Rect tableRect;
    private TableConfig config;
    private TableParser<T> parser;
    private TableData<T> tableData;

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

    /**
     * 初始化
     */
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
                tableTitle.onMeasure(scaleRect,showRect,config);
                tableTitle.onDraw(canvas,showRect, tableData.getTableName(), config);
                drawGridBackground(canvas);
                if(config.isShowYSequence()) {
                    yAxis.onMeasure(scaleRect, showRect, config);
                    yAxis.onDraw(canvas, showRect, tableData, config);
                }
                if(config.isShowXSequence()) {
                    xAxis.onMeasure(scaleRect, showRect, config);
                    xAxis.onDraw(canvas, showRect, tableData, config);

                }
                provider.onDraw(canvas, scaleRect, showRect, tableData, config);
            }
        }

    }

    /**
     * 绘制网格背景
     * @param canvas
     */
    private void drawGridBackground(Canvas canvas) {
        config.getGridStyle().fillPaint(paint);
        canvas.drawRect(showRect,paint);
    }

    public TableConfig getConfig() {
        return config;
    }

    /**
     * 设置解析数据
     * @param data 表格数据
     */
    public PageTableData<T> setData(List<T> data){
        if(annotationParser == null){
            annotationParser = new AnnotationParser<>();
        }
        PageTableData<T> tableData = annotationParser.parse(data);
        if(tableData != null) {
            setTableData(tableData);
        }
        return tableData;
    }



    /**
     * 设置表格数据
     * @param tableData
     */
    public void setTableData(TableData<T> tableData){
        this.tableData = tableData;
        notifyDataChanged();
    }

    /**
     * 通知更新
     */
    public void notifyDataChanged(){
        if(tableData != null) {
            config.setPaint(paint);
            parser.parse(tableData, config);
            TableInfo info = measurer.measure(tableData, config);
            xAxis.setHeight(info.getTopHeight());
            yAxis.setWidth(info.getyAxisWidth());
            invalidate();
        }
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

    public void addData(List<T> t,boolean isFoot){
        if(t != null && t.size() >0) {
            int size = tableData.getT().size();
            parser.addData(tableData, t,isFoot,config);
            measurer.addTableHeight(tableData, size);
        }
        invalidate();
    }

    /**
     * 滚动到原点
     */
    public void end(){
        matrixHelper.flingEnd(showRect,tableRect);
    }
    /**
     * 滚动到原点
     */
    public void back(){
        matrixHelper.flingBack();
    }
}

