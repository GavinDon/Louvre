package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.stxx.louvre.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URLEncoder

class PayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        doAsync {
          val param=  PayTask(this@PayActivity).payV2("", true)
            uiThread {

            }
        }


    }
    fun String.abc(key:String,value:String){
       URLEncoder.encode("sign","UTF-8")
    }
}
