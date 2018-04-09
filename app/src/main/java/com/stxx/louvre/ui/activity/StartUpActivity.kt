package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jakewharton.rxbinding2.view.RxView
import com.stxx.louvre.R
import com.stxx.louvre.net.RxSchedulers
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

/**
 * 启动页
 * 承载首页加载过多数据
 */
class StartUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        StatusBarUtil.setTransparent(this)//设置全透明
        val view: View = StartUpUI().setContentView(this)
        val tv: TextView = view.findViewWithTag("tv")
        initDownTime(tv)
        RxView.clicks(tv).debounce(3, TimeUnit.MILLISECONDS)
                .subscribe({
                    startActivity<MainActivity>()
                })
    }

    /**
     * 倒计时
     */
    private fun initDownTime(textView: TextView) {
        val countTime = 3L //倒计时3s
        Observable.interval(0, 1000L, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.applySchedulers())
                .map(Function<Long, Long> {
                    return@Function countTime - it
                }).take(countTime + 1)
                .subscribe({
                    textView.text = "$it 跳过"
                    if (it == 0L) {
                        startActivity<MainActivity>()
                        finish()
                    }
                })
    }
}

class StartUpUI : AnkoComponent<StartUpActivity> {
    override fun createView(ui: AnkoContext<StartUpActivity>) = with(ui) {
        frameLayout {
            imageView {
                scaleType = ImageView.ScaleType.FIT_XY
                Glide.with(context)
                        .load(R.mipmap.start_up)
                        .into(this)
            }
            //跳过按钮
            textView {
                tag = "tv"
                visibility = View.VISIBLE
                setPadding(28, 10, 28, 10)
                textSize = 14f
                textColor = ContextCompat.getColor(context, android.R.color.white)
                backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.shape_down_time)
            }.lparams(width = wrapContent, height = wrapContent) {
                rightMargin = dip(16)
                topMargin = dip(55)
                gravity = Gravity.END
            }
        }
    }

}
