package com.wang.navigation

import androidx.fragment.app.Fragment
import com.wang.navigation.NavigationView
import com.wang.navigation.listener.OnTabItemSelectedListener

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
interface ItemController {
    /**
     * 设置选中项
     *
     * @param index 顺序索引
     */
    fun setSelect(index: Int)

    /**
     * 获取当前选中项索引
     *
     * @return 索引
     */
    fun getFragmentByIndex(index: Int): Fragment?

    /**
     * 获取导航按钮总数
     *
     * @return 总数
     */
    fun getItemCount(): Int
    /**
     * 获取导航按钮文字
     *
     * @param index 顺序索引
     * @return 文字
     */
    fun getItemTitle(index: Int): String?

    /**
     * 设置对应Fragment 的ID
     *
     * @param containerId
     * @return
     */
    fun setContainer(containerId: Int): NavigationView?
    fun setBackgroudColor(backgroudColor: Int): NavigationView?

    /**
     * 设置选中前与选中后文字颜色
     *
     * @param defaultColor
     * @param selectColor
     * @return
     */
    fun setColors(defaultColor: Int, selectColor: Int): NavigationView?
    fun setTitleSize(titleSizeInDp: Int): NavigationView?
    fun setIconWidth(iconWidth: Int): NavigationView?
    fun setTitleIconMargin(titleIconMargin: Int): NavigationView?
    fun setIconHeight(iconHeight: Int): NavigationView?
    fun addRoundItem(
        fragmentClass: Class<*>,
        title: String,
        iconResBefore: Int,
        iconResAfter: Int
    ): NavigationView

    fun addItem(
        fragmentClass: Class<*>,
        title: String,
        iconResBefore: Int,
        iconResAfter: Int
    ): NavigationView

    fun setDefaultSelect(firstCheckedIndex: Int): NavigationView?
    fun build(): NavigationView.NavigationPager?

    /**
     * 导航栏按钮点击监听
     *
     * @param listener [OnTabItemSelectedListener]
     */
    fun setTabItemSelectedListener(listener: OnTabItemSelectedListener?)
}