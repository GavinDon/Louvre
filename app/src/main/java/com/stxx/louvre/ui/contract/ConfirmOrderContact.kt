package com.stxx.louvre.ui.contract

import com.stxx.louvre.base.BaseMvp
import com.stxx.louvre.entity.AddressListBean
import com.stxx.louvre.entity.OrderListByIdRespBean
import okhttp3.RequestBody

/**
 * description:
 * Created by liNan on 2018/5/2 15:38

 */
interface ConfirmOrderContact {

    interface View : BaseMvp.View {
        fun respOrderInfoSuccess(orderInfo: MutableList<OrderListByIdRespBean>) //订单信息
        fun respStatusAddress(addressListBean: AddressListBean.RowsBean) //默认地址

        fun respPayWay(name: String)
        fun respCoupon(name: String)
        fun respReceipt(name: String)
        fun respSendStyle(name: String)
        fun respSaveOrder(orderStr:String)
    }

    interface Presenter : BaseMvp.Presenter<View> {
        fun reqOrderInfo()
        fun showAddressList()
        fun reqCouponInfo() //优惠券信息
        fun reqReceipt() //发票列表
        fun reqSendStyle(goodsId: String) //获取配送方式
        fun reqPayWay() //支付 方式
        fun reqSaveOrder(body:RequestBody,carId:String) //支付前保存订单
    }
}