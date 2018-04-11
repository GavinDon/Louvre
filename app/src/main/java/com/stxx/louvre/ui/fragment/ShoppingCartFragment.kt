package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.stxx.louvre.R
import com.stxx.louvre.adapter.ShoppingCartAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.ShoppingCarBean
import com.stxx.louvre.entity.event.BottomBadgeEvent
import com.stxx.louvre.entity.event.ShoppingCartEvent
import com.stxx.louvre.selector.SelectorFactory
import com.stxx.louvre.selector.SelectorShape
import com.stxx.louvre.selector.Shape
import com.stxx.louvre.ui.activity.ConfirmOrderActivity
import com.stxx.louvre.ui.activity.GoodsDetailActivity
import kotlinx.android.synthetic.main.fragment_follow.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.support.v4.startActivity


/**
 * description: 购物车
 * Created by liNan on 2018/2/27 15:20

 */
class ShoppingCartFragment : BaseFragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private lateinit var lst: MutableList<ShoppingCarBean>
    private lateinit var mAdapter: ShoppingCartAdapter
    private var isClickItemCb = false
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follow, null, false)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 从适配器中发送数据过来(此处相当于观察者)
     * 此处只更新底部总价、数量、是否全选
     */
    @Subscribe
    fun onEvent(shoppingCartEvent: ShoppingCartEvent) {
        shopping_cart_tv_price.text = shoppingCartEvent.totalPrice.toString()
        shopping_cart_tv_price1.text = shoppingCartEvent.totalPrice.toString()
        //判断若选中的个数在列表item总数之间只需改变底部checkbox的状态 不需要进行额外的操作
        val num = shoppingCartEvent.checkNum
        if (num!! < mAdapter.itemCount && num > 0) {
            isClickItemCb = true
        }
        //当底部checkbox本身就是未选中状态时，再次把底部checkbox 设置为未选中不会触发回调方法，所以不能改变isClickItemCb的状态
        //也就不能在全选时有效
        if (!shopping_cart_cb_all.isChecked) {
            isClickItemCb = false
        }
        shopping_cart_cb_all.isChecked = shoppingCartEvent.state!!
        //发送数据到mainActivity来更新底部购物车按钮badge数量
        EventBus.getDefault().post(BottomBadgeEvent(shoppingCartEvent.totalNum))
    }

    override fun initView() {
        mLayoutManager = LinearLayoutManager(this.context)
        rvShoppingCar.setHasFixedSize(true)
        rvShoppingCar.layoutManager = mLayoutManager
        RecyclerViewDivider.with(this.context!!).color(ContextCompat.getColor(this.context!!, R.color.dividerColor)).build().addTo(rvShoppingCar)
        mAdapter = ShoppingCartAdapter(R.layout.adapter_shoppingcart, null)
        rvShoppingCar.adapter = mAdapter
        loadData()
        //底部全选checkbox的监听事件
        shopping_cart_cb_all.setOnCheckedChangeListener(this)
        mAdapter.onItemChildClickListener = this
        //设置提交订单按钮背景色
        val btnBackGround = SelectorFactory.create(SelectorShape.SelectorBuilder()
                .normalColor(ContextCompat.getColor(this.context!!, R.color.price_red))
                .pressColor(ContextCompat.getColor(this.context!!, R.color.price_red_press))
                .shapeBuilder(Shape.ShapeBuilder())
                .build())
        shopping_cart_btn_pay.backgroundDrawable = btnBackGround
        shopping_cart_btn_pay.setOnClickListener(this)
        //判断rv滚动之后显示滚到顶部按钮
        rvShoppingCar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (mLayoutManager.findFirstVisibleItemPosition() >= 1) {
                    shopping_cart_ib_scroll.visibility = View.VISIBLE
                } else {
                    shopping_cart_ib_scroll.visibility = View.GONE
                }
            }
        })
        shopping_cart_ib_scroll.setOnClickListener {
            rvShoppingCar.smoothScrollToPosition(0)
        }
    }

    /**
     * 请求数据，当数据为空时显示emptyView
     */
    private fun loadData() {
        lst = mutableListOf()
        for (i in 0..15) {
            lst.add(ShoppingCarBean(false, i.toString(), "华为畅享7plus 3GB + 32GB 香槟金",
                    "数量：0.345kg", 1F, 1))
        }
        //数据为空时设置的空页面
        if (lst.isNotEmpty()) {
            mAdapter.addData(lst)
        } else {
            shopping_cart_cl_balance.visibility = View.GONE
        }
    }

    /**
     * 底部全选checkbox的监听事件
     * 当全选的时候列表中的checkbox为选中状态，再次全选的时候取掉全选状态
     */
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isClickItemCb)
        //当是点击item的checkbox时，不用遍历所有数据改变状态，只需要改变底部checkbox的状态就行
            isClickItemCb = false
        else
            mAdapter.AllOrNone(isChecked)
    }

    override fun onClick(v: View?) {
        if (v is Button) {
            startActivity<ConfirmOrderActivity>()
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        startActivity<GoodsDetailActivity>()
    }


}

