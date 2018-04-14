package com.stxx.louvre.ui.contract

import android.widget.EditText
import com.stxx.louvre.base.BaseMvp

/**
 * description:
 * Created by liNan on 2018/4/13 15:11

 */
interface LoginContact {

    interface  View:BaseMvp.View

    interface  Presenter:BaseMvp.Presenter<View>{
        fun showSmsCodeWidget(viewPhone:EditText,viewCode:EditText)
    }
}