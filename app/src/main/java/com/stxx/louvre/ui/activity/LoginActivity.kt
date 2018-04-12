package com.stxx.louvre.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.blankj.utilcode.util.RegexUtils
import com.jaeger.library.StatusBarUtil
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast




/**
 * description:
 * Created by liNan on 2018/4/12 14:44

 */
class LoginActivity : BaseActivity(), View.OnClickListener {


    override fun inflateViewId() = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setTransparent(this)//设置全透明
        val span = SpannableString(login_tv_subscribe.text)
        span.setSpan(ForegroundColorSpan(Color.RED), 7, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        login_tv_subscribe.text = span
        login_btn_request_code.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_btn_request_code -> {
                if (RegexUtils.isMobileExact(login_et_phone.text)) {
                    if (login_et_code.visibility==View.GONE){
//                        val animator = ObjectAnimator.ofFloat(login_et_code, "translationY", -1f, -0f,1f)
//                        animator.duration = 5000
//                        animator.start()
                        login_et_code.visibility = View.VISIBLE
                        val mShowAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                -1.0f, Animation.RELATIVE_TO_SELF, -0.0f)
                        mShowAction.repeatMode = Animation.REVERSE
                        mShowAction.duration = 500
                        login_et_code.startAnimation(mShowAction)
                    }



                } else {
                    toast("请输入正确的手机号码")
                }
            }
        }


    }
}