package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.LoginBean
import com.stxx.louvre.entity.event.BottomBadgeEvent
import com.stxx.louvre.net.ApiService
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.ui.fragment.*
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
//        fragmentController = FragmentController(R.id.flContent, this)
//        fragmentController.addFragment(HomeFragment(), RecommendFragment(), ShoppingCartFragment(), PersonalFragment())
//        supportFragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment()).commit() //默认显示HomeFragment
        initFragment()
        switchFragment(0)
        EventBus.getDefault().register(this)

        RetrofitManager.create(ApiService::class.java).getLogin("18602928514", "123456")
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<LoginBean>() {
                    override fun onSuccess(response: LoginBean?) {
                        toast(response?.user?.id.toString())
                    }
                })
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
    private fun switchFragment(position: Int) {
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
