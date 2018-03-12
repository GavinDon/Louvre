package com.stxx.louvre.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.ShoppingCarBean
import com.stxx.louvre.widgets.AmountView

/**
 * description:购物车适配器
 * Created by liNan on 2018/3/5 16:41

 */
class ShoppingCarAdapter(layoutResId: Int, data: MutableList<ShoppingCarBean>?) : BaseQuickAdapter<ShoppingCarBean, BaseViewHolder>(layoutResId, data) {
    private lateinit var mListener: OnAmountChangeListener
    override fun convert(helper: BaseViewHolder?, item: ShoppingCarBean?) {
        helper!!.setText(R.id.tv_shopping_cart_title, item!!.goodsName)
                .setText(R.id.tv_shopping_cart_detail, item.description)
                .setText(R.id.tv_shopping_cart_price, item.price.toString())
                .setChecked(R.id.cb_shopping_cart, item.checked)
                .addOnClickListener(R.id.cb_shopping_cart)
//                .setNestView(R.id.iv_shopping_cart)
//        Glide.with(mContext)
//                .load(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher))
//                .into(helper.getView(R.id.iv_shopping_cart))


        /**
         * 购物车加减数量
         */
        val amountView = helper.getView<AmountView>(R.id.amount_shopping_cart)
                .setGoodsStorage(10)
        val numView = amountView.findViewById<EditText>(R.id.etAmount2)
        val btnDecrease = amountView.findViewById<Button>(R.id.btnDecrease)
        numView.setText(item.goodsAmount.toString())
        amountView.setOnAmountChangeListener(object : AmountView.OnAmountChangeListener {
            override fun onAmountChange(view: View, oldAmout: Int, amount: Int) {

                if (amount > 1) {
                    btnDecrease.isClickable = true
                    btnDecrease.setTextColor(ContextCompat.getColor(mContext, R.color.semi))
                } else {
                    btnDecrease.isClickable = false
                    btnDecrease.setTextColor(ContextCompat.getColor(mContext, R.color.light))
                }
                //当开始加数或者减数的时候默认选中 并更新适配器的数据
                val currentPos = data.indexOf(item)
//                mData[currentPos].checked = true
                mData[currentPos].goodsAmount = amount
                notifyItemChanged(currentPos, mData[currentPos])
                mListener.onAmountChange(amount, currentPos)
            }
        })


    }

    fun setOnAmountChange(onAmountChange: OnAmountChangeListener) {
        mListener = onAmountChange
    }

    interface OnAmountChangeListener {
        fun onAmountChange(nowAmount: Int, position: Int)
    }
}