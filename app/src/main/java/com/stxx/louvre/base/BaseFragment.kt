package com.stxx.louvre.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * description:fragment基类
 * Created by liNan on 2018/2/27 15:20

 */
abstract class BaseFragment: Fragment() {
        private val STATE_SAVE_IS_HIDDEN: String = "STATE_SAVE_IS_HIDDEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager!!.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    abstract  fun initView()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN,isHidden)
    }



}