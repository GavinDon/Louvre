package com.stxx.louvre.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * description:
 * Created by liNan on 2018/2/28 9:37

 */
class HomeFragmentAdapter(val fm: FragmentManager, val list: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int = list.size

}