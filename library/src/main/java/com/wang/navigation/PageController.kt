package com.wang.navigation

import androidx.fragment.app.Fragment
import com.wang.navigation.NavigationView
import com.wang.navigation.listener.OnTabItemSelectedListener

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
interface PageController {

    /**
     * 设置对应Fragment 的ID
     *
     * @param containerId
     * @return
     */
    fun setContainer(containerId: Int): NavigationPageView

    /**
     * 设置默认选中的Fragment
     *
     * @param defaultIndex 顺序索引
     */
    fun setDefaultSelect(defaultIndex: Int): NavigationPageView

    /**
     * 添加导航页
     */
    fun addPage(
        fragmentClass: Class<*>
    ): NavigationPageView

    /**
     * 构建导航
     */
    fun build(): NavigationPageView.NavigationPager

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
     * 设置导航指示器颜色
     *
     * @param defaultColor
     * @param selectColor
     * @return
     */
    fun setColors(defaultColor: Int, selectColor: Int): NavigationPageView

    /**
     * 设置导航指示器图片
     */
    fun setIcons(defaultIcon: Int, selectIcon: Int): NavigationPageView

    /**
     * 设置导航按钮图标大小
     */
    fun setIconWidth(iconWidth: Int): NavigationPageView
    fun setIconHeight(iconHeight: Int): NavigationPageView

    /**
     * 设置Item上，中，下最小间距
     */
    fun setMargin(titleIconMargin: Int): NavigationPageView

    /**
     *设置导航指示器距离底部的间距
     */
    fun setMarginBottom(iconMargin: Int): NavigationPageView

    /**
     * 导航栏按钮点击监听
     *
     * @param listener [OnTabItemSelectedListener]
     */
    fun setTabItemSelectedListener(listener: OnTabItemSelectedListener?)
}