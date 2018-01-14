# SmartTable
[![](https://jitpack.io/v/huangyanbin/smartTable.svg)](https://jitpack.io/#huangyanbin/smartTable)
###### A Android automatically generated table framework
* [Chinese README](/README.md/)
* [Apk download 1.3version](/img/smartTable.apk)
* [Version of history](/README_old_version_en.md/)
> function introduction
1. fast configuration automatic generation of tables;
2. automatic calculation table width;
3. table column header combination;
4. the table holds the left sequence, the top sequence, the first row, the column heading, and the statistics row;
5. automatic statistics, sort (also can customize the statistical rules);
6. format graphic, serial number, column title formatting;
7. configuration, background, text, grid, padding and so on;
8. form notation;
9. table content, column title click event;
10. zoom mode and scroll mode;
11. annotation mode;
12. contents are displayed in many lines.
13. pagination mode;
14. dynamically added data at the end of the end.
15. rich formatting;
16. support two dimensional array display (for similar timetable, film ballot, etc.)
>  video display

![set table background](/img/bg.png)

![Base function](/img/table.gif)

![zoom function](/img/zoom.gif)

![progress](/img/progress.jpg)

![seat](/img/seat.jpg)
> How Use

- Quote

> *  Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

> * 2. Add the dependency

```gradle
dependencies {
	        compile 'com.github.huangyanbin:SmartTable:1.5'
	}
```

> * Use SmartTable

```
 <com.bin.david.form.core.SmartTable
       android:id="@+id/table"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
      />
 ```

**- Annotation mode**

> *  Add annotations to the class that you need to generate

```
@SmartTable(name="table name")
```

> *  Add notes to fields you need to display

```
// ID sorts the table for the table in which the field is located
 @SmartColumn(id =1,name = "ColumnName")
```
> * Setting table data

```
        table = findViewById(R.id.table);
        table.setData(list);
```

> The annotation pattern is so simple that you can run directly to see the effect. Of course, this is just the basic configuration of annotations. There are also automatic statistics, column combinations, and so on. If you want to know more about the annotation, please check the demo.


**- Basic mode**
```
    //Common column
   Column<String> column1 = new Column<>("name", "name");
   Column<Integer> column2 = new Column<>("age", "age");
   Column<Long> column3 = new Column<>("time", "time");
    Column<String> column4 = new Column<>("portrait", "portrait");
   //Combination column
  Column totalColumn1 = new Column("Combination name",column1,column2);

  //tableData  Datas is the data that needs to be filled
   final TableData<User> tableData = new TableData<>("表格名",userList,totalColumn1,column3);
   //set data
    table = findViewById(R.id.table);
    //table.setZoom(true,3);//boolean isZoom
    table.setTableData(tableData);

```
**- Basic method introduction**

 There are two parameters in the > ```Column``` construction method, ```IFormat<T>```, ```IDrawFormat<T>```. ```IFormat<T>``` is used to format the display text, such as the update time field ```time``` timestamp in the ```User``` object. We can rewrite this method if we want to display different formats. ```IDrawFormat<T>``` is used to display the drawing format, such as ```User``` object avatars in the field ```portrait``` time stamp, you can use this method, provides a framework for including several ```IDrawFormat``` (text, Bitmap, Resoure pictures, with pictures).


 > ```Column``` provides

 1. whether the automatic sorting ```setAutoCount (Boolean isAutoCount).
 2. whether to arrange ```isReverseSort``` in reverse order
 3. set sort compare ```setComparator```
 4. statistical formatting ```setCountFormat```
 5. click event ```OnColumnItemClickListener```


 The basic methods in > ```TableData```

 1. set sequence ```setSortColumn```
 2. set column headings to format ```settitleDrawFormat```
 3. set the top serial number to format ```setXSequenceFormat```
 4. set the left serial number to format ```setYSequenceFormat```
 5. sets whether to display statistics ```setShowCount```



 The basic methods in > ```TableConfig```

 1. set content text style ```setContentStyle```
 2. set the left sequence text style ```setYSequenceStyle```
 3. set top sequence text style ```setXSequenceStyle```
 4. set column heading text style ```setColumnTitleStyle```
 5. set table title text style ```setTableTitleStyle```
 6. set statistics row style ```setCountStyle```
 7. set column header grid style ```setColumnTitleGridStyle```
 8. set table grid style ```setGridStyle```
 9. set grid column padding ```setVerticalPadding```
 10. set grid row padding ```setHorizontalPadding```
 11. set left sequence background ```setYSequenceBackgroundColor```
 12. set right sequence background ```setXSequenceBackgroundColor```
 13. set column header background ```setColumnTitleBackgroundColor```
 14. set content background ```setContentBackgroundColor```
 15. setting statistics row background ```setCountBackgroundColor```
 16. fixed left ```setFixedYSequence```
 17. fixed top ```setFixedXSequence```
 18. fixed column headings ```setFixedTitle.
 19. fixing the first column ```setFixedFirstColumn```
 20. fixed statistics row```setFixedCountRow```



 ### summary

 After writing SmartChart, you have a further understanding of Android drawing. To do SmartTable, just a small demo, after a week of work stealing a write, basically completed the main function table, there have been combined with other functions, because behind not using, then only the beginning design function, has to meet the daily needs.

 > the use of tables in Android is very few, and the main screen is out of one page, and the user experience is bad. In the implementation process, try to experience a better sense, I feel through fixed title and first line experience is best, so the default set fixed. Of course, you can set it yourself. There are a lot of pits in it. I hope friends in need can use it.
## *License*

SmartTable is released under the Apache 2.0 license.

```
Copyright 2017 Huangyanbin.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at following link.

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitat










