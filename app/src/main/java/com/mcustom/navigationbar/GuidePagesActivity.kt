package com.mcustom.navigationbar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mcustom.navigationbar.fragment.*
import kotlinx.android.synthetic.main.activity_guide_pages.viewPager
import kotlinx.android.synthetic.main.activity_guide_pages.*

/**
 * @WYU-WIN
 * @date 2020/7/17 0017.
 * description：
 */
class GuidePagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_pages)

        navigationView.setContainer(R.id.frame_main_lay).setMarginBottom(40)
            .setIcons(R.mipmap.ic_launcher, R.mipmap.file)
            .addPage(FragmnetB::class.java).addPage(FragmnetC::class.java)
            .addPage(FragmnetD::class.java).addPage(FragmnetE::class.java).build()
            .setWithViewPager(viewPager).apply()
    }


    fun action1(view: View) {
        navigationView.setSelect(0)
    }

    fun action2(view: View) {
        navigationView.setSelect(1)
    }

    fun action3(view: View) {
        navigationView.setSelect(2)
    }

    fun action4(view: View) {
        navigationView.setSelect(3)
    }
}