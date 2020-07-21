# NavigationBar
Android 一行代码创建底部导航栏

## 看下效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200720201539567.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE2OTcwMg==,size_16,color_FFFFFF,t_70)

## 一、如何引入
### Step 1. 将JitPack存储库添加到您的构建文件中
```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
### Step 2. 添加依赖项
```java
dependencies {
    ···
    implementation 'com.github.wy749814530:NavigationBar:1.0.7'
}
```
## 二、XML 布局
```java
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fl_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/gray_999999">

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </androidx.viewpager.widget.ViewPager>


    <com.wang.navigation.NavigationView
        android:id="@+id/navigationView"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:layout_gravity="bottom" />
</FrameLayout>
```

## 三、Activity中使用
```java
navigationView.setContainer(R.id.fl_container)
      .setBackgroudColor(ContextCompat.getColor(this, R.color.white_ffffff)).setColors(
          ContextCompat.getColor(this, R.color.gray_999999),
          ContextCompat.getColor(this, R.color.blue_49a6f6)
      ).addItem(
          FragmnetA::class.java,
          "AAAA",
          R.mipmap.ic_launcher,
          R.mipmap.ic_launcher_round
      )
      .addItem(
          FragmnetB::class.java,
          "BBBB",
          R.mipmap.ic_launcher,
          R.mipmap.ic_launcher_round
      ).addRoundItem(
          FragmnetC::class.java,
          "CCCC",
          R.mipmap.ic_launcher,
          R.mipmap.ic_launcher_round
      ).addRoundItem(
          FragmnetD::class.java,
          "",
          R.mipmap.ic_launcher,
          R.mipmap.ic_launcher_round
      )
      .addItem(
          FragmnetE::class.java,
          "EEEE",
          R.mipmap.ic_launcher,
          R.mipmap.ic_launcher_round
      ).build().setWithViewPager(viewPager).apply();

  navigationView.setTabItemSelectedListener(object : OnTabItemSelectedListener {
      override fun onSelected(index: Int, old: Int) {
          Toast.makeText(
              this@MainActivity,
              "== onSelected index $index , old is $old==",
              Toast.LENGTH_SHORT
          ).show()
      }

      override fun onRepeat(index: Int) {
          Toast.makeText(
              this@MainActivity,
              "== onSelected index $index ==",
              Toast.LENGTH_SHORT
          ).show()
      }

  })
```

## 四、API

### 1. 设置Fragment承载布局id
```java
fun setContainer(containerId: Int): NavigationView
```

### 2. 设置默认选中的Fragment
```java
fun setDefaultSelect(firstCheckedIndex: Int): NavigationView
```

### 3. 添加圆形凸起的Item
```java
fun addRoundItem(
    fragmentClass: Class<*>,
    title: String,
    defaultIcon: Int,
    selectIcon: Int
): NavigationView
```

### 4. 添加普通Item
```java
fun addItem(
    fragmentClass: Class<*>,
    title: String,
    defaultIcon: Int,
    selectIcon: Int
): NavigationView
```
### 5. 构建导航
```java
 fun build(): NavigationView.NavigationPager
```
#### 5.1 NavigationView.NavigationPager
##### 5.1.1 若需要支持左右滑动则需要设置ViewPager,不需要左右滑动，则不需要调用此方法。
```java
 fun setWithViewPager(viewPager: ViewPager?): NavigationPager
```
#### 5.1.2 构建完成开始绘制
```java
fun applay()
```

### 6. 设置要显示的Fragment
```java
fun setSelect(index: Int)
```

### 7. 获取导航Fragment总个数
```java
fun getItemCount(): Int
```

### 8. 获取对应Fragment实例
```java
fun getFragmentByIndex(index: Int): Fragment?
```

### 9. 获取导航按钮文字
```java
fun getItemTitle(index: Int): String?
```

### 10. 设置导航栏背景颜色
```java
fun setBackgroudColor(backgroudColor: Int): NavigationView
```

### 11. 设置导航按钮选中前与选中后文字颜色
```java
fun setColors(defaultColor: Int, selectColor: Int): NavigationView
```

### 12. 设置导航按钮字体大小
```java
fun setTextSize(titleSizeInDp: Int): NavigationView
```

### 13. 设置导航按钮图标大小
```java
// 如果是圆形突出Item大小会自动调整
fun setIconWidth(iconWidth: Int): NavigationView
fun setIconHeight(iconHeight: Int): NavigationView
```

### 14. 设置Item上，中，下最小间距
```java
fun setMargin(titleIconMargin: Int): NavigationView
```


#### 源码地址
https://github.com/wy749814530/NavigationBar
劳烦顺手点下start






