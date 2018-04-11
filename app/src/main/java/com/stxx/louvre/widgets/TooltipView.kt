package com.stxx.louvre.widgets

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.stxx.louvre.R
import org.jetbrains.anko.find


/**
 * description:失败成功提示控件
 * Created by liNan on 2018/4/11 12:55

 */
class TooltipView : DialogFragment() {
    //单例
    private object Holder {
        val INSTANCE = TooltipView()
    }

    private lateinit var tvTipText: TextView
    private lateinit var imgTip: ImageView
    private var strTipText = ""
    private var iTipTag = 0

    companion object {
        const val SUCCESS_IMG: Int = 0 //失败加载的图片
        const val FAILED_IMG: Int = 1  //成功加载的图片
        val instance: TooltipView by lazy { Holder.INSTANCE }
    }

    override fun onStart() {
        super.onStart()
        dialog.window.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND or WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val dm = DisplayMetrics()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent) //设置透明
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        dialog.window.setLayout(((dm.widthPixels * 0.65).toInt()), ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = layoutInflater.inflate(R.layout.view_tooltip, container, false)
//        dialog.window.attributes.windowAnimations = R.style.dialogAnim
        tvTipText = view.find(R.id.tooltip_tv_txt)
        imgTip = view.find(R.id.tooltip_iv_img)
        tvTipText.text = strTipText
        if (iTipTag == 0) {
            imgTip.setBackgroundResource(R.mipmap.success)
        } else {
            imgTip.setBackgroundResource(R.mipmap.fail)
        }
        return view
    }

    fun setTipText(txt: String): TooltipView {
        strTipText = txt
        return this
    }

    fun setTipImgFlag(flag: Int): TooltipView {
        iTipTag = flag
        return this
    }
}