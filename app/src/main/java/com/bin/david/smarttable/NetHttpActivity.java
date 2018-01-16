package com.bin.david.smarttable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.PM25;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class NetHttpActivity extends AppCompatActivity {

    private SmartTable<PM25> table;
    private Handler mHandler = new Handler();
    private boolean isFrist = true;
    private Runnable AddDataRunnable = new Runnable() {
        @Override
        public void run() {
           getData();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable<PM25>) findViewById(R.id.table);

        table.getConfig().setContentBackgroundFormat(new BaseBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor() {
                return ContextCompat.getColor(NetHttpActivity.this,R.color.content_bg);
            }

            @Override
            public boolean isDraw(CellInfo cellInfo) {
               return cellInfo.position%2 == 1;
            }
        }).setColumnBackgroundFormat(new BaseBackgroundFormat<Column>() {
            @Override
            public int getBackGroundColor() {
                return ContextCompat.getColor(NetHttpActivity.this,R.color.column_bg);
            }

            @Override
            public boolean isDraw(Column column) {
                if("area".equals(column.getFieldName())) {
                    return true;
                }
                return false;
            }

            @Override
            public int getTextColor(Column column) {
                return  ContextCompat.getColor(NetHttpActivity.this,R.color.white);
            }
        });
        getData();

    }
    public void onClick(View view) {
        Intent intent = new Intent(this,AnnotationModeActivity.class);
        startActivity(intent);
    }

    public void getData(){
        String url = "http://www.pm25.in/api/querys/pm10.json?city=%E4%B8%8A%E6%B5%B7&token=5j1znBVAsnSf5xQyNQyq&avg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<PM25>>() {}.getType();
                        List<PM25> pm25List = gson.fromJson(response,type);
                        if(isFrist) {
                            table.setData(pm25List);
                            isFrist = false;
                        }else{
                            table.addData(pm25List,true);
                            table.end();
                        }
                        mHandler.postDelayed(AddDataRunnable,1000);

                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(AddDataRunnable);
    }
}
