package com.bin.david.smarttable;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.Lesson;
import com.bin.david.smarttable.bean.LessonPoint;
import com.bin.david.smarttable.bean.Student;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.bean.TanBean;
import com.bin.david.smarttable.view.QuickChartDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnnotationModeActivity extends AppCompatActivity {

    private SmartTable<Student> table;
    private QuickChartDialog quickChartDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15));
        quickChartDialog = new QuickChartDialog();
     /*   List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("语文",true));
        lessons.add(new Lesson("数学",true));
        lessons.add(new Lesson("英语",false));
        lessons.add(new Lesson("物理",false));
        lessons.add(new Lesson("化学",true));*/
       List<String> texts =  new ArrayList<>();
        texts.add("测试哈1");
        texts.add("测试哈2");
        texts.add("测试哈3");
        List<Lesson> lessons2 = new ArrayList<>();
        Lesson lesson1 = new Lesson("软件",true);

        lesson1.setTest(texts);
        lesson1.setLessonPoints(new LessonPoint[]{new LessonPoint("软件工程"),new LessonPoint("离散数学")});
        Lesson lesson2 = new Lesson("生物",true);
        lesson2.setLessonPoints(new LessonPoint[]{new LessonPoint("医学构造"),new LessonPoint("生物科技")});
        //lesson2.setTest(texts);
        lessons2.add(lesson1);
        lessons2.add(lesson2);
        lessons2.add(new Lesson("微积分",false));
        Random random = new Random();
      //  Lesson[] lessonArray = new Lesson[]{new Lesson("政治",false),new Lesson("法学",false)};
        List<TanBean> tanBeans = TanBean.initDatas();
        final List<Student> students = new ArrayList<>();
        //测试 从其他地方获取url
        int urlSize = tanBeans.size();
        for(int i = 0;i <30; i++) {
            Student student = new Student("用户"+i, random.nextInt(70), System.currentTimeMillis()
                    - random.nextInt(70)*3600*1000*24,true,new ChildData("测试"+i));
            student.setUrl(tanBeans.get(i%urlSize).getUrl());
            //student.setLessons(i%3 ==0?lessons2:lessons);
            student.setLessons(lessons2);
           // student.setLessonsArray(lessonArray);
            students.add(student);
        }
        table = (SmartTable<Student>) findViewById(R.id.table);
        table.setData(students);
        table.getConfig().setShowTableTitle(true);
        table.getConfig().setShowXSequence(true);
        table.getConfig().setShowYSequence(true);
        table.setZoom(true,2,0.2f);
       /* //设置单个格子背景颜色
        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if("name".equals(cellInfo.column.getFieldName())
                        && cellInfo.row%2 ==1) {
                    return ContextCompat.getColor(AnnotationModeActivity.this, R.color.selectColor);
                }else{
                    return TableConfig.INVALID_COLOR;
                }
            }
            //根据背景颜色设置字体颜色
            @Override
            public int getTextColor(CellInfo cellInfo) {
                if("name".equals(cellInfo.column.getFieldName())
                        && cellInfo.row%2 ==1) {
                    return ContextCompat.getColor(AnnotationModeActivity.this, R.color.white);
                }else{
                    return super.getTextColor(cellInfo);
                }
            }
        });*/
    }

    public void onClick(View view) {
        fling(TableStyle.FLING);
    }

    //飞滚
    private void fling(TableStyle item) {
        quickChartDialog.showDialog(this, item, new String[]{"Left","Top","Right","Bottom"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getMatrixHelper().flingLeft(200);
                } else if (position == 1) {
                    table.getMatrixHelper().flingTop(200);
                }else if(position ==2){
                    table.getMatrixHelper().flingRight(200);
                }else {
                    table.getMatrixHelper().flingBottom(2000);
                }
                table.invalidate();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quickChartDialog = null;
    }
}
