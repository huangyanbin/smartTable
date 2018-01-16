package com.bin.david.smarttable.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.bin.david.smarttable.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by huang on 2018/1/16.
 */

public class SheetAdapter  extends BaseQuickAdapter<String,BaseViewHolder>{

    private int selectPosition =0;
    public SheetAdapter(@Nullable List<String> data) {
        super(R.layout.item_sheet, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_sheet_name,item).setTextColor(R.id.tv_sheet_name,
                ContextCompat.getColor(mContext,
                        selectPosition == helper.getAdapterPosition()?R.color.arc23:R.color.arc_temp));
        helper.getView(R.id.tv_sheet_name).setBackgroundColor( ContextCompat.getColor(mContext,
                selectPosition == helper.getAdapterPosition()?R.color.white:R.color.arc_bg));
    }



    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }
}
