package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.selector.SelectorFactory
import com.stxx.louvre.selector.SelectorShape
import com.stxx.louvre.selector.Shape
import kotlinx.android.synthetic.main.activity_delivery_address.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.startActivity

/**
 * 收货地址列表
 */
class DeliveryAddressActivity : BaseActivity() {


    override fun inflateViewId(): Int = R.layout.activity_delivery_address

override fun initView(savedInstanceState: Bundle?) {
        setTitle("地址管理")
        address_tv_new.backgroundDrawable = SelectorFactory.create(SelectorShape.SelectorBuilder()
                .normalColor(ContextCompat.getColor(this, R.color.price_red))
                .pressColor(ContextCompat.getColor(this, R.color.price_red_press))
                .shapeBuilder(Shape.ShapeBuilder())
                .build())

        address_tv_new.setOnClickListener { startActivity<PlusAddressActivity>() }

    }
}
