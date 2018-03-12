package com.stxx.louvre.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity


/**
 * description:管理Fragment 工具类
 * 两种方式来展示fragment
 * 一种不用考虑重叠问题的 replace 方式　
 * 另一种就是hide的方式  （会导致重叠）
 * Created by liNan on 2017/6/16 17:08
 */
class FragmentController(frameLayoutId: Int, activity: AppCompatActivity) {



    private val frameLayoutId = frameLayoutId
    private val fragments: ArrayList<Fragment> by lazy { arrayListOf<Fragment>() } //初始化装载Fragment的集合
    private val fragmentManager: FragmentManager by lazy {activity.supportFragmentManager}
    private var showPosition = -1

    /**
     * 添加fragment对象到集合中
     */
    fun addFragment(vararg fragment: Fragment) {
        //从可变参数中取值并放到集合中
        fragment.forEach { fragments.add(it) }
        //fragmentTransaction 中添加 fragment
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        fragments.forEach { ft.add(frameLayoutId, it, it::class.java.simpleName)}
        ft.commit()

    }

    /**
     * 显示fragment的开关
     */
    fun switchFragment(position: Int): FragmentController {
        showPosition = position
        return this
    }

    /**
     * 根据tag 来找出fragment 的实例
     */
    fun findWithByTag(array:ArrayList<String>) {
        array.forEach { fragments.add(fragmentManager.findFragmentByTag(it))}
    }

    /**
     * 使用 hide - show 的方式
     */
    fun show() {
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        fragments.forEach { ft.hide(it) }
        val fragment: Fragment = fragments[showPosition]
        ft.show(fragment)
        ft.commit()
    }

    /**
     * 使用替换的方式 不添加到回退栈中
     */
    fun showReplace() {
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.replace(frameLayoutId, fragments[showPosition])
        ft.addToBackStack(null) //添加回退
        ft.commit()
    }
}