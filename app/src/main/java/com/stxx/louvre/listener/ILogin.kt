package com.stxx.louvre.listener

import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.blankj.utilcode.util.SPUtils
import com.stxx.louvre.base.Constant
import com.stxx.louvre.base.MyApplication
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.net.CookiesManager
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers

/**
 * description: 自动登陆接口
 * Created by liNan on 2018/4/28 9:19

 */
interface ILogin {
    /**
     * 进行登陆
     */
    fun goLogin() {
        CookiesManager.clearAllCookies()
        val userName = SPUtils.getInstance().getString("userName", "")
        val passWord = SPUtils.getInstance().getString("passWord", "")
        if (userName.isNotEmpty() && passWord.isNotEmpty()) {
            RetrofitManager.create().getLogin(userName, passWord)
                    .compose(RxSchedulers.applySchedulers())
                    .subscribe(object : MySubscribe<CodeAndMsg>() {
                        override fun onSuccess(response: CodeAndMsg?) {
                            if (0 == response?.code) {
                                syncCookie()
                            } else {

                            }
                        }
                    })
        }
    }

    /**
     * 根据是否存在cookie来判断登陆
     * 判断是否已经登陆
     */
    fun judgeIsLogin(): Boolean {
        return CookiesManager.getCookies().size > 0
    }

    /**
     * 同步cookie
     */
    private fun syncCookie() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(MyApplication.appContext)
        }
        val manager = CookieManager.getInstance()
        manager.setAcceptCookie(true)
        manager.removeAllCookie()
        if (!CookiesManager.getCookies().isEmpty()) {
            val cookies = CookiesManager.getCookies()
            for (i in cookies.indices) {
                val cookie = cookies[i]
                val value = cookie.name() + "=" + cookie.value() + ";domain=" + cookie.domain() + ";path=/;"
                manager.setCookie(Constant.WEB_BASE_URL, value)
            }
        }
    }
}