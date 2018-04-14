package com.stxx.louvre.base

/**
 * description: baseMvp
 * Created by liNan on 2018/2/27 10:12

 */
interface BaseMvp {
    interface View
    interface Presenter<in V> {
        //绑定view
        fun attachView(view: V){
        }

        //解绑View
        fun detachView(view: V) {
            view to null
        }
    }
}