package com.stxx.louvre.ui.fragment

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.constraint.ConstraintLayout
import android.support.v4.app.NotificationCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stxx.louvre.R
import com.stxx.louvre.adapter.PersonalListAdapter
import com.stxx.louvre.base.BaseFragment
import com.stxx.louvre.entity.PersonalBean
import com.stxx.louvre.net.CookiesManager
import com.stxx.louvre.ui.WebActivity
import com.stxx.louvre.ui.activity.DeliveryAddressActivity
import com.stxx.louvre.ui.activity.LoginActivity
import com.stxx.louvre.ui.activity.RegisterActivity
import com.stxx.louvre.widgets.WaveView
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


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
        rv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = mAdapter
        }
        loadData()
        mAdapter.onItemClickListener = this
        createNotify()
        waveView.setOnWaveInterface(this)
        iv_user_icon.setOnClickListener { startActivity<LoginActivity>() }
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
            0, 1 -> startActivity<RegisterActivity>()
            4 -> startActivity<DeliveryAddressActivity>()
            2 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    showNotify()
            }
            3 -> {
//                val aa = CookiesManager.getCookies()[0].value()
//                LogUtils.i(aa)
            }
            5 -> {
                startActivity<WebActivity>()
            }
            6 ->{
                CookiesManager.clearAllCookies()
                toast("您已退出登陆")
            }

        }
    }

    private fun createNotify() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "chat"
            val channelName = "聊天信息"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            createNotifyChannel(channelId, channelName, importance)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotifyChannel(channelId: String, channelName: String, importance: Int) {
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = context!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun showNotify() {
        val notificationManager = context!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = notificationManager.getNotificationChannel("chat")
        if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context!!.packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
            startActivity(intent)
            toast("请手动将打开通知")
        }
        val notification = NotificationCompat.Builder(context!!, "chat")
                .setShowWhen(true)
                .setContentText("今天中午吃什么")
                .setContentTitle("收到一条消息")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
        notificationManager.notify(1, notification)
    }

    override fun onWaveAnimation(y: Float) {
        val llp = tv_user_nickname.layoutParams as ConstraintLayout.LayoutParams
        llp.setMargins(0, 0, 0, y.toInt())
        tv_user_nickname.layoutParams = llp
    }


}
