package com.stxx.louvre.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.AllOrderBean
import com.stxx.louvre.entity.JudgeOrderStatus

/**
 * description: 订单状态
 * Created by liNan on 2018/5/4 12:35

 */
class OrderStatusAdapter(layoutResId: Int, data: MutableList<AllOrderBean.RowsBean>?) : BaseQuickAdapter<AllOrderBean.RowsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: AllOrderBean.RowsBean?) {
        helper!!.setText(R.id.ada_order_tv_orderNum2, item?.orderId.toString())
                .setText(R.id.ada_order_tv_orderTime2, item?.orderTime)
                .setText(R.id.ada_order_goodsName, item?.commodityName)
                .setText(R.id.ada_order_tv_orderState2, JudgeOrderStatus.getStatus(item?.status))
                .setText(R.id.ada_order_tv_orderTotalPrice2, item?.orderPrice.toString())
                .setText(R.id.ada_order_goodsPrice, "￥"+item?.orderPrice)
                .setText(R.id.ada_order_pay_way, if (item?.payMethod == "1") "支付宝" else "微信")
        Glide.with(this.mContext).asBitmap().load(item?.picture).into(helper!!.getView(R.id.ada_order_iv))
    }
}