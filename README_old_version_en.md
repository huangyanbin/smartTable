# SmartTable version of history

###### An Android automatically generated table framework


**- version 1.6**

> repair the zoom offset problem
> increase import Excel
Increase cell merge
> increase the upper left corner voids formatting

**- version 1.5**

> fix the zoom problem
> add ArrayTableData to support two dimensional arrays

**- version 1.4**

> delete set the fixed first column setFixedFirstColumn method, column's setFixed (Boolean fixed) to fix any column.
> support SmartTable.addData List<T> t (Boolean isFoot) to add data. Incremental data analysis and computation form size are more efficient.
> repair the zoom center offset problem;
> support content multiline display.
![content multiline display](/img/multline.jpg)


**-  version 1.3**

- setting a single lattice background

In the online reference ```html``` > ```table```, find the pattern pretty much, according to this idea, SmartTable added support for different backgrounds on single grid support, there are 5 ```IBackgroundFormat``` style in ```TableConfig```, according to ```boolean isDraw (T T) to determine whether ` ` ` return data to draw the background of ```drawBackground```, the default of drawing the whole background. Of course, you can define your own ```IBackgroundFormat``` using other shapes.

- setting a single lattices font

Due to a single grid support > background support, font color is also need to adjust according to the background, so it supports a single lattice font settings, ```int getTextColor ```IBackgroundFormat``` (T T) ', you only need to rewrite it, according to the needs of different color settings.

- paging

"Too much data on the client experience is not good, so the development of paging mode, without using the annotation case, only need to use the ```PageTableData``` page table data instead of tabular data before the ```TableData``` class, the ```setPageSize``` method uses the ```PageTableData``` settings page number. The paging is finished.
If you use annotations, please annotate elements in ```@SmartTable```, add ```pageSize``` attribute, setData will return ```PageTableData``` object, you can use it to finish other settings.

- other

> SmartTable adds the notifyDataChanged method to re parse the calculation layout;

> provides the back method fling to the origin;

> increase the network request picture display example.

**-version 1.2**

Automatic statistics, sorting (also customizable statistics rules);
> form annotation;
> zoom mode and scroll mode.

**-  version 1.1**

Table column header combinations;
The table is fixed to the left sequence, the top sequence, the first line, the column headline and the statistical line.
Automatic statistics, sorting (also customizable statistics rules);

> table content, column headlines, click events;



**-  version 1.0**

Faster configuration automatically generates forms;
> automatic calculation form is wide;
> the configuration of the form, text, grid, padding and so on;
Table schema, sequence number, column headline formatting;
> support the annotation mode.
