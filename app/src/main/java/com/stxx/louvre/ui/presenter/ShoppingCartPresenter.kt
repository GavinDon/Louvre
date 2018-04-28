package com.stxx.louvre.ui.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.RequestEntity
import com.stxx.louvre.entity.ShoppingCartListRespBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.ui.contract.ShoppingCartContact
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * description:
 * Created by liNan on 2018/4/27 16:57

 */
class ShoppingCartPresenter : ShoppingCartContact.Presenter {

    private lateinit var mView: ShoppingCartContact.View
    /**
     * 请求购物车列表
     */
    override fun reqShoppingCartList() {
        val reqJson = Gson().toJson(RequestEntity.ShoppingCartReqBean())
        val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), reqJson)
        RetrofitManager.create().getShoppingCartList(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<ShoppingCartListRespBean>() {
                    override fun onSuccess(response: ShoppingCartListRespBean?) {
                        if (null != response && response.total > 0) {
                            mView.loadListSuccess(response)
                        } else {
                            mView.loadListFail()
                        }
                    }
                })
    }

    /**
     * 去结算
     */
    override fun reqBalanceAccounts(rows: MutableList<ShoppingCartListRespBean.RowsBean>) {
        val checkRows = rows.filter { it.status == 0 } //选中状态的商品进行提交
        val totalPrice = checkRows.sumByDouble { it.olD_PRICE.toDouble() * it.count } // 总价钱
        val number = checkRows.sumBy { it.count } // 总数
        val idList = checkRows.map { it.id } //商品ID集合
        val reqBean = RequestEntity.BalanceAccountReqBean(number, totalPrice.toString(), idlist = idList)
        val reqJson = Gson().toJson(reqBean)
        LogUtils.i(reqJson)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJson)
        RetrofitManager.create().ShoppingClear(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (null != response && 0 == response.code) {
                            ToastUtils.showShort(response.msg)
                        } else {
                            ToastUtils.showShort(response?.msg)
                        }
                    }
                })
    }

    override fun attachView(view: ShoppingCartContact.View) {
        mView = view
    }

}