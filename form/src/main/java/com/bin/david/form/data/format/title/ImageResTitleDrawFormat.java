package com.bin.david.form.data.format.title;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.bin.david.form.data.column.Column;

/**
 * Created by huang on 2017/10/30.
 */

public abstract class ImageResTitleDrawFormat extends BitmapTitleDrawFormat {

    private BitmapFactory.Options options = new BitmapFactory.Options();
    //使用缓存
    private LruCache<Integer,Bitmap> cache;


    public ImageResTitleDrawFormat(int imageWidth, int imageHeight) {
        this(imageWidth,imageHeight,null);
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 16;
        cache = new LruCache<Integer,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key,Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;// KB
            }
        };
    }

    public ImageResTitleDrawFormat(int imageWidth, int imageHeight, BitmapFactory.Options options) {
        super(imageWidth, imageHeight);
        this.options = options;
    }

    @Override
    protected Bitmap getBitmap(Column column) {
        int resID = getResourceID(column);
        Bitmap bitmap = cache.get(resID);
        if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(),resID,options);
            if(bitmap !=null) {
                cache.put(resID, bitmap);
            }
        }
        return bitmap;
    }

    protected abstract Context getContext();

    protected abstract int getResourceID(Column column);


}
