package com.stxx.louvre.entity

/**
 * description:统一返回接口
 * Created by liNan on 2018/4/14 14:40

 */
data class DataResponse<out T> ( val code:Int, val msg:String, val data:T)