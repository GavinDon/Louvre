package com.stxx.louvre.ui.activity

import android.os.Bundle
import com.jaeger.library.StatusBarUtil
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
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
    }

    /**
     * 登陆成功
     */
    override fun loginSuccess() {
        //通知登陆成功
        this.finish()
    }
}
