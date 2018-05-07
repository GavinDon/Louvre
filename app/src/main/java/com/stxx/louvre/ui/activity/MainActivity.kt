package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.blankj.utilcode.util.SPUtils
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.event.BottomBadgeEvent
import com.stxx.louvre.entity.event.ConvertFragment
import com.stxx.louvre.net.CookiesManager
import com.stxx.louvre.ui.fragment.*
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import java.util.*

open class MainActivity : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {
    private lateinit var fragmentController: FragmentController
    private val mFragments = ArrayList<BaseFragment>()
    private val mTextBadgeItem = TextBadgeItem()
    private var mLastFgIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
        }
    }

    override fun inflateViewId(): Int = R.layout.activity_main
    override fun initView(savedInstanceState: Bundle?) {
        mTextBadgeItem.hide()
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(BottomNavigationItem(R.mipmap.home, "首页")).setActiveColor(R.color.bottom_bar)
                .addItem(BottomNavigationItem(R.mipmap.categary, "分类")).setActiveColor(R.color.bottom_bar)
                .addItem(BottomNavigationItem(R.mipmap.shopping_car, "购物车").setBadgeItem(mTextBadgeItem)).setActiveColor(R.color.bottom_bar)
                .addItem(BottomNavigationItem(R.mipmap.me, "我的"))
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise()
        initFragment()
        switchFragment(0)
        EventBus.getDefault().register(this)
        Beta.checkUpgrade(false,false)//检测更新

    }

    /**
     * 初始化fragment
     */
    private fun initFragment() {
        mFragments.add(HomeFragment())
        mFragments.add(RecommendFragment())
        mFragments.add(ShoppingCartFragment())
        mFragments.add(PersonalFragment())
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    open fun onEvent(bottomBageEvent: BottomBadgeEvent) {
        if (bottomBageEvent.num > 0)
            mTextBadgeItem.setBorderWidth(4)
                    .setBackgroundColorResource(R.color.price_red)
                    .setText(bottomBageEvent.num.toString())
                    .show(true)
        else
            mTextBadgeItem.setText("")
                    .hide(true)
    }

    /**
     * 购物车为空时 点击去逛逛跳转到首页
     */
    @Subscribe
    open fun onShoppingComeEvent(index: ConvertFragment) {
        bottom_navigation_bar.selectTab(0)
    }

    override fun onTabSelected(position: Int) {
//        fragmentController.switchFragment(position).show()
        switchFragment(position)
    }


    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                toast("再按一次退出程序")
                mExitTime = System.currentTimeMillis()
            } else {
                //退出之后清除cookie
                CookiesManager.clearAllCookies()
                SPUtils.getInstance().remove("userName")
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    fun switchFragment(position: Int) {
        if (position >= mFragments.size) {
            return
        }
        val ft = supportFragmentManager.beginTransaction()
        val targetFg = mFragments[position]
        val lastFg = mFragments[mLastFgIndex]
        mLastFgIndex = position
        ft.hide(lastFg)
        if (!targetFg.isAdded)
            ft.add(R.id.flContent, targetFg)
        ft.show(targetFg)
        ft.commitAllowingStateLoss()
    }
}
