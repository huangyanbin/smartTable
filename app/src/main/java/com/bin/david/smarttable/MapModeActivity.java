package com.bin.david.smarttable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.MapTableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.utils.JsonHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import static com.bin.david.smarttable.utils.JsonHelper.reflect;

public class MapModeActivity extends AppCompatActivity {

    public SmartTable table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_table);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        table = (SmartTable) findViewById(R.id.table);

        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if(cellInfo.row %2 == 1) {
                    return ContextCompat.getColor(MapModeActivity.this, R.color.content_bg);
                }
                return TableConfig.INVALID_COLOR;
            }

        });
        getData();

    }
    public void onClick(View view) {
        Intent intent = new Intent(this,AnnotationModeActivity.class);
        startActivity(intent);
    }

    public void getData(){
        //直接设置Map
       /* List<Object> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("资产名称", "台式机");
        map.put("账面数量", 1);
        map.put("扫描数量", 1);
        map.put("单位", "台");
        map.put("规格型号", "45*50");
        list.add(map);

        map = new HashMap<>();
        map.put("资产名称", "笔记本");
        map.put("账面数量", 2);
        map.put("扫描数量", 3);
        map.put("单位", "台");
        map.put("规格型号", "45*50");
        list.add(map);

        MapTableData tableData = MapTableData.create("测试", list);
        table.setTableData(tableData);*/

        //Json
        String json="{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true,\"links\":[{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"},{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"},{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"},{\"name\":\"Google\",\"url\":\"http://www.google.com\"},{\"name\":\"Baidu\",\"url\":\"http://www.baidu.com\"},{\"name\":\"SoSo\",\"url\":\"http://www.SoSo.com\"}]}";
        MapTableData tableData = MapTableData.create("Json表格",JsonHelper.jsonToMapList(json));
        table.setTableData(tableData);

       /* String url = "http://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
        OkHttpUtils
                .get()
                .tag(this)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            HashMap<String,Object> objects = JsonHelper.reflect(jsonObject);
                            List<Map<String,Object>> mapList = new ArrayList<>();
                            mapList.add(objects);
                            MapTableData tableData = MapTableData.create("Map表格",mapList,null);
                            table.setTableData(tableData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
