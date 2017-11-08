package com.bin.david.form.data.format.sequence;


/**
 * Created by huang on 2017/11/7.
 */

public class NumberSequenceFormat implements ISequenceFormat{

    @Override
    public String format(Integer position) {
        return String.valueOf(position);
    }
}
