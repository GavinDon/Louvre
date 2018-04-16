package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.PersonalListAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.PersonalBean
import com.stxx.louvre.ui.activity.DeliveryAddressActivity
import com.stxx.louvre.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * description: 个人中心
 * Created by liNan on 2018/2/27 15:20

 */
class PersonalFragment : BaseFragment(), BaseQuickAdapter.OnItemClickListener {

    private lateinit var mAdapter: PersonalListAdapter
    private lateinit var itemData: MutableMap<String, Boolean>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, null, false)
    }

    override fun initView() {
        mAdapter = PersonalListAdapter(R.layout.item_personal, null)
        rv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = mAdapter
        }
        loadData()
        mAdapter.onItemClickListener = this
    }

    /**
     *
     */
    private fun loadData() {
        itemData = mutableMapOf(Pair("退款管理", false),
                Pair("我的拍卖", false),
                Pair("客服与帮助", true),
                Pair("我的保证金", false),
                Pair("收获地址", false),
                Pair("邀请好友", true),
                Pair("申请成为匠人", false)
        )
        var itemList = arrayListOf<PersonalBean>()
        for (i in itemData) {
            itemList.add(PersonalBean(i.key, i.value))
        }
        mAdapter.setNewData(itemList)
    }
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (position) {
            0, 1 -> startActivity<LoginActivity>()
            4 -> startActivity<DeliveryAddressActivity>()
        }
    }

}
