package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.adapter.HomeTabAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.ProtfoloListBean
import com.stxx.louvre.entity.RequestEntity
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.ui.WebActivity
import kotlinx.android.synthetic.main.fragment_tab.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity

/**
 * description: 首页作品集
 * Created by liNan on 2018/4/10 8:55

 */
class HomeTabProtfolioFragment : BaseFragment() {
    private lateinit var mAdapter: HomeTabAdapter
    //    private var lst = mutableListOf<HomeRecDataBean>()
    private var lst = mutableListOf<ProtfoloListBean.RowsBean>()
    private var totalPage: Int = 1
    private var currentPageNo: Int = 1
    private val pageSize: String = "10"
    private var isErr: Boolean = false //加载是否失败
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, null, false)
    }

    override fun initView() {
        mAdapter = HomeTabAdapter(R.layout.adapter_home_tab, lst)
        val stageLayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tabRv.setHasFixedSize(true)
        tabRv.layoutManager = stageLayout
        tabRv.adapter = mAdapter
        tabSwipeRefresh.isEnabled = false
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        reqData("$currentPageNo", pageSize)
        /**
         * 上拉加载更多
         */
        mAdapter.setOnLoadMoreListener({
            if (currentPageNo >= totalPage) {
                mAdapter.loadMoreEnd()
            } else {
                if (isErr) {
                    mAdapter.loadMoreFail()
                } else {
                    reqData("$currentPageNo", pageSize)
                    mAdapter.loadMoreComplete()
                }
            }
        }, tabRv)
        mAdapter.setOnItemClickListener { _, _, position ->
            startActivity<WebActivity>("url" to Constant.PROTFOLO_DETAIL_URL + mAdapter.data[position].id)
        }
    }

    private fun reqData(pageNumber: String, pagerSize: String) {
        val reqJson = Gson().toJson(RequestEntity.ProtfolioBean(pageNumber = pageNumber, pageSize = pagerSize))
        LogUtils.i(reqJson)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJson)
        RetrofitManager.create().getProtfolioList(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<ProtfoloListBean>() {
                    override fun onSuccess(response: ProtfoloListBean?) {
                        if (null != response) {
                            if (pageNumber.toInt() == 1) {
                                totalPage = response.totalPages //页面总数
                                currentPageNo = response.pageNo + 1 //当前页面索引加1是上拉加载数据时的索引
                                lst.addAll(response.rows)
                                //设置瀑布流高度差来达到效果
                                for (i in lst.indices) {
                                    lst[i].imgHeight = (i % 2) * 100 + 500
                                }
                                mAdapter.setNewData(lst)
                            } else {
                                val lst2 = mutableListOf<ProtfoloListBean.RowsBean>()
                                lst2.addAll(response.rows)
                                //设置瀑布流高度差来达到效果
                                for (i in lst2.indices) {
                                    lst2[i].imgHeight = (i % 2) * 100 + 500
                                }
                                mAdapter.addData(lst2)
                            }
                            isErr = false
                        } else {
                            isErr = true
                        }
                    }
                })
    }
}