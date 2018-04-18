package com.stxx.louvre.ui.activity

import android.os.Bundle
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.ui.contract.LoginContact
import com.stxx.louvre.ui.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContact.View {

    private lateinit var mPresenter:LoginPresenter
    override fun inflateViewId() = R.layout.activity_login
    override fun initView(savedInstanceState: Bundle?) {
        mPresenter= LoginPresenter()
        mPresenter.attachView(this)
        login_btn.setOnClickListener {
            mPresenter.reqLogin(login_et_phone.text.toString(),login_et_psw.text.toString())
        }
    }
    override fun loginSuccess() {
    }
}
