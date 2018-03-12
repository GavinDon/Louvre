package com.stxx.louvre.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.stxx.louvre.R
import com.stxx.louvre.adapter.ShoppingCarAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.base.Constaint
import com.stxx.louvre.entity.ShoppingCarBean
import com.stxx.louvre.selector.SelectorFactory
import com.stxx.louvre.selector.SelectorShape
import com.stxx.louvre.selector.Shape
import com.stxx.louvre.ui.activity.GoodsDetailActivity
import com.stxx.louvre.utils.MyUtil
import kotlinx.android.synthetic.main.fragment_follow.*
import org.jetbrains.anko.backgroundDrawable


/**
 * description: 购物车
 * Created by liNan on 2018/2/27 15:20

 */
class ShoppingCartFragment : BaseFragment(), CompoundButton.OnCheckedChangeListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, ShoppingCarAdapter.OnAmountChangeListener {


    private lateinit var lst: MutableList<ShoppingCarBean>
    private lateinit var mAdapter: ShoppingCarAdapter
    private var fTotalPrice = 0.0
    private var chooseCbNum = 0 //选中checkbox 的数量
    private var isClickItemCb = false
    private lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follow, null, false)
    }

    override fun initView() {
        mLayoutManager = LinearLayoutManager(this.context)
        rvShoppingCar.setHasFixedSize(true)
        rvShoppingCar.layoutManager = mLayoutManager
        RecyclerViewDivider.with(this.context!!).color(ContextCompat.getColor(this.context!!, R.color.dividerColor)).build().addTo(rvShoppingCar)
        mAdapter = ShoppingCarAdapter(R.layout.adapter_shoppingcart, null)
        rvShoppingCar.adapter = mAdapter
        loadData()
        //左边checkbox的点击事件
        mAdapter.onItemChildClickListener = this
        //item 点击事件
        mAdapter.onItemClickListener = this
        //数量加减监听事件
        mAdapter.setOnAmountChange(this)
        //底部全选checkbox的监听事件
        shopping_cart_cb_all.setOnCheckedChangeListener(this)
        //设置提交订单按钮背景色
        val btnBackGround = SelectorFactory.create(SelectorShape.SelectorBuilder()
                .normalColor(ContextCompat.getColor(this.context!!, R.color.price_red))
                .pressColor(ContextCompat.getColor(this.context!!, R.color.price_red_press))
                .shapeBuilder(Shape.ShapeBuilder())
                .build())
        shopping_cart_btn_pay.backgroundDrawable = btnBackGround
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
     * 购物车列表中checkbox的监听事件
     * 当变化时对应的底部‘结算’控件跟着变化
     */
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val mData = mAdapter.data[position]
        val itemPrice = mData.price * mData.goodsAmount
        //选中时加总价 或者取消选中时减总价
        if (view is CheckBox) {
            if (view.isChecked) {
                fTotalPrice += itemPrice
                chooseCbNum++
            } else {
                fTotalPrice -= itemPrice
                chooseCbNum--
            }
            //更新适配器中的checkbox 状态
            mData.checked = view.isChecked
            mAdapter.notifyItemChanged(position, mData)
        }
        //如果当前checkbox是false 则认为全选不成立，改变全选的状态
        if (!mAdapter.data[position].checked) {
            isClickItemCb = true
            clickItemCheckbox(position)
        }
        if (chooseCbNum == mAdapter.itemCount) {
            shopping_cart_cb_all.isChecked = true
        }
        if (oldPosition == position) {
            oldPosition = -1
        }

        shopping_cart_cl_total.visibility = View.GONE
        shopping_cart_cl_check_total.visibility = View.VISIBLE
        shopping_cart_tv_price1.text = fTotalPrice.toString() //设置总价
        shopping_cart_tv_preferential.text = "总额:￥$fTotalPrice 立减￥0.00"
    }

    /**
     * 当点击购物车列表中的一个item时
     * @param position 代表点击了哪个Item
     */
    private fun clickItemCheckbox(position: Int) {
        val mData = mAdapter.data[position]
        mData.checked = false
        mAdapter.notifyItemChanged(position, mData)
        //当底部checkbox本身就是未选中状态时，再次把底部checkbox 设置为未选中不会触发回调方法，所以不能改变isClickItemCb的状态
        //也就不能在全选时有效
        if (!shopping_cart_cb_all.isChecked) {
            isClickItemCb = false
        }
        shopping_cart_cb_all.isChecked = false
    }

    /**
     * 底部全选checkbox的监听事件
     * 当全选的时候列表中的checkbox为选中状态，再次全选的时候取掉全选状态
     */
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isClickItemCb) {
            //当是点击item的checkbox时，不用遍历所有数据改变状态，只需要改变底部checkbox的状态就行
            isClickItemCb = false
        } else {
            fTotalPrice = 0.0
            if (isChecked) {
                //全选
                for (i in 0 until mAdapter.itemCount) {
                    val mData = mAdapter.data[i]
                    mData.checked = true
                    fTotalPrice += (mData.price * mData.goodsAmount)
                }
                chooseCbNum = mAdapter.itemCount
                shopping_cart_cl_total.visibility = View.GONE
                shopping_cart_cl_check_total.visibility = View.VISIBLE
                shopping_cart_tv_price1.text = fTotalPrice.toString() //设置总价
                shopping_cart_tv_preferential.text = "总额:￥$fTotalPrice 立减￥0.00"
            } else {
                //全不选
                for (i in 0 until mAdapter.itemCount) {
                    val mData = mAdapter.data[i]
                    mData.checked = false
                }
                chooseCbNum = 0
                shopping_cart_cl_total.visibility = View.VISIBLE
                shopping_cart_cl_check_total.visibility = View.GONE
            }
            mAdapter.notifyItemRangeChanged(0, mAdapter.itemCount, mAdapter.data)
//        mAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 计数控件amountView的监听回调，
     * 当数量变化时重新计算总价格,当前的checkbox为选中状态
     */
    private var oldPosition = -1  //上一个加减商品数量的位置

    override fun onAmountChange(nowAmount: Int, position: Int) {

        //第一次点击的时候肯定不相等，所以肯定会执行判断item是否checked
        //若已经check则直接进行总价钱的计算并显示价格
        //若没有check则进行item 的选中状态修改并更新适配器，
        //当选中的个数与列表的个数相等时 则显示底部的checkbox被选中状态
        //还有一种情况则是用户点击添加商品个数然后接着点击左边的checkbox取消选中，
        // 取消完之后又接着添加或者减少商品个数，此时无论如何也不会进行checkbox 是否check的判断
        // 所以要在checkbox的点击事件中把OldPosition置为-1
        if (oldPosition != position && !mAdapter.data[position].checked) {
            mAdapter.data[position].checked = true
            mAdapter.notifyItemChanged(position, mAdapter.data[position])
            if (++chooseCbNum == mAdapter.itemCount) {
//                isClickItemCb = false
                shopping_cart_cb_all.isChecked = true
            }
        }
        oldPosition = position
        fTotalPrice = 0.0
        for (i in 0 until mAdapter.itemCount) {
            val mData = mAdapter.data[i]
            if (mData.checked) {
                fTotalPrice += (mData.price * mData.goodsAmount)
            }
        }
        shopping_cart_cl_total.visibility = View.GONE
        shopping_cart_cl_check_total.visibility = View.VISIBLE
        shopping_cart_tv_price1.text = fTotalPrice.toString() //设置总价
        shopping_cart_tv_preferential.text = "总额:￥$fTotalPrice 立减￥0.00"

    }

    /**
     * item点击事件 共享动画
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val v = mAdapter.getViewByPosition(rvShoppingCar, position, R.id.iv_shopping_cart)
        if (v != null) {
            v.isDrawingCacheEnabled = true
            val bitmap = v.drawingCache
            val bitmapByte = MyUtil.bitmap2Bytes(bitmap)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this.activity!!, v, Constaint.SHARE_ELMMENT).toBundle()
            val intent = Intent()
            intent.putExtra("bitmap", bitmapByte)
            intent.setClass(context, GoodsDetailActivity::class.java)
//            ActivityCompat.startActivity(context!!, intent, options)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fTotalPrice = 0.0
        chooseCbNum = 0
    }


}

