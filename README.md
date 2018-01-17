# SmartTable
[![](https://jitpack.io/v/huangyanbin/smartTable.svg)](https://jitpack.io/#huangyanbin/smartTable)
###### 一款android自动生成表格框架
* [English README](/README.en.md/)
* [apk version 1.6版本下载地址](/img/smartTable.apk)
* [历史版本介绍](/README_old_version.md/)
* [更多功能详情介绍](https://juejin.im/post/5a55ae6c5188257350511a8c)
> 功能介绍
1.  快速配置自动生成表格；
2.  自动计算表格宽高；
3.  表格列标题组合；
4.  表格固定左序列、顶部序列、第一行、列标题、统计行；
5.  自动统计，排序（自定义统计规则）；
6.  表格图文、序列号、列标题格式化；
7.  表格各组成背景、文字、网格、padding等配置；
8.  表格批注；
9.  表格内容、列标题点击事件；
10. 缩放模式和滚动模式；
11. 注解模式；
12. 内容多行显示；
13. 分页模式；
14. 首尾动态添加数据;
15. 丰富的格式化;
16. 支持二维数组展示（用于类似日程表，电影选票等）
17. 支持导入excel
18. 表格合并单元
![日程](/img/progress.jpg)

![选票](/img/seat.jpg)
> 视频展示
![设置背景](/img/bg.png)

![基本功能](/img/table.gif)

![缩放功能](/img/zoom.gif)
> 导入表格
![Excel](/img/excel.png)

---
> 如何使用

- 引用

> * Step 1. 添加 JitPack repository 到你的build文件

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

> * Step 2. 增加依赖

```gradle
dependencies {
	        compile 'com.github.huangyanbin:SmartTable:1.6'
	}
```

> * 使用表格View

```
 <com.bin.david.form.core.SmartTable
       android:id="@+id/table"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
      />
 ```

**- 注解模式**

> *  在您需要生成的表格的类增加注解

```
@SmartTable(name="表名")

```

> *  在你需要显示的字段增加注解

```
// id为该字段所在表格排序位置
 @SmartColumn(id =1,name = "列名")
 //如果需要查询到该成员变量里面去，通过设置type实现
 @SmartColumn(type = ColumnType.Child)
```
> * 设置表格数据

```
        table = findViewById(R.id.table);
        table.setData(list);
```

> 注解模式就是这么简单，你可以直接运行查看效果了。当然这只是注解基本配置，注解里面还有自动统计，列组合等，如果你想要了解注解更多，请查看demo.


**- 基本模式**
```
    //普通列
   Column<String> column1 = new Column<>("姓名", "name");
   Column<Integer> column2 = new Column<>("年龄", "age");
   Column<Long> column3 = new Column<>("更新时间", "time");
    Column<String> column4 = new Column<>("头像", "portrait");
    //如果是多层，可以通过.来实现多级查询
      Column<String> column5 = new Column<>("班级", "class.className");
   //组合列
  Column totalColumn1 = new Column("组合列名",column1,column2);

  //表格数据 datas是需要填充的数据
   final TableData<User> tableData = new TableData<>("表格名",userList,totalColumn1,column3);
   //设置数据
    table = findViewById(R.id.table);
    //table.setZoom(true,3);是否缩放
    table.setTableData(tableData);

```
**- 基本方法介绍**

  >  ```Column```构造方法中还有两个参数 ```IFormat<T>```, ```IDrawFormat<T>```。其中```IFormat<T>```是用于格式化显示文字，比如```User```对象中有更新时间字段```time```时间戳。我们想要显示不同格式，就可以重写该方法。```IDrawFormat<T>```是用于显示绘制格式化，比如```User```对象中有头像字段```portrait```时间戳，就可以使用该方法，框架提供几种```IDrawFormat```包括（文字、Bitmap、Resoure图片、图文结合）。


  >  ```Column```提供了

   1. 是否自动排序```setAutoCount(boolean isAutoCount)```
   2. 是否反序排列```isReverseSort```
   3. 设置排序比较```setComparator```
   4. 统计格式化```setCountFormat```
   5. 点击事件```OnColumnItemClickListener```


 >  ```TableData```中基本方法

1.    设置排序列```setSortColumn```
    2. 设置列标题格式化```settitleDrawFormat```
    3. 设置顶部序列号格式化```setXSequenceFormat```
    4. 设置左边序列号格式化```setYSequenceFormat```
    5. 设置是否显示统计```setShowCount```



 >  ```TableConfig```中基本方法

1.    设置内容文字样式 ```setContentStyle```
     2. 设置左边序列文字样式 ```setYSequenceStyle```
     3. 设置顶部序列文字样式 ```setXSequenceStyle```
     4. 设置列标题文字样式 ```setColumnTitleStyle```
     5. 设置表格标题文字样式 ```setTableTitleStyle```
     6. 设置统计行样式 ```setCountStyle```
     7. 设置列标题网格样式 ```setColumnTitleGridStyle```
     8. 设置表格网格样式 ```setGridStyle```
     9. 设置网格列padding ```setVerticalPadding```
     10. 设置网格行padding ```setHorizontalPadding```
     11. 设置左序列背景 ```setYSequenceBackgroundColor```
     12. 设置右序列背景 ```setXSequenceBackgroundColor```
     13. 设置列标题背景 ```setColumnTitleBackgroundColor```
     14. 设置内容背景 ```setContentBackgroundColor```
     15. 设置统计行背景 ```setCountBackgroundColor```
     16. 固定左侧 ```setFixedYSequence```
     17. 固定顶部  ```setFixedXSequence```
     18. 固定列标题  ```setFixedTitle ```
     19. 固定第一列 ```setFixedFirstColumn``` //1.4版本取消了
     20. 固定统计行```setFixedCountRow```



### 总结

> 写完SmartChart之后，对android 绘图有了进一步的理解。开始了做SmartTable，开始只是一个小demo,经过一星期的上班偷着写，基本完成表格主要功能，本来还有合并等功能，由于后面没有采用，便只做了开始设计功能，已经满足日常需求。

> android中使用表格的场景很少，主要屏幕一页放不下，用户体验不好。在实现过程中，尽量做到体验感好些，我感觉通过固定标题和第一行体验最好，所以默认设置固定。当然你可以自己设置。里面还有不少的坑，希望有需要的朋友可以使用它。

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


