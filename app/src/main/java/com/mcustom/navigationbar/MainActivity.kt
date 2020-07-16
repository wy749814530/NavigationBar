package com.mcustom.navigationbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mcustom.navigationbar.fragment.*
import com.wang.navigation.listener.OnTabItemSelectedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}