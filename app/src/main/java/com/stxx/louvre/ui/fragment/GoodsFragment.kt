package com.stxx.louvre.ui.fragment

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.selector.SelectorFactory
import com.stxx.louvre.selector.SelectorShape
import com.stxx.louvre.selector.Shape
import com.stxx.louvre.widgets.TooltipView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_goods.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.support.v4.toast
import java.util.concurrent.TimeUnit

/**
 *商品详情中 商品Fragment
 */
class GoodsFragment : BaseFragment() {
    private val mTipDialog = TooltipView.instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_goods, container, false)
    }

    override fun initView() {
        val btnBackGround = SelectorFactory.create(SelectorShape.SelectorBuilder()
                .normalColor(ContextCompat.getColor(this.context!!, R.color.price_red))
                .pressColor(ContextCompat.getColor(this.context!!, R.color.price_red_press))
                .shapeBuilder(Shape.ShapeBuilder())
                .build())
        goods_join_shopping_cart.backgroundDrawable = btnBackGround
        RxView.clicks(goods_join_shopping_cart).debounce(500,TimeUnit.MILLISECONDS) .subscribe {
            mTipDialog.setTipImgFlag(TooltipView.SUCCESS_IMG)
                    .setTipText("加入购物车成功")
            val ft = fragmentManager?.beginTransaction()
            ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            mTipDialog.show(ft, "GoodsFragment")
            hideTip() //取消提示框
        }

    }

    /**
     *  订时取消弹窗
     */
    private fun hideTip() {
      Observable.just("1")
                .delay(800, TimeUnit.MILLISECONDS)
                .subscribe({
                    if (mTipDialog.isResumed) {
                        mTipDialog.dismiss()
                    }
                }, {
                    toast(it.message.toString())
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        mTipDialog to null
    }

}
