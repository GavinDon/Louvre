package com.stxx.louvre.ui.activity

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import com.jaeger.library.StatusBarUtil
import com.stxx.louvre.R
import com.stxx.louvre.base.Constant
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.*
import java.util.*


/**
 * 第一次进入app时的引导页面
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var imgs: Array<Drawable?>
    private val dots = mutableListOf<View>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        StatusBarUtil.setTransparent(this)//设置全透明
        judgeFirstIn()
    }

    /**
     * 是否是第一次进入App
     */
    private fun judgeFirstIn() {
        val sp = getSharedPreferences("app", Context.MODE_PRIVATE)
        val first = sp.getBoolean(Constant.SHARE_IS_FIRST, true)
        if (first) {
            initViewPager()
        } else {
            startActivity<StartUpActivity>()
            finish()
        }
    }

    /**
     * 创建引导视图和滑动圆点
     */
    private fun initViewPager() {
        val views = ArrayList<View>() //viewPagerAdapter 需要装载的view集合
        //引导页面的图片集合
        imgs = arrayOf(ContextCompat.getDrawable(this, R.mipmap.ic_launcher), ContextCompat.getDrawable(this, R.mipmap.ic_launcher), ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
        //把引导的页面装载到集合中(除过最后一页因为最后一页需要添加一个button)
        (0 until imgs.size - 1).mapTo(destination = views) {
            UI {
                imageView {
                    scaleType = ImageView.ScaleType.FIT_XY
                    image = imgs[it]
                }

            }.view
        }
        //给最后一个引导页面添加button
        views.add(UI {
            frameLayout {
                imageView {
                    scaleType = ImageView.ScaleType.FIT_XY
                    image = imgs[imgs.lastIndex]
                }
                button("进入卢浮宫.->") {
                    backgroundResource = R.drawable.shape_entry
                    padding = dip(8)
                    textSize = 18f
                }.lparams(width = wrapContent) {
                    bottomMargin = dip(40)
                    gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                }.setOnClickListener {
                    val sp = getSharedPreferences("app", Context.MODE_PRIVATE)
                    val edit = sp.edit()
                    edit.putBoolean(Constant.SHARE_IS_FIRST, false) //设置不是第一次进入app
                    edit.apply()
                    startActivity<MainActivity>()
                    finish()
                }
            }
        }.view)
        vpSplash.adapter = VpAdapter(views)
        vpSplash.addOnPageChangeListener(MyPagerListener(imgs))
        //设置dot的margin
        val dotLp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dotLp.setMargins(10, 0, 0, 10)
        //滑动关联的小圆点
        for (i in 0 until views.size) {
            val dot = UI {
                imageView {
                    //默认选中第一个页面对应的圆点样式
                    backgroundResource = if (i == 0) {
                        R.drawable.dot_checked
                    } else {
                        R.drawable.dot_normal
                    }

                }.layoutParams = dotLp

            }.view
            ll_dots.addView(dot)
            dots.add(dot)
        }
    }


    /**
     * 页面滑动时的监听
     */
    inner class MyPagerListener(val imgs: Array<Drawable?>) : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            //当从索引为0开始滑动到1时 设置相邻的两个圆点为未选中状态
            dots[position].setBackgroundResource(R.drawable.dot_checked)
            if (position != 0) {
                dots[position - 1].setBackgroundResource(R.drawable.dot_normal)
            }
            if (position != 2) {
                dots[position + 1].setBackgroundResource(R.drawable.dot_normal)
            }
        }

    }
}

/**
 * 给viewpager 装载数据
 * @ui 引导页面集合
 */
class VpAdapter(private val view: ArrayList<View>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return view.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(view[position])
        return view[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(view[position])
    }

}

