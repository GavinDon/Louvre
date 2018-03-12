package com.stxx.louvre.ui.activity

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewCompat
import android.transition.Explode
import android.view.Window
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.base.Constaint.Companion.SHARE_ELMMENT
import kotlinx.android.synthetic.main.activity_goods_detail.*


/**
 * 商品详情
 */
class GoodsDetailActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun inflateViewId(): Int {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.exitTransition = Explode()
        return R.layout.activity_goods_detail
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView(savedInstanceState: Bundle?) {
        ViewCompat.setTransitionName(goods_detail_iv, SHARE_ELMMENT)
        val bis = intent.getByteArrayExtra("bitmap")
        val bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.size)
        goods_detail_iv.setImageBitmap(bitmap)

    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }

}
