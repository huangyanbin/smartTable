package com.bin.david.form.component;


import com.bin.david.form.data.style.FontStyle;

/**
 * Created by huang on 2017/10/26.
 */

public interface ITableTitle extends IComponent<String> {

    int getSize();

    void setSize(int size);


    int getDirection();

    void setDirection(int direction);

}
