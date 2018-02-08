package com.bin.david.smarttable;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.BitmapDrawFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnClickListener;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.TanBean;
import com.bin.david.smarttable.bean.UserInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MinModeActivity extends AppCompatActivity{

    private SmartTable<UserInfo> table;
    private Map<String,Bitmap> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15)); //设置全局字体大小
        table = (SmartTable<UserInfo>) findViewById(R.id.table);

        WindowManager wm = this.getWindowManager();
        int screenWith = wm.getDefaultDisplay().getWidth();
        table.getConfig().setMinTableWidth(screenWith); //设置最小宽度 屏幕宽度
        //生成数据
        final List<UserInfo> testData = new ArrayList<>();
        Random random = new Random();
        List<TanBean> tanBeans = TanBean.initDatas();
        //测试 从其他地方获取url
        int urlSize = tanBeans.size();
        for(int i = 0;i <50; i++) {
            UserInfo userData = new UserInfo("用户"+i, random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("测试"+i));
            userData.setUrl(tanBeans.get(i%urlSize).getUrl());
            testData.add(userData);
        }

        final Column<String> nameColumn = new Column<>("姓名", "name");
        nameColumn.setAutoCount(true);
        final Column<Integer> ageColumn = new Column<>("年龄", "age");
        ageColumn.setAutoCount(true);
        int imgSize = DensityUtils.dp2px(this,25);
        final Column<String> avatarColumn = new Column<>("头像", "url", new BitmapDrawFormat<String>(imgSize,imgSize) {
            @Override
            protected Bitmap getBitmap(final String s, String value, int position) {
                if(map.get(s)== null) {
                    Glide.with(MinModeActivity.this).asBitmap().load(s)
                            .apply(bitmapTransform(new CenterCrop())).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            map.put(s, bitmap);
                            table.invalidate();
                        }
                    });
                }
                return map.get(s);
            }
        });

        final TableData<UserInfo> tableData = new TableData<>("测试",testData,nameColumn,
                avatarColumn);
        tableData.setShowCount(true);
        table.getConfig().setColumnTitleBackgroundColor(getResources().getColor(R.color.windows_bg));
        table.getConfig().setCountBackgroundColor(getResources().getColor(R.color.windows_bg));
        int size = DensityUtils.dp2px(this,15);
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size,size, TitleImageDrawFormat.RIGHT,10) {
            @Override
            protected Context getContext() {
                return MinModeActivity.this;
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
                Toast.makeText(MinModeActivity.this,"点击了"+value,Toast.LENGTH_SHORT).show();
            }
        });
        FontStyle fontStyle = new FontStyle();
        fontStyle.setTextColor(getResources().getColor(android.R.color.white));

        table.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                if(!columnInfo.column.isParent()) {
                    table.setSortColumn(columnInfo.column, !columnInfo.column.isReverseSort());
                }
                Toast.makeText(MinModeActivity.this,"点击了"+columnInfo.column.getColumnName(),Toast.LENGTH_SHORT).show();
            }
        });
        table.getConfig().setTableTitleStyle(new FontStyle(this,15,getResources().getColor(R.color.arc1)));
        ICellBackgroundFormat<CellInfo> backgroundFormat = new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if(cellInfo.position%2 == 0) {
                    return ContextCompat.getColor(MinModeActivity.this, R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }


        };
        ICellBackgroundFormat<Integer> backgroundFormat2 = new BaseCellBackgroundFormat<Integer>() {
            @Override
            public int getBackGroundColor(Integer position) {
                if(position%2 == 0){
                    return ContextCompat.getColor(MinModeActivity.this,R.color.arc1);
                }
                return TableConfig.INVALID_COLOR;

            }


            @Override
            public int getTextColor(Integer position) {
                if(position%2 == 0) {
                    return ContextCompat.getColor(MinModeActivity.this, R.color.white);
                }
                return TableConfig.INVALID_COLOR;
            }
        };
        table.getConfig().setContentBackgroundFormat(backgroundFormat)
                .setYSequenceBgFormat(backgroundFormat2);
        table.setTableData(tableData);


    }




}
