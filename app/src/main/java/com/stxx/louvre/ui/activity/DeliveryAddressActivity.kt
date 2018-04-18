package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.AddressListAdapter
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.AddressListBean
import com.stxx.louvre.selector.SelectorFactory
import com.stxx.louvre.selector.SelectorShape
import com.stxx.louvre.selector.Shape
import com.stxx.louvre.ui.contract.DeliveryAddressContact
import com.stxx.louvre.ui.presenter.DeliveryPresenter
import kotlinx.android.synthetic.main.activity_delivery_address.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.startActivityForResult


/**
 * 收货地址列表
 */
class DeliveryAddressActivity : BaseActivity(), DeliveryAddressContact.View, BaseQuickAdapter.OnItemChildClickListener {



    private lateinit var mAdapter: AddressListAdapter
    private lateinit var deliveryPresenter: DeliveryPresenter
    override fun inflateViewId(): Int = R.layout.activity_delivery_address

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("地址管理")
        deliveryPresenter = DeliveryPresenter()
        deliveryPresenter.attachView(this)
        mAdapter = AddressListAdapter(R.layout.adapter_delivery, null)
        address_tv_new.backgroundDrawable = SelectorFactory.create(SelectorShape.SelectorBuilder()
                .normalColor(ContextCompat.getColor(this, R.color.price_red))
                .pressColor(ContextCompat.getColor(this, R.color.price_red_press))
                .shapeBuilder(Shape.ShapeBuilder())
                .build())
        address_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        address_rv.setHasFixedSize(true)
        address_rv.adapter = mAdapter
        address_tv_new.setOnClickListener {
            startActivityForResult<PlusAddressActivity>(Constant.PLUS_ADDRESS_REQUEST_CODE, "name" to "plus")
        }

        mAdapter.onItemChildClickListener = this
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val id = mAdapter.data[position].id
        if (view?.id == R.id.ada_delivery_tv_deleter) {
            deliveryPresenter.deleterAddress(id, position)
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.openLoadAnimation()
        deliveryPresenter.getAddressList()
    }


    /**
     * 显示地址列表
     */
    override fun showAddress(rows: List<AddressListBean.RowsBean>) {
        mAdapter.setNewData(rows)
    }

    /**
     * 设置默认地址
     */
    override fun setDefault() {
    }

    /**
     * 删除成功
     */
    override fun deleterSuccess(pos: Int) {
        mAdapter.remove(pos)
        mAdapter.notifyItemRemoved(pos)
    }
    override fun onDestroy() {
        super.onDestroy()
        deliveryPresenter.detachView(this)
    }
}
