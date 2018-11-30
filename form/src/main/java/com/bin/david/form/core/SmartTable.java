package com.bin.david.form.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bin.david.form.component.IComponent;
import com.bin.david.form.component.ITableTitle;
import com.bin.david.form.component.TableProvider;
import com.bin.david.form.component.TableTitle;
import com.bin.david.form.component.XSequence;
import com.bin.david.form.component.YSequence;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.PageTableData;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.data.TableInfo;
import com.bin.david.form.data.format.selected.ISelectFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnTableChangeListener;
import com.bin.david.form.matrix.MatrixHelper;
import com.bin.david.form.utils.DensityUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by huang on 2017/10/30.
 * 表格
 */

public class SmartTable<T> extends View implements NestedScrollingChild2, OnTableChangeListener {

    static final String TAG = "SmartTable";

    private static final int INVALID_POINTER = -1;

    private XSequence<T> xAxis;
    private YSequence<T> yAxis;
    private ITableTitle tableTitle;
    private TableProvider<T> provider;
    private Rect showRect;
    private Rect tableRect;
    private TableConfig config;
    private TableParser<T> parser;
    private TableData<T> tableData;
    private int defaultHeight = 300;
    private int defaultWidth = 300;
    private TableMeasurer<T> measurer;
    private AnnotationParser<T> annotationParser;
    protected Paint paint;
    private MatrixHelper matrixHelper;
    private boolean isExactly = true; //是否是测量精准模式
    private AtomicBoolean isNotifying = new AtomicBoolean(false); //是否正在更新数据
    private boolean isYSequenceRight;

    private boolean isCeiling;
    private int mActivePointerId = INVALID_POINTER;
    private int mLastX;
    private int mLastY;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private int mNestedOffsetY;
    private NestedScrollingChildHelper mScrollingChildHelper;

    public SmartTable(Context context) {
        this(context, null);
    }

    public SmartTable(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }

    /**
     * 初始化
     */
    private void init() {
        FontStyle.setDefaultTextSpSize(getContext(), 13);
        config = new TableConfig();
        config.dp10 = DensityUtils.dp2px(getContext(), 10);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        showRect = new Rect();
        tableRect = new Rect();
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
        matrixHelper.setOnInterceptListener(provider.getOperation());

    }

    /**
     * @param ceiling true：吸顶
     */
    public void setCeiling(boolean ceiling) {
        isCeiling = ceiling;
    }

    /**
     * 绘制
     * 首先通过计算的table大小，计算table title大小
     * 再通过 matrixHelper getZoomProviderRect计算实现缩放和位移的Rect
     * 再绘制背景
     * 绘制XY序号列
     * 最后绘制内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isNotifying.get()) {
            setScrollY(0);
            showRect.set(getPaddingLeft(), getPaddingTop(),
                    getWidth() - getPaddingRight(),
                    getHeight() - getPaddingBottom());
            if (tableData != null) {
                Rect rect = tableData.getTableInfo().getTableRect();
                if (rect != null) {
                    if (config.isShowTableTitle()) {
                        measurer.measureTableTitle(tableData, tableTitle, showRect);
                    }
                    tableRect.set(rect);
                    Rect scaleRect = matrixHelper.getZoomProviderRect(showRect, tableRect,
                            tableData.getTableInfo());
                    if (config.isShowTableTitle()) {
                        tableTitle.onMeasure(scaleRect, showRect, config);
                        tableTitle.onDraw(canvas, showRect, tableData.getTableName(), config);
                    }
                    drawGridBackground(canvas, showRect, scaleRect);
                    if (config.isShowYSequence()) {
                        yAxis.onMeasure(scaleRect, showRect, config);
                        if (isYSequenceRight) {
                            canvas.save();
                            canvas.translate(showRect.width(), 0);
                            yAxis.onDraw(canvas, showRect, tableData, config);
                            canvas.restore();
                        } else {
                            yAxis.onDraw(canvas, showRect, tableData, config);
                        }
                    }
                    if (config.isShowXSequence()) {
                        xAxis.onMeasure(scaleRect, showRect, config);
                        xAxis.onDraw(canvas, showRect, tableData, config);
                    }
                    if (isYSequenceRight) {
                        canvas.save();
                        canvas.translate(-yAxis.getWidth(), 0);
                        provider.onDraw(canvas, scaleRect, showRect, tableData, config);
                        canvas.restore();
                    } else {
                        provider.onDraw(canvas, scaleRect, showRect, tableData, config);
                    }
                }
            }
        }
    }

    /**
     * 绘制表格边框背景
     *
     * @param canvas
     */
    private void drawGridBackground(Canvas canvas, Rect showRect, Rect scaleRect) {
        config.getContentGridStyle().fillPaint(paint);
        if (config.getTableGridFormat() != null) {
            config.getTableGridFormat().drawTableBorderGrid(canvas, Math.max(showRect.left, scaleRect.left),
                    Math.max(showRect.top, scaleRect.top),
                    Math.min(showRect.right, scaleRect.right),
                    Math.min(scaleRect.bottom, showRect.bottom), paint);
        }
    }

    /**
     * 获取表格配置
     * 可以使用TableConfig进行样式的配置，包括颜色，是否固定，开启统计行等
     *
     * @return 表格配置
     */
    public TableConfig getConfig() {
        return config;
    }

    /**
     * 设置解析数据
     *
     * @param data 表格数据
     */
    public PageTableData<T> setData(List<T> data) {
        if (annotationParser == null) {
            annotationParser = new AnnotationParser<>(config.dp10);
        }
        PageTableData<T> tableData = annotationParser.parse(data);
        if (tableData != null) {
            setTableData(tableData);
        }
        return tableData;
    }


    /**
     * 设置表格数据
     *
     * @param tableData
     */
    public void setTableData(TableData<T> tableData) {
        if (tableData != null) {
            this.tableData = tableData;
            notifyDataChanged();
        }
    }

    public ITableTitle getTableTitle() {
        return tableTitle;
    }


    /**
     * 通知更新
     */
    public void notifyDataChanged() {

        if (tableData != null) {
            config.setPaint(paint);
            //开启线程
            isNotifying.set(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //long start = System.currentTimeMillis();
                    parser.parse(tableData);
                    TableInfo info = measurer.measure(tableData, config);
                    xAxis.setHeight(info.getTopHeight());
                    yAxis.setWidth(info.getyAxisWidth());
                    requestReMeasure();
                    postInvalidate();
                    isNotifying.set(false);
                    //long end = System.currentTimeMillis();
                    //Log.e("smartTable","notifyDataChanged timeMillis="+(end-start));
                }

            }).start();

        }
    }

    /**
     * 添加数据
     * 通过这个方法可以实现动态添加数据，参数isFoot可以实现首尾添加
     *
     * @param t      新增数据
     * @param isFoot 是否在尾部添加
     */
    public void addData(final List<T> t, final boolean isFoot) {
        if (t != null && t.size() > 0) {
            isNotifying.set(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    parser.addData(tableData, t, isFoot);
                    measurer.measure(tableData, config);
                    requestReMeasure();
                    postInvalidate();
                    isNotifying.set(false);

                }
            }).start();
        }
    }


    /**
     * 通知重绘
     * 增加锁机制，避免闪屏和数据更新异常
     */
    @Override
    public void invalidate() {
        if (!isNotifying.get()) {
            super.invalidate();
        }

    }

    /**
     * 通知重新测量大小
     */
    private void requestReMeasure() {
        //不是精准模式 且已经测量了
        if (!isExactly && getMeasuredHeight() != 0 && tableData != null) {
            if (tableData.getTableInfo().getTableRect() != null) {
                int defaultHeight = tableData.getTableInfo().getTableRect().height()
                        + getPaddingTop();
                int defaultWidth = tableData.getTableInfo().getTableRect().width();
                int[] realSize = new int[2];
                getLocationInWindow(realSize);
                DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
                int screenWidth = dm.widthPixels;
                int screenHeight = dm.heightPixels;
                int maxWidth = screenWidth - realSize[0];
                int maxHeight = screenHeight - realSize[1];
                defaultHeight = Math.min(defaultHeight, maxHeight);
                defaultWidth = Math.min(defaultWidth, maxWidth);
                //Log.e("SmartTable","old defaultHeight"+this.defaultHeight+"defaultWidth"+this.defaultWidth);
                if (this.defaultHeight != defaultHeight
                        || this.defaultWidth != defaultWidth) {
                    this.defaultHeight = defaultHeight;
                    this.defaultWidth = defaultWidth;
                    // Log.e("SmartTable","new defaultHeight"+defaultHeight+"defaultWidth"+defaultWidth);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            requestLayout();
                        }
                    });

                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        requestReMeasure();
    }

    /**
     * 计算组件宽度
     */

    private int measureWidth(int widthMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//精确模式
            result = specSize;
        } else {
            isExactly = false;
            result = defaultWidth;//最大尺寸模式，getDefaultWidth方法需要我们根据控件实际需要自己实现
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 计算组件高度
     */

    private int measureHeight(int measureSpec) {

        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            isExactly = false;
            result = defaultHeight;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 将触摸事件交给Iouch处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean returnValue = false;

        MotionEvent event = MotionEvent.obtain(ev);
        final int actionMasked = ev.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mNestedOffsetY = 0;
        }
        event.offsetLocation(0, mNestedOffsetY);
        switch (actionMasked) {
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e(TAG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }
                int eventX = (int) ev.getX(activePointerIndex);
                int eventY = (int) ev.getY(activePointerIndex);
                int deltaX = mLastX - eventX;
                int deltaY = mLastY - eventY;

                // NestedPreScroll
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1];
                    mLastY = eventY - mScrollOffset[1];
                    event.offsetLocation(0, mScrollOffset[1]);
                    mNestedOffsetY += mScrollOffset[1];
                }
                // NestedScroll
                if (dispatchNestedScroll(0, mScrollOffset[1], 0, deltaY, mScrollOffset)) {
                    if (isCeiling) {
                        event.offsetLocation(0, mScrollOffset[1]);
                        mNestedOffsetY += mScrollOffset[1];
                        mLastY -= mScrollOffset[1];
                    }
                }

                returnValue = matrixHelper.handlerTouchEvent(event);
                break;
            case MotionEvent.ACTION_DOWN:
                returnValue = matrixHelper.handlerTouchEvent(event);
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                mActivePointerId = ev.getPointerId(0);
                // start NestedScroll
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_UP:
                mActivePointerId = INVALID_POINTER;
                returnValue = matrixHelper.handlerTouchEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                returnValue = matrixHelper.handlerTouchEvent(event);
                // end NestedScroll
                stopNestedScroll();
                break;
        }
        return returnValue;
    }

    /**
     * 分发事件
     * 在这里会去调用MatrixHelper onDisallowInterceptEvent方法
     * 判断是否阻止parent拦截自己的事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        matrixHelper.onDisallowInterceptEvent(this, event);
        return super.dispatchTouchEvent(event);
    }


    /**
     * 表格移动缩放改变回调
     *
     * @param scale      缩放值
     * @param translateX X位移值
     * @param translateY Y位移值
     */
    @Override
    public void onTableChanged(float scale, float translateX, float translateY) {
        if (tableData != null) {
            config.setZoom(scale);
            tableData.getTableInfo().setZoom(scale);
            invalidate();
        }
    }

    /**
     * 获取列点击事件
     */
    public OnColumnClickListener getOnColumnClickListener() {
        return provider.getOnColumnClickListener();
    }

    /**
     * 设置列点击事件,实现对列的监听
     *
     * @param onColumnClickListener 列点击事件
     */
    public void setOnColumnClickListener(OnColumnClickListener onColumnClickListener) {
        this.provider.setOnColumnClickListener(onColumnClickListener);
    }

    /**
     * 列排序
     * 你可以调用这个方法，对所有数据进行排序，排序根据设置的column排序
     *
     * @param column    列
     * @param isReverse 是否反序
     */
    public void setSortColumn(Column column, boolean isReverse) {
        if (tableData != null && column != null) {
            column.setReverseSort(isReverse);
            tableData.setSortColumn(column);
            setTableData(tableData);
        }
    }

    public Rect getShowRect() {
        return showRect;
    }


    /**
     * 获取绘制表格内容者
     *
     * @return 绘制表格内容者
     */
    public TableProvider<T> getProvider() {
        return provider;
    }


    /**
     * 获取表格数据
     * TableData是解析数据之后对数据的封装对象，包含table column,rect等信息
     *
     * @return 表格数据
     */
    public TableData<T> getTableData() {
        return tableData;
    }


    /**
     * 开启缩放
     *
     * @param zoom 是否缩放
     */
    public void setZoom(boolean zoom) {

        matrixHelper.setCanZoom(zoom);
        invalidate();

    }

    /**
     * 开启缩放设置缩放值
     *
     * @param zoom    是否缩放
     * @param maxZoom 最大缩放值
     * @param minZoom 最小缩放值
     */
    public void setZoom(boolean zoom, float maxZoom, float minZoom) {

        matrixHelper.setCanZoom(zoom);
        matrixHelper.setMinZoom(minZoom);
        matrixHelper.setMaxZoom(maxZoom);
        invalidate();

    }


    /**
     * 获取缩放移动辅助类
     * 如果你需要更多的移动功能，可以使用它
     *
     * @return 缩放移动辅助类
     */
    public MatrixHelper getMatrixHelper() {
        return matrixHelper;
    }


    /**
     * 设置选中格子格式化
     *
     * @param selectFormat 选中格子格式化
     */
    public void setSelectFormat(ISelectFormat selectFormat) {
        this.provider.setSelectFormat(selectFormat);
    }


    @Override
    public int computeHorizontalScrollRange() {
        final int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int scrollRange = matrixHelper.getZoomRect().right;
        final int scrollX = -matrixHelper.getZoomRect().right;
        final int overScrollRight = Math.max(0, scrollRange - contentWidth);
        if (scrollX < 0) {
            scrollRange -= scrollX;
        } else if (scrollX > overScrollRight) {
            scrollRange += scrollX - overScrollRight;
        }
        return scrollRange;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction < 0) {
            return matrixHelper.getZoomRect().top != 0;
        } else {
            return matrixHelper.getZoomRect().bottom > matrixHelper.getOriginalRect().bottom;
        }

    }

    @Override
    public int computeHorizontalScrollOffset() {
        return Math.max(0, -matrixHelper.getZoomRect().top);
    }


    @Override
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override
    public int computeVerticalScrollRange() {

        final int contentHeight = getHeight() - getPaddingBottom() - getPaddingTop();
        int scrollRange = matrixHelper.getZoomRect().bottom;
        final int scrollY = -matrixHelper.getZoomRect().left;
        final int overScrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            scrollRange -= scrollY;
        } else if (scrollY > overScrollBottom) {
            scrollRange += scrollY - overScrollBottom;
        }

        return scrollRange;
    }

    @Override
    public int computeVerticalScrollOffset() {

        return Math.max(0, -matrixHelper.getZoomRect().left);
    }

    @Override
    public int computeVerticalScrollExtent() {

        return super.computeVerticalScrollExtent();

    }

    public XSequence<T> getXSequence() {
        return xAxis;
    }

    public YSequence getYSequence() {
        return yAxis;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (tableData != null && getContext() != null) {
            if (((Activity) getContext()).isFinishing()) {
                release();
            }
        }
    }

    /**
     * 可以在Activity onDestroy释放
     */
    private void release() {
        matrixHelper.unRegisterAll();
        annotationParser = null;
        measurer = null;
        provider = null;
        matrixHelper = null;
        provider = null;
        if (tableData != null) {
            tableData.clear();
            tableData = null;
        }
        xAxis = null;
        yAxis = null;
    }

    public boolean isYSequenceRight() {
        return isYSequenceRight;
    }

    public void setYSequenceRight(boolean YSequenceRight) {
        isYSequenceRight = YSequenceRight;
    }


    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return getScrollingChildHelper().startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int type) {
        getScrollingChildHelper().stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow,
                                           int type) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow,
                type);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }
}

