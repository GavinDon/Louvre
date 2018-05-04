package com.stxx.louvre.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.AddressListBean

/**
 * description:
 * Created by liNan on 2018/5/2 19:21

 */
class BottomDialogAdapter(layoutResId: Int, data: List<AddressListBean.RowsBean>?) : BaseQuickAdapter<AddressListBean.RowsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: AddressListBean.RowsBean?) {
        helper!!.setText(R.id.ada_bottom_dialog_name, item?.trueName)
                .setText(R.id.ada_bottom_dialog_phone, item?.phone)
                .setText(R.id.ada_bottom_dialog_detail, item?.area.toString())

    }
}