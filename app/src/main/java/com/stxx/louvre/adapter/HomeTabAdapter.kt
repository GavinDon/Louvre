package com.stxx.louvre.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.ProtfoloListBean
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * description: 首页作品 adapter
 *Created by liNan on 2018/2/28 9:14
 */

class HomeTabAdapter(layoutResId: Int, data: MutableList<ProtfoloListBean.RowsBean>) : BaseQuickAdapter<ProtfoloListBean.RowsBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: ProtfoloListBean.RowsBean?) {
        helper!!.setText(R.id.ada_home_article_name,item?.artistName)
        val iv = helper!!.getView<ImageView>(R.id.ada_home_tab_iv)
        iv.layoutParams.height = item!!.imgHeight
        val multi = MultiTransformation(
                RoundedCornersTransformation(18, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext).load(item.thumbnail)
                .apply(bitmapTransform(multi))
                .into(iv)
    }
}
