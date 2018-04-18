package com.stxx.louvre.utils

import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * description:扩展函数
 * Created by liNan on 2018/4/18 11:45

 */
infix fun RequestBody.convert(mJson: String): RequestBody {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJson)
}