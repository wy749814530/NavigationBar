package com.avigation.view;


import androidx.fragment.app.Fragment;

import com.avigation.listener.OnTabItemSelectedListener;

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
public interface ItemController {
    /**
     * 设置选中项
     *
     * @param index 顺序索引
     */
    void setSelect(int index);

    /**
     * 获取当前选中项索引
     *
     * @return 索引
     */
    Fragment getFragmentByIndex(int index);

    /**
     * 获取导航按钮总数
     *
     * @return 总数
     */
    int getItemCount();

    /**
     * 获取导航按钮文字
     *
     * @param index 顺序索引
     * @return 文字
     */
    String getItemTitle(int index);

    /**
     * 设置对应Fragment 的ID
     *
     * @param containerId
     * @return
     */
    NavigationView setContainer(int containerId);

    NavigationView setBackgroudColor(int backgroudColor);
    /**
     * 设置选中前与选中后文字颜色
     *
     * @param defaultColor
     * @param selectColor
     * @return
     */
    NavigationView setColors(int defaultColor, int selectColor);

    NavigationView setTitleSize(int titleSizeInDp);

    NavigationView setIconWidth(int iconWidth);

    NavigationView setTitleIconMargin(int titleIconMargin);

    NavigationView setIconHeight(int iconHeight);

    NavigationView addRoundItem(Class fragmentClass, String title, int iconResBefore, int iconResAfter);

    NavigationView addItem(Class fragmentClass, String title, int iconResBefore, int iconResAfter);

    NavigationView setDefaultSelect(int firstCheckedIndex);

    NavigationView.NavigationPager build();

    /**
     * 导航栏按钮点击监听
     *
     * @param listener {@link OnTabItemSelectedListener}
     */
    void setTabItemSelectedListener(OnTabItemSelectedListener listener);
}
