package com.stxx.louvre.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.ui.contract.LoginContact
import com.stxx.louvre.ui.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast


/**
 * description: 登陆
 * Created by liNan on 2018/4/12 14:44

 */
class LoginActivity : BaseActivity(), View.OnClickListener, LoginContact.View {


    private lateinit var mPresenter: LoginPresenter

    override fun inflateViewId() = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setTransparent(this)//设置全透明
        mPresenter = LoginPresenter()
        mPresenter.attachView(this)
        val span = SpannableString(login_tv_subscribe.text)
        span.setSpan(ForegroundColorSpan(Color.RED), 7, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        login_tv_subscribe.text = span
        login_btn_request_code.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_btn_request_code -> {
                if (login_btn_request_code.text == "注册") {
                    mPresenter.reqRegister(login_et_phone.text.toString(), login_et_psw2.toString(),login_et_code.text.toString())
                } else {
                    mPresenter.showSmsCodeWidget(login_et_phone, login_fl)
                    mPresenter.reqSmsCode(login_et_phone.text.toString().trim())
                }

            }
        }
    }

    override fun registerFinish() {
        toast("注册成功")
    }

    override fun loginSmsCode() {
        login_btn_request_code.text = "注册"
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView(this)
    }
}