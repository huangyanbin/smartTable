package com.bin.david.smarttable;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.selected.BaseSelectFormat;
import com.bin.david.form.data.table.FormTableData;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.Form;

/**
 * Created by huang on 2018/4/10.
 */

public class FormModeActivity extends AppCompatActivity {

    private SmartTable<Form> table;
    private View llBottom;
    private Button searchBtn;
    private EditText editText;
    private Form selectForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table);
        table = (SmartTable<Form>) findViewById(R.id.table);
        int dp5 = DensityUtils.dp2px(this,10);
        table.getConfig().setVerticalPadding(dp5)
        .setTextLeftOffset(dp5);
        llBottom = findViewById(R.id.ll_bottom);
        searchBtn = (Button) findViewById(R.id.tv_query);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectForm !=null){
                    selectForm.setName(editText.getText().toString());
                    table.invalidate();
                    editText.setText("");
                }
            }
        });
        editText = (EditText)findViewById(R.id.edit_query) ;
        Form[][] forms = {
                {
                        new Form("姓名", Paint.Align.LEFT), new Form(""),
                        new Form("性别", Paint.Align.LEFT), new Form(""),
                        new Form("出生日期", Paint.Align.LEFT),new Form(""),
                        new Form("民族", Paint.Align.LEFT), new Form(""),
                        new Form("婚否", Paint.Align.LEFT), new Form(""),
                        new Form(1, 4, "照片")
                },
                {
                        new Form("学历", Paint.Align.LEFT), new Form(""),
                        new Form("专业", Paint.Align.LEFT), new Form(3, 1, ""),
                        new Form("何种语言", Paint.Align.LEFT), new Form(3, 1, "")
                },
                {
                        new Form("籍贯", Paint.Align.LEFT), new Form(""),
                        new Form(2, 1, "户口所在地", Paint.Align.LEFT), new Form(3, 1, ""),
                        new Form(""), new Form(2, 1, "")
                },
                {
                        new Form(2, 1, "现住址电话", Paint.Align.LEFT), new Form(8, 1, "")

                },
                {
                        new Form(2, 1, "身份证号码", Paint.Align.LEFT), new Form(4, 1, "")
                        , new Form(2, 1, "暂住证号码", Paint.Align.LEFT), new Form(3, 1, "")
                },
                {
                        new Form(2, 1, "应急联系人及电话", Paint.Align.LEFT), new Form(4, 1, "")
                        , new Form(2, 1, "联系人电话号码", Paint.Align.LEFT), new Form(3, 1, "")
                },
                {
                        new Form(2, 1, "申请职位", Paint.Align.LEFT), new Form(4, 1, "")
                        , new Form(2, 1, "本人要求待遇", Paint.Align.LEFT), new Form(3, 1, "")
                },
               {
                        new Form(11, 1, "家庭成员及主要社会关系")
                },

                {
                        new Form(2, 1, "姓名"),
                        new Form(2, 1, "与本人关系"),
                        new Form(7, 1, "单位及职务"),
                },
                {
                        new Form(2, 1, ""),
                        new Form(2, 1, ""),
                        new Form(7, 1, ""),
                },
                {
                        new Form(2, 1, ""),
                        new Form(2, 1, ""),
                        new Form(7, 1, ""),
                },
                {
                        new Form(2, 1, ""),
                        new Form(2, 1, ""),
                        new Form(7, 1, ""),
                },
                {
                        new Form(2, 1, ""),
                        new Form(2, 1, ""),
                        new Form(7, 1, ""),
                },
                {
                        new Form(2, 1, ""),
                        new Form(2, 1, ""),
                        new Form(7, 1, ""),
                },
                {
                        new Form(2, 1, ""),
                        new Form(2, 1, ""),
                        new Form(7, 1, ""),
                },
                {
                        new Form(11, 1, "工作经历")
                },
                {
                        new Form(4, 1, "起止时间"),
                        new Form(6, 1, "单位"),
                        new Form(1, 1, ""),
                },
                {
                        new Form(4, 1, ""),
                        new Form(6, 1, ""),
                        new Form(1, 1, ""),
                },
                {
                        new Form(4, 1, ""),
                        new Form(6, 1, ""),
                        new Form(1, 1, ""),
                },
                {
                        new Form(4, 1, ""),
                        new Form(6, 1, ""),
                        new Form(1, 1, ""),
                },
                {
                        new Form(11, 1, "本人保证以下资料全部属实，否则本人愿意承担由此造成的一切后果")
                },
                {
                         new Form(2, 1, "申请人签名"), new Form(4, 1, "")
                        , new Form(2, 1, "日期"), new Form(3, 1, "")
                }

        };
        final FormTableData<Form> tableData = FormTableData.create(table, "登记表", 11, forms);
        tableData.setFormat(new IFormat<Form>() {
            @Override
            public String format(Form form) {
                if (form != null) {
                    return form.getName();
                } else {
                    return "";
                }
            }
        });
        table.setSelectFormat(new BaseSelectFormat());
        tableData.setOnItemClickListener(new TableData.OnItemClickListener<Form>() {
            @Override
            public void onClick(Column column, String value, Form form, int col, int row) {
                if(form !=null){
                    selectForm = form;
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }

            }

        });
        table.getConfig().setTableGridFormat(new BaseGridFormat(){
            @Override
            protected boolean isShowHorizontalLine(int col, int row, CellInfo cellInfo) {
                if(row == tableData.getLineSize() -1){
                    return false;
                }
                return true;
            }

            @Override
            protected boolean isShowVerticalLine(int col, int row, CellInfo cellInfo) {
                if(row == tableData.getLineSize() -1){
                    return false;
                }
                return true;
            }
        });
        table.setTableData(tableData);


    }
}
