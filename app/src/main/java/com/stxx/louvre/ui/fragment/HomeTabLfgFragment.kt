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
                .subscribe(object : MySubscribe<HomeLfgBean>() {
                    override fun onSuccess(response: HomeLfgBean?) {
                        mAdapter.setNewData(response?.rows)
                    }
                })
    }

    /**
     * 加载数据
     */
//    private fun loadData() {
//        val iconUrl = "http://m.qpic.cn/psb?/86a78e84-6bb5-4d12-aaf6-a659330f4dbe/A*8q3nJhWjwdvcrOp6oaVR3tLDD201CbcsbxAkbmbrI!/b/dGEBAAAAAAAA&bo=tAB5AAAAAAADB.8!&rf=viewer_4"
//        val storeName = "卢浮宫"
//        val statement = resources.getString(R.string.statement)
//        for (i in 0 until 12) {
//            lstData.add(HomeTabLfgBean(iconUrl, storeName, "$i 小时前", statement, R.mipmap.start_up))
//        }
//        mAdapter.setNewData(lstData)
//        val lstData2 = mutableListOf<HomeTabLfgBean>()
//
//        mAdapter.setOnLoadMoreListener({
//            for (i in 0 until 10) {
//                lstData2.add(HomeTabLfgBean(iconUrl, storeName, "$i 小时前", statement, R.mipmap.start_up))
//            }
//            tabLfgRv.postDelayed({
//                mAdapter.addData(lstData2)
//                mAdapter.loadMoreComplete()
//
//            }, 1000)
//        }, tabLfgRv)
//
//    }


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

//    override fun convert(helper: BaseViewHolder?, item: HomeTabLfgBean?) {
//        helper!!.getView<TextView>(R.id.ada_home_tab_lfg_store_name)
//        helper!!.setText(R.id.ada_home_tab_lfg_store_name, item?.storeName)
//                .setText(R.id.ada_home_tab_lfg_hour_ago, item?.HoursAgo)
//                .setText(R.id.ada_home_tab_lfg_statement, item?.statement)
//        val multi = MultiTransformation(
//                RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL))
//        Glide.with(mContext)
//                .load(item?.storeIcon)
//                .apply(RequestOptions.bitmapTransform(multi))
//                .into(helper.getView(R.id.ada_home_tab_lfg_store_icon))
//
//        Glide.with(mContext)
//                .load(ContextCompat.getDrawable(mContext, item!!.playbill))
////                .apply(RequestOptions.bitmapTransform(multi))
//                .into(helper.getView(R.id.ada_home_tab_lfg_img_playbill))
//
//    }

}