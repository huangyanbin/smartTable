package com.bin.david.form.data.format.sequence;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.format.IFormat;

/**
 * Created by huang on 2017/11/7.
 *
 *序号格式化
 */

public interface ISequenceFormat extends IFormat<Integer>{


   void draw(Canvas canvas, int sequence, Rect rect,TableConfig config);

}
