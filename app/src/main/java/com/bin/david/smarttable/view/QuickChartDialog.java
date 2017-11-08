package com.bin.david.smarttable.view;

import android.app.Activity;


import com.bin.david.smarttable.bean.TableStyle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 2017/10/13.
 */

public class QuickChartDialog {
    private Map<TableStyle,BaseCheckDialog<String>> map;

    public QuickChartDialog(){
        map = new HashMap<>();
    }

    public   void showDialog(Activity activity, TableStyle chartStyle, String[] dataList, OnCheckChangeAdapter adapter){
        BaseCheckDialog<String> dialog;
        if(map.containsKey(chartStyle)){
            dialog = map.get(chartStyle);
        }else {
            dialog = new BaseCheckDialog<>(chartStyle.value,adapter);
        }
        map.put(chartStyle,dialog);
        dialog.show(activity,false, Arrays.asList(dataList));
    }

    public abstract static class  OnCheckChangeAdapter implements BaseCheckDialog.OnCheckChangeListener<String> {
        @Override
        public String getItemText(String s) {
            return s;
        }
    }
}
