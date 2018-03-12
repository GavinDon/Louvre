package com.stxx.louvre.widgets

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.entity.AddressPickerBean
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * description:模仿京东地址选择器
 * Created by liNan on 2018/3/9 16:20

 */
class AddressPickerView : LinearLayout, TabLayout.OnTabSelectedListener, BaseQuickAdapter.OnItemChildClickListener {


    private val mTableLayout: TabLayout by lazy { find<TabLayout>(R.id.picker_tabLayout) }
    private val rvAddress: RecyclerView by lazy { find<RecyclerView>(R.id.picker_rv) }
    private var mRvData: MutableList<AddressPickerBean.AddressItemBean> = mutableListOf()
    private lateinit var mAdapter: AddressAdapter
    private lateinit var addressPickerBean: AddressPickerBean
    private var mSelectProvince: AddressPickerBean.AddressItemBean? = null
    private var mSelectCity: AddressPickerBean.AddressItemBean? = null
    private var mSelectDistrict: AddressPickerBean.AddressItemBean? = null
    private var mSelectProvincePosition = 0 //选中省份位置
    private var mSelectCityPosition = 0//选中城市位置
    private var mSelectDistrictPosition = 0//选中区县位置
    private var mListener: OnAddressCheckListener? = null

    private var isAddTabFinish = false

    interface OnAddressCheckListener {
        fun onCheckListener(a: String)
    }

    fun setOnAddressCheckListener(listener: OnAddressCheckListener) {
        mListener = listener
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.address_picker_view, this)
        initView()
    }

    private fun initView() {
//        mTableLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context,R.color.price_red))
//        mTableLayout.setTabTextColors(ContextCompat.getColor(context, R.color.price_red), ContextCompat.getColor(context, R.color.price_red_press))
        mTableLayout.addTab(mTableLayout.newTab().setText("请选择"))
        rvAddress.layoutManager = LinearLayoutManager(this.context)
        mAdapter = AddressAdapter(android.R.layout.simple_list_item_1, null)
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT)
        rvAddress.adapter = mAdapter
        mAdapter.onItemChildClickListener = this
        mTableLayout.addOnTabSelectedListener(this)
        post { initData() }

    }

    /**
     * 从asserts文件夹下获取省市区json文件
     */
    private fun initData() {
        val jsonSB = StringBuilder()
        val addressJsonStream = BufferedReader(InputStreamReader(this.context.assets.open("address.json")))
        addressJsonStream.readLines().forEach {
            jsonSB.append(it)
        }
        addressPickerBean = Gson().fromJson(jsonSB.toString(), AddressPickerBean::class.java)
        mRvData.addAll(addressPickerBean.province)
        mAdapter.setNewData(mRvData)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (mTableLayout.selectedTabPosition) {
            0 -> {
                mSelectProvince = mRvData[position]
                mSelectProvincePosition = position //选中的位置
                if (!isAddTabFinish) {
                    mTableLayout.addTab(mTableLayout.newTab().setText("请选择"))
                }
                mTableLayout.getTabAt(0)?.text = mRvData[position].n
                mTableLayout.getTabAt(1)?.select()
            }
            1 -> {
                mTableLayout.getTabAt(1)?.text = mRvData[position].n
                mSelectCity = mRvData[position]
                mSelectCityPosition = position
                if (!isAddTabFinish) {
                    mTableLayout.addTab(mTableLayout.newTab().setText("请选择"))
                }
                mTableLayout.getTabAt(2)?.select()
            }
            2 -> {
                mTableLayout.getTabAt(2)?.text = mRvData[position].n
                mSelectDistrict = mRvData[position]
                mSelectDistrictPosition = position
                context.toast(mSelectCity?.n.toString())
                if (mListener != null) {
                    mListener!!.onCheckListener(mSelectProvince?.n + mSelectCity?.n + mSelectDistrict?.n)
                }
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                mSelectCity to null
                mSelectDistrict to null
                mSelectCityPosition = 0
                mSelectDistrictPosition = 0
                rvAddress.smoothScrollToPosition(mSelectProvincePosition)
                mRvData.clear()
                mRvData.addAll(addressPickerBean.province)
                mAdapter.setNewData(mRvData)
                mAdapter.notifyDataSetChanged()
            }
            1 -> {
                mSelectDistrict to null
                mSelectDistrictPosition = 0
                mRvData.clear()
                for (item in addressPickerBean.city) {
                    if (item.p == mSelectProvince?.i) {
                        mRvData.add(item)
                    }
                }
                mAdapter.setNewData(mRvData)
                mAdapter.notifyDataSetChanged()
                rvAddress.smoothScrollToPosition(mSelectCityPosition)
            }
            2 -> {
                isAddTabFinish = true
                mRvData.clear()
                for (item in addressPickerBean.district) {
                    if (item.p == mSelectCity?.i) {
                        mRvData.add(item)
                    }
                }
                mAdapter.setNewData(mRvData)
                mAdapter.notifyDataSetChanged()
                rvAddress.smoothScrollToPosition(mSelectDistrictPosition)
            }

        }
    }

    private fun getColor(colorId: Int) = ContextCompat.getColor(context, colorId)


}

/**
 * 城市地区列表适配器
 */
class AddressAdapter : BaseQuickAdapter<AddressPickerBean.AddressItemBean, BaseViewHolder> {
    override fun convert(helper: BaseViewHolder?, item: AddressPickerBean.AddressItemBean?) {
        helper!!.setText(android.R.id.text1, item?.n)
                .addOnClickListener(android.R.id.text1)

    }

    constructor(layoutResId: Int, data: MutableList<AddressPickerBean.AddressItemBean>?) : super(layoutResId, data)

}