package com.stxx.louvre.listener

import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.blankj.utilcode.util.SPUtils
import com.stxx.louvre.base.Constant
import com.stxx.louvre.base.MyApplication
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.UserInfoBean
import com.stxx.louvre.net.CookiesManager
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

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
        val userName = SPUtils.getInstance().getString(Constant.USER_NAME, "")
        val passWord = SPUtils.getInstance().getString(Constant.USER_PSW, "")
        if (userName.isNotEmpty() && passWord.isNotEmpty()) {
            RetrofitManager.create().getLogin(userName, passWord)
                    .compose(RxSchedulers.applySchedulers())
                    .doOnNext {
                        if (0 == it?.code) {
                            syncCookie()
                        }
                    }
                    .observeOn(Schedulers.io())
                    .flatMap(Function<CodeAndMsg, Observable<UserInfoBean>> {
                        return@Function RetrofitManager.create().getPersonalInfo()
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : MySubscribe<UserInfoBean>() {
                        override fun onSuccess(response: UserInfoBean?) {
                            if (null != response && null != response.member) {
                                SPUtils.getInstance().put(Constant.USER_ID, response.member.userId)
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