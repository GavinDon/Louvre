package com.stxx.louvre.ui.activity

import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.jaeger.library.StatusBarUtil
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.net.CookiesManager
import com.stxx.louvre.ui.contract.LoginContact
import com.stxx.louvre.ui.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity(), LoginContact.View {

    private lateinit var mPresenter: LoginPresenter
    override fun inflateViewId() = R.layout.activity_login
    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setTransparent(this)
        mPresenter = LoginPresenter()
        mPresenter.attachView(this)
        //登陆事件
        login_btn.setOnClickListener {
            mPresenter.reqLogin(login_et_phone.text.toString(), login_et_psw.text.toString())
        }
        //跳转到注册
        tv_register.setOnClickListener {
            startActivity<RegisterActivity>()
            this.finish()
        }
        //忘记密码
        tv_forget_psw.setOnClickListener { startActivity<UpdatePswActivity>() }
    }

    /**
     * 登陆成功
     */
    override fun loginSuccess() {
        //登陆成功同步cookie给webView
        syncCookie()
    }

    /**
     * 同步cookie
     */
    private fun syncCookie() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this)
        }
        val manager = CookieManager.getInstance()
        manager.setAcceptCookie(true)
        manager.removeAllCookie()
        if (!CookiesManager.getCookies().isEmpty()) {
            val cookies = CookiesManager.getCookies()
            for (i in cookies.indices) {
                val cookie = cookies[i]
                val value = cookie.name() + "=" + cookie.value() + ";domain=" + cookie.domain() + ";path=/;"
                manager.setCookie("http://124.115.16.18/wapapp/dist/view/gerenzhongxin.html", value)
            }
            this.finish()
        }
    }
}
