package com.stxx.louvre.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.ShoppingCarBean
import com.stxx.louvre.entity.event.ShoppingCartEvent
import com.stxx.louvre.widgets.AmountView
import org.greenrobot.eventbus.EventBus

/**
 * description:购物车适配器
 * Created by liNan on 2018/3/5 16:41

 */
class ShoppingCarAdapter(layoutResId: Int, data: MutableList<ShoppingCarBean>?) : BaseQuickAdapter<ShoppingCarBean, BaseViewHolder>(layoutResId, data) {

    private var position: Int = -1
    private val shoppingEvent: ShoppingCartEvent = ShoppingCartEvent()

    override fun convert(helper: BaseViewHolder?, item: ShoppingCarBean?) {
        helper!!.setText(R.id.tv_shopping_cart_title, item!!.goodsName)
                .setText(R.id.tv_shopping_cart_detail, item.description)
                .setText(R.id.tv_shopping_cart_price, item.price.toString())
                .setChecked(R.id.cb_shopping_cart, item.checked)
                .addOnClickListener(R.id.shopping_cart_tv_deleter)
                .addOnClickListener(R.id.ada_shopping_cart_content)
//        Glide.with(mContext)
//                .load(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher))
//                .into(helper.getView(R.id.iv_shopping_cart))
        val itemCheckbox = helper.getView<CheckBox>(R.id.cb_shopping_cart) //item checkBox
        val itemDeleter = helper.getView<TextView>(R.id.shopping_cart_tv_deleter) // item delete
        itemCheckbox.setOnClickListener {
            position = data.indexOf(item)
            val checkState = (it as CheckBox).isChecked
            data[position].checked = checkState
            notifyItemChanged(position, item)
            countNum()
            EventBus.getDefault().post(shoppingEvent)
        }
        itemDeleter.setOnClickListener {
            position = data.indexOf(item)
            remove(position)
            notifyItemRemoved(position)
            countNum()
            EventBus.getDefault().post(shoppingEvent)
        }
        //购物车加减
        val amountView = helper.getView<AmountView>(R.id.amount_shopping_cart)
                .setGoodsStorage(10)
        val numView = amountView.findViewById<EditText>(R.id.etAmount2)
        val btnDecrease = amountView.findViewById<Button>(R.id.btnDecrease)
        numView.setText(item.goodsAmount.toString())
        amountView.setOnAmountChangeListener(object : AmountView.OnAmountChangeListener {
            override fun onAmountChange(view: View, oldAmount: Int, amount: Int) {
                if (amount > 1) {
                    btnDecrease.isClickable = true
                    btnDecrease.setTextColor(ContextCompat.getColor(mContext, R.color.semi))
                } else {
                    btnDecrease.isClickable = false
                    btnDecrease.setTextColor(ContextCompat.getColor(mContext, R.color.light))
                }
                //当开始加数或者减数的时候默认选中 并更新适配器的数据
                val currentPos = data.indexOf(item)
                mData[currentPos].goodsAmount = amount
                mData[currentPos].checked = true //只要加减数量就默认选中
                notifyItemChanged(currentPos, mData[currentPos])
                countNum()
                EventBus.getDefault().post(shoppingEvent)
            }
        })
    }

    /**
     * 计算价格，数量
     */
    private fun countNum() {
        //选中数量
        shoppingEvent.checkNum = mData.count { it.checked }
        //全选状态
        shoppingEvent.state = shoppingEvent.checkNum == mData.size
        //选中的总价格(使用kotlin操作符非常方便的实现)
        shoppingEvent.totalPrice = mData.filter { it.checked }.sumByDouble {
            it.goodsAmount * it.price.toDouble()
        }
        shoppingEvent.totalNum = 0//重置总数
        for (i in 0 until mData.size) {
            if (mData[i].checked) {
                shoppingEvent.totalNum += mData[i].goodsAmount
            }
        }
    }


    /**
     * 全选or反选
     * @param isChecked 选中还是取消选中
     */
    fun AllOrNone(isChecked: Boolean) {
        if (isChecked)
            mData.forEach {
                it.checked = true
            }
        else
            mData.forEachIndexed { index: Int, _: ShoppingCarBean? ->
                val mSourceData = mData[index]
                mSourceData.checked = false
            }
        notifyDataSetChanged()
        countNum()
        EventBus.getDefault().post(shoppingEvent)
    }

}