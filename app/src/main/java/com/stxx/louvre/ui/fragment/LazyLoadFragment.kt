package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stxx.louvre.base.BaseFragment

/**
 * description: 延迟加载 Fragment
 * Created by liNan on 2018/2/28 9:23

 */
abstract class LazyLoadFragment : BaseFragment() {
    private var bIsViewCreated = false
    private var bIsDataLoaded = false

    abstract fun loadData()
    abstract fun initView(view: View)
    abstract fun getLayoutResId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
//        bIsViewCreated = true
//        if (userVisibleHint && !bIsDataLoaded) {
//            loadData()
//            bIsDataLoaded = true
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        bIsViewCreated = true
        if (userVisibleHint && !bIsDataLoaded) {
            loadData()
            bIsDataLoaded = true
        }
    }

    /**
     * setUserVisibleHint先于onCreateView()调用
     * 所以可以由此判断Fragment是否初始创建
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && bIsViewCreated && bIsDataLoaded) {
            loadData()
            bIsDataLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bIsViewCreated = false
        bIsDataLoaded = false

    }

    override fun initView() {

    }
}