package com.bin.david.form.matrix;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by huang on 2017/10/18.
 */

public interface ITouch {
    /**
     * 用于判断是否请求不拦截事件
     * 解决手势冲突问题
     * @param view
     * @param event
     */
    void onDisallowInterceptEvent(View view, MotionEvent event);

    /**
     * 处理touchEvent
     * @param event
     */
    boolean handlerTouchEvent(MotionEvent event);
}
