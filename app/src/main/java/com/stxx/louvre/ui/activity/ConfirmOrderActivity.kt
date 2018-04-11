package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.view.View
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import kotlinx.android.synthetic.main.activity_fill_in_order.*
import org.jetbrains.anko.startActivity

/**
 * 确认订单
 */
class ConfirmOrderActivity : BaseActivity(), View.OnClickListener {


    override fun inflateViewId(): Int = R.layout.activity_fill_in_order

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("确认订单")
        tv_confirm_order.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_confirm_order -> {
                startActivity<PayActivity>()
            }
        }
    }

}
