package com.stxx.louvre.ui.presenter

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.stxx.louvre.base.BaseMvp
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.contract.LoginContact

/**
 * description:
 * Created by liNan on 2018/4/18 10:39

 */
class LoginPresenter : LoginContact.Presenter {
    private lateinit var mView: BaseMvp.View
    /**
     * 登陆请求
     */
    override fun reqLogin(userName: String, password: String) {
        RetrofitManager.create().getLogin(userName, password)
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(mView as LoginActivity))
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (0 == response?.code) {
                            SPUtils.getInstance().put("userName",userName)
                            SPUtils.getInstance().put("passWord", password)
                            (mView as LoginActivity).loginSuccess()
                        } else {
                            ToastUtils.showShort(response?.msg)
                        }
                    }
                })
    }

    override fun attachView(view: LoginContact.View) {
        super.attachView(view)
        this.mView = view
    }
}