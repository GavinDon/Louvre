package com.stxx.louvre.adapter

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * description: 首页艺术家作品等 adapter
 *Created by liNan on 2018/2/28 9:14
 */

class HomeTabAdapter(layoutResId: Int, data: MutableList<Int>) : BaseQuickAdapter<Int, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder?, item: Int?) {
        val iv = helper!!.getView<ImageView>(R.id.ada_home_tab_iv)
        val multi = MultiTransformation(
                RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext, item!!))
                .apply(bitmapTransform(multi))

                .into(iv)
    }


}
