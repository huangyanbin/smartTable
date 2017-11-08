package com.bin.david.smarttable.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bin.david.form.utils.DensityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.bin.david.smarttable.R;
import java.util.List;

/**
 * Created by huang on 2017/9/7.
 */

public class BaseCheckDialog<T> {

    private View popView;
    private BaseDialog dialog;
    private BaseQuickAdapter<T,BaseViewHolder> quickAdapter;
    private int selectPosition =-1;
    private OnCheckChangeListener<T> listener;
    private String title;
    private boolean isFillHeight;

    public BaseCheckDialog(String title,OnCheckChangeListener<T> listener) {
        this.listener = listener;
        this.title = title;
    }
    /**
     * 显示
     * @param context 上下文
     * @param isNewData 是否是新数据
     * @param list 数据源
     */
    public void show(Context context,boolean isNewData,List<T> list){
        show(context,isNewData?-1:selectPosition,list);
    }


    /**
     * 显示
     * @param context 上下文
     * @param defaultPosition 默认选中的位置
     * @param list 数据源
     */
    public void show(Context context,int defaultPosition, List<T> list){
        selectPosition = defaultPosition;
        if(popView == null) {
            popView = View.inflate(context, R.layout.pop_base_check, null);
            TextView textView = (TextView) popView.findViewById(R.id.tv_check_title);
            textView.setText(title);
            final RecyclerView recyclerView = (RecyclerView) popView.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            quickAdapter = new BaseQuickAdapter<T,BaseViewHolder>(R.layout.item_pop_calculator,list) {
                @Override
                protected void convert(BaseViewHolder helper, T item) {
                    int position = helper.getAdapterPosition();
                    CheckBox checkBox = helper.getView(R.id.cb_check);
                    checkBox.setChecked(position == selectPosition);
                    helper.setText(R.id.tv_indicator_title,listener.getItemText(item));

                }
            };
            recyclerView.setAdapter(quickAdapter);
            quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    selectPosition = position;
                    quickAdapter.notifyDataSetChanged();
                    listener.onItemClick(quickAdapter.getData().get(position),position);
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    },30);

                }
            });
        }else{
            quickAdapter.setNewData(list);
            quickAdapter.notifyDataSetChanged();
        }
        boolean isFillHeight = BaseDialog.getScreen((Activity) context).getHeight()
                - list.size()* DensityUtils.dp2px(context,50) <= 0;
        if(dialog == null) {
            dialog = new BaseDialog.Builder(context)
                    .setFillWidth(true)
                    .setFillHeight(isFillHeight)
                    .setContentView(popView)
                    .create();
        }else if (isFillHeight != this.isFillHeight){
            dialog.notifyHeight(isFillHeight);
        }
        this.isFillHeight = isFillHeight;
        dialog.show();
    }



    public void  dismiss(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public  interface OnCheckChangeListener<T>{
         String getItemText(T t);
        void onItemClick(T t, int position);
    }
}
