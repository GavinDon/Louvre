package com.stxx.louvre.ui.fragment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.PersonalListAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.PersonalBean
import com.stxx.louvre.entity.event.LoginResultEvent
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.ui.WebActivity
import com.stxx.louvre.ui.activity.DeliveryAddressActivity
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.activity.OrderStatusActivity
import com.stxx.louvre.widgets.WaveView
import kotlinx.android.synthetic.main.fragment_mine.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


/**
 * description: 个人中心
 * Created by liNan on 2018/2/27 15:20

 */
class PersonalFragment : BaseFragment(), BaseQuickAdapter.OnItemClickListener, WaveView.OnWaveInterface {

    private lateinit var mAdapter: PersonalListAdapter

    private lateinit var itemData: MutableMap<String, Boolean>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, null, false)
    }

    override fun initView() {
        mAdapter = PersonalListAdapter(R.layout.item_personal, null)
        EventBus.getDefault().register(this)
        rv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = mAdapter
        }
        loadData()
        mAdapter.onItemClickListener = this
//        createNotify()
        waveView.setOnWaveInterface(this)

        tvAllOrder.setOnClickListener { startActivity<OrderStatusActivity>("showIndex" to 0) }
        tvWaitPay.setOnClickListener { startActivity<OrderStatusActivity>("showIndex" to 1) }
        tvReceiveGood.setOnClickListener { startActivity<OrderStatusActivity>("showIndex" to 2) }
        tvHasFinish.setOnClickListener { startActivity<OrderStatusActivity>("showIndex" to 3) }
        tvWaitComment.setOnClickListener { startActivity<OrderStatusActivity>("showIndex" to 4) }
    }

    /**
     * true 代表显示分隔线
     */
    private fun loadData() {
        itemData = mutableMapOf(Pair("发票管理", false),
                Pair("优惠券", true),
                Pair("客服与帮助", false),
                Pair("浏览记录", false),
                Pair("商品收藏", true),
                Pair("收获地址", false)
//                Pair("退出登陆", false)
        )
        var itemList = arrayListOf<PersonalBean>()
        for (i in itemData) {
            itemList.add(PersonalBean(i.key, i.value))
        }
        mAdapter.setNewData(itemList)
    }

    override fun onResume() {
        super.onResume()
        val userName = SPUtils.getInstance().getString(Constant.USER_ID)
        if (userName.isNotEmpty()) {
            tv_user_nickname.text = userName
        }
        iv_user_icon.setOnClickListener {
            if (userName.isEmpty()) {
                startActivity<LoginActivity>()
            }
        }
    }

    /**
     * 登陆成功改变状态
     */
    @Subscribe
    fun onLoginEvent(userBean: LoginResultEvent) {
        tv_user_nickname.text = userBean.userInfoBean.member.userId
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (position) {
        //发票管理
            0 -> startActivity<WebActivity>("url" to Constant.RECEIPT_MANAGER_URL)
        //优惠券
            1 -> startActivity<WebActivity>("url" to Constant.MY_COUPON_URL)
        //客服
            2 -> startActivity<WebActivity>("url" to "http://m.tb.cn/x.eQJdYi")
        //浏览记录
            3 -> startActivity<WebActivity>("url" to Constant.BROWSER_HISTORY_URL)
        // 商品收藏
            4 -> startActivity<WebActivity>("url" to Constant.MY_COLLECTOR_URL)
        //地址管理
            5 -> startActivity<DeliveryAddressActivity>()
//            6 -> {
//                CookiesManager.clearAllCookies()
//                toast("您已退出登陆")
//            }

        }
    }
//
//    private fun chooseIcon() {
//        val rxPermissions = RxPermissions(this.activity!!)
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .subscribe({
//                    if (it) {
//                        Matisse.from(this)
//                                .choose(MimeType.allOf())
//                                .countable(true)
//                                .maxSelectable(1)
//                                .gridExpectedSize(dip(120))
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.85f)
//                                .imageEngine(PicassoEngine())
//                                .capture(true)
//                                .captureStrategy(CaptureStrategy(true, "com.stxx.louvre.fileprovider"))
//                                .theme(R.style.Matisse_Dracula)
//                                .forResult(Constant.CHOOSE_ICON_REQUEST_CODE)
//
//                    } else {
//                        ToastUtils.showLong("您拒绝授权")
//                    }
//                }, {
//                    toast(it.message.toString())
//                })
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Constant.CHOOSE_ICON_REQUEST_CODE && resultCode == RESULT_OK) {
//            val mSelected = Matisse.obtainResult(data)
//            val multi = MultiTransformation(
//                    RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.ALL))
//            Glide.with(this).load(mSelected[0]).apply(RequestOptions.bitmapTransform(multi)).into(iv_user_icon)
//            val filePath = "${context!!.filesDir}/headInfo/"
//            val fileName = "${System.currentTimeMillis()}.png"
//            var tempUri = Uri.fromFile(File(filePath, fileName))
//            tempUri = mSelected[0]
//
//        }
//
//    }

    private fun reqUploadFile(file: File) {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val req = MultipartBody.Part.createFormData("srcAddress", file.name, body)
        RetrofitManager.create().uploadMemberIcon(req)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        LogUtils.i(t?.message)
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        LogUtils.i(response?.body()?.string())
                    }
                })
    }


    override fun onWaveAnimation(y: Float) {
        val llp = tv_user_nickname.layoutParams as ConstraintLayout.LayoutParams
        llp.setMargins(0, 0, 0, y.toInt())
        tv_user_nickname.layoutParams = llp
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }


}
