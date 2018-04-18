package com.stxx.louvre.ui.presenter

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.EditText
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.ui.activity.RegisterActivity
import com.stxx.louvre.ui.contract.RegisterContact
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * description:
 * Created by liNan on 2018/4/13 15:16

 */
class RegisterPresenter : RegisterContact.Presenter {
    /**
     * 注册
     */
    override fun reqRegister(userName: String, password: String, vCode: String) {
        RetrofitManager.create().getRegister(userName, password, vCode)
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(mView as RegisterActivity))
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (response?.code == 0) {
                            mView.registerFinish()
                        } else {
                            ToastUtils.showShort(response?.msg)
                        }
                    }
                })
    }

    private lateinit var mView: RegisterContact.View

    /**
     * 发送验证码
     */
    override fun reqSmsCode(phone: String) {
        val call = RetrofitManager.create().getSmsCode(phone)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                ToastUtils.showShort(t?.message)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val resp = response?.body()?.string()
                val code: Int? = JSONObject(resp).getInt("code")
                if (0 == code) {
                    ToastUtils.showShort("验证码发送成功")
                    mView.registerSmsCode()
                } else {
                    ToastUtils.showShort(JSONObject(resp).getString("msg"))
                }
            }
        })
    }


    /**
     * 输入手机号码之后显示输入验证码editText
     */
    override fun showSmsCodeWidget(viewPhone: EditText, viewCode: View) {
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
            (mView as RegisterActivity).toast("输入的手机号有误,请重新输入")
        }
    }


    override fun attachView(view: RegisterContact.View) {
        mView = view
    }

}