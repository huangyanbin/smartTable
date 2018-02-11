package com.bin.david.form.data;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/2/5.
 * 数组结构
 * 用于处理数组解析所占格子数量计算
 */

public class ArrayStructure {

    private SparseArray<List<Integer>> structureArray; //结构
    private int maxLevel;
    private List<Integer> cellSizes;
    private boolean isEffective; //是否是有效的


    public ArrayStructure(){
        structureArray = new SparseArray<>();
    }

    /**
     * 放入null对象结构数量
     * @param level
     * @param isFoot
     */
    public void putNull(int level,boolean isFoot){
        if(isEffective&& level <= maxLevel){
            for(int i = level;i <=maxLevel;i++){
                put(i,1,isFoot);
            }
        }
    }

    /**
     * 放入Array数量
     * @param level 层级
     * @param isFoot 是否插入尾部
     */
    public void put(int level,int arraySize,boolean isFoot){
        if(isEffective) {
            List<Integer> structures = structureArray.get(level);
            if (structures == null) {
                structures = new ArrayList<>();
                structureArray.put(level, structures);
            }
            recordPerSizeList(structures,arraySize,isFoot);
        }
    }


    /**
     * 记录当前ArraySize数量
     */
    private void recordPerSizeList(List<Integer> structure,int size,boolean isFoot){
        int perListSize = structure.size();
        if( perListSize== 0){
            structure.add(size-1);
        }else{
            int per = structure.get(perListSize-1);
            if(isFoot) {
                structure.add(per + size);
            }else{
                moveArrayPosition(structure,size);
                structure.add(0,size-1);
            }
        }
    }
    /**
     * 移动Array结构数量位置
     * 插入head需要移动
     */
    private void moveArrayPosition(List<Integer> structure,int moveSize){
        int totalSize = structure.size();
        for(int i =0;i <totalSize;i++){
            Integer perSize = structure.get(i);
            perSize+=moveSize;
            structure.set(i,perSize);
        }
    }

    /**
     * 清空
     */
    public void clear(){
        structureArray.clear();
    }

    /**
     * 获取指定层级位置占格子数量
     * @param level 层级
     * @param position 位置
     * @return 占格子数量
     */
    public int getLevelCellSize(int level,int position){
        if(maxLevel <= level){
            return 1;
        }
        int[] startAndEnd = getPerStartAndEnd(level+1,position);
        if(startAndEnd !=null){
            startAndEnd= getStartAndEnd(level+2,startAndEnd[0],startAndEnd[1]);
            return startAndEnd[1]- startAndEnd[0]+1;
        }
        return 1;
    }




    private int[] getStartAndEnd(int level,int start,int end){
        if(structureArray.get(level) != null) {
            int[] starts =getPerStartAndEnd(level, start);
            int[] ends = getPerStartAndEnd(level, end);
            if(starts == null || ends ==null){
                return new int[]{start,end};
            }
            return getStartAndEnd(level + 1, starts[0], ends[1]);
        }
        return new int[]{start,end};
    }

    private int[] getPerStartAndEnd(int level,int position){
        List<Integer> structures  = structureArray.get(level);
        if(structures !=null &&structures.size() >position) {
            int end = structures.get(position);
            int start = 0;
            if (position > 0) {
                start = structures.get(position - 1)+1;
            }
            return new int[]{start, end};
        }
        return null;
    }

    /**
     * 当前结构位置每个位置所占格子数
     * @return 格子数
     */
    public List<Integer> getCellSizes() {
        return cellSizes;
    }

    public void setCellSizes(List<Integer> cellSizes) {
        this.cellSizes = cellSizes;
    }
    /**
     * 获取保存最大层级
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * 设置保存最大层级
     * @param maxLevel 最大层级
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * 是否是有效的数据结构
     * @return 有效的数据结构
     */
    public boolean isEffective() {
        return isEffective;
    }
    /**
     * 设置是否是有效的数据结构
     */
    public void setEffective(boolean effective) {
        isEffective = effective;
    }
}
