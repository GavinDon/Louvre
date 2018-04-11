package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseFragment

/**
 * description: 详情Fragment
 * Created by liNan on 2018/4/11 10:07

 */
class GoodsDetailFragment:BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_goods, container, false)
    }

    override fun initView() {
    }
}