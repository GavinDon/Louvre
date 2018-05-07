package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.HomeLfgBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.ui.WebActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_home_tab_lfg.*
import org.jetbrains.anko.support.v4.startActivity
import java.text.SimpleDateFormat
import java.util.*

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
        mAdapter.setOnItemClickListener { _, _, position ->
            LogUtils.i(Constant.HOME_NEWS_URL + mAdapter.data[position].id)
            startActivity<WebActivity>("url" to Constant.HOME_NEWS_URL + mAdapter.data[position].id)
        }
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
                .setText(R.id.ada_home_tab_lfg_hour_ago, showLatest(item?.utime))
                .setText(R.id.ada_home_tab_lfg_statement,item?.context)
        val multi = MultiTransformation(
                RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext)
                .load(item?.imgurl)
//                .apply(RequestOptions.bitmapTransform(multi))
                .into(helper.getView(R.id.ada_home_tab_lfg_img_playbill))
        Glide.with(mContext)
                .load(ContextCompat.getDrawable(mContext,R.mipmap.login_logo))
                .apply(RequestOptions.bitmapTransform(multi))
                .into(helper.getView(R.id.ada_home_tab_lfg_store_icon))
    }

    /**
     * @param uTime 更新时间
     */
    private fun showLatest(uTime: String?): String {
        //小时前，分钟前， 几天前 几月前  几年前  2018-04-18 16:58:52
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val uDate = format.parse(uTime)
        val lon = uDate.time
        val ct = Date().time
        val diffTime = (ct - lon) / 1000 //以秒为单位
        val beforeMill = 1//秒
        val beforeMinute = 60 //分钟
        val beforeHour = 3600 //1小时=3600s
        val beforeDay = (24 * 3600)  //一天
        val beforeMonth = (24 * 3600 * 30) //一月 都按30天来算
        var r: Int
        when {
            diffTime > beforeMonth -> {
                r = (diffTime / beforeMonth).toInt()
                return "$r 月前"
            }
            diffTime > beforeDay -> {
                r = (diffTime / beforeDay).toInt()
                return "$r 天前"
            }
            diffTime > beforeHour -> {
                r = (diffTime / beforeHour).toInt()
                return "$r 小时前"
            }
            diffTime > beforeMinute -> {
                r = (diffTime / beforeMinute).toInt()
                return "$r 分钟前"
            }
            diffTime > beforeMill -> {
                r = (diffTime / beforeMill).toInt()
                return "$r 秒前"
            }
            else -> {
                return "刚刚"
            }
        }


        return "刚刚"
    }

}