package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.ArticleResponseBean
import com.stxx.louvre.entity.RequestEntity
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.ui.WebActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_tab.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity

/**
 * description: 首页 艺术家
 * Created by liNan on 2018/4/10 8:55

 */
class HomeTabArticleFragment : BaseFragment() {
    private lateinit var mAdapter: ArticleListAdapter
    private var lst = mutableListOf<ArticleResponseBean.RowsBean>()
    private var totalPage: Int = 1
    private var currentPageNo: Int = 1
    private val pageSize: String = "10"
    private var isErr: Boolean = false //加载是否失败
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, null, false)
    }

    override fun initView() {
        mAdapter = ArticleListAdapter(R.layout.adapter_home_tab, lst)
        val stageLayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tabRv.setHasFixedSize(true)
        tabRv.layoutManager = stageLayout
        tabRv.adapter = mAdapter
        tabSwipeRefresh.isEnabled = false
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        reqData("$currentPageNo", pageSize)
        mAdapter.setOnItemClickListener { _, _, position ->
            startActivity<WebActivity>("url" to "${Constant.ARTICLE_DETAIL_URL}?id=${mAdapter.data[position].id}")
        }

        /**
         * 上拉加载更多
         */
        mAdapter.setOnLoadMoreListener({
            if (currentPageNo >= totalPage) {
                mAdapter.loadMoreEnd()
            } else {
                if (isErr) {
                    mAdapter.loadMoreFail()
                } else {
                    reqData("$currentPageNo", pageSize)
                    mAdapter.loadMoreComplete()
                }
            }
        }, tabRv)
    }

    private fun reqData(pageNumber: String, pagerSize: String) {
        val reqJson = Gson().toJson(RequestEntity.ArticleReqBean(pageNumber.toInt(), pagerSize.toInt()))
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJson)
        RetrofitManager.create().getArticleList(body)
                .compose(RxSchedulers.applySchedulers())
                .subscribe(object : MySubscribe<ArticleResponseBean>() {
                    override fun onSuccess(response: ArticleResponseBean?) {
                        if (null != response) {
                            totalPage = response.totalPages //页面总数
                            currentPageNo = response.pageNo + 1 //当前页面索引加1是上拉加载数据时的索引
                            if (pageNumber.toInt() == 1) {
                                lst.addAll(response.rows)
                                //设置瀑布流高度差来达到效果
                                for (i in lst.indices) {
                                    lst[i].hits = (i % 2) * 100 + 500
                                }
                                mAdapter.setNewData(lst)
                            } else {
                                val lst2 = mutableListOf<ArticleResponseBean.RowsBean>()
                                lst2.addAll(response.rows)
                                //设置瀑布流高度差来达到效果
                                for (i in lst2.indices) {
                                    lst2[i].hits = (i % 2) * 100 + 500
                                }
                                mAdapter.addData(lst2)
                            }
                            isErr = false
                        } else {
                            isErr = true
                        }
                    }
                })
    }
}

class ArticleListAdapter(layoutResId: Int, data: MutableList<ArticleResponseBean.RowsBean>?) : BaseQuickAdapter<ArticleResponseBean.RowsBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: ArticleResponseBean.RowsBean?) {
        helper!!.setText(R.id.ada_home_article_name, item?.artName)
        val iv = helper.getView<ImageView>(R.id.ada_home_tab_iv)
        iv.layoutParams.height = item!!.hits
        val multi = MultiTransformation(
                RoundedCornersTransformation(18, 0, RoundedCornersTransformation.CornerType.ALL))
        Glide.with(mContext).load(item.picture)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(iv)
    }

}