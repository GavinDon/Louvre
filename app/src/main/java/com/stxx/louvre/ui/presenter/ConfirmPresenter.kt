package com.stxx.louvre.ui.presenter

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.entity.*
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.ui.activity.ConfirmOrderActivity
import com.stxx.louvre.ui.contract.ConfirmOrderContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.find

/**
 * description: 确认
 * Created by liNan on 2018/5/2 15:43

 */
class ConfirmPresenter : ConfirmOrderContact.Presenter {


    private lateinit var mView: ConfirmOrderContact.View

    override fun attachView(view: ConfirmOrderContact.View) {
        mView = view
    }

    /**
     * 获取用户地址列表
     */
    override fun showAddressList() {
        val reqJson = Gson().toJson(RequestEntity.AddressListBean())
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqJson)
        RetrofitManager.create().getAddressList(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<AddressListBean>() {
                    override fun onSuccess(response: AddressListBean?) {
                        if (null != response && response.rows.isNotEmpty()) {
                            //默认地址
                            val statusAddress = response.rows.filter { it.isdefalurt == 0 }
                            if (statusAddress.isNotEmpty()) {
                                mView.respStatusAddress(statusAddress[0])
                            }else{
                                ToastUtils.showShort("请选择一个默认地址")
                            }

                        } else {
                            ToastUtils.showShort("请选择地址")
                        }
                    }
                })
    }

    /**
     * 保存订单
     */
    override fun reqSaveOrder(body: RequestBody, id: String) {
        var orderStr = ""
        RetrofitManager.create().saveOrderInfoApp(body)
                .compose(RxSchedulers.applySchedulers())
                .doOnNext {
                    if (it?.code == "0") {
                        orderStr = it.rows
                    }
                }
                .observeOn(Schedulers.io())
                .flatMap(Function<SaveOrderResBean, Observable<CodeAndMsg>> {
                    if (it.code == "0") {
                        val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), Gson().toJson(listOf(id)))
                        return@Function RetrofitManager.create().removeOrder(body)
                    } else {
                        ToastUtils.showShort("商品库存不足")
                        return@Function null
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (null != response && 0 == response.code) {
                            if (!TextUtils.isEmpty(orderStr)) {
                                mView.respSaveOrder(orderStr)
                            } else {
                                ToastUtils.showShort("删除订单失败")
                            }
                        } else {
                            ToastUtils.showLong(response?.msg)
                        }
                    }

                })

    }


    /**
     * 获取订单列表得到id 根据id 获取订单信息
     */
    override fun reqOrderInfo() {
        RetrofitManager.create().getOrderList()
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<ConfirmOrderListRespBean>() {
                    override fun onSuccess(response: ConfirmOrderListRespBean?) {
                        if (null != response) {
                            val reqJson = Gson().toJson(response.idlist)
                            val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), reqJson)
                            getOrderInfo(body)
                        }else{
                            ToastUtils.showShort("500")
                        }
                    }
                })
    }

    /**
     * 根据id 获取订单信息
     */
    private fun getOrderInfo(body: RequestBody) {
        RetrofitManager.create().getOrderInfoById(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<MutableList<OrderListByIdRespBean>>() {
                    override fun onSuccess(response: MutableList<OrderListByIdRespBean>?) {
                        if (null != response) {
                            mView.respOrderInfoSuccess(response)
                        }
                    }
                })
    }

    /**
     * 配送方式
     */
    override fun reqSendStyle(goodsId: String) {
//        val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "\"$goodsId\"")
//        RetrofitManager.create().getSendStyle(body)
//                .compose(RxSchedulers.applySchedulers())
//                .compose(ProgressUtils.applyProgressBar(mView as ConfirmOrderActivity))
//                .subscribe(object : MySubscribe<SendStyleBean>() {
//                    override fun onSuccess(response: SendStyleBean?) {
//
//                    }
//                })
        createBottomDialog(arrayListOf(MySendStyle("快递"), MySendStyle("自提"), MySendStyle("送货上门")).toList())
    }

    /**
     * 优惠券
     */
    override fun reqCouponInfo() {
//        RetrofitManager.create().getCouponInfo()
//                .compose(RxSchedulers.applySchedulers())
//                .compose(ProgressUtils.applyProgressBar(mView as ConfirmOrderActivity))
//                .subscribe(object : MySubscribe<CouponInfoBean>() {
//                    override fun onSuccess(response: CouponInfoBean?) {
//
//                    }
//                })
        mView.respCoupon("暂无优惠券")
    }

    /**
     * 发票信息
     */
    override fun reqReceipt() {
        val reqJson = Gson().toJson(RequestEntity.AddressListBean())
        val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), reqJson)
        RetrofitManager.create().getReceiptList(body)
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(mView as ConfirmOrderActivity))
                .subscribe(object : MySubscribe<ReceiptInfoBean>() {
                    override fun onSuccess(response: ReceiptInfoBean?) {
                        if (null != response && response.total > 0) {
                            createBottomDialog(response?.rows)
                        } else {
                            mView.respReceipt("暂无发票")
                        }
                    }
                })

    }

    override fun reqPayWay() {
        createBottomDialog(arrayListOf(MyPayWayData("支付宝"), MyPayWayData("微信")).toList())
    }


    private fun <T> createBottomDialog(t: T) {
        val mAdapter = BottomDialogAdapter(android.R.layout.simple_list_item_1, t as MutableList<*>)
        val bottomView = (mView as ConfirmOrderActivity).layoutInflater.inflate(R.layout.bottom_dialog, null, false)
        val mRv = bottomView.find<RecyclerView>(R.id.bottom_dialog_rv)
        mRv.layoutManager = LinearLayoutManager(mView as Context, LinearLayoutManager.VERTICAL, false)
        mRv.adapter = mAdapter
        val dialog = BottomSheetDialog(mView as Context)
        mAdapter.setOnItemClickListener { _, _, position ->
            when (t[position]) {
                is MyPayWayData -> mView.respPayWay((t[position] as MyPayWayData).name)
                is ReceiptInfoBean.RowsBean -> mView.respReceipt((t[position] as ReceiptInfoBean.RowsBean).name)
                is MySendStyle -> mView.respSendStyle((t[position] as MySendStyle).name)
            }
            dialog.dismiss()
        }
        dialog.setContentView(bottomView)
        if (dialog.isShowing) {
            dialog.dismiss()
        } else {
            dialog.show()
        }
    }

    data class MyPayWayData(val name: String) //支付方式
    data class MySendStyle(val name: String) //配送方式


    class BottomDialogAdapter<T>(layoutResId: Int, data: MutableList<T>?) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder?, item: T) {
            when (item) {
                is MyPayWayData -> helper!!.setText(android.R.id.text1, item.name)
                is ReceiptInfoBean.RowsBean -> helper!!.setText(android.R.id.text1, item.name)
                is MySendStyle -> helper!!.setText(android.R.id.text1, item.name)
            }
        }
    }


}