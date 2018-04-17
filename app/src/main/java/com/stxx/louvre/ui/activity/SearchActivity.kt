package com.stxx.louvre.ui.activity

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.common_title.*

class SearchActivity : BaseActivity() {
    private lateinit var mAdapter: SearchAdapter
    private val lst = mutableListOf<String>()
    override fun inflateViewId(): Int = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("搜索")
        tvSearch.isFocusable = true
        val flexManager = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)
        search_rv.layoutManager = flexManager
        mAdapter = SearchAdapter(R.layout.adapter_search, null)
        search_rv.adapter = mAdapter
        initData()
    }


    private fun initData() {
        lst.add("轴为竖直方向")
        lst.add("方向")
        lst.add("这个时候控件就")
        lst.add("按如下方")
        lst.add("对应主轴")
        lst.add("来显示")
        lst.add("来显示a")
        lst.add("属性表示换行与否")
        mAdapter.setNewData(lst)
    }
}

class SearchAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.ada_search_tv, item)
    }
}
