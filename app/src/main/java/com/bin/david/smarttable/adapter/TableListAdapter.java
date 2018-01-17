package com.bin.david.smarttable.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.ColumnInfo;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.count.ICountFormat;
import com.bin.david.form.data.format.draw.ImageResDrawFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.tip.MultiLineBubbleTip;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.R;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.UserInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by huang on 2017/10/13.
 */

public class TableListAdapter extends BaseQuickAdapter<String,BaseViewHolder> {



    public TableListAdapter(@Nullable List<String> data) {
        super(R.layout.item_table, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(mContext,15));
        final SmartTable<UserInfo> table =  helper.getView(R.id.table);
        final List<UserInfo> testData = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i <50; i++) {
            testData.add(new UserInfo("用户"+i, random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("测试"+i)));
        }

        final Column<String> nameColumn = new Column<>("姓名", "name");
        nameColumn.setAutoCount(true);
        final Column<Integer> ageColumn = new Column<>("年龄", "age");
        ageColumn.setAutoCount(true);
        Column<String> column4 = new Column<>("测试多重查询", "childData.child");
        column4.setAutoCount(true);
        final IFormat<Long> format =  new IFormat<Long>() {
            @Override
            public String format(Long aLong) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(aLong);
                return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            }
        };
        final Column<Long> timeColumn = new Column<>("时间", "time",format);
        timeColumn.setCountFormat(new ICountFormat<Long, Long>() {
            private long maxTime;
            @Override
            public void count(Long aLong) {
                if(aLong > maxTime){
                    maxTime = aLong;
                }
            }

            @Override
            public Long getCount() {
                return maxTime;
            }

            @Override
            public String getCountString() {
                return format.format(maxTime);
            }

            @Override
            public void clearCount() {
                maxTime =0;
            }
        });
        int size = DensityUtils.dp2px(mContext,15);
        Column<Boolean> column5 = new Column<>("勾选1", "isCheck", new ImageResDrawFormat<Boolean>(size,size) {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.check;
                }
                return 0;
            }
        });
        Column<Boolean> column6 = new Column<>("勾选2", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.LEFT,10) {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.clock_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column7 = new Column<>("勾选3", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.activity_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column8 = new Column<>("勾选4", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.TOP,10) {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.brush_fill;
                }
                return 0;
            }
        });
        Column<Boolean> column9 = new Column<>("勾选5", "isCheck", new TextImageDrawFormat<Boolean>(size,size, TextImageDrawFormat.BOTTOM,10) {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            protected int getResourceID(Boolean isCheck, String value, int position) {
                if(isCheck){
                    return R.mipmap.collection_fill;
                }
                return 0;
            }
        });
        Column totalColumn1 = new Column("总项1",nameColumn,ageColumn);
        Column totalColumn2 = new Column("总项2",nameColumn,ageColumn,timeColumn);
        Column totalColumn = new Column("总项",nameColumn,totalColumn1,totalColumn2,timeColumn);

        final TableData<UserInfo> tableData = new TableData<>("测试",testData,nameColumn,column4,column5,column6,column7,column8,column9,totalColumn,totalColumn1,totalColumn2,timeColumn);

        tableData.setShowCount(true);
        // ageColumn.setAutoCount(true);
        //table.getConfig().setYSequenceBackgroundColor(mContext.getResources().getColor(R.color.arc1));
        //table.getConfig().setXSequenceBackgroundColor(mContext.getResources().getColor(R.color.arc2));
        table.getConfig().setColumnTitleBackgroundColor(mContext.getResources().getColor(R.color.windows_bg));
        //table.getConfig().setContentBackgroundColor(mContext.getResources().getColor(R.color.arc21));
        table.getConfig().setCountBackgroundColor(mContext.getResources().getColor(R.color.windows_bg));
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size,size, TitleImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return mContext;
            }

            @Override
            protected int getResourceID(Column column) {
                if(!column.isParent()){
                    if(tableData.getSortColumn() == column){
                        setDirection(TextImageDrawFormat.RIGHT);
                        if(column.isReverseSort()){
                            return R.mipmap.sort_up;
                        }
                        return R.mipmap.sort_down;

                    }else{
                        setDirection(TextImageDrawFormat.LEFT);
                        if(column == nameColumn){
                            return R.mipmap.name;
                        }else if(column == ageColumn){
                            return R.mipmap.age;
                        }else if(column == timeColumn){
                            return R.mipmap.update;
                        }
                    }
                    return 0;
                }
                setDirection(TextImageDrawFormat.LEFT);
                int level = tableData.getTableInfo().getMaxLevel()-column.getLevel();
                if(level ==0){
                    return R.mipmap.level1;
                }else if(level ==1){
                    return R.mipmap.level2;
                }
                return 0;
            }
        });
        ageColumn.setOnColumnItemClickListener(new OnColumnItemClickListener<Integer>() {
            @Override
            public void onClick(Column<Integer> column, String value, Integer integer, int position) {
                Toast.makeText(mContext,"点击了"+value,Toast.LENGTH_SHORT).show();
            }
        });
        com.bin.david.form.data.style.FontStyle fontStyle = new com.bin.david.form.data.style.FontStyle();
        fontStyle.setTextColor(mContext.getResources().getColor(android.R.color.white));
        MultiLineBubbleTip<Column> tip = new MultiLineBubbleTip<Column>(mContext,R.mipmap.round_rect,R.mipmap.triangle,fontStyle) {
            @Override
            public boolean isShowTip(Column column, int position) {
                if(column == nameColumn){
                    return true;
                }
                return false;
            }


            @Override
            public String[] format(Column column, int position) {
                UserInfo data = testData.get(position);
                String[] strings = {"批注","姓名："+data.getName(),"年龄："+data.getAge()};
                return strings;
            }
        };
        tip.setColorFilter(Color.parseColor("#FA8072"));
        tip.setAlpha(0.8f);
        table.getProvider().setTip(tip);

        table.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                if(!columnInfo.column.isParent()) {
                        table.setSortColumn(columnInfo.column, !columnInfo.column.isReverseSort());

                }
                Toast.makeText(mContext,"点击了"+columnInfo.column.getColumnName(),Toast.LENGTH_SHORT).show();
            }
        });
        table.getConfig().setTableTitleStyle(new com.bin.david.form.data.style.FontStyle(mContext,15,mContext.getResources().getColor(R.color.arc1)));
        table.setTableData(tableData);
    }
}
