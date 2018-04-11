package com.stxx.louvre.ui.fragment

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
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.adapter.RecommendRightAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.ClassifyBean
import com.stxx.louvre.entity.RecommendListBean
import com.stxx.louvre.ui.activity.GoodsListActivity
import kotlinx.android.synthetic.main.fragment_recommend.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.startActivity
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * description: 推荐页面
 * Created by liNan on 2018/2/27 15:20

 */
class RecommendFragment : BaseFragment() {
    private var lastRadioButton = 0
    private lateinit var classifyBean: ClassifyBean
    private val leftNames = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recommend, null, false)
    }
    override fun initView() {
        initRightRecycleView()
        readJsonData()
    }

    private lateinit var mAdapter: RecommendRightAdapter
    private fun initRightRecycleView() {
        rvRight.layoutManager = GridLayoutManager(this.context, 3, GridLayoutManager.VERTICAL, false)
        rvRight.setHasFixedSize(true)
        mAdapter = RecommendRightAdapter(R.layout.recommond_right_item, R.layout.recommend_item_title, null)
        rvRight.adapter = mAdapter
        val headerView = layoutInflater.inflate(R.layout.recommend_head_view, null)
        mAdapter.addHeaderView(headerView)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity<GoodsListActivity>()
        }
    }

    private fun radomData(leftName: String): ArrayList<RecommendListBean> {
        val list = ArrayList<RecommendListBean>()
        list.clear()
        for (i in 0 until classifyBean.data.size) {
            if (leftName == classifyBean.data[i].leftName) {
                val right = classifyBean.data[i].right
                for (j  in 0 until  right.size){
                    list.add(RecommendListBean(true,right[j].title))
                    for (k in 0 until right[j].detail.size){
                        list.add(RecommendListBean(right[j].detail[k]))
                    }
                }
            }
        }
        return list
    }

    private fun readJsonData() {
        val jsonSB = StringBuilder()
        doAsync {
            val addressJsonStream = BufferedReader(InputStreamReader(this@RecommendFragment.context!!.assets.open("classifyJson.json")))
            addressJsonStream.readLines().forEach {
                jsonSB.append(it)
            }
            addressJsonStream.close()
            onUiThread {
                classifyBean = Gson().fromJson(jsonSB.toString(), ClassifyBean::class.java)
                if (classifyBean.code == 0 && classifyBean.data.size > 0) {
                    classifyBean.data.forEach {
                        leftNames.add(it.leftName)
                    }
                    createUi()
                }
            }
        }
    }

    /**
     * 唉 后悔 不如用xml写着来的美
     * 啥么这是
     * 烦
     */
    private fun createUi() {
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
                        mAdapter.setNewData(  radomData(leftNames[i]))
                        mRadioButton.isChecked = true
                        mRadioButton.setCompoundDrawables(createRbDrawable(), null, null, null)
                    }
                    mRadioButton.setOnClickListener {
                        //当点击时取出id放到全局变量 当下次再点击时把当前设置在左边的竖线取消掉
                        this@radioGroup.find<RadioButton>(lastRadioButton).setCompoundDrawables(null, null, null, null)
                        mRadioButton.setCompoundDrawables(createRbDrawable(), null, null, null)
                        mAdapter.setNewData(radomData(mRadioButton.text.toString()))
                        lastRadioButton = it.id
                    }
                }
            }
        }.view
        lvLeft.addView(leftUi)
    }

    /*
    *  创建radioButton 选中时左边的竖线
    * */
    private fun createRbDrawable(): Drawable {
        val rbCheckDrawable = ContextCompat.getDrawable(context!!, R.mipmap.line)
        rbCheckDrawable!!.setBounds(0, 0, rbCheckDrawable.intrinsicWidth, dip(40))
        return rbCheckDrawable
    }


}