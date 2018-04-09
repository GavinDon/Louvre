package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.stxx.louvre.R
import com.stxx.louvre.adapter.HomeTabAdapter
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
    private var lst = mutableListOf<Int>()

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
        lst = intArrayOf(R.mipmap.start_up,
                R.mipmap.start_up,
                R.mipmap.start_up,
                R.mipmap.start_up,
                R.mipmap.start_up,
                R.mipmap.start_up).toMutableList()
        mAdapter.setNewData(lst)
    }

    override fun initView(view: View) {
        mAdapter = HomeTabAdapter(R.layout.adapter_home_tab, lst)
        val stageLayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tabRv.setHasFixedSize(true)
        tabRv.layoutManager = stageLayout
        tabRv.adapter = mAdapter
    }


}