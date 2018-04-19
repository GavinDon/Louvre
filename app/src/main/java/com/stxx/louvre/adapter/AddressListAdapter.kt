package com.stxx.louvre.adapter

import android.widget.CheckBox
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.AddressListBean
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * description:地址列表适配器
 * Created by liNan on 2018/4/18 15:52

 */
class AddressListAdapter(layoutResId: Int, data: MutableList<AddressListBean.RowsBean>?) : BaseQuickAdapter<AddressListBean.RowsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: AddressListBean.RowsBean?) {
        val address = item?.province + item?.city + item?.area + item?.address
        helper!!.setText(R.id.ada_delivery_tv_address, address)
                .setText(R.id.ada_delivery_tv_nickname, item?.trueName)
                .setText(R.id.ada_delivery_tv_phone, item?.phone)
                .setChecked(R.id.ada_delivery_cb, item?.isdefalurt == 0) //0默认选中
                .addOnClickListener(R.id.ada_delivery_tv_deleter)
                .addOnClickListener(R.id.ada_delivery_tv_edit)
        helper.getView<CheckBox>(R.id.ada_delivery_cb).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val reqJson = "\"${item?.id}\""
                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJson)
                RetrofitManager.create().getUpdateAddress(body)
                        .compose(RxSchedulers.applySchedulers())
                        .subscribe(object : MySubscribe<CodeAndMsg>() {
                            override fun onSuccess(response: CodeAndMsg?) {
                                if (0 == response?.code) {
                                    ToastUtils.showLong("更新成功")
                                    notifyItemMoved(data.indexOf(item), 0)
                                }else{
                                    ToastUtils.showShort(response?.msg)
                                }
                            }
                        })
            }

        }
    }

}