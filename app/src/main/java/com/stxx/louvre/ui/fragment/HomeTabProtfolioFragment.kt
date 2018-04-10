package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.HomeTabAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.HomeRecDataBean
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * description: 首页作品集
 * Created by liNan on 2018/4/10 8:55

 */
class HomeTabProtfolioFragment : BaseFragment() {
    private lateinit var mAdapter: HomeTabAdapter
    private var lst = mutableListOf<HomeRecDataBean>()

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
        loadData()
    }

    private fun loadData() {
        for (i in 0 until 21) {
            lst.add(HomeRecDataBean(R.mipmap.start_up, "", (i % 2) * 100 + 500))
        }
        mAdapter.setNewData(lst)
        val lst2 = mutableListOf<HomeRecDataBean>()
        mAdapter.setOnLoadMoreListener({
            for (i in 0 until 10) {
                lst2.add(HomeRecDataBean(R.mipmap.start_up, "", (lst.size % 2) * 100 + 500))
            }
            tabRv.postDelayed({
                mAdapter.addData(lst2)
                mAdapter.loadMoreComplete()

            }, 1000)
        }, tabRv)
    }
}