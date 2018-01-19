package com.bin.david.smarttable;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.PM25;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
//测试是否和刷新加载更多冲突
public class RefreshActivity extends AppCompatActivity {

    private SmartTable<PM25> table;
    private boolean isFrist = true;
    private RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable<PM25>) findViewById(R.id.table);
        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        table.getConfig().setContentBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if(cellInfo.position%2 == 1) {
                    return ContextCompat.getColor(RefreshActivity.this, R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }

        });
        table.getConfig().setShowTableTitle(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
                //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
               getData(true);
               // refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
        getData(false);

    }


    public void getData(final boolean isFoot){
        String url = "http://www.pm25.in/api/querys/pm10.json?city=%E4%B8%8A%E6%B5%B7&token=5j1znBVAsnSf5xQyNQyq&avg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(!isFoot) {
                            refreshLayout.finishRefresh(false);
                        }else{
                            refreshLayout.finishLoadmore(false);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Gson gson = new Gson();
                        try {
                            Type type = new TypeToken<ArrayList<PM25>>() {}.getType();
                            List<PM25> pm25List = gson.fromJson(response,type);
                            if(isFrist) {
                                table.setData(pm25List);
                                isFrist = false;
                            }else{
                                if(!isFoot) {
                                    refreshLayout.finishRefresh();
                                }else{
                                    refreshLayout.finishLoadmore();
                                }
                                table.addData(pm25List,isFoot);
                            }
                        }catch (Exception e){

                        }


                    }

                });
    }


}
