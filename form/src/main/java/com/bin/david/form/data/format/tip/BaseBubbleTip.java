package com.bin.david.form.data.format.tip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;


/**
 * Created by huang on 2017/10/20.
 * 气泡提示
 */

public abstract class BaseBubbleTip<C,S> implements ITip<C,S>{

    public static final int INVALID =0;
    private Rect tipRect;
    private Paint paint;
    private int padding;
    private Bitmap triangleBitmap;
    private NinePatch ninePatch;
    private boolean isReversal = false;
    protected int deviation;
    private float alpha;
    private int colorFilter = INVALID;

    public BaseBubbleTip(Context context, int backgroundDrawableID, int triangleDrawableID, FontStyle fontStyle){
        tipRect = new Rect();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontStyle.fillPaint(paint);
        triangleBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), triangleDrawableID));
        if(ninePatch == null) {
            Bitmap bmp_9 = BitmapFactory.decodeResource(context.getResources(),
                    backgroundDrawableID);
            ninePatch = new NinePatch(bmp_9, bmp_9.getNinePatchChunk(), null);

        }
        padding = DensityUtils.dp2px(context,5);
        deviation = DensityUtils.dp2px(context,5);
    }

    public void drawTip(Canvas canvas, float x, float y, Rect rect,C c,int position) {
        if(isShowTip(c,position)) {
            S content = format(c,position);
            int triangleWidth = triangleBitmap.getWidth();
            int triangleHeight = triangleBitmap.getHeight();
            int textWidth = getTextWidth(content);
            int textHeight = getTextHeight(content);
            int w = textWidth + padding * 2;
            int h = textHeight + padding * 2;
            tipRect.left = (int) x - w / 2;
            tipRect.right = (int) x + w / 2;
            tipRect.bottom = (int) y - triangleHeight + triangleWidth / 8;
            tipRect.top = tipRect.bottom - h;
            int tranX = 0;
            if (tipRect.left < rect.left) {
                tranX = rect.left - tipRect.left - triangleWidth/2;

            } else if (tipRect.right > rect.right) {
                tranX = rect.right - tipRect.right + triangleWidth/2;
            }

            if (tipRect.top < rect.top) {
                showBottom(canvas, x, y, content, textWidth,textHeight, tranX);
            } else if (tipRect.bottom > rect.bottom) {
                showTop(canvas, x, y, content, textWidth,textHeight,  tranX);
            } else if (isReversal) {
                showBottom(canvas, x, y, content, textWidth,textHeight,  tranX);
            } else {
                showTop(canvas, x, y, content, textWidth,textHeight,  tranX);
            }
        }

    }

    public abstract int getTextHeight(S content);

    public abstract int getTextWidth(S content);


    public abstract void drawText(Canvas canvas,Rect tipRect,S content,int textWidth,int textHeight,Paint paint);

    private void showTop(Canvas canvas, float x, float y, S content, int textWidth, int textHeight,int tranX) {
        canvas.save();
        int triangleWidth = triangleBitmap.getWidth();
        int triangleHeight = triangleBitmap.getHeight();
        startColorFilter();
        canvas.drawBitmap(triangleBitmap,x-triangleWidth/2,
                y-triangleHeight,paint);
        canvas.translate(tranX,0);
        ninePatch.draw(canvas, tipRect);
        clearColorFilter();
        drawText(canvas, tipRect,content,textWidth,textHeight,paint);
        canvas.restore();
    }

    private void showBottom(Canvas canvas, float x, float y, S content, int textWidth,int textHeight,int tranX) {
        canvas.save();
        int triangleWidth = triangleBitmap.getWidth();
        int triangleHeight = triangleBitmap.getHeight();
        canvas.rotate(180,x,y);
        startColorFilter();
        canvas.drawBitmap(triangleBitmap,x-triangleWidth/2,
                y-triangleHeight,paint);
        canvas.translate(-tranX,0);
        ninePatch.draw(canvas, tipRect);
        clearColorFilter();
        paint.setColorFilter(null);
        canvas.rotate(180, tipRect.centerX(), tipRect.centerY());
        drawText(canvas, tipRect,content,textWidth,textHeight,paint);
        canvas.restore();
    }

    private void startColorFilter() {
        if (colorFilter != INVALID){
            paint.setColorFilter(new PorterDuffColorFilter(colorFilter, PorterDuff.Mode.SRC_IN));
            ninePatch.setPaint(paint);
            paint.setAlpha((int) (alpha*255));
        }
    }

    private void clearColorFilter(){

        if(colorFilter != INVALID) {
            paint.setColorFilter(null);
            paint.setAlpha(255);
        }
    }


    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean reversal) {
        isReversal = reversal;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPadding() {
        return padding;
    }

    public int getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(int colorFilter) {
        this.colorFilter = colorFilter;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
