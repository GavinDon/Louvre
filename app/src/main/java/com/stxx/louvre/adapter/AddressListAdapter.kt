package com.stxx.louvre.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.AddressListBean

/**
 * description:
 * Created by liNan on 2018/4/18 15:52

 */
class AddressListAdapter(layoutResId: Int, data: MutableList<AddressListBean.RowsBean>?) : BaseQuickAdapter<AddressListBean.RowsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: AddressListBean.RowsBean?) {
        val address = item?.province + item?.city + item?.area + item?.address
        helper!!.setText(R.id.ada_delivery_tv_address, address)
                .setText(R.id.ada_delivery_tv_nickname, item?.trueName)
                .setText(R.id.ada_delivery_tv_phone, item?.phone)
                .addOnClickListener(R.id.ada_delivery_tv_deleter)
                .addOnClickListener(R.id.ada_delivery_tv_edit)

    }
}