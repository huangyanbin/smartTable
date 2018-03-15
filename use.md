## SmartTable 2.0

##### 背景

> 在SmartTable 背景分为两类，一种是修改组件整体背景和组件格子背景。举个栗子，你想要修改整个内容区域背景颜色，只需要

```
  table.getConfig().setContentBackground(new IBackgroundFormat() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(设置你想要的颜色);
                canvas.drawRect(rect,paint);
            }
        });

```
> 而如果你想要内容行间隔交替变色

```
table.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {
                if(cellInfo.row%2==0){
                    paint.setColor(设置你想要的颜色);
                   canvas.drawRect(rect,paint);
                }
            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                return 0;
            }
        });

```

> 下面是所有组件背景的配置：

```
        //设置内容背景
        table.getConfig().setContentBackground(new IBackgroundFormat(){...});
         //设置X序号行背景(顶部序号)
        table.getConfig().setXSequenceBackground(new IBackgroundFormat(){...});
          //设置Y序号行背景(左侧序号)
        table.getConfig().setYSequenceBackground(new IBackgroundFormat(){...});
          //设置统计行背景 (需要开启统计行)
        table.getConfig().setCountBackground(new IBackgroundFormat(){...});
           //设置列标题背景
        table.getConfig().setColumnTitleBackground(new IBackgroundFormat(){...});
```

> 组件格子背景的配置：

```
        table.getConfig().setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {...});
        table.getConfig().setColumnCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {...});
        table.getConfig().setCountBgCellFormat(new ICellBackgroundFormat<CellInfo>() {...});
        table.getConfig().setXSequenceCellBgFormat(new ICellBackgroundFormat<CellInfo>() {...});
        table.getConfig().setYSequenceCellBgFormat(new ICellBackgroundFormat<CellInfo>() {...}) ;
```

> 细心的同学肯定发现，组件背景实现是```IBackgroundFormat```，而组件格子背景是实现```ICellBackgroundFormat```。

> ```IBackgroundFormat```中需要实现 ```public void drawBackground(Canvas canvas, Rect rect, Paint paint)```,有了画布，绘制范围，画笔三个参数就可以绘制背景了，你可以绘制背景是一个圆，五角星，或者一张图片。如果你只是想设置背景颜色，你可以使用基本实现类```new BaseBackgroundFormat(getResources().getColor(R.color.white))```。

> ```ICellBackgroundFormat```中需要实现 ``` public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint)， public int getTextColor(CellInfo cellInfo)```,有了画布，绘制范围，画笔三个参数就可以绘制背景了，在CellInfo 里面有一个格子的基本信息，比如数据data,行row 列 col ,你可以根据条件判断是否绘制，咋绘制。```getTextColor```方法，你可以根据格子信息来调整字体的颜色，这样可以保持格子的美观。如果你只是想改变格子的北京颜色，你可以使用实现类 ICellBackgroundFormat

        下面是内容背景间隔颜色实例
```
 ICellBackgroundFormat<Integer> backgroundFormat = new BaseCellBackgroundFormat<Integer>() {
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
```
