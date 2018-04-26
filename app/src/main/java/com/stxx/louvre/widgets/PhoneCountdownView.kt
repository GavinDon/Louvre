package com.stxx.louvre.widgets

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.SPUtils
import com.stxx.louvre.R
import com.stxx.louvre.net.RxSchedulers
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import org.jetbrains.anko.find
import java.util.concurrent.TimeUnit

/**
 * description: 自定义一个输入电话号码 倒计时View
 * Created by liNan on 2018/4/20 15:21

 */
class PhoneCountdownView : ConstraintLayout, TextWatcher {


    private val etPhone: EditText by lazy { find<EditText>(R.id.et_viewCountdown) }
    private val ivClose: ImageView by lazy { find<ImageView>(R.id.iv_viewClose) }
    private val btnSms: SubmitButton  by lazy { find<SubmitButton>(R.id.btn_view) }
    private var mDisposable: Disposable? = null
    private var exitTime: Long? = null
    private var exitSeconds: Long? = null //返回上一页面时 短信倒计时还剩多少秒
    private val countDownTime = 40L //倒计时总数
    private lateinit var listener: OnRegexSmsListener

    interface OnRegexSmsListener {
        fun onRegexSuccess()
    }


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_countdown, this)
        initView()
    }

    private fun initView() {
        val mExitTime = SPUtils.getInstance().getLong("exitTime")
        val mExitSeconds = SPUtils.getInstance().getLong("exitSeconds")

        val currentTime = System.currentTimeMillis()
        //如果退出时间为-1表示没有给sp中存数据
        if (mExitTime != -1L) {
            val diffTime = (currentTime - mExitTime) / 1000 //计算时间差
            //如果当前时间和退出当前页面之前的时间小于还剩倒计时数
            if (diffTime < mExitSeconds) {
                initDownTime(mExitSeconds - diffTime)
            }
        }
        btnSms.setOnClickListener {
            if (RegexUtils.isMobileExact(etPhone.text.toString())) {
                initDownTime(countDownTime)//倒计时60s
                listener.onRegexSuccess()
            }

        }
        ivClose.setOnClickListener {
            etPhone.text = null
        }
        etPhone.addTextChangedListener(this)
    }

    /**
     * 倒计时60s
     *  @param countTime 倒计时总数
     */
    private fun initDownTime(countTime: Long) {
        btnSms.isClickable = false
        mDisposable = Observable.interval(0, 1000L, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.applySchedulers())
                .map(Function<Long, Long> {
                    return@Function countTime - it
                })
                .take(countTime + 1)
                .subscribe({
                    btnSms.text = "${it}s之后获取"
                    exitTime = System.currentTimeMillis()
                    exitSeconds = it
                    if (0L == it) {
                        btnSms.isClickable = true
                        btnSms.text = "获取验证码"
                    }
                })
    }

    override fun afterTextChanged(s: Editable?) {
        if (null != s && s.isNotEmpty()) {
            ivClose.visibility = View.VISIBLE
        } else {
            ivClose.visibility = View.GONE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    fun setOnRegexListener(onRegexSmsListener: OnRegexSmsListener) {
        this.listener = onRegexSmsListener
    }

    /**
     * 取消订时器
     */
    fun onFinish() {
        //只要一个不为空 另一个肯定不为空
        if (null != exitTime) {
            //把退出时的毫秒数和还剩多少秒保存起来
            SPUtils.getInstance().put("exitTime", exitTime!!)
            SPUtils.getInstance().put("exitSeconds", exitSeconds!!)
        }
        if (null != mDisposable && !mDisposable!!.isDisposed) {
            mDisposable?.dispose()
        }
    }


}