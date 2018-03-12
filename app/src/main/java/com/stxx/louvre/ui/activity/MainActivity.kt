package com.stxx.louvre.ui.activity

import android.content.Context
import android.os.Bundle
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {
    private lateinit var fragmentController: FragmentController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
        }
    }

    override fun inflateViewId(): Int = R.layout.activity_main
    override fun initView(savedInstanceState: Bundle?) {
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(BottomNavigationItem(R.mipmap.home, "首页")).setActiveColor(R.color.bottom_bar)
                .addItem(BottomNavigationItem(R.mipmap.recormend, "分类")).setActiveColor(R.color.bottom_bar)
                .addItem(BottomNavigationItem(R.mipmap.watch, "购物车")).setActiveColor(R.color.bottom_bar)
                .addItem(BottomNavigationItem(R.mipmap.me, "我的")).setActiveColor(R.color.bottom_bar)
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise()
        fragmentController = FragmentController(R.id.flContent, this)
        fragmentController.addFragment(HomeFragment(), RecommendFragment(), ShoppingCartFragment(), PersonalFragment())
        supportFragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment()).commit() //默认显示HomeFragment
    }

    override fun onTabSelected(position: Int) {
        fragmentController.switchFragment(position).showReplace()
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    /**
     * 获取状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}
