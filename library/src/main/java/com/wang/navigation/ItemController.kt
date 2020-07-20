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
     * 设置对应Fragment 的ID
     *
     * @param containerId
     * @return
     */
    fun setContainer(containerId: Int): NavigationView

    /**
     * 设置默认选中的Fragment
     *
     * @param defaultIndex 顺序索引
     */
    fun setDefaultSelect(defaultIndex: Int): NavigationView

    /**
     * 添加圆形凸起的Item
     */
    fun addRoundItem(
        fragmentClass: Class<*>,
        title: String,
        defaultIcon: Int,
        selectIcon: Int
    ): NavigationView

    /**
     * 添加普通Item
     */
    fun addItem(
        fragmentClass: Class<*>,
        title: String,
        iconResBefore: Int,
        iconResAfter: Int
    ): NavigationView

    /**
     * 构建导航
     */
    fun build(): NavigationView.NavigationPager

    /**
     * 设置选中项
     *
     * @param index 顺序索引
     */
    fun setSelect(index: Int)

    /**
     * 获取对应Fragment实例
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
     * 设置导航栏背景颜色
     */
    fun setBackgroudColor(backgroudColor: Int): NavigationView

    /**
     * 设置选中前与选中后文字颜色
     *
     * @param defaultColor
     * @param selectColor
     * @return
     */
    fun setColors(defaultColor: Int, selectColor: Int): NavigationView

    /**
     * 设置item文字颜色
     */
    fun setTextSize(titleSizeInDp: Int): NavigationView

    /**
     * 设置导航按钮图标大小
     */
    fun setIconWidth(iconWidth: Int): NavigationView
    fun setIconHeight(iconHeight: Int): NavigationView

    /**
     * 设置Item上，中，下最小间距
     */
    fun setMargin(titleIconMargin: Int): NavigationView

    /**
     * 导航栏按钮点击监听
     *
     * @param listener [OnTabItemSelectedListener]
     */
    fun setTabItemSelectedListener(listener: OnTabItemSelectedListener?)
}