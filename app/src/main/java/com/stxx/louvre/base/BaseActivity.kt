package com.stxx.louvre.base

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.jaeger.library.StatusBarUtil
import com.stxx.louvre.R
import kotlinx.android.synthetic.main.common_activity_title.*
import org.jetbrains.anko.longToast

/**
 * description: baseActivity
 * Created by liNan on 2018/2/27 10:20

 */
abstract class BaseActivity : AppCompatActivity(), BaseMvp.View, BGASwipeBackHelper.Delegate {
    private lateinit var mSwipeBackHelper: BGASwipeBackHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setStatusBar()
//        StatusBarUtil.setTransparent(this)
        initSwipeBackFinish()
        setContentView(inflateViewId())
        initView(savedInstanceState)
    }

    //initial ui
    abstract fun initView(savedInstanceState: Bundle?)

    //add ui id
    abstract fun inflateViewId(): Int


    /**
     * 设置标题 并添加返回按扭事件
     */
    open fun setTitle(txtTitle: String) {
        try {
            app_txt.text = txtTitle
            tv_back.setOnClickListener {
                onBackPressed()
            }
        } catch (e: Exception) {
            Log.i("Louvre", "xml布局中未include标题")
            longToast(e.message.toString())
        }
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }

    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
        overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out)
    }

    private fun setStatusBar() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.status_bar))
        }
    }
}