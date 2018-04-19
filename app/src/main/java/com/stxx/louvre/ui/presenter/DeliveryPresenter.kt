package com.stxx.louvre.ui.presenter

import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.stxx.louvre.entity.AddressListBean
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.RequestEntity
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.ui.activity.DeliveryAddressActivity
import com.stxx.louvre.ui.contract.DeliveryAddressContact
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * description:
 * Created by liNan on 2018/4/18 16:51

 */
class DeliveryPresenter : DeliveryAddressContact.Presenter {


    private lateinit var mView: DeliveryAddressContact.View
    /**
     *获取地址列表
     */
    override fun getAddressList() {
        val reqJson = Gson().toJson(RequestEntity.AddressListBean())
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqJson)
        RetrofitManager.create().getAddressList(body)
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(mView as DeliveryAddressActivity))
                .subscribe(object : MySubscribe<AddressListBean>() {
                    override fun onSuccess(response: AddressListBean?) {
                        if (null != response?.rows ) {
                            mView.showAddress(response.rows)
                        } else {
                            ToastUtils.showShort("")
                        }
                    }
                })
    }

    /**
     * 删除地址
     */
    override fun deleterAddress(id: String, position: Int) {
        val body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), "\"$id\"")
        RetrofitManager.create().getRemoveAddress(body)
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(mView as DeliveryAddressActivity))
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (response?.code == 0) {
                            mView.deleterSuccess(position)
                        } else {
                            ToastUtils.showShort(response?.msg)
                        }
                    }
                })
    }

    /**
     * 编辑地址
     */
    override fun editAddress(id: String, pos: Int) {

    }
    /**
     * 设置默认选中地址
     */
    override fun setDefaultAddress(id: String) {

    }

    /**
     * 绑定view
     */
    override fun attachView(view: DeliveryAddressContact.View) {
        mView = view
    }
}