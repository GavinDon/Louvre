package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.HomeTabAdapter
import com.stxx.louvre.entity.HomeRecDataBean
import kotlinx.android.synthetic.main.fragment_tab.*
import org.jetbrains.anko.bundleOf

/**
 * description: tab列表fragment
 * Created by liNan on 2018/2/28 8:43

 */
class TabFragment : LazyLoadFragment() {
    //tab索引
    private var tabIndex: Int = 0
    private lateinit var mAdapter: HomeTabAdapter
    private var lst = mutableListOf<HomeRecDataBean>()

    companion object {
        private const val TAB_KEY = "tab_key"
        fun newsInstance(tab: Int): TabFragment {
            val obj = TabFragment()
            obj.arguments = bundleOf(Pair(TAB_KEY, tab))
            return obj
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_tab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            tabIndex = arguments!!.getInt(TAB_KEY)
        }
    }

    override fun loadData() {
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


    override fun initView(view: View) {
        mAdapter = HomeTabAdapter(R.layout.adapter_home_tab, lst)
        val stageLayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tabRv.setHasFixedSize(true)
        tabRv.layoutManager = stageLayout
        tabRv.adapter = mAdapter
        tabSwipeRefresh.isEnabled = false
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)

    }


}