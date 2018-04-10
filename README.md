# SmartTable
```
 android自动生成表格框架
```
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/huangyanbin/smartTable.svg)](https://jitpack.io/#huangyanbin/smartTable)
* [English README](/README.en.md/)
* [历史版本介绍](/README_old_version.md/)
* [更多功能详情介绍](https://juejin.im/post/5a55ae6c5188257350511a8c)
* [apk version 2.0版本下载地址](/img/smartTable.apk)
* [SmartTable2.0基本使用手册持续更新中](/use.md)

> ![下载地址](/img/table.png)

> ![QQ群](/img/SmartTableCode.png)


#####  功能介绍

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
16. 支持二维数组展示（用于类似日程表，电影选票等）；
17. 导入excel(支持颜色，字体，背景，批注，对齐，图片等基本Excel属性)；
18. 表格合并单元(支持注解合并，支持自动合并)；
19. 支持其他刷新框架SmartRefreshLayout；
20. 可配置表格最小宽度(小于该宽度自动适配)；
21. 支持直接List或数组字段转列；
22. 支持Json数据直接转换成表格；
23. 支持表格网格指定行列显示；
24. 支持自动生成表单。

##### 功能展示

![基本功能](/img/table.gif)

> 缩放功能

![缩放](/img/zoom.gif)

> 刷新功能(使用SmartRefreshLayout)

![刷新](/img/refresh.gif)

> 导入Excel(使用Jxl)

![Excel](/img/old_excel.png)
![Excel](/img/new_excel.png)

> 导入二维数组

![日程](/img/progress.jpg)
![头像](/img/avator.jpg)

> List或数组转列

![课表表](/img/arrayColumn.gif)

> 表单功能

![表单功能](/img/1.png)

##### 如何使用

- 引用

>   添加 JitPack repository 到你的build文件

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

> 增加依赖

```gradle
dependencies {
	        compile 'com.github.huangyanbin:SmartTable:2.2.0'
	}
```
     如果你不需要数组转列功能，你可以使用1.7.1版本，需要的话，请使用最新版本2.2.0
     2.0格式化配置有所变化，文档还没写，如果要需求，可以在QQ群询问。

>  使用表格View

```
 <com.bin.david.form.core.SmartTable
       android:id="@+id/table"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
      />
 ```

##### 注解模式

>  在您需要生成的表格的类增加注解

```
@SmartTable(name="表名")
```

> 在你需要显示的字段增加注解

```
// id为该字段所在表格排序位置
 @SmartColumn(id =1,name = "列名")
 //如果需要查询到该成员变量里面去，通过设置type实现
 @SmartColumn(type = ColumnType.Child)
```
> 设置表格数据

```
  table = findViewById(R.id.table);
  table.setData(list);
```

    注解模式就是这么简单，你可以直接运行查看效果了。当然这只是注解基本配置，注解里面还有自动统计，列组合等，如果你想要了解注解更多，请查看demo.


#####  基本模式
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

##### 注解模式更多功能

>  ```@SmartTable```表格注解，用于生成表格。

注解 | 作用
---|---
name | 表格名
count | 是否显示统计行
pageSize | 页数量
currentPage | 当前页

> ```@SmartColumn```列，用于注解列。

注解 | 作用
---|---
name | 列标题
id |  列排序位置(越小越在前面)
parent | 父列名称(不设置则没有父列)
align  | 列对齐方式
type   | 设置是否查询下一级
autoMerge |设置是否自动合并
maxMergeCount |合并最大数量
autoCount |是否开启统计
fixed |是否固定该列


> 解释：

- align

      设置该列对齐方式，默认居中

- type

      有ColumnType.Own,ColumnType.Child,两个值可以设置，假设UserInfo 有个属性是Family family对象，你想解析faily对象的属性monther,father两个属性，则需要设置Child，并在monther,father下添加相对应的注解@SmartColumn，否则只解析到Family，默认是Own。

- autoMerge

      假设你返回的数据格式化之后该列附近数据有相同，则会自动合并成一个单元格，默认不开启合并。

- autoCount

       table 开启显示统计行，设置autoCount为true，则该列可以自动统计，默认为false。

- fixed

       fixed设置为true，该列滚动到最左边时，可以自动固定住。

#####  基本方法介绍


  >  ```Column```类常用方法

    1. 是否自动排序 setAutoCount(boolean isAutoCount)
    2. 是否反序排列 isReverseSort
    3. 设置排序比较 setComparator
    4. 统计格式化 setCountFormat
    5. 列内容点击事件 OnColumnItemClickListener
    6. 滑动到表格左边时固定列 setFixed
    7.  设置列对齐 setTextAlign
    8. 设置开启自动合并 setAutoMerge
    9. 设置开启最大数量 setMaxMergeCount
    10. 设置绘制样式格式化 setDrawFormat
    11. 设置文字格式化 setFormat



 >  ```TableData```类常用方法

    1. 设置排序列 setSortColumn
    2. 设置列标题格式化 settitleDrawFormat
    3. 设置顶部序列号格式化 setXSequenceFormat
    4. 设置左边序列号格式化 setYSequenceFormat
    5. 设置是否显示统计 setShowCount
    6. 设置列标题绘制格式化 setTitleDrawFormat
    7. 设置X序号行文字格式化 setXSequenceFormat
    8. 设置Y序号列文字格式化 setYSequenceFormat
    9. 设置添加自定义合并规则setUserCellRange(List<CellRange> userCellRange)




 >  ```TableConfig```类常用方法

     1.  设置内容文字样式  setContentStyle
     2.  设置左边序列文字样式 setYSequenceStyle
     3.  设置顶部序列文字样式 setXSequenceStyle
     4.  设置列标题文字样式 setColumnTitleStyle
     5.  设置表格标题文字样式 setTableTitleStyle
     6.  设置统计行样式  setCountStyle
     7.  设置列标题网格样式 setColumnTitleGridStyle
     8.  设置内容网格样式 setGridStyle
     9.  设置网格列padding setVerticalPadding
     10. 设置网格行padding setHorizontalPadding
     11. 设置左序列背景 setYSequenceBackgroundColor
     12. 设置横序行背景 setXSequenceBackgroundColor
     13. 设置列标题背景 setColumnTitleBackgroundColor
     14. 设置内容背景 setContentBackgroundColor
     15. 设置统计行背景 setCountBackgroundColor
     16. 固定左侧 setFixedYSequence
     17. 固定顶部  setFixedXSequence
     18. 固定列标题  setFixedTitle
     19. 固定第一列 setFixedFirstColumn//1.4版本取消了 可以使用Column.setFixed 固定任意列。
     20. 固定统计行  setFixedCountRow
     21. 列标题上下padding setColumnTitleVerticalPadding
     22. 增加列标题左右padding setColumnTitleHorizontalPadding
     22. 序列网格样式 setSequenceGridStyle
     23. 列标题网格样式 columnTitleGridStyle
     24. 设置是否显示顶部序号列 setShowXSequence
     35. 设置是否显示左侧序号列 setShowYSequence
     36. 设置是否显示表格标题 setShowTableTitle
     37. 设置是否显示列标题 isShowColumnTitle
     38. 设置表格最小宽度 setMinTableWidth

> ```SmartTable```类常用方法

    1.  设置列标题点击事件   setOnColumnClickListener
    2.  设置排序列 setSortColumn
    3.  设置是否开启缩放 setZoom(boolean zoom,float maxZoom,float minZoom)
    4.  添加新数据 addData(List<T> t, boolean isFoot)
    5.  设置选中Cell样式 setSelectFormat
    6.  重新计算布局 notifyDataChanged

  ```Column构造方法中还有两个参数 IFormat<T>, IDrawFormat<T>。其中IFormat<T>是用于格式化显示文字，比如User对象中有更新时间字段time时间戳。我们想要显示不同格式，就可以重写该方法。IDrawFormat<T>是用于显示绘制格式化，比如User对象中有头像字段portrait时间戳，就可以使用该方法，框架提供几种IDrawFormat包括（文字、Bitmap、Resoure图片、图文结合）。```

#####   二维数组以及Excel
> 你只需要用ArrayTableData 代替TableData就可以。设置想要展示的二维数组和列标题。

```
  String[] week = {"日","一","二","三","四","五","六"};
        Integer[][] infos = {{0,1,2,1,1,0,1,1,0,1,1,2,3}, {4,2,1,1,0,1,1,0,1,1,2,2,3},
                {2,2,0,1,2,4,1,0,1,3,0,1,1},{2,1,1,0,1,4,0,1,1,2,2,0,3},
                {0,1,2,4,1,0,1,4,0,1,1,2,2}, {1,0,1,3,2,2,0,1,2,1,1,0,4},
                {3,1,2,4,0,1,2,1,1,0,1,1,0}};
  ArrayTableData<Integer> tableData = ArrayTableData.create("日程表",week,infos,new IDrawFormat<Integer>(){...});

```
> 请参考demo和[好用漂亮的Android 表格框架2](https://juejin.im/post/5a5dce7651882573256bd043)

#####  数组或者List转列以及Json表格

> 请参考demo和[好用漂亮的Android 表格框架3](https://juejin.im/post/5a7a8eef5188257a84621eda)


##### 关于混淆

    keep解析数据的类以及SmartTable类。


### 总结

    这次写了SmartTable,本来没想写那么多，结果越写越多。功能也很完善了，好多新的想法由于之前没有考虑到，无法进一步实现。但对基础的Excel支持我觉得已经很完美了，甚至可以展示Excel中图表（使用SmartChart）。如果你在android端需要使用表格，这个肯定可以满足你的需求，希望有需要的同学可以使用它。

### 打赏
     如果你觉得对你有帮助，客官打个赏！

![打赏](/img/dashang.jpg)


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


