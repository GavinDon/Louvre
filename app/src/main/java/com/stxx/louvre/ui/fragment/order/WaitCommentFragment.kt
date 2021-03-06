package com.stxx.louvre.ui.fragment.order

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.stxx.louvre.R
import com.stxx.louvre.adapter.OrderStatusAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.AllOrderBean
import com.stxx.louvre.entity.UserInfoBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_all_order.*
import org.jetbrains.anko.support.v4.toast

/**
 * description:待评论
 * Created by liNan on 2018/5/3 14:53

 */
class WaitCommentFragment : BaseFragment() {

    private lateinit var mAdapter: OrderStatusAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_all_order, container, false)
        return mView
    }

    override fun initView() {
        order_rv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        RecyclerViewDivider.with(this.context!!).drawable(ContextCompat.getDrawable(context!!, R.drawable.big_divider)!!).build().addTo(order_rv)
        mAdapter = OrderStatusAdapter(R.layout.adapter_all_order, null)
        order_rv.adapter = mAdapter
        reqAllOrder()
    }

    /**
     * 获取订单
     */
    private fun reqAllOrder() {
        RetrofitManager.create().getPersonalInfo()
                .compose(RxSchedulers.applySchedulers())
                .doOnNext {
                    if (0 != it.code) {
                        toast("个人信息获取失败，请重新登陆")
                    }
                }
                .observeOn(Schedulers.io())
                .flatMap(io.reactivex.functions.Function<UserInfoBean, Observable<AllOrderBean>> {
                    return@Function RetrofitManager.create().getOrderStatus(it.member.userId + "", orderStatus = "6")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MySubscribe<AllOrderBean>() {
                    override fun onSuccess(response: AllOrderBean?) {
                        if (null != response && 0 == response.code && response.rows.isNotEmpty()) {
                            mAdapter.setNewData(response.rows)
                        }
                    }

                })

    }
}