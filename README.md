# NavigationBar
简单实用的底部导航栏

## XML 布局
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

## Activity中使用
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
      ).build().setWithViewPager(viewPager).applay();

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
