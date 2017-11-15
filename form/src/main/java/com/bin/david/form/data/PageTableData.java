package com.bin.david.form.data;

import com.bin.david.form.data.format.title.ITitleDrawFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2017/11/15.
 * 提供分页的接口
 */
public class PageTableData<T> extends TableData<T> {

    private List<T> totalData;
    private int currentPage;
    private int totalPage;
    private int pageSize;
    List<T> pageData;

    public PageTableData(String tableName, List<T> t,List<Column> columns) {
        this(tableName,t,columns,null);
    }

    public PageTableData(String tableName, List<T> t, Column... columns) {
        this(tableName,t, Arrays.asList(columns));
    }

    public PageTableData(String tableName, List<T> t,List<Column> columns, ITitleDrawFormat titleDrawFormat) {
        super(tableName, t, columns, titleDrawFormat);
        pageData = new ArrayList<>();
        totalData = t;
        pageSize =t.size();
        currentPage = 0;
        totalPage = 1;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if(currentPage <0){
           currentPage =0;
        }else if(currentPage >= totalPage){
            currentPage = totalPage-1;
        }
        this.currentPage = currentPage;
        pageData.clear();
        int totalSize = totalData.size();
        for(int i = currentPage*pageSize; i < (currentPage+1)*pageSize;i++){
            if(i < totalSize) {
                pageData.add(totalData.get(i));
            }
        }

        setT(pageData);
    }

    public int getTotalPage() {
        return totalPage;
    }



    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        int total = totalData.size();
        if(pageSize <1){
            pageSize = 1;
        }else if(pageSize > total){
            pageSize = total;
        }
        this.pageSize = pageSize;
        totalPage = total/pageSize;
        totalPage = total % pageSize == 0 ? totalPage : totalPage+1;
        setCurrentPage(currentPage);
    }
}
