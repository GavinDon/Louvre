package com.stxx.louvre.net

import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import com.stxx.louvre.entity.DataResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException


/**
 * description: 处理网络返回
 * Created by liNan on 2018/4/14 15:00

 */
abstract class MySubscribe<T> : Observer<T> {
    private lateinit var mDisposable: Disposable
    override fun onSubscribe(d: Disposable) {
        this.mDisposable = d
    }

    override fun onNext(response: T) {
        if (response is DataResponse<*>) {
            if (response.code == 0) onSuccess(response) else onFail(response.msg)
        } else {
            onSuccess(response)
        }
    }

    override fun onError(e: Throwable) {
        LogUtils.e("Retrofit", e.message)
        if (e is HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK)
        } else if (e is ConnectException || e is UnknownHostException) {   //连接错误
            onException(ExceptionReason.CONNECT_ERROR)
        } else if (e is InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT)
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR)
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR)
        }
        onFinish()
    }

    override fun onComplete() {}

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract fun onSuccess(response: T?)

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    fun onFail(message: String) {
        ToastUtils.showLong(message)
    }

    /**
     * 取消订阅
     */
     fun onFinish() {
        mDisposable.dispose()
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    private fun onException(reason: ExceptionReason) {
        when (reason) {
            MySubscribe.ExceptionReason.CONNECT_ERROR -> ToastUtils.showLong("连接错误", Toast.LENGTH_SHORT)

            MySubscribe.ExceptionReason.CONNECT_TIMEOUT -> ToastUtils.showLong("连接超时", Toast.LENGTH_SHORT)

            MySubscribe.ExceptionReason.BAD_NETWORK -> ToastUtils.showLong("请检查网络", Toast.LENGTH_SHORT)

            MySubscribe.ExceptionReason.PARSE_ERROR -> ToastUtils.showLong("解析错误", Toast.LENGTH_SHORT)

            MySubscribe.ExceptionReason.UNKNOWN_ERROR -> ToastUtils.showLong("未知错误", Toast.LENGTH_SHORT)
        }
    }

    /**
     * 请求网络失败原因
     */
    enum class ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }
}