package com.bin.david.form.data.format.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * Created by huang on 2017/10/30.
 */

public abstract class LeftTopDrawFormat extends ImageResDrawFormat<String> {


    public LeftTopDrawFormat() {
        super(20, 20);
    }

    @Override
    protected int getResourceID(String s, String value, int position) {
        return getResourceID();
    }

    protected abstract int getResourceID();

   public void setImageSize(int w,int h){
       setImageWidth(w);
       setImageHeight(h);
   }

}
