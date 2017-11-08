package com.bin.david.form.data.format.sequence;


import com.bin.david.form.utils.LetterUtils;

/**
 * Created by huang on 2017/11/7.
 */

public class LetterSequenceFormat implements ISequenceFormat{

    @Override
    public String format(Integer position) {
        return LetterUtils.ToNumberSystem26(position);
    }
}
