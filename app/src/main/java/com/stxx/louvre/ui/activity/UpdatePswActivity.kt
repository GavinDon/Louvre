package com.stxx.louvre.ui.activity

import android.os.Bundle
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import kotlinx.android.synthetic.main.activity_update_psw.*

/**
 * 修改密码/ 忘记密码
 */
class UpdatePswActivity : BaseActivity() {

    override fun inflateViewId() = R.layout.activity_update_psw

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("忘记密码")
    }

    override fun onDestroy() {
        super.onDestroy()
        my_countDown.onFinish()
    }

}
