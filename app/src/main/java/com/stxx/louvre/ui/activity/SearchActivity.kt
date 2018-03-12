package com.stxx.louvre.ui.activity

import android.os.Bundle
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity

class SearchActivity : BaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        setTitle("搜索")

    }

    override fun inflateViewId(): Int = R.layout.activity_search




}
