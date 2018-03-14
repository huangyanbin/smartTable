package com.bin.david.smarttable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.LruCache;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.form.utils.DrawUtils;
import com.bin.david.smarttable.bean.StudentInfo;

/**
 * Created by huang on 2018/3/11.
 */

public class DesktopDrawFormat implements IDrawFormat<StudentInfo>{

    private Context context;
    private int dp10;
    private Rect imgRect;
    private Rect drawRect;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    //使用缓存
    private LruCache<Integer,Bitmap> cache;

    public DesktopDrawFormat(Context context) {
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 16;
        cache = new LruCache<Integer,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key,Bitmap bitmap){
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;// KB
            }
        };
        this.context = context;
        imgRect = new Rect();
        drawRect = new Rect();
        dp10 = DensityUtils.dp2px(context,10);
    }


    @Override
    public int measureWidth(Column<StudentInfo> column, int position, TableConfig config) {
        return DensityUtils.dp2px(context,57);
    }

    @Override
    public int measureHeight(Column<StudentInfo> column,int position, TableConfig config) {

        return DensityUtils.dp2px(context,63);
    }





    @Override
    public void draw(Canvas c, Rect rect, CellInfo<StudentInfo> cellInfo, TableConfig config) {
        config.getPaint().setColor(Color.BLACK);
        drawRect.set(rect);
        int dp10  = (int) (this.dp10*config.getZoom());
        drawRect.right -= dp10;
        drawRect.left += dp10;
        drawRect.top+= dp10;
        drawRect.bottom -= dp10 *2;
        if(cellInfo.data !=null){
            StudentInfo studentInfo =cellInfo.data;
            drawBitmap(c,drawRect,getBitmap(R.mipmap.zuo_03),config);
            drawRect.top+= dp10 /2;
            Bitmap bitmap = getBitmap(R.mipmap.avator_1);
            int w = drawRect.height()*bitmap.getWidth()/bitmap.getHeight();
            drawRect.left = drawRect.centerX()-w/2;
            drawRect.right = drawRect.centerX()+w/2;
            drawBitmap(c,drawRect,bitmap,config);
            rect.top+= dp10*5;
            DrawUtils.drawSingleText(c,config.getPaint(),rect,studentInfo.getName());

        }else{
            drawBitmap(c,drawRect,getBitmap(R.mipmap.zuo_05),config);
        }


    }

    protected Bitmap getBitmap(int resID) {
        Bitmap bitmap = cache.get(resID);
        if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),resID,options);
            if(bitmap !=null) {
                cache.put(resID, bitmap);
            }
        }
        return bitmap;
    }



    public void drawBitmap(Canvas c, Rect rect,Bitmap bitmap,TableConfig config) {
        Paint paint = config.getPaint();
        if(bitmap != null) {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            imgRect.set(0,0,width,height);
            c.drawBitmap(bitmap, imgRect, rect, paint);
        }
    }




}
