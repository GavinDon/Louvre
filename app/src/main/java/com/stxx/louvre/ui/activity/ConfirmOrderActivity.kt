package com.stxx.louvre.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.base.Constant.Companion.CHECK_ADDRESS_REQUEST_CODE
import com.stxx.louvre.entity.AddressListBean
import com.stxx.louvre.entity.OrderListByIdRespBean
import com.stxx.louvre.entity.RequestEntity
import com.stxx.louvre.ui.contract.ConfirmOrderContact
import com.stxx.louvre.ui.presenter.ConfirmPresenter
import kotlinx.android.synthetic.main.activity_fill_in_order.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * 确认订单
 */
class ConfirmOrderActivity : BaseActivity(), View.OnClickListener, ConfirmOrderContact.View {


    private lateinit var mAdapter: ConfirmAdapter
    private lateinit var mPresenter: ConfirmOrderContact.Presenter
    private var addressData: AddressListBean.RowsBean? = null
    private var mOrderInfo = mutableListOf<OrderListByIdRespBean>()

    override fun inflateViewId(): Int {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        return R.layout.activity_fill_in_order
    }

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("确认订单")
        tv_confirm_order.setOnClickListener(this)
        mPresenter = ConfirmPresenter()
        mPresenter.attachView(this)
        initRecycleView()
        cl_confirm_address.setOnClickListener(this)
        confirm_ll_pay_coupon.setOnClickListener(this)
        confirm_ll_receive_style.setOnClickListener(this)
        confirm_ll_receipt.setOnClickListener(this)
        confirm_ll_pay_style.setOnClickListener(this)

    }

    private fun initRecycleView() {
        confirmRv.setHasFixedSize(true)
        confirmRv.isNestedScrollingEnabled = false
        confirmRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RecyclerViewDivider.with(this).asSpace().build().addTo(confirmRv)
        mPresenter.reqOrderInfo() //请求订单信息
        mPresenter.showAddressList() //请求默认地址
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_confirm_order -> {
                when {
                    TextUtils.isEmpty(confirm_tv_pay_style2.text) -> {
                        ToastUtils.showShort("请选择支付方式")
                        return
                    }
                    addressData == null -> {
                        ToastUtils.showShort("请选择地址")
                        return
                    }
                    mOrderInfo.isEmpty() -> {
                        ToastUtils.showShort("商品信息异常")
                        return
                    }

                }
                val saveOrderObj = RequestEntity.SaveOrder(mOrderInfo[0].p_NAME,
                        mOrderInfo[0].deposit,
                        "0",
                        confirm_et_leave_message.text.toString(), //留言
                        mOrderInfo[0].olD_PRICE,
                        mOrderInfo[0].rent,
                        "0",
                        mOrderInfo[0].producT_ID,
                        if (confirm_tv_pay_style2.text == "支付宝") "1" else "2",
                        addressData?.id,
                        "1", //购物车进行购买type=1
                        mOrderInfo[0].picture,
                        mOrderInfo[0].olD_PRICE,
                        mOrderInfo[0].p_NAME,
                        "0",
                        "${mOrderInfo[0].id}"


                )
                val reqJson = Gson().toJson(saveOrderObj)
                LogUtils.i(reqJson)
                val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), reqJson)
                mPresenter.reqSaveOrder(body, mOrderInfo[0].id)


            }
            R.id.cl_confirm_address -> startActivityForResult<DeliveryAddressActivity>(CHECK_ADDRESS_REQUEST_CODE, Pair("confirmOrder", true))
            R.id.confirm_ll_pay_style -> mPresenter.reqPayWay()
            R.id.confirm_ll_pay_coupon -> mPresenter.reqCouponInfo() //优惠券
            R.id.confirm_ll_receive_style -> mPresenter.reqSendStyle(mAdapter.data[0].id) //提货方式
            R.id.confirm_ll_receipt -> mPresenter.reqReceipt() // 发票信息

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == CHECK_ADDRESS_REQUEST_CODE && null != data) {
            addressData = data.getParcelableExtra("confirmResult")
            fillStatusAddress()
        }
    }

    /**
     * 从服务器获取订单数据
     */
    override fun respOrderInfoSuccess(orderInfo: MutableList<OrderListByIdRespBean>) {
        mAdapter = ConfirmAdapter(R.layout.adapter_confirm_order, orderInfo)
        this.mOrderInfo = orderInfo
        confirmRv.adapter = mAdapter
        val totalPrice = orderInfo.sumByDouble { it.count * it.olD_PRICE.toDouble() }.toString()
        confirm_tv_total.text = totalPrice
        confirm_tv_submit_total.text = "实付款 $totalPrice"
    }


    /**
     * 默认地址
     */
    override fun respStatusAddress(addressListBean: AddressListBean.RowsBean) {
        addressData = addressListBean
        fillStatusAddress()
    }

    override fun respSendStyle(name: String) {
        confirm_tv_receive_style2.text = name
    }

    override fun respPayWay(name: String) {
        confirm_tv_pay_style2.text = name
    }

    override fun respReceipt(name: String) {
        confirm_tv_receipt2.text = name
    }

    override fun respCoupon(name: String) {
        confirm_tv_coupon2.text = name
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView(this)
    }

    private fun fillStatusAddress() {
        if (null != addressData) {
            tv_confirm_name.text = addressData?.trueName
            tv_confirm_phone.text = addressData?.phone
            tv_confirm_detail_address.text = addressData?.province + addressData?.city + addressData?.area + addressData?.address
        } else {
            tv_confirm_detail_address.text = "去添加地址"
        }

    }

    //支付
    override fun respSaveOrder(orderStr: String) {
        if (confirm_tv_pay_style2.text == "支付宝") {
            doAsync {
                val payResult = PayTask(this@ConfirmOrderActivity).payV2(orderStr, true)
                uiThread {
                    when (payResult["resultStatus"]) {
                        "9000" -> toast("支付成功")
                        "4000" -> toast("订单支付失败")
                        "6002" -> toast("网络中断")
                        "6001" -> toast("您取消了支付")
                        else -> {
                            toast("支付错误 ")
                        }
                    }
                }
            }
        } else {
            toast("暂不支持微信支付")
        }

    }

}


class ConfirmAdapter(layoutResId: Int, data: MutableList<OrderListByIdRespBean>?) : BaseQuickAdapter<OrderListByIdRespBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: OrderListByIdRespBean?) {
        helper!!.setText(R.id.ada_confirm_order_tv_name, item?.p_NAME)
                .setText(R.id.ada_confirm_order_tv_author, "作者:${item?.artisT_NAME}")
                .setText(R.id.ada_confirm_order_tv_price, item?.olD_PRICE)
                .setText(R.id.ada_confirm_order_tv_number, "X${item?.count}")

        Glide.with(mContext).asBitmap().load(item?.thumbnail).into(helper.getView(R.id.ada_confirm_order_iv))
    }
}

