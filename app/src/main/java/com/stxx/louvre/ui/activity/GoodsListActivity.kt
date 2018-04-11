package com.stxx.louvre.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import kotlinx.android.synthetic.main.activity_goos_list.*
import org.jetbrains.anko.startActivity

class GoodsListActivity : BaseActivity(), View.OnClickListener {


    private lateinit var mAdapter: GoodsListAdapter
    private val lstData = mutableListOf<GoodListBean>()

    override fun inflateViewId() = R.layout.activity_goos_list

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = GoodsListAdapter(R.layout.adapter_goods_list, null)
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        goods_list_rv.setHasFixedSize(true)
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        RecyclerViewDivider.with(this).color(ContextCompat.getColor(this, R.color.dividerColor)).build().addTo(goods_list_rv)
        goods_list_rv.layoutManager = lm
        goods_list_rv.adapter=mAdapter
        loadData()
        tvPrice.setOnClickListener(this)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity<GoodsDetailActivity>()
        }

    }

    private fun loadData() {
        val imgUrl = "http://m.qpic.cn/psb?/86a78e84-6bb5-4d12-aaf6-a659330f4dbe/A*8q3nJhWjwdvcrOp6oaVR3tLDD201CbcsbxAkbmbrI!/b/dGEBAAAAAAAA&bo=tAB5AAAAAAADB.8!&rf=viewer_4"
        val introduce = resources.getString(R.string.statement)
        val lstData2 = mutableListOf<GoodListBean>()

        for (i in 0..10) {
            lstData.add(GoodListBean(imgUrl, introduce, (2 * i + 1).toDouble(), common = null))
        }
        mAdapter.setNewData(lstData)

        mAdapter.setOnLoadMoreListener({
            for (i in 0 until 10) {
                lstData2.add(GoodListBean(imgUrl, introduce, (2 * i + 1).toDouble(), common = null))
            }
           goods_list_rv.postDelayed({
                mAdapter.addData(lstData2)
                mAdapter.loadMoreComplete()

            }, 1000)

        }, goods_list_rv)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvPrice -> {
                val a = mAdapter.data.sortBy {
                    it.price
                }
                println(a.toString())
            }
        }
    }

}

data class GoodListBean(val imgurl: String?, val introduce: String, val price: Double, val common: String?)

class GoodsListAdapter(layoutResId: Int, data: MutableList<GoodListBean>?) : BaseQuickAdapter<GoodListBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: GoodListBean?) {
        helper!!.setText(R.id.ada_goods_list_tv_introduce, item!!.introduce)
                .setText(R.id.ada_goods_list_tv_price, item!!.price.toString())
                .setImageResource(R.id.ada_goods_list_img,R.mipmap.start_up)
    }
}
