package com.bin.david.smarttable.bean;

/**
 * Created by huang on 2017/10/13.
 */

public enum TableStyle {

  FIXED_X_AXIS("固定顶部"),
  FIXED_Y_AXIS("固定左侧"),
  FIXED_TITLE("固定标题"),
  @Deprecated
  FIXED_FIRST_COLUMN("固定第一列"),
  FIXED_COUNT_ROW("固定统计行"),
  ZOOM("是否缩放"),
  ALIGN("文字位置"),
  ANIM("动画"),
  FLING("Fling"),
  SHOW_SEQ("隐藏序列");





    public String value;

     TableStyle(String value){
        this.value = value;
    }


}
