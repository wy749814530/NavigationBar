package com.avigation.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.avigation.adapter.FragmentViewPagerAdapter;
import com.avigation.listener.OnTabItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
public class NavigationView extends View implements ItemController {
    //////////////////////////////////////////////////
    //数据准备
    //////////////////////////////////////////////////
    private int containerId;
    private List<Class> fragmentClassList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<Integer> defaultIconResList = new ArrayList<>();
    private List<Integer> selectIconResList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Boolean> roundList = new ArrayList<>();
    private List<Bitmap> defaultBitmapList = new ArrayList<>();
    private List<Bitmap> selectBitmapList = new ArrayList<>();
    private List<Rect> iconRectList = new ArrayList<>();
    private List<Integer> textXList = new ArrayList<>();

    private int defaultColor = Color.parseColor("#999999");
    private int selectColor = Color.parseColor("#ff5d5e");
    private int backgroudColor = Color.parseColor("#FFFFFF");

    private OnTabItemSelectedListener selectedListener;
    private NavigationPager navigationPager;

    private Paint paint = new Paint();

    private int itemCount;
    private int currentSelectIndex = 0;
    private int defaultSelectIndex = 0;
    private int textSize = 12;
    private int iconWidth = 30;
    private int iconHeight = 30;
    private int titleIconMargin = 5;
    private Context context;

    public NavigationView(Context context) {
        super(context, null);
        this.context = context;
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public NavigationView setContainer(int containerId) {
        this.containerId = containerId;
        return this;
    }

    @Override
    public NavigationView setBackgroudColor(int backgroudColor) {
        this.backgroudColor = backgroudColor;
        return this;
    }

    @Override
    public NavigationView setColors(int defaultColor, int selectColor) {
        this.defaultColor = defaultColor;
        this.selectColor = selectColor;
        return this;
    }

    @Override
    public NavigationView setTitleSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    @Override
    public NavigationView setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
        return this;
    }

    @Override
    public NavigationView setTitleIconMargin(int titleIconMargin) {
        this.titleIconMargin = titleIconMargin;
        return this;
    }

    @Override
    public NavigationView setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
        return this;
    }

    @Override
    public NavigationView addRoundItem(Class fragmentClass, String title, int iconResBefore, int iconResAfter) {
        fragmentClassList.add(fragmentClass);
        titleList.add(title);
        defaultIconResList.add(iconResBefore);
        selectIconResList.add(iconResAfter);
        roundList.add(true);
        return this;
    }

    @Override
    public NavigationView addItem(Class fragmentClass, String title, int iconResBefore, int iconResAfter) {
        fragmentClassList.add(fragmentClass);
        titleList.add(title);
        defaultIconResList.add(iconResBefore);
        selectIconResList.add(iconResAfter);
        roundList.add(false);
        return this;
    }

    @Override
    public NavigationView setDefaultSelect(int selectIndex) {//从0开始
        this.defaultSelectIndex = selectIndex;
        return this;
    }

    @Override
    public void setSelect(int index) {
        if (navigationPager == null || index >= fragmentList.size() || index < 0) {
            return;
        }

        if (navigationPager.enableSlide()) {
            navigationPager.setCurrentItem(target);
        } else {
            switchFragment(target);
        }
        target = index;
        currentSelectIndex = index;
        invalidate();
    }

    @Override
    public Fragment getFragmentByIndex(int index) {
        if (index >= fragmentList.size()) {
            return null;
        }
        return fragmentList.get(index);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    @Override
    public String getItemTitle(int index) {
        if (index >= titleList.size()) {
            return null;
        }
        return titleList.get(index);
    }

    @Override
    public NavigationPager build() {
        itemCount = fragmentClassList.size();
        //预创建icon的Rect并缓存
        for (int i = 0; i < itemCount; i++) {
            Bitmap beforeBitmap = getBitmap(defaultIconResList.get(i));
            defaultBitmapList.add(beforeBitmap);

            Bitmap afterBitmap = getBitmap(selectIconResList.get(i));
            selectBitmapList.add(afterBitmap);

            Rect rect = new Rect();
            iconRectList.add(rect);

            Class clx = fragmentClassList.get(i);
            try {
                Fragment fragment = (Fragment) clx.newInstance();
                fragmentList.add(fragment);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        currentSelectIndex = defaultSelectIndex;
        if (navigationPager == null) {
            navigationPager = new NavigationPager();
        }
        return navigationPager;
    }

    public class NavigationPager {
        private ViewPager mViewPager;
        private ViewPagerPageChangeListener mPageChangeListener;
        private FragmentViewPagerAdapter mViewPagerAdapter;
        private boolean enableSlide = false;

        public NavigationPager setWithViewPager(ViewPager viewPager) {
            if (viewPager == null) {
                enableSlide = false;
                return this;
            }
            mViewPager = viewPager;
            mViewPagerAdapter = new FragmentViewPagerAdapter(((AppCompatActivity) context).getSupportFragmentManager(), fragmentList);
            mViewPager.setAdapter(mViewPagerAdapter);

            if (mPageChangeListener != null) {
                mViewPager.removeOnPageChangeListener(mPageChangeListener);
            } else {
                mPageChangeListener = new ViewPagerPageChangeListener();
            }

            mViewPager.addOnPageChangeListener(mPageChangeListener);
            enableSlide = true;
            return this;
        }

        public boolean enableSlide() {
            return enableSlide;
        }

        public void setCurrentItem(int item) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(item);
            }
        }

        public void applay() {
            if (mViewPager == null) {
                switchFragment(currentSelectIndex);
            }
            invalidate();
        }
    }


    @Override
    public void setTabItemSelectedListener(OnTabItemSelectedListener listener) {
        selectedListener = listener;
    }

    private class ViewPagerPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (selectedListener != null) {
                selectedListener.onSelected(position, currentSelectIndex);
            }
            setSelect(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //////////////////////////////////////////////////
    //初始化数据基础
    //////////////////////////////////////////////////
    private int titleBaseLine;
    private int marginTop = 0;
    private int parentItemWidth;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (itemCount != 0) {
            //单个item宽高
            parentItemWidth = getWidth() / itemCount;
            int parentItemHeight = getHeight();
            if (hadRoudItem()) {
                marginTop = 16;
            } else {
                marginTop = 0;
            }

            //图标边长
            int iconWidth = dp2px(this.iconWidth);//先指定20dp
            int iconHeight = dp2px(this.iconHeight);
            //图标文字margin
            int textIconMargin = dp2px(titleIconMargin);
            //标题高度
            int titleSize = dp2px(textSize);//这里先指定10dp
            paint.setTextSize(titleSize);
            Rect measureRect = new Rect();
            String textBound = "测量高度";
            paint.getTextBounds(textBound, 0, textBound.length(), measureRect);

            //从而计算得出图标的起始top坐标、文本的baseLine
            titleBaseLine = getHeight() - textIconMargin - measureRect.height() - measureRect.top;
            int iconTop = marginTop + textIconMargin;

            //对icon的rect的参数进行赋值
            int firstRectX = (parentItemWidth - iconWidth) / 2;//第一个icon的左
            for (int i = 0; i < itemCount; i++) {
                int rectX = i * parentItemWidth + firstRectX;
                boolean isRoundItem = roundList.get(i);
                if (isRoundItem) {
                    String titleText = titleList.get(i);
                    if (TextUtils.isEmpty(titleText)) {
                        // 计算有弧度，但是没有文字的Item的Rect
                        int rleft = rectX - marginTop / 2;
                        int rtop = marginTop - marginTop / 2;
                        int rectWh = getHeight() - (marginTop - marginTop / 2) * 2;
                        int rright = rleft + rectWh;
                        int rbottom = rtop + rectWh;

                        Rect temp = iconRectList.get(i);
                        temp.left = rleft;
                        temp.top = rtop;
                        temp.right = rright;
                        temp.bottom = rbottom;
                    } else {
                        // 计算有弧度，并且有文字的Item的Rect
                        Rect temp = iconRectList.get(i);
                        temp.left = rectX - marginTop / 2;
                        temp.top = marginTop - marginTop / 2;
                        temp.right = rectX + iconWidth + marginTop / 2;
                        temp.bottom = marginTop + iconHeight + marginTop / 2;
                    }
                } else {
                    // 计算没有弧度的Item的Rect
                    Rect temp = iconRectList.get(i);
                    temp.left = rectX;
                    temp.top = iconTop;
                    temp.right = rectX + iconWidth;
                    temp.bottom = iconTop + iconHeight;
                }

                //计算Item文字位置
                String titleText = titleList.get(i);
                // 测量文字宽度
                paint.getTextBounds(titleText, 0, titleText.length(), measureRect);
                textXList.add((parentItemWidth - measureRect.width()) / 2 + parentItemWidth * i);
            }


        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemCount != 0) {
            paint.setAntiAlias(true);
            paint.setColor(backgroudColor);
            // 画背景颜色
            if (hadRoudItem()) {
                canvas.drawRect(0, marginTop, getWidth(), getHeight(), paint);
            } else {
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            }

            for (int i = 0; i < itemCount; i++) {
                paint.setColor(backgroudColor);
                boolean isRoundItem = roundList.get(i);
                if (isRoundItem) {
                    // 画弧形区域
                    Rect rect = iconRectList.get(i);
                    int radius = getHeight() / 2;
                    canvas.drawCircle(rect.centerX(), radius, radius, paint);
                }
                //画对应图标
                Bitmap bitmap = null;
                if (i == currentSelectIndex) {
                    bitmap = selectBitmapList.get(i);
                } else {
                    bitmap = defaultBitmapList.get(i);
                }
                Rect rect = iconRectList.get(i);
                canvas.drawBitmap(bitmap, null, rect, paint);//null代表bitmap全部画出
                //画文字
                String title = titleList.get(i);
                if (i == currentSelectIndex) {
                    paint.setColor(selectColor);
                } else {
                    paint.setColor(defaultColor);
                }
                int x = textXList.get(i);
                canvas.drawText(title, x, titleBaseLine, paint);
            }
        }
    }

    //////////////////////////////////////////////////
    //点击事件: 通过点的坐标所在区域判断应该响应哪一个Fragment
    //////////////////////////////////////////////////

    int target = -1;
    long clickTime = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                target = controlArea((int) event.getX());
                // 可以在这里改造长按事件
                break;
            case MotionEvent.ACTION_UP:
                if (event.getY() < 0) {
                    break;
                }
                if (target == controlArea((int) event.getX())) {
                    //这里触发点击事件
                    long currentTime = System.currentTimeMillis();
                    if (currentSelectIndex == target && selectedListener != null && (currentTime - clickTime) < 500) {
                        selectedListener.onRepeat(currentSelectIndex);
                    }
                    clickTime = System.currentTimeMillis();
                    setSelect(target);
                }
                target = -1;
                break;
        }
        return true;
    }

    ///////////////////////////////////////////////////////
    // 内部处理方法区域
    ///////////////////////////////////////////////////////

    private Bitmap getBitmap(int resId) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resId);
        return bitmapDrawable.getBitmap();
    }

    private int dp2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 是否是弧形Item
     *
     * @return
     */
    private boolean hadRoudItem() {
        for (int i = 0; i < itemCount; i++) {
            if (roundList.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回点位置所在区域
     *
     * @param x
     * @return
     */
    private int controlArea(int x) {
        return x / parentItemWidth;
    }

    //////////////////////////////////////////////////
    //碎片处理代码
    //////////////////////////////////////////////////
    private Fragment currentFragment;

    private void switchFragment(int target) {
        if (target == currentSelectIndex || target < 0 || target >= fragmentList.size()) {
            return;
        }
        Fragment fragment = fragmentList.get(target);
        int frameLayoutId = containerId;
        if (fragment != null) {
            //只支持AppCompatActivity
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (currentFragment != null) {
                    transaction.hide(currentFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            currentFragment = fragment;
            transaction.commit();
        }
    }
}

