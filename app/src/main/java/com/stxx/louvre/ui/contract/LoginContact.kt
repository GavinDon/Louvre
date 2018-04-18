package com.stxx.louvre.ui.contract

import com.stxx.louvre.base.BaseMvp

/**
 * description:
 * Created by liNan on 2018/4/18 10:38

 */
interface LoginContact {
    interface  View:BaseMvp.View{
      fun   loginSuccess()
    }

    interface  Presenter:BaseMvp.Presenter<View>{
        fun reqLogin(userName:String,password:String)
    }
}