package com.bin.david.form.data.table;

import com.bin.david.form.data.Column;
import com.bin.david.form.data.format.title.ITitleDrawFormat;
import com.bin.david.form.data.table.TableData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2017/11/15.
 * 提供分页的表数据
 */
public class PageTableData<T> extends TableData<T> {

    /**
     * 表总数据
     */
    private List<T> totalData;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 总页
     */
    private int totalPage;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 当前页数据
     */
    List<T> pageData;

    /**
     *
     * @param tableName 表名
     * @param t 数据
     * @param columns 列列表
     */
    public PageTableData(String tableName, List<T> t,List<Column> columns) {
        this(tableName,t,columns,null);
    }
    /**
     *
     * @param tableName 表名
     * @param t 数据
     * @param columns 列列表
     */
    public PageTableData(String tableName, List<T> t, Column... columns) {
        this(tableName,t, Arrays.asList(columns));
    }
    /**
     *
     * @param tableName 表名
     * @param t 数据
     * @param columns 列列表
     * @param titleDrawFormat 列标题绘制格式化
     */
    public PageTableData(String tableName, List<T> t,List<Column> columns, ITitleDrawFormat titleDrawFormat) {
        super(tableName, t, columns, titleDrawFormat);
        pageData = new ArrayList<>();
        totalData = t;
        pageSize =t.size();
        currentPage = 0;
        totalPage = 1;
    }

    /**
     * 获取当前页
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置指定页
     * @param currentPage
     */
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

    /**
     * 获取页数
     * @return 总页数
     */
    public int getTotalPage() {
        return totalPage;
    }


    /**
     *  获取页数量
     * @return 页数量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页数量
     * @param pageSize 页数量
     */
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
