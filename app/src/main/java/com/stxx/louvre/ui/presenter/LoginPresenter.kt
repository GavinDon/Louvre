package com.stxx.louvre.ui.presenter

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.EditText
import com.blankj.utilcode.util.RegexUtils
import com.stxx.louvre.base.BaseMvp
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.contract.LoginContact
import org.jetbrains.anko.toast

/**
 * description:
 * Created by liNan on 2018/4/13 15:16

 */
class LoginPresenter : LoginContact.Presenter {
    private lateinit var mView: BaseMvp.View
    /**
     * 输入手机号码之后显示输入验证码editText
     */
    override fun showSmsCodeWidget(viewPhone: EditText, viewCode: EditText) {
        if (RegexUtils.isMobileExact(viewPhone.text)) {
            if (viewCode.visibility == View.GONE) {
                viewCode.visibility = View.VISIBLE
                val mShowAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                        -1.0f, Animation.RELATIVE_TO_SELF, -0.0f)
                mShowAction.repeatMode = Animation.REVERSE
                mShowAction.duration = 500
                viewCode.startAnimation(mShowAction)
            }
        } else {
            (mView as LoginActivity).toast("输入的手机号有误,请重新输入")
        }
    }

    override fun attachView(view: LoginContact.View) {
        mView = view
    }

}