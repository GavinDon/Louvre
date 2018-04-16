package com.stxx.louvre.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.PersonalBean

/**
 * description:个人中心列表
 * Created by liNan on 2018/2/28 14:17

 */
class PersonalListAdapter(layoutResId: Int, data: MutableList<PersonalBean>?) : BaseQuickAdapter<PersonalBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: PersonalBean?) {
        val divider = helper!!.getView<View>(R.id.hDivider)
        if (item!!.isShowDivider) {
            divider.visibility = View.VISIBLE
        }
        helper.setText(R.id.tvItemName, item.name)
//                .addOnClickListener(R.id.consItem)

//        item!!.isShowDivider ?: divider.visibility = View.GONE

    }
}