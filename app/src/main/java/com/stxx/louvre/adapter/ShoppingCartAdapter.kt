package com.stxx.louvre.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.ShoppingCartListRespBean
import com.stxx.louvre.entity.event.ShoppingCartEvent
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.widgets.AmountView
import okhttp3.MediaType
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

/**
 * description:购物车适配器
 * Created by liNan on 2018/3/5 16:41

 */
class ShoppingCartAdapter(layoutResId: Int, data: MutableList<ShoppingCartListRespBean.RowsBean>?) : BaseQuickAdapter<ShoppingCartListRespBean.RowsBean, BaseViewHolder>(layoutResId, data) {

    private var position: Int = -1
    private val shoppingEvent: ShoppingCartEvent = ShoppingCartEvent()

    override fun convert(helper: BaseViewHolder?, item: ShoppingCartListRespBean.RowsBean?) {
        helper!!.setText(R.id.tv_shopping_cart_title, item?.p_NAME)
                .setText(R.id.tv_shopping_cart_detail, "作者:" + item?.artisT_NAME)
                .setText(R.id.tv_shopping_cart_price, item?.olD_PRICE.toString())
                .setChecked(R.id.cb_shopping_cart, item?.status == 0) //使用status来判断是否显示选中状态
                .addOnClickListener(R.id.shopping_cart_tv_deleter)
                .addOnClickListener(R.id.ada_shopping_cart_content)
        Glide.with(mContext)
                .load(item?.thumbnail)
                .into(helper.getView(R.id.iv_shopping_cart))
        val itemCheckbox = helper.getView<CheckBox>(R.id.cb_shopping_cart) //item checkBox
        val itemDeleter = helper.getView<TextView>(R.id.shopping_cart_tv_deleter) // item delete
        itemCheckbox.setOnClickListener {
            position = data.indexOf(item)
            val checkState = (it as CheckBox).isChecked
            data[position].status = if (checkState) 0 else 1
            notifyItemChanged(position, item)
            countNum()
            EventBus.getDefault().post(shoppingEvent)
        }
        itemDeleter.setOnClickListener {
            deleterShopping(item!!)
        }
        //购物车加减
        val amountView = helper.getView<AmountView>(R.id.amount_shopping_cart)
                .setGoodsStorage(10000)
        val numView = amountView.findViewById<EditText>(R.id.etAmount2)
        val btnDecrease = amountView.findViewById<Button>(R.id.btnDecrease)
        numView.setText(item?.count.toString())
        /**
         * 购物车加减回调
         */
        amountView.setOnAmountChangeListener(object : AmountView.OnAmountChangeListener {
            override fun onAmountChange(view: View, oldAmount: Int, amount: Int) {
                if (amount > 1) {
                    btnDecrease.isClickable = true
                    btnDecrease.setTextColor(ContextCompat.getColor(mContext, R.color.semi))
                } else {
                    btnDecrease.isClickable = false
                    btnDecrease.setTextColor(ContextCompat.getColor(mContext, R.color.light))
                }
                // 代表加操作
                if (oldAmount < amount) {
                    reqIncreaseShopping(item!!, amount)
                } else {
                    //代表减操作
                    reqDecreaseShopping(item!!, amount)
                }
            }
        })
    }

    /**
     * 计算价格，数量
     */
    private fun countNum() {
        //选中数量
        shoppingEvent.checkNum = mData.count { it.status == 0 }
        //全选状态
        shoppingEvent.state = shoppingEvent.checkNum == mData.size
        //选中的总价格(使用kotlin操作符非常方便的实现)
        try {
            shoppingEvent.totalPrice = mData.filter { it.status == 0 }.sumByDouble {
                it.count * it.olD_PRICE.toDouble()
            }
        } catch (exception: Exception) {
            ToastUtils.showShort("oldPrice must not be null ")
        }


        shoppingEvent.totalNum = 0//重置总数
        for (i in 0 until mData.size) {
            if (mData[i].status == 0) {
                shoppingEvent.totalNum += mData[i].count
            }
        }
        EventBus.getDefault().post(shoppingEvent)
    }


    /**
     * 全选or反选
     * @param isChecked 选中还是取消选中
     */
    fun AllOrNone(isChecked: Boolean) {
        if (isChecked)
            mData.forEach {
                it.status = 0
            }
        else
            mData.forEachIndexed { index: Int, _: ShoppingCartListRespBean.RowsBean? ->
                val mSourceData = mData[index]
                mSourceData.status = 1
            }
        notifyDataSetChanged()
        countNum()
        EventBus.getDefault().post(shoppingEvent)
    }

    /**
     * 购物车增加
     */
    private fun reqIncreaseShopping(item: ShoppingCartListRespBean.RowsBean, amount: Int) {
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "\"${item.id}\"")
        RetrofitManager.create().ShoppingIncrease(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (null != response && response.code == 0) {
                            //当开始加数或者减数的时候默认选中 并更新适配器的数据
                            val currentPos = data.indexOf(item)
                            mData[currentPos].count = amount
                            mData[currentPos].status = 0 //只要加减数量就默认选中
                            notifyItemChanged(currentPos, mData[currentPos])
                            countNum()
                        } else {
                            ToastUtils.showLong(response?.msg)
                        }
                    }
                })
    }

    /**
     * 购物车减少
     */
    private fun reqDecreaseShopping(item: ShoppingCartListRespBean.RowsBean, amount: Int) {
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "\"${item.id}\"")
        RetrofitManager.create().ShoppingDecrease(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (null != response && response.code == 0) {
                            val currentPos = data.indexOf(item)
                            mData[currentPos].count = amount
                            mData[currentPos].status = 0 //只要加减数量就默认选中
                            notifyItemChanged(currentPos, mData[currentPos])
                            countNum()
                        } else {
                            ToastUtils.showLong(response?.msg)
                        }
                    }
                })
    }

    /**
     * 删除购物车
     */
    private fun deleterShopping(item: ShoppingCartListRespBean.RowsBean) {
        val reqJson = Gson().toJson(arrayListOf(item.id))
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJson)
        RetrofitManager.create().ShoppingDeleter(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (null != response && 0 == response.code) {
                            position = data.indexOf(item)
                            remove(position)
                            notifyItemRemoved(position)
                            countNum()
                        } else {
                            ToastUtils.showLong(response?.msg)
                        }

                    }
                })
    }

}