package com.wang.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.wang.navigation.adapter.FragmentViewPagerAdapter
import com.wang.navigation.control.ItemController
import com.wang.navigation.listener.OnTabItemSelectedListener
import java.util.*


/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
class NavigationView : View, ItemController {
    private var TAG: String = this::class.java.simpleName

    //////////////////////////////////////////////////
    //数据准备
    //////////////////////////////////////////////////
    private var containerId = 0
    private val fragmentClassList: MutableList<Class<*>> = ArrayList()
    private val titleList: MutableList<String?> = ArrayList()
    private val defaultIconResList: MutableList<Int> = ArrayList()
    private val selectIconResList: MutableList<Int> = ArrayList()
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val roundList: MutableList<Boolean> = ArrayList()
    private val iconRectList: MutableList<Rect> = ArrayList()
    private val textXList: MutableList<Int> = ArrayList()
    private var defaultColor = Color.parseColor("#999999")
    private var selectColor = Color.parseColor("#ff5d5e")
    private var backgroudColor = Color.parseColor("#FFFFFF")
    private var selectedListener: OnTabItemSelectedListener? = null
    private var navigationPager: NavigationPager? = null
    private val paint = Paint()
    private var itemCount = 0
    private var currentSelectIndex = -1
    private var defaultSelectIndex = 0
    private var textSize = 12
    private var iconWidth = 30
    private var iconHeight = 30
    private var titleIconMargin = 5

    constructor(context: Context) : super(context, null) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    override fun setContainer(containerId: Int): NavigationView {
        this.containerId = containerId
        return this
    }

    override fun setBackgroudColor(backgroudColor: Int): NavigationView {
        this.backgroudColor = backgroudColor
        return this
    }

    override fun setColors(defaultColor: Int, selectColor: Int): NavigationView {
        this.defaultColor = defaultColor
        this.selectColor = selectColor
        return this
    }

    override fun setTextSize(textSize: Int): NavigationView {
        this.textSize = textSize
        return this
    }

    override fun setIconWidth(iconWidth: Int): NavigationView {
        this.iconWidth = iconWidth
        return this
    }

    override fun setMargin(titleIconMargin: Int): NavigationView {
        this.titleIconMargin = titleIconMargin
        return this
    }

    override fun setIconHeight(iconHeight: Int): NavigationView {
        this.iconHeight = iconHeight
        return this
    }

    override fun addRoundItem(
        fragmentClass: Class<*>,
        title: String,
        iconResBefore: Int,
        iconResAfter: Int
    ): NavigationView {
        fragmentClassList.add(fragmentClass)
        titleList.add(title)
        defaultIconResList.add(iconResBefore)
        selectIconResList.add(iconResAfter)
        roundList.add(true)
        return this
    }

    override fun addItem(
        fragmentClass: Class<*>,
        title: String,
        iconResBefore: Int,
        iconResAfter: Int
    ): NavigationView {
        fragmentClassList.add(fragmentClass)
        titleList.add(title)
        defaultIconResList.add(iconResBefore)
        selectIconResList.add(iconResAfter)
        roundList.add(false)
        return this
    }

    override fun setDefaultSelect(selectIndex: Int): NavigationView { //从0开始
        defaultSelectIndex = selectIndex
        return this
    }

    override fun setSelect(index: Int) {
        if (index >= fragmentList.size || index < 0) {
            return
        }
        if (navigationPager != null && navigationPager!!.enableSlide()) {
            navigationPager?.setCurrentItem(index)
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

    override fun getItemTitle(index: Int): String? {
        return if (index >= titleList.size) {
            null
        } else titleList[index]
    }

    fun builder() {
        itemCount = fragmentClassList.size
        //预创建bitmap的Rect并缓存
        //预创建icon的Rect并缓存
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
        currentSelectIndex = defaultSelectIndex;
        switchFragment(defaultSelectIndex)
        invalidate()
    }

    override fun build(): NavigationPager {
        itemCount = fragmentClassList.size
        //预创建icon的Rect并缓存
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
            currentSelectIndex = defaultSelectIndex;
            if (mViewPager == null) {
                switchFragment(defaultSelectIndex)
            }
            invalidate()
        }
    }

    override fun setTabItemSelectedListener(listener: OnTabItemSelectedListener?) {
        selectedListener = listener
    }

    private inner class ViewPagerPageChangeListener : OnPageChangeListener {
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
            upSelect(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    //////////////////////////////////////////////////
    //初始化数据基础
    //////////////////////////////////////////////////
    private var titleBaseLine = 0
    private var marginTop = 0
    private var parentItemWidth = 0
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (itemCount != 0) {
            //单个item宽高
            parentItemWidth = width / itemCount
            val parentItemHeight = height
            marginTop = if (hadRoudItem()) {
                16
            } else {
                0
            }

            //图标边长
            val iconWidth = dp2px(iconWidth.toFloat()) //先指定20dp
            val iconHeight = dp2px(iconHeight.toFloat())
            //图标文字margin
            val textIconMargin = dp2px(titleIconMargin.toFloat())
            //标题高度
            val titleSize = dp2px(textSize.toFloat()) //这里先指定10dp
            paint.textSize = titleSize.toFloat()
            val measureRect = Rect()
            val textBound = "测量高度"
            paint.getTextBounds(textBound, 0, textBound.length, measureRect)

            //从而计算得出图标的起始top坐标、文本的baseLine
            titleBaseLine = height - textIconMargin - measureRect.height() - measureRect.top
            val iconTop = marginTop + textIconMargin

            //对icon的rect的参数进行赋值
            val firstRectX = (parentItemWidth - iconWidth) / 2 //第一个icon的左
            for (i in 0 until itemCount) {
                val rectX = i * parentItemWidth + firstRectX
                val isRoundItem = roundList[i]
                if (isRoundItem) {
                    val titleText = titleList[i]
                    if (TextUtils.isEmpty(titleText)) {
                        // 计算有弧度，但是没有文字的Item的Rect
                        val rleft = rectX - marginTop / 2
                        val rtop = marginTop - marginTop / 2
                        val rectWh = height - (marginTop - marginTop / 2) * 2
                        val rright = rleft + rectWh
                        val rbottom = rtop + rectWh
                        val temp = iconRectList[i]
                        temp.left = rleft
                        temp.top = rtop
                        temp.right = rright
                        temp.bottom = rbottom
                    } else {
                        // 计算有弧度，并且有文字的Item的Rect
                        val temp = iconRectList[i]
                        temp.left = rectX - marginTop / 2
                        temp.top = marginTop - marginTop / 2
                        temp.right = rectX + iconWidth + marginTop / 2
                        temp.bottom = marginTop + iconHeight + marginTop / 2
                    }
                } else {
                    // 计算没有弧度的Item的Rect
                    val temp = iconRectList[i]
                    temp.left = rectX
                    temp.top = iconTop
                    temp.right = rectX + iconWidth
                    temp.bottom = iconTop + iconHeight
                }

                //计算Item文字位置
                val titleText = titleList[i]
                // 测量文字宽度
                paint.getTextBounds(titleText, 0, titleText!!.length, measureRect)
                textXList.add((parentItemWidth - measureRect.width()) / 2 + parentItemWidth * i)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (itemCount != 0) {
            paint.isAntiAlias = true
            paint.color = backgroudColor
            // 画背景颜色
            if (hadRoudItem()) {
                canvas.drawRect(
                    0f,
                    marginTop.toFloat(),
                    width.toFloat(),
                    height.toFloat(),
                    paint
                )
            } else {
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            }
            for (i in 0 until itemCount) {
                paint.color = backgroudColor
                val isRoundItem = roundList[i]
                if (isRoundItem) {
                    // 画弧形区域
                    val rect = iconRectList[i]
                    val radius = height / 2
                    canvas.drawCircle(
                        rect.centerX().toFloat(),
                        radius.toFloat(),
                        radius.toFloat(),
                        paint
                    )
                }
                //画对应图标
                var bitmap: Bitmap? = null
                val rect = iconRectList[i]
                bitmap = if (i == currentSelectIndex) {
                    getIconBitmap(selectIconResList[i])
                } else {
                    getIconBitmap(defaultIconResList[i])
                }
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, null, rect, paint) //null代表bitmap全部画出
                }

                //画文字
                val title = titleList[i]
                if (i == currentSelectIndex) {
                    paint.color = selectColor
                } else {
                    paint.color = defaultColor
                }
                val x = textXList[i]
                canvas.drawText(title!!, x.toFloat(), titleBaseLine.toFloat(), paint)
            }
        }
    }

    //////////////////////////////////////////////////
    //点击事件: 通过点的坐标所在区域判断应该响应哪一个Fragment
    //////////////////////////////////////////////////
    var target = -1
    var clickTime: Long = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> target = controlArea(event.x.toInt())
            MotionEvent.ACTION_UP -> {
                if (event.y < 0) {
                    return true
                }
                if (target == controlArea(event.x.toInt())) {
                    //这里触发点击事件
                    val currentTime = System.currentTimeMillis()
                    if (currentSelectIndex == target && selectedListener != null && currentTime - clickTime < 500) {
                        selectedListener!!.onRepeat(currentSelectIndex)
                    }
                    clickTime = System.currentTimeMillis()
                    setSelect(target)
                }
                target = -1
            }
        }
        return true
    }

    ///////////////////////////////////////////////////////
    // 内部处理方法区域
    ///////////////////////////////////////////////////////
    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
        val bmp = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }

    fun getIconBitmap(iconId: Int): Bitmap? {
        return try {
            val icon = ContextCompat.getDrawable(context, iconId)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && icon is AdaptiveIconDrawable) {
                val bitmap = Bitmap.createBitmap(
                    icon.getIntrinsicWidth(),
                    icon.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                icon.setBounds(0, 0, canvas.width, canvas.height)
                icon.draw(canvas)
                bitmap
            } else {
                (icon as BitmapDrawable).bitmap
            }
        } catch (e: Exception) {
            null
        }
    }


    private fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 是否是弧形Item
     *
     * @return
     */
    private fun hadRoudItem(): Boolean {
        for (i in 0 until itemCount) {
            if (roundList[i]) {
                return true
            }
        }
        return false
    }

    /**
     * 返回点位置所在区域
     *
     * @param x
     * @return
     */
    private fun controlArea(x: Int): Int {
        return x / parentItemWidth
    }

    private fun upSelect(index: Int) {
        if (index >= fragmentList.size || index < 0) {
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
        if (pageIndex < 0 || pageIndex >= fragmentList.size) {
            return
        }
        currentSelectIndex = target
        val fragment = fragmentList[pageIndex]
        val frameLayoutId = containerId
        if (fragment != null) {
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