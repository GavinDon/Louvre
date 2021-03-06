package com.stxx.louvre.ui.contract

import android.widget.EditText
import com.stxx.louvre.base.BaseMvp

/**
 * description:
 * Created by liNan on 2018/4/13 15:11

 */
interface RegisterContact {

    interface  View:BaseMvp.View{
        fun registerFinish() //注册完成
        fun registerSmsCode()//发送验证码成功
    }

    interface  Presenter:BaseMvp.Presenter<View>{
        fun showSmsCodeWidget(viewPhone:EditText,viewCode:android.view.View)
        fun reqSmsCode(phone:String) //请求验证码
        fun reqRegister(userName: String,password: String,vCode:String)
    }
}