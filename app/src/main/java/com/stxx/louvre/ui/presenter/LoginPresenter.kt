package com.stxx.louvre.ui.presenter

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.stxx.louvre.base.BaseMvp
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.UserInfoBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.contract.LoginContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

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
//        RetrofitManager.create().getLogin(userName, password)
//                .compose(RxSchedulers.applySchedulers())
//                .compose(ProgressUtils.applyProgressBar(mView as LoginActivity))
//                .subscribe(object : MySubscribe<CodeAndMsg>() {
//                    override fun onSuccess(response: CodeAndMsg?) {
//                        if (0 == response?.code) {
//                            SPUtils.getInstance().put(Constant.USER_NAME, userName)
//                            SPUtils.getInstance().put(Constant.USER_PSW, password)
//                            (mView as LoginActivity).loginSuccess()
//                        } else {
//                            ToastUtils.showShort(response?.msg)
//                        }
//                    }
//                })

        RetrofitManager.create().getLogin(userName, password)
                .compose(RxSchedulers.applySchedulers())
                .doOnNext {
                    if (0 == it?.code) {
                        SPUtils.getInstance().put(Constant.USER_NAME, userName)
                        SPUtils.getInstance().put(Constant.USER_PSW, password)
                    } else {
                        ToastUtils.showShort(it?.msg)
                    }
                }
                .observeOn(Schedulers.io())
                .flatMap(Function<CodeAndMsg, Observable<UserInfoBean>> {
                    return@Function RetrofitManager.create().getPersonalInfo()
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MySubscribe<UserInfoBean>() {
                    override fun onSuccess(response: UserInfoBean?) {
                        if (null != response && null != response.member) {
                            SPUtils.getInstance().put(Constant.USER_ID, response.member.userId)
                            val userJson= Gson().toJson(response)
                            SPUtils.getInstance().put(Constant.USER_INFO,userJson)
                            (mView as LoginActivity).loginSuccess()
                        } else {
                            ToastUtils.showShort("登陆失败")
                        }
                    }

                })

    }


    override fun attachView(view: LoginContact.View) {
        super.attachView(view)
        this.mView = view
    }
}