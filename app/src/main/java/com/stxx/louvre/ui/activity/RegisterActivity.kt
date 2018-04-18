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
import com.stxx.louvre.ui.contract.RegisterContact
import com.stxx.louvre.ui.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast


/**
 * description: I注册
 * Created by liNan on 2018/4/12 14:44

 */
class RegisterActivity : BaseActivity(), View.OnClickListener, RegisterContact.View {


    private lateinit var mPresenter: RegisterPresenter

    override fun inflateViewId() = R.layout.activity_register

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setTransparent(this)//设置全透明
        mPresenter = RegisterPresenter()
        mPresenter.attachView(this)
        val span = SpannableString(register_tv_subscribe.text)
        span.setSpan(ForegroundColorSpan(Color.RED), 7, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        register_tv_subscribe.text = span
        register_btn_request_code.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.register_btn_request_code -> {
                if (register_btn_request_code.text == "注册") {
                    val psw1 = register_et_psw.text.toString()
                    val psw2 = register_et_psw2.text.toString()
                    if (psw1 == psw2) {
                        mPresenter.reqRegister(register_et_phone.text.toString(), psw1, register_et_code.text.toString())
                    } else {
                        toast("两次密码不一致")
                    }
                } else {
                    mPresenter.showSmsCodeWidget(register_et_phone, register_fl)
                    mPresenter.reqSmsCode(register_et_phone.text.toString().trim())
                }

            }
        }
    }

    override fun registerFinish() {
        toast("注册成功")
    }

    override fun registerSmsCode() {
        register_btn_request_code.text = "注册"
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView(this)
    }
}