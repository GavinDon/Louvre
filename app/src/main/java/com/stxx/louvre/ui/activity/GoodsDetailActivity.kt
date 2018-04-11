package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.stxx.louvre.R
import com.stxx.louvre.adapter.GoodsTabWithVpAdapter
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.ui.fragment.GoodsCommonFragment
import com.stxx.louvre.ui.fragment.GoodsDetailFragment
import com.stxx.louvre.ui.fragment.GoodsFragment
import kotlinx.android.synthetic.main.activity_goods_detail.*
import java.util.*


/**
 * 商品详情
 */
class GoodsDetailActivity : BaseActivity() {
    private val mFragmentLst = ArrayList<Fragment>()
    override fun inflateViewId(): Int {
        return R.layout.activity_goods_detail
    }

    override fun initView(savedInstanceState: Bundle?) {
        addItems()

    }

    /**
     * 绑定Tab与viewpager的联动
     */
    private fun addItems() {
        mFragmentLst.add(GoodsFragment())
        mFragmentLst.add(GoodsDetailFragment())
        mFragmentLst.add(GoodsCommonFragment())
        goods_detail_vp.adapter = GoodsTabWithVpAdapter(supportFragmentManager, mFragmentLst)
        goods_detail_vp.currentItem = 0
        mTabLayout.setupWithViewPager(goods_detail_vp)
        //设置tab属性
        mTabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        mTabLayout.tabMode = TabLayout.MODE_FIXED
    }

}
