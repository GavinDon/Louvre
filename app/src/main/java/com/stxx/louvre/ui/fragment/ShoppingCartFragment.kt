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
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.ShoppingCartListRespBean
import com.stxx.louvre.entity.event.BottomBadgeEvent
import com.stxx.louvre.entity.event.ConvertFragment
import com.stxx.louvre.entity.event.ShoppingCartEvent
import com.stxx.louvre.listener.ILogin
import com.stxx.louvre.selector.SelectorFactory
import com.stxx.louvre.selector.SelectorShape
import com.stxx.louvre.selector.Shape
import com.stxx.louvre.ui.WebActivity
import com.stxx.louvre.ui.activity.ConfirmOrderActivity
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.contract.ShoppingCartContact
import com.stxx.louvre.ui.presenter.ShoppingCartPresenter
import com.stxx.louvre.widgets.SubmitButton
import kotlinx.android.synthetic.main.fragment_follow.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


/**
 * description: 购物车
 * Created by liNan on 2018/2/27 15:20

 */
class ShoppingCartFragment : BaseFragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, ShoppingCartContact.View, ILogin {


    private lateinit var mAdapter: ShoppingCartAdapter
    private var isClickItemCb = false
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mPresenter: ShoppingCartPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follow, null, false)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    /**
     * 在onResume中判断是否登陆来选择显示的空布局
     */
    override fun onResume() {
        super.onResume()
        if (judgeIsLogin()) {
            shopping_cart_srl.isEnabled = true
            //让显示自动刷新  *注意只是出现刷新动画，不会请求数据 所以手动请求一次数据*
            shopping_cart_srl.measure(0, 0)
            shopping_cart_srl.post({
                shopping_cart_srl.isRefreshing = true
            })
            mPresenter.reqShoppingCartList()
        } else {
            //当未登陆时禁止下拉
            shopping_cart_srl.isEnabled = false
            showUnLoginLayout()
        }
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
        if (shoppingCartEvent.totalNum <= 0) showEmptyShoppingLayout() //如果客户删除购物车到0时则显示空布局
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
                if (mLayoutManager.findFirstVisibleItemPosition() >= 4) {
                    shopping_cart_ib_scroll.visibility = View.VISIBLE
                } else {
                    shopping_cart_ib_scroll.visibility = View.GONE
                }
            }
        })
        shopping_cart_ib_scroll.setOnClickListener {
            rvShoppingCar.smoothScrollToPosition(0)
        }
        mPresenter = ShoppingCartPresenter()
        mPresenter.attachView(this)
        //下拉刷新
        shopping_cart_srl.setOnRefreshListener {
            mPresenter.reqShoppingCartList()
        }
    }

    /**
     * 当加载数据状态为0且size大于0时回调loadListSuccess
     */
    override fun loadListSuccess(shoppingCartList: ShoppingCartListRespBean) {
        mAdapter.setNewData(shoppingCartList.rows)
        mAdapter.AllOrNone(true) //返回的数据默认全部选中
        shopping_cart_srl.isRefreshing = false
        shopping_cart_cl_balance.visibility = View.VISIBLE
    }

    override fun loadListFail() {
        showEmptyShoppingLayout() //在登陆成功的前提下 没有加载到数据则显示此布局
        shopping_cart_srl.isRefreshing = false
    }

    /**
     * 显示未登陆布局
     */
    private fun showUnLoginLayout() {
        val view = layoutInflater.inflate(R.layout.empty_shping_cart, rvShoppingCar, false)
        mAdapter.emptyView = view
        view.find<SubmitButton>(R.id.empty_btn).setOnClickListener { startActivity<LoginActivity>() }
        shopping_cart_cl_balance.visibility = View.GONE
    }

    /**
     * 显示没有商品布局(去逛逛)
     */
    private fun showEmptyShoppingLayout() {
        //若适配器数据<=0则认为没有商品加入购物车
        if (mAdapter.data.size <= 0) {
            shopping_cart_cl_balance.visibility = View.GONE
            val view = layoutInflater.inflate(R.layout.empty_goods, rvShoppingCar, false)
            mAdapter.emptyView = view
            view.find<SubmitButton>(R.id.empty_goods_btn).setOnClickListener {
                //通知mainActivity切换首页fragment
                EventBus.getDefault().post(ConvertFragment("0"))
            }
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

    /**
     * 结算购物车
     */
    override fun onClick(v: View?) {
        if (v is Button) {
            //当前台未选中任何商品时不进行提交 
            val data = mAdapter.data
            when {
                data.none { it.status == 0 } -> toast("未选中任何商品")
                data.filter { it.status == 0 }.size != 1 -> toast("目前只能选中一种商品来进行购买")
                else -> {
                    mPresenter.reqBalanceAccounts(data)

                }
            }
        }
    }

    /**
     * 去结算成功之后 跳转确认订单页面
     */
    override fun respBanlanceAccounts() {
        startActivity<ConfirmOrderActivity>(Constant.INTENT_CONFIRM_ORDER to mAdapter.data.filter { it.status == 0 })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        startActivity<WebActivity>("url" to Constant.PROTFOLO_DETAIL_URL + mAdapter.data[position].id)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView(this)
    }


}

