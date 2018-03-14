package com.bin.david.form.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.LruCache;

import com.bin.david.form.core.TableConfig;

/**
 * Created by huang on 2018/3/12.
 */

public class BitmapDrawer {


    private Rect imgRect;
    private Rect drawRect;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    //使用缓存
    private LruCache<Integer,Bitmap> cache;

    public BitmapDrawer() {
        this.imgRect = new Rect();
        this.drawRect = new Rect();
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 16;
        cache = new LruCache<Integer,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key,Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;// KB
            }
        };

    }

    public void drawRes(Context context,Canvas c, Rect rect, int resID, TableConfig config){
        Bitmap bitmap = cache.get(resID);
        if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),resID,options);
            if(bitmap !=null) {
                cache.put(resID, bitmap);
            }
            drawBitmap(c,rect,bitmap,config);
        }
    }


    public void drawBitmap(Canvas c, Rect rect,Bitmap bitmap,TableConfig config) {
        Paint paint = config.getPaint();
        if (bitmap != null) {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            imgRect.set(0, 0, width, height);
            float scaleX = (float) width / rect.width();
            float scaleY = (float) height / rect.height();
            if (scaleX > 1 || scaleY > 1) {
                if (scaleX > scaleY) {
                    width = (int) (width / scaleX);
                    height = rect.height();
                } else {
                    height = (int) (height / scaleY);
                    width = rect.width();
                }
            }
            width = (int) (width * config.getZoom());
            height = (int) (height * config.getZoom());
            int disX = (rect.right - rect.left - width) / 2;
            int disY = (rect.bottom - rect.top - height) / 2;
            drawRect.left = rect.left + disX;
            drawRect.top = rect.top + disY;
            drawRect.right = rect.right - disX;
            drawRect.bottom = rect.bottom - disY;
            c.drawBitmap(bitmap, imgRect, drawRect, paint);
        }
    }
}
