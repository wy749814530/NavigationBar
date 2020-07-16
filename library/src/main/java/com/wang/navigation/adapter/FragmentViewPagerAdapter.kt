package com.wang.navigation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class FragmentViewPagerAdapter(
    fm: FragmentManager?,
    fragments: List<Fragment>?
) : FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var mFragments: MutableList<Fragment> =
        ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    init {
        mFragments.clear()
        mFragments.addAll(fragments!!)
    }
}