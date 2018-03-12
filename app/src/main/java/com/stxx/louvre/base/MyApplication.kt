package com.stxx.louvre.base

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper

/**
 * description:
 * Created by liNan on 2018/2/27 10:19

 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化滑动返回
        BGASwipeBackHelper.init(this,null)
    }

}