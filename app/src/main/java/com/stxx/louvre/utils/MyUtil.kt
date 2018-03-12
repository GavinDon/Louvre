package com.stxx.louvre.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.RECTANGLE
import android.graphics.drawable.StateListDrawable
import java.io.ByteArrayOutputStream


/**
 * description:
 * Created by liNan on 2018/3/7 17:10

 */
class MyUtil {

    companion object {
        @SuppressLint("WrongConstant")
        fun getDrawable(context: Context,rgb: Int, corneradius: Int): GradientDrawable {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(rgb)
            gradientDrawable.gradientType = RECTANGLE
            gradientDrawable.cornerRadius = corneradius.toFloat()
            return gradientDrawable
        }

        fun getSelector(normalDrawable: Drawable, pressDrawable: Drawable): StateListDrawable {
            val stateListDrawable = StateListDrawable()
            //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), pressDrawable)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), normalDrawable)
            //设置默认状态
            stateListDrawable.addState(intArrayOf(), normalDrawable)
            return stateListDrawable
        }

        private fun dp2px(context: Context?, dp: Float): Float {
            return context?.resources?.displayMetrics?.density ?: -1f
        }

        /**
         * Bitmap转bytes
         * @param bitmap
         * @return
         */
         fun bitmap2Bytes(bitmap: Bitmap): ByteArray {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            return baos.toByteArray()
        }
    }
}