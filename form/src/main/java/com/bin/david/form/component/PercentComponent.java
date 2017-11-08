package com.bin.david.form.component;

import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;

/**图表百分百组件
 * Created by huang on 2017/10/26.
 */

public abstract class PercentComponent<T> implements IComponent<T> {
    
    private static final float DEFAULT_PERCENT = 0.1f;
    private float percent = DEFAULT_PERCENT;
    private Rect rect = new Rect();
    protected int direction;


    
    @Override
    public void computeRect(Rect scaleRect,Rect showRect,TableConfig config) {
        rect.left = showRect.left;
        rect.right = showRect.right;
        rect.top = showRect.top;
        rect.bottom = showRect.bottom;
        int h = (int) (showRect.height()*percent);
        int w = (int) (showRect.width()*percent);
        switch (direction){
            case TOP:
                rect.bottom = rect.top+h;
                scaleRect.top +=h;
                showRect.top +=h;
                break;
            case LEFT:
                rect.right = rect.left + w;
                scaleRect.left +=w;
                showRect.left +=w;
                break;
            case RIGHT:
                rect.left = rect.right -w;
                scaleRect.right -= w;
                showRect.right -=w;
                break;
            case BOTTOM:
                rect.top = rect.bottom -h;
                scaleRect.bottom -= h;
                showRect.bottom -=h;
                break;
        }
    }


    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
