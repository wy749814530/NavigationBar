package com.wang.navigation.listener

/**
 * 导航栏按钮选中事件监听
 */
interface OnTabItemSelectedListener {
    /**
     * 选中导航栏的某一项
     * @param index 索引导航按钮，按添加顺序排序
     * @param old   前一个选中项，如果没有就等于-1
     */
    fun onSelected(index: Int, old: Int)

    /**
     * 重复选中
     * @param index 索引导航按钮，按添加顺序排序
     */
    fun onRepeat(index: Int) /*    */
    /**
     * 长按选择
     * @param index 索引导航按钮，按添加顺序排序
     */
    /*
    void onLongClick(int index);*/
}