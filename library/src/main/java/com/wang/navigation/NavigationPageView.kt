package com.wang.navigation

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.wang.navigation.adapter.FragmentViewPagerAdapter
import com.wang.navigation.listener.OnTabItemSelectedListener
import java.util.*

/**
 * @WYU-WIN
 * @date 2020/7/17 0017.
 * description：
 */
class NavigationPageView : View, PageController {
    //////////////////////////////////////////////////
    //数据准备
    //////////////////////////////////////////////////
    private var containerId = 0
    private val fragmentClassList: MutableList<Class<*>> = ArrayList()
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val iconRectList: MutableList<Rect> = ArrayList()
    private var defaultIcon: Int = 0
    private var selectIcon: Int = 0
    private var defaultBitmap: Bitmap? = null
    private var selectBitmap: Bitmap? = null
    private var defaultColor = Color.parseColor("#999999")
    private var selectColor = Color.parseColor("#ff5d5e")
    private var backgroudColor = Color.parseColor("#FFFFFF")
    private var selectedListener: OnTabItemSelectedListener? = null
    private var navigationPager: NavigationPager? = null
    private val paint = Paint()
    private var itemCount = 0
    private var currentSelectIndex = -1
    private var defaultSelectIndex = 0
    private var iconWidth = dp2px(10f)
    private var iconHeight = dp2px(10f)
    private var iconMargin = dp2px(5f)
    private var marginBottom = dp2px(50f)
    private var iconBaseY = 0

    constructor(context: Context) : super(context, null) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    override fun setContainer(containerId: Int): NavigationPageView {
        this.containerId = containerId
        return this
    }

    override fun setColors(defaultColor: Int, selectColor: Int): NavigationPageView {
        this.defaultColor = defaultColor
        this.selectColor = selectColor
        return this
    }

    override fun setIcons(defaultIcon: Int, selectIcon: Int): NavigationPageView {
        this.defaultIcon = defaultIcon
        this.selectIcon = selectIcon
        return this
    }

    override fun setIconWidth(iconWidth: Int): NavigationPageView {
        this.iconWidth = dp2px(iconWidth.toFloat())
        return this
    }

    override fun setIconHeight(iconHeight: Int): NavigationPageView {
        this.iconHeight = dp2px(iconHeight.toFloat())
        return this
    }

    override fun setMargin(iconMargin: Int): NavigationPageView {
        this.iconMargin = dp2px(iconMargin.toFloat())
        return this
    }

    override fun setMarginBottom(iconMargin: Int): NavigationPageView {
        this.marginBottom = dp2px(iconMargin.toFloat())
        return this
    }

    override fun addPage(
        fragmentClass: Class<*>
    ): NavigationPageView {
        fragmentClassList.add(fragmentClass)
        return this
    }

    override fun setDefaultSelect(selectIndex: Int): NavigationPageView { //从0开始
        defaultSelectIndex = selectIndex
        return this
    }

    override fun setSelect(index: Int) {
        if (navigationPager == null || index >= fragmentList.size || index < 0) {
            return
        }
        if (navigationPager!!.enableSlide()) {
            navigationPager!!.setCurrentItem(index)
        } else {
            switchFragment(index)
        }
        invalidate()
    }


    override fun getFragmentByIndex(index: Int): Fragment? {
        return if (index >= fragmentList.size) {
            null
        } else fragmentList[index]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun build(): NavigationPager {
        itemCount = fragmentClassList.size
        //预创建icon的Rect并缓存
        if (selectIcon != 0 && defaultIcon != 0) {
            defaultBitmap = getBitmap(defaultIcon)
            selectBitmap = getBitmap(selectIcon)
        }

        for (i in 0 until itemCount) {
            val rect = Rect()
            iconRectList.add(rect)
            val clx = fragmentClassList[i]
            try {
                val fragment = clx.newInstance() as Fragment
                fragmentList.add(fragment)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        if (navigationPager == null) {
            navigationPager = NavigationPager()
        }
        return navigationPager!!
    }

    inner class NavigationPager {
        private var mViewPager: ViewPager? = null
        private var mPageChangeListener: ViewPagerPageChangeListener? = null
        private var mViewPagerAdapter: FragmentViewPagerAdapter? = null
        private var enableSlide = false
        fun setWithViewPager(viewPager: ViewPager?): NavigationPager {
            if (viewPager == null) {
                enableSlide = false
                return this
            }
            mViewPager = viewPager
            mViewPagerAdapter = FragmentViewPagerAdapter(
                (context as AppCompatActivity).supportFragmentManager,
                fragmentList
            )
            mViewPager!!.adapter = mViewPagerAdapter
            if (mPageChangeListener != null) {
                mViewPager!!.removeOnPageChangeListener(mPageChangeListener!!)
            } else {
                mPageChangeListener = ViewPagerPageChangeListener()
            }
            mViewPager!!.addOnPageChangeListener(mPageChangeListener!!)
            enableSlide = true
            return this
        }

        fun enableSlide(): Boolean {
            return enableSlide
        }

        fun setCurrentItem(item: Int) {
            if (mViewPager != null) {
                mViewPager!!.currentItem = item
            }
        }

        fun apply() {
            if (mViewPager == null) {
                switchFragment(defaultSelectIndex)
            }
            invalidate()
        }
    }

    override fun setTabItemSelectedListener(listener: OnTabItemSelectedListener?) {
        selectedListener = listener
    }

    private inner class ViewPagerPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
            if (selectedListener != null) {
                selectedListener!!.onSelected(position, currentSelectIndex)
            }
            uploadSelect(position)
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    //////////////////////////////////////////////////
    //初始化数据基础
    //////////////////////////////////////////////////

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        if (itemCount != 0 && selectBitmap != null && defaultBitmap != null) {
            //图标边长
            //图标间距margin
            val localIconMargin = dp2px(iconMargin.toFloat())
            val localMarginBottom = dp2px(marginBottom.toFloat())
            //单个item宽高
            var itemLen = iconWidth * itemCount + localIconMargin * (itemCount - 1)
            var itemStartX = (width - itemLen) / 2
            //从而计算得出图标的起始top坐标、文本的baseLine
            iconBaseY = height - localMarginBottom
            //对icon的rect的参数进行赋值
            for (i in 0 until itemCount) {
                val rectX = i * (iconWidth + localIconMargin) + itemStartX
                // 计算没有弧度的Item的Rect
                val temp = iconRectList[i]
                temp.left = rectX
                temp.top = iconBaseY
                temp.right = rectX + iconWidth
                temp.bottom = iconBaseY + iconHeight
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (itemCount != 0) {
            paint.isAntiAlias = true
            paint.color = backgroudColor
            // 画背景颜色
            for (i in 0 until itemCount) {
//                paint.color = backgroudColor
//                val isRoundItem = roundList[i]
//                if (isRoundItem) {
//                    // 画弧形区域
//                    val rect = iconRectList[i]
//                    val radius = height / 2
//                    canvas.drawCircle(
//                        rect.centerX().toFloat(),
//                        radius.toFloat(),
//                        radius.toFloat(),
//                        paint
//                    )
//                }
                //画对应图标
                var bitmap = if (i == currentSelectIndex) {
                    selectBitmap
                } else {
                    defaultBitmap
                }
                val rect = iconRectList[i]
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, null, rect, paint)
                }
            }
        }
    }

    ///////////////////////////////////////////////////////
    // 内部处理方法区域
    ///////////////////////////////////////////////////////
    private fun getBitmap(resId: Int): Bitmap {
        val bitmapDrawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(resId, null) as BitmapDrawable
        } else {
            context.resources.getDrawable(resId) as BitmapDrawable
        }
        return bitmapDrawable.bitmap
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 更新选中状态
     */
    private fun uploadSelect(index: Int) {
        if (navigationPager == null || index >= fragmentList.size || index < 0) {
            return
        }
        currentSelectIndex = index
        invalidate()
    }

    //////////////////////////////////////////////////
    //碎片处理代码
    //////////////////////////////////////////////////
    private var currentFragment: Fragment? = null
    private fun switchFragment(pageIndex: Int) {
        Log.i(
            "BottomBar.Java",
            "pageIndex : $pageIndex , currentSelectIndex : $currentSelectIndex"
        )
        if (pageIndex == currentSelectIndex || pageIndex < 0 || pageIndex >= fragmentList.size) {
            return
        }
        currentSelectIndex = pageIndex
        val fragment = fragmentList[pageIndex]
        val frameLayoutId = containerId
        if (fragment != null) {
            //只支持AppCompatActivity
            val transaction =
                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            if (fragment.isAdded) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment!!).show(fragment)
                } else {
                    transaction.show(fragment)
                }
            } else {
                if (currentFragment != null) {
                    transaction.hide(currentFragment!!).add(frameLayoutId, fragment)
                } else {
                    transaction.add(frameLayoutId, fragment)
                }
            }
            currentFragment = fragment
            transaction.commit()
        }
    }

}