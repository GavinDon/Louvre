package com.stxx.louvre.ui.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import com.stxx.louvre.R
import com.stxx.louvre.adapter.RecommendRightAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.RecommendItemBean
import com.stxx.louvre.entity.RecommendListBean
import kotlinx.android.synthetic.main.fragment_recommend.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.dip
import java.util.*
import kotlin.collections.ArrayList


/**
 * description: 推荐页面
 * Created by liNan on 2018/2/27 15:20

 */
class RecommendFragment : BaseFragment() {
    private var lastRadioButton = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recommend, null, false)
    }

    private val leftNames = arrayOf("张三", "李四", "王五", "赵六", "田七", "李四", "王五", "赵六", "田七", "李四", "王五", "赵六", "田七", "李四", "王五", "赵六", "田七", "李四", "王五", "赵六", "田七", "").toMutableList()
    override fun initView() {
        val leftUi = UI {
            radioGroup {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                leftNames.forEachIndexed { i, item ->
                    val mRadioButton = radioButton {
                        id = i
                        isClickable = true
                        text = item
                        textSize = 14f
                        textColor = ContextCompat.getColor(context, R.color.sel_recommend_left_text)
                        buttonDrawable = ColorDrawable(Color.TRANSPARENT) //去掉圆点
                        backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.sel_recommend_left_bg)
                        gravity = Gravity.CENTER
                    }.lparams(width = dip(100), height = dip(45))
                    //设置选中第一个item
                    if (i == 0) {
                        mRadioButton.isChecked = true
                        mRadioButton.setCompoundDrawables(createRbDrawable(), null, null, null)
                    }
                    mRadioButton.setOnClickListener {
                        //当点击时取出id放到全局变量 当下次再点击时把当前设置在左边的竖线取消掉
                        this@radioGroup.find<RadioButton>(lastRadioButton).setCompoundDrawables(null, null, null, null)
                        mRadioButton.setCompoundDrawables(createRbDrawable(), null, null, null)
                        mAdapter.setNewData(radomData())
                        lastRadioButton = it.id
                    }
                }
            }
        }.view
        lvLeft.addView(leftUi)
        initRightRecycleView()
//        lvLeft.smoothScrollBy()
    }

    private lateinit var mAdapter: RecommendRightAdapter
    private fun initRightRecycleView() {
        rvRight.layoutManager = GridLayoutManager(this.context, 3, GridLayoutManager.VERTICAL, false)
        rvRight.setHasFixedSize(true)
        mAdapter = RecommendRightAdapter(R.layout.recommond_right_item, R.layout.recommend_item_title, radomData())
        rvRight.adapter = mAdapter
        val headerView = layoutInflater.inflate(R.layout.recommend_head_view, null)
        mAdapter.addHeaderView(headerView)

    }

    private fun radomData(): ArrayList<RecommendListBean> {
        val list = ArrayList<RecommendListBean>()
        val sb = StringBuffer()
        val source = arrayOf("我", "每", "世", "界", "如", "你", "只", "没", "果", "喜", "就", "欢", "的", "生", "一", "爱", "有", "过", "你", "在", "想", "会")
        for (i in 0 until Random().nextInt(3) + 3) {
            list.add(RecommendListBean(true, "header$i"))
            for (i in 0 until Random().nextInt(2) + 5) {
                for (i in 0 until 4) {
                    val index = Random().nextInt(22)
                    sb.append(source[index])
                }
                list.add(RecommendListBean(RecommendItemBean(sb.toString())))
                sb.delete(0, sb.length)
            }

        }
        return list

    }


    private fun createColorStateList(selected: String, normal: String): ColorStateList {
        val colors = intArrayOf(Color.parseColor(selected), Color.parseColor(normal))
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
//        states[1] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf(-1)
        return ColorStateList(states, colors)
    }

    /*
    创建radioButton 选中时左边的竖线
     */
    private fun createRbDrawable(): Drawable {
        val rbCheckDrawable = ContextCompat.getDrawable(context!!, R.mipmap.line)
        rbCheckDrawable!!.setBounds(0, 0, rbCheckDrawable.intrinsicWidth, dip(40))
        return rbCheckDrawable
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }
}