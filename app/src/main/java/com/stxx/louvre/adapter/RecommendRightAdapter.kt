package com.stxx.louvre.adapter

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.RecommendListBean

/**
 * description:分类列表
 * Created by liNan on 2018/2/28 21:46

 */
class RecommendRightAdapter(layoutResId: Int, sectionHeadResId: Int, data: MutableList<RecommendListBean>?) : BaseSectionQuickAdapter<RecommendListBean, BaseViewHolder>(layoutResId, sectionHeadResId, data) {

    override fun convert(helper: BaseViewHolder?, item: RecommendListBean?) {
        helper!!.setText(R.id.tvTest,item!!.t.imgUrl)
    }

    override fun convertHead(helper: BaseViewHolder?, item: RecommendListBean?) {
        helper!!.setText(R.id.tvRecommendTitle, item!!.header)
    }
}