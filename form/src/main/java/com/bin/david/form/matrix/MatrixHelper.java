package com.bin.david.form.matrix;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.bin.david.form.listener.OnTableChangeListener;
import com.bin.david.form.listener.TableClickObserver;
import com.bin.david.form.listener.Observable;

import java.util.List;

/**
 * Created by huang on 2017/9/29.
 * 图表放大缩小协助类
 */

public class MatrixHelper extends Observable<TableClickObserver> implements ITouch, ScaleGestureDetector.OnScaleGestureListener {

    private   int MAX_ZOOM = 5;
    public static final int MIN_ZOOM = 1;
    private float zoom = MIN_ZOOM; //缩放比例  不得小于1
    private int translateX; //以左上角为准，X轴位移的距离
    private int translateY;//以左上角为准，y轴位移的距离
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private boolean isCanZoom = false;
    private boolean isScale; //是否正在缩放
    private Rect originalRect; //原始大小
    private Rect zoomRect;
    private float mDownX, mDownY;
    private int pointMode; //屏幕的手指点个数
    private Scroller scroller;
    private int mMinimumVelocity;
    private boolean isFling;
    private OnTableChangeListener listener;
    private float flingRate = 0.5f; //速率

    public MatrixHelper(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new OnChartGestureListener());
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        scroller = new Scroller(context);
    }

    /**
     * 处理手势
     */
    @Override
    public boolean handlerTouchEvent(MotionEvent event) {
        if (isCanZoom) {
            mScaleGestureDetector.onTouchEvent(event);
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }



    /**
     * 判断是否需要接收触摸事件
     */
    @Override
    public void onDisallowInterceptEvent(View view, MotionEvent event) {
        if (!isCanZoom) {
            return;
        }
        ViewParent parent = view.getParent();
        if (zoomRect == null || originalRect == null) {
            parent.requestDisallowInterceptTouchEvent(false);
            return;
        }
        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                pointMode = 1;
                //ACTION_DOWN的时候，赶紧把事件hold住
                mDownX = event.getX();
                mDownY = event.getY();
                if(originalRect.contains((int)mDownX,(int)mDownY)){ //判断是否落在图表内容区中
                    parent.requestDisallowInterceptTouchEvent(true);
                }else{
                    parent.requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                pointMode += 1;
                parent.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointMode > 1) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    return;
                }
                float disX = event.getX() - mDownX;
                float disY = event.getY() - mDownY;
                boolean isDisallowIntercept = true;
                if (Math.abs(disX) > Math.abs(disY)) {
                    if ((disX > 0 && toRectLeft()) || (disX < 0 && toRectRight())) { //向右滑动
                        isDisallowIntercept = false;
                    }
                } else {
                    if ((disY > 0 && toRectTop()) || (disY < 0 && toRectBottom())) {
                        isDisallowIntercept = false;
                    }
                }
                parent.requestDisallowInterceptTouchEvent(isDisallowIntercept);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointMode -= 1;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                pointMode = 0;
                parent.requestDisallowInterceptTouchEvent(false);
        }

    }

    private boolean toRectLeft() {
        return translateX <= -(zoomRect.width() - originalRect.width()) / 2;
    }

    private boolean toRectRight() {
        return translateX >= (zoomRect.width() - originalRect.width()) / 2;
    }

    private boolean toRectBottom() {

        return translateY >= (zoomRect.height() - originalRect.height()) / 2;
    }

    private boolean toRectTop() {
        return translateY <= -(zoomRect.height() - originalRect.height()) / 2;
    }

    public void notifyViewChanged(){
        if(listener != null) {
            listener.onTableChanged(zoom, translateX, translateY);
        }
    }


    @Override
    public void notifyObservers(List<TableClickObserver> observers) {
        //暂时不需要
    }

    private int tempTranslateX; //以左上角为准，X轴位移的距离
    private int tempTranslateY;//以左上角为准，y轴位移的距离

    private float tempScale = MIN_ZOOM; //缩放比例  不得小于1

    class OnChartGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //if(Math.abs(distanceX) >touchSlop) {
                translateX += distanceX;
            //}
            //if(Math.abs(distanceY) > touchSlop) {
                translateY += distanceY;
            //}
            notifyViewChanged();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(Math.abs(velocityX) >mMinimumVelocity || Math.abs(velocityY) >mMinimumVelocity) {
               scroller.setFinalX(0);
               scroller.setFinalY(0);
                tempTranslateX = translateX;
                tempTranslateY = translateY;
                scroller.fling(0,0,(int)velocityX,(int)velocityY,-50000,50000
               ,-50000,50000);
                isFling = true;
                startFilingAnim();
            }

            return true;
        }

        private Point startPoint = new Point(0, 0);
        private Point endPoint = new Point();
        private TimeInterpolator interpolator = new DecelerateInterpolator();
        private PointEvaluator evaluator= new PointEvaluator();
        private void startFilingAnim() {

            int scrollX =Math.abs(scroller.getFinalX());
            int scrollY =Math.abs(scroller.getFinalY());
            if(scrollX >scrollY){
                endPoint.set((int)(scroller.getFinalX()*flingRate),0);
            }else{
                endPoint.set(0,(int)(scroller.getFinalY()*flingRate));
            }
            final ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator,startPoint,endPoint);
            valueAnimator.setInterpolator(interpolator);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(isFling) {
                        Point point = (Point) animation.getAnimatedValue();
                        //Log.e("huang","point x"+point.x+"point y"+point.y);
                        translateX = tempTranslateX - point.x;
                        translateY = tempTranslateY - point.y;
                        notifyViewChanged();
                    }else{
                        animation.cancel();
                    }
                }
            });
            int duration = (int)(Math.max(scrollX,scrollY)*flingRate)/2;
            valueAnimator.setDuration(duration>300 ?300:duration);
            valueAnimator.start();
        }

        @Override
        public boolean onDown(MotionEvent e) {
            isFling = false;

            return true;
        }




        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isCanZoom) {
                float oldZoom = zoom;
                if (isScale) { //缩小
                    zoom = zoom / 1.5f;
                    if (zoom < 1) {
                        zoom = MIN_ZOOM;
                        isScale = false;
                    }
                } else { //放大
                    zoom = zoom * 1.5f;
                    if (zoom > MAX_ZOOM) {
                        zoom = MAX_ZOOM;
                        isScale = true;
                    }
                }
                float factor = zoom / oldZoom;
                resetTranslate(factor);
                notifyViewChanged();
            }

            return true;
        }

        //单击
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            notifyViewChanged();
            for (TableClickObserver observer : observables) {
                observer.onClick(e.getX(), e.getY());
            }
            return true;
        }
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        tempScale = this.zoom;

        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float oldZoom = zoom;
        float scale = detector.getScaleFactor();
        this.zoom = tempScale * scale;
        float factor = zoom / oldZoom;
        resetTranslate(factor);
        notifyViewChanged();
        if (this.zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
            return true;
        } else if (this.zoom < 1) {
            this.zoom = 1;
            return true;
        }
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    /**
     * 重新计算偏移量
     * * @param factor
     */
    private void resetTranslate(float factor) {

        translateX = (int) (translateX * factor);
        translateY = (int) (translateY * factor);
    }

    /**
     * 获取图片内容的缩放大小
     *
     * @return 缩放后内容的大小
     */
    public Rect getZoomProviderRect(Rect showRect,Rect providerRect) {
        originalRect = providerRect;
        Rect scaleRect = new Rect();
        int showWidth = showRect.width();
        int showHeight = showRect.height();
        int oldw = providerRect.width();
        int oldh = providerRect.height();
        int newWidth = (int) (oldw * zoom);
        int newHeight = (int) (oldh * zoom);
        int offsetTranslateX = (newWidth - oldw) / 2;
        int offsetTranslateY = (newHeight - oldh) / 2;
        int minTranslateX = 0;
        int maxTranslateX = offsetTranslateX+ oldw-showWidth;
        int minTranslateY = 0;
        int maxTranslateY = offsetTranslateY+ oldh-showHeight;

        int offsetX = 0;
        int offsetY =0;
        if(translateX  < minTranslateX){
            translateX = minTranslateX;
        }else if(translateX > maxTranslateX){
            translateX = maxTranslateX;
        }
        if(translateY < minTranslateY){
            translateY = minTranslateY;
        }else if(translateY > maxTranslateY){
            translateY = maxTranslateY;
        }
        if(translateX <0){
            translateX =0;
        }
        if(translateY <0){
            translateY =0;
        }
        scaleRect.left = providerRect.left - offsetX - translateX;
        scaleRect.right = providerRect.right + offsetX - translateX;
        scaleRect.top = providerRect.top - offsetY - translateY;
        scaleRect.bottom = providerRect.bottom + offsetY - translateY;
        zoomRect = scaleRect;
        return scaleRect;

    }




    public boolean isCanZoom() {
        zoom = 1f;
        return isCanZoom;

    }

    public OnTableChangeListener getOnTableChangeListener() {
        return listener;
    }

    public void setOnTableChangeListener(OnTableChangeListener onTableChangeListener) {
        this.listener = onTableChangeListener;
    }

    public void setCanZoom(boolean canZoom) {
        isCanZoom = canZoom;
    }

    public int getMaxZoom() {
        return MAX_ZOOM;
    }

    public void setMaxZoom(int maxZoom) {
        if(maxZoom <1){
            maxZoom = 1;
        }
        this.MAX_ZOOM = maxZoom;
    }

    public float getZoom() {
        return zoom;
    }

    public float getFlingRate() {
        return flingRate;
    }

    public void setFlingRate(float flingRate) {
        this.flingRate = flingRate;
    }
}
