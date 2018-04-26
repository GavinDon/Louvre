package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.widget.EditText
import com.blankj.utilcode.util.ToastUtils
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.UpdatePswBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.widgets.PhoneCountdownView
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_update_psw.*
import org.jetbrains.anko.find

/**
 * 修改密码/ 忘记密码
 */
class UpdatePswActivity : BaseActivity(), PhoneCountdownView.OnRegexSmsListener {

    private val etPhone: EditText by lazy { my_countDown.find<EditText>(R.id.et_viewCountdown) }
    override fun inflateViewId() = R.layout.activity_update_psw

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("忘记密码")
        my_countDown.setOnRegexListener(this)
        btn_update.setOnClickListener {
            if (et_psw.text.isEmpty() && et_psw2.text.isEmpty()) {
                ToastUtils.showShort("请输入密码")
            } else if (et_psw.text.toString() != et_psw2.text.toString()) {
                ToastUtils.showShort("两次密码不一致")
            } else if (et_code.text.toString().isEmpty()) {
                ToastUtils.showShort("请输入验证码")
            } else {
                updatePassWord()
            }
        }
    }

    /**
     * 手机号格式正确回调开始发送验证码
     */
    override fun onRegexSuccess() {
        RetrofitManager.create().getUpdatewordSms(etPhone.text.toString())
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (0 == response?.code) {
                            ToastUtils.showShort("验证码已发送到${etPhone.text}")
                        } else {
                            ToastUtils.showShort(response?.msg)
                        }
                    }
                })
    }

    /**
     * 修改密码
     */
    private fun updatePassWord() {
        var token = ""
        RetrofitManager.create().getVertifyPassword(etPhone.text.toString(), et_code.text.toString())
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(this))
                .doOnNext({ t ->
                    if (t.code == 0) {
                        token = t.token
                    } else {
                        ToastUtils.showShort(t.msg)
                        return@doOnNext
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(Function<UpdatePswBean, Observable<CodeAndMsg>> { t: UpdatePswBean ->
                    if (t.code == 0) {
                        return@Function RetrofitManager.create().getPswUpdateFinish(etPhone.text.toString(), token, et_psw.text.toString())
                    }
                    return@Function null
                })
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (0 == response?.code) {
                            ToastUtils.showShort("修改成功")
                            this@UpdatePswActivity.finish()
                        } else {
                            ToastUtils.showShort("修改失败!")
                        }
                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        my_countDown.onFinish()
    }

}
