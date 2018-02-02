package com.bin.david.form.data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2018/2/1.
 * */

public class ColumnNode {

    private String name;
    private List<ColumnNode> children;
    private ArrayColumn arrayColumn;
    private ColumnNode parent;

    public ColumnNode(String name, ColumnNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public ColumnNode(String name, ColumnNode parent, ArrayColumn arrayColumn) {
        this(name,parent);
        this.arrayColumn = arrayColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnNode> getChildren() {
        return children;
    }


    public ArrayColumn getArrayColumn() {
        return arrayColumn;
    }

    public void setArrayColumn(ArrayColumn arrayColumn) {
        this.arrayColumn = arrayColumn;
    }

    public ColumnNode getParent() {
        return parent;
    }

    public void setParent(ColumnNode parent) {
        this.parent = parent;
    }

    public int getTotalLine(int position){
        int maxLineSize = 1;
        for(ColumnNode child :children){
            int childLineSize = child.getTotalLine(position);
            if(maxLineSize < childLineSize){
                maxLineSize = childLineSize;
            }
        }
        if(arrayColumn !=null){
            int count = arrayColumn.getLineCount(position);
            maxLineSize = Math.max(count,maxLineSize);
        }
        return maxLineSize;
    }

    /**
     * 判断节点位置
     * @param node
     * @param level
     * @return
     */
    public static int getNodeLevel(ColumnNode node,int level){
        ColumnNode parent = node.getParent();
        if(parent != null) {
            if (parent.arrayColumn != null) {
                level += 1;
            }
            getNodeLevel(parent, level);
        }
        return level;
    }

    public static int getPositionReize(ColumnNode node,int position){
        int total = 1;
        if(node.getChildren().size() >0){
            for(ColumnNode child: node.getChildren()){
                if(child.arrayColumn !=null){
                    int[] result = child.arrayColumn.getPerStartAndEnd(position);
                    for(int i = result[0]; i < result[1];i++){
                        total +=  getPositionReize(child,i);
                    }
                }
            }
        }else if(node.arrayColumn !=null){
            return getPositionReize(node.getParent(),position);
        }
        return total;
    }


}
