package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.stxx.louvre.R
import com.stxx.louvre.adapter.OrderTabWithVpAdapter
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.ui.fragment.order.*
import kotlinx.android.synthetic.main.activity_order_status.*
import java.util.*

/**
 * description:
 * Created by liNan on 2018/5/3 14:33

 */
class OrderStatusActivity : BaseActivity() {
    private val mFragmentLst = ArrayList<Fragment>()
    private lateinit var vpAdapter: OrderTabWithVpAdapter
    private var showIndex = 0

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("我的订单")
        showIndex = intent.getIntExtra("showIndex", 0)
        addItems()
    }

    override fun inflateViewId() = R.layout.activity_order_status

    /**
     * 绑定Tab与viewpager的联动
     */
    private fun addItems() {
        mFragmentLst.add(AllOrderFragment()) //全部
        mFragmentLst.add(WaitPayOrderFragment()) //待支付
        mFragmentLst.add(WaitReceiveOrderFragment()) //待收货
        mFragmentLst.add(HasFinishOrderFragment()) //已完成
        mFragmentLst.add(WaitCommentFragment()) //待评价
        vpAdapter = OrderTabWithVpAdapter(supportFragmentManager, mFragmentLst)
        order_vp.adapter = vpAdapter
        order_vp.currentItem = showIndex
        order_tl.setupWithViewPager(order_vp)
        //设置tab属性
        order_tl.tabGravity = TabLayout.GRAVITY_CENTER
        order_tl.tabMode = TabLayout.MODE_FIXED
    }
}