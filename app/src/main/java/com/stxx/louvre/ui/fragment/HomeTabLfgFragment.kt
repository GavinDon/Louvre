package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.HomeLfgBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_home_tab_lfg.*

/**
 * description: 首页卢浮宫
 * Created by liNan on 2018/4/10 8:55

 */
class HomeTabLfgFragment : BaseFragment() {
    private lateinit var mAdapter: HomeTabLfgAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_tab_lfg, null, false)
    }

    override fun initView() {
        tabLfgRv.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        tabLfgRv.layoutManager = linearLayoutManager
        mAdapter = HomeTabLfgAdapter(R.layout.adapter_home_tab_lfg, null)
        tabLfgRv.adapter = mAdapter
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        reqData()
    }

    private fun reqData() {
        RetrofitManager.create().getHomeRecommend("1", "10")
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(activity!!))
                .subscribe(object : MySubscribe<HomeLfgBean>() {
                    override fun onSuccess(response: HomeLfgBean?) {
                        mAdapter.setNewData(response?.rows)
                    }
                })
    }

}

/**
 * 适配器
 */
class HomeTabLfgAdapter(layoutResId: Int, data: MutableList<HomeLfgBean.RowsBean>?) : BaseQuickAdapter<HomeLfgBean.RowsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: HomeLfgBean.RowsBean?) {
        helper!!.setText(R.id.ada_home_tab_lfg_store_name, item?.name)
        val multi = MultiTransformation(
                RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext)
                .load(item?.imgurl)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(helper.getView(R.id.ada_home_tab_lfg_img_playbill))
    }

}