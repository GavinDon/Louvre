package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stxx.louvre.R
import java.net.URLEncoder

class PayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)



    }
    fun String.abc(key:String,value:String){
       URLEncoder.encode("sign","UTF-8")
    }
}
