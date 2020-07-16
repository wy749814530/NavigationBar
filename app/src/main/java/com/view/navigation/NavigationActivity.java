package com.view.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.avigation.view.NavigationView;
import com.avigation.listener.OnTabItemSelectedListener;
import com.view.navigation.fragmnet.FragmnetA;
import com.view.navigation.fragmnet.FragmnetB;
import com.view.navigation.fragmnet.FragmnetC;
import com.view.navigation.fragmnet.FragmnetD;
import com.view.navigation.fragmnet.FragmnetE;

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
public class NavigationActivity extends AppCompatActivity {
    NavigationView navigationView;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottombar);
        navigationView = findViewById(R.id.bottom_bar);
        viewPager = findViewById(R.id.viewPager);

        navigationView.setContainer(R.id.fl_container).setBackgroudColor(ContextCompat.getColor(this, R.color.white_ffffff)).
                setColors(ContextCompat.getColor(this, R.color.gray_999999), ContextCompat.getColor(this, R.color.blue_49a6f6)).
                addItem(FragmnetA.class, "AAAA", R.mipmap.ic_launcher, R.mipmap.ic_launcher_round).
                addItem(FragmnetB.class, "BBBB", R.mipmap.ic_launcher, R.mipmap.ic_launcher_round).
                addItem(FragmnetC.class, "CCCC", R.mipmap.ic_launcher, R.mipmap.ic_launcher_round).
                addItem(FragmnetD.class, "", R.mipmap.ic_launcher, R.mipmap.ic_launcher_round).
                addItem(FragmnetE.class, "EEEE", R.mipmap.ic_launcher, R.mipmap.ic_launcher_round).
                build().setWithViewPager(viewPager).applay();

        navigationView.setTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("NavigationActivity", "--- onSelected ---" + index + " , old : " + old);
            }

            @Override
            public void onRepeat(int index) {
                Log.i("NavigationActivity", "--- onRepeat ---" + index);
            }
        });
    }

    public void action1(View view) {
        navigationView.setSelect(0);
    }

    public void action2(View view) {
        navigationView.setSelect(1);
    }

    public void action3(View view) {
        navigationView.setSelect(2);
    }

    public void action4(View view) {
        navigationView.setSelect(3);
    }

    public void refresh1(View view) {
        FragmnetA fragmneta = (FragmnetA) navigationView.getFragmentByIndex(0);
        fragmneta.refresh();
    }

    public void refresh2(View view) {
        FragmnetB fragmnetb = (FragmnetB) navigationView.getFragmentByIndex(1);
        fragmnetb.refresh();
    }

    public void refresh3(View view) {
        FragmnetC fragmnetc = (FragmnetC) navigationView.getFragmentByIndex(2);
        fragmnetc.refresh();
    }

    public void refresh4(View view) {
        FragmnetD fragmnetd = (FragmnetD) navigationView.getFragmentByIndex(3);
        fragmnetd.refresh();
    }
}
