package com.stxx.louvre.adapter

import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.entity.HomeRecDataBean
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * description: 首页艺术家作品等 adapter
 *Created by liNan on 2018/2/28 9:14
 */

class HomeTabAdapter(layoutResId: Int, data: MutableList<HomeRecDataBean>) : BaseQuickAdapter<HomeRecDataBean, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder?, item: HomeRecDataBean?) {
        val iv = helper!!.getView<ImageView>(R.id.ada_home_tab_iv)
        iv.layoutParams.height=item!!.height
        val multi = MultiTransformation(
                RoundedCornersTransformation(18, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext, item!!.img))
                .apply(bitmapTransform(multi))
                .into(iv)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
//        val lp: LinearLayout.LayoutParams = holder.getView<ImageView>(R.id.ada_home_tab_iv).layoutParams as LinearLayout.LayoutParams
//        lp.height = mData[position].height
////        lp.width = ScreenUtils.getScreenWidth() / 2 - 16
//        if (position % 2 == 0) {
//            lp.rightMargin = 8
//        } else {
//            lp.leftMargin = 8
//        }
    }


}
