package com.stxx.louvre.contract

import com.stxx.louvre.base.BaseMvp

/**
 * description:
 * Created by liNan on 2018/2/28 9:11

 */
interface HomeContact {

    interface View : BaseMvp.View {
    }

    interface Presenter : BaseMvp.Presenter<View> {
        fun reqTabData()
    }
}