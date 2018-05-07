package com.stxx.louvre.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.PersonalListAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.base.Constant
import com.stxx.louvre.base.Constant.Companion.CHOOSE_ICON_REQUEST_CODE
import com.stxx.louvre.base.Constant.Companion.UCROP_ICON_REQUEST_CODE
import com.stxx.louvre.entity.PersonalBean
import com.stxx.louvre.entity.event.LoginResultEvent
import com.stxx.louvre.ui.WebActivity
import com.stxx.louvre.ui.activity.DeliveryAddressActivity
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.activity.OrderStatusActivity
import com.stxx.louvre.widgets.WaveView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.fragment_mine.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
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
            } else {
                chooseIcon()
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


    private fun chooseIcon() {
        val rxPermissions = RxPermissions(this.activity!!)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe({
                    if (it) {
                        Matisse.from(this)
                                .choose(MimeType.allOf())
                                .countable(true)
                                .maxSelectable(1)
                                .gridExpectedSize(dip(120))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .imageEngine(PicassoEngine())
                                .capture(true)
                                .captureStrategy(CaptureStrategy(true, "com.stxx.louvre.fileprovider"))
                                .theme(R.style.Matisse_Dracula)
                                .forResult(Constant.CHOOSE_ICON_REQUEST_CODE)

                    } else {
                        ToastUtils.showLong("您拒绝授权")
                    }
                }, {
                    toast(it.message.toString())
                })
    }

    private fun uCropImage(sourceUri: Uri) {

        val filePath = "${context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}" //android/data/pictures
        val fileName = "headIcon${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(filePath, fileName)) //裁剪完成后保存图片路径

        //裁剪属性设置
        val options = UCrop.Options()
        options.setToolbarColor(Color.parseColor("#303030"))
        options.setStatusBarColor(Color.parseColor("#303030"))
        options.setShowCropGrid(false)
        options.setShowCropFrame(false)
        options.setCircleDimmedLayer(true) //设置圆形裁剪
        options.setHideBottomControls(true)//隐藏下边控制栏
        options.withAspectRatio(16F, 9F)
        options.withMaxResultSize(48, 48)
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(context!!, this, UCROP_ICON_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && null != data)
            when (requestCode) {
            //选择图片
                CHOOSE_ICON_REQUEST_CODE -> {
                    val mSelected = Matisse.obtainResult(data)
                    uCropImage(mSelected[0])
                }
            //裁剪图片
                UCROP_ICON_REQUEST_CODE -> {
                    val cropResultUri = UCrop.getOutput(data)
                    val f = File(cropResultUri!!.path)
                    reqUploadFile(f)
                }

            }

    }


    private fun reqUploadFile(file: File) {
        val body = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val req = MultipartBody.Part.createFormData("srcAddress", file.name, body)
        val serverImgUri = "headinfo/18602928514${System.currentTimeMillis()}"
        val iconIo= RequestBody.create(MediaType.parse("text/plan"),"file")
//        RetrofitManager.create().uploadMemberIcon()
//                .enqueue(object : Callback<ResponseBody> {
//                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
//                        LogUtils.i(t?.message)
//                    }
//
//                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
//                        LogUtils.i(response?.body()?.string())
//                    }
//                })
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
