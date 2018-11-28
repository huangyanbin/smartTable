package com.bin.david.smarttable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.ChildData;
import com.bin.david.smarttable.bean.Lesson;
import com.bin.david.smarttable.bean.LessonPoint;
import com.bin.david.smarttable.bean.Student;
import com.bin.david.smarttable.bean.TanBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * Created by owp on 2018/11/28.
 */
public class NestedScrollActivity extends AppCompatActivity {

    private NestedScrollView scrollView;
    private Button btn;
    private SmartTable<Student> table;

    private boolean isCeiling;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_table);

        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this,15)); //设置全局字体大小
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        table = (SmartTable<Student>) findViewById(R.id.table);


        table.setNestedScrollingEnabled(true);


        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.setCeiling(isCeiling = !isCeiling);
                btn.setText(isCeiling ? "普通效果" : "吸顶效果");
            }
        });

        table.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                table.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                table.getLayoutParams().height = scrollView.getMeasuredHeight();

                loadData();
            }
        });
    }

    private void loadData() {
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
        table.setData(students);
        table.getConfig().setShowTableTitle(true);
        table.getConfig().setShowXSequence(true);
        table.getConfig().setShowYSequence(true);
        table.setZoom(true,2,0.2f);
    }
}
