package com.stxx.louvre.base

import android.app.Application
import android.content.Context
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import kotlin.properties.Delegates

/**
 * description:
 * Created by liNan on 2018/2/27 10:19

 */
class MyApplication : Application() {
    companion object {
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = getAppContext()
        //初始化滑动返回
        BGASwipeBackHelper.init(this, null)
    }

    private fun getAppContext(): Context {
        return this.applicationContext
    }

}