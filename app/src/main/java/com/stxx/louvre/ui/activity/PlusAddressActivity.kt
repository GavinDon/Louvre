package com.stxx.louvre.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomSheetDialog
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.jakewharton.rxbinding2.view.RxView
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.RequestEntity
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.widgets.AddressPickerView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_plus_address.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit


/**
 * 新添加地址
 */
class PlusAddressActivity : BaseActivity(), View.OnClickListener {
    private var addressArray = listOf<String>() //省市区用'-'分割后的数组
    private val areaDialog: BottomSheetDialog by lazy { BottomSheetDialog(this) }
    override fun inflateViewId() = R.layout.activity_plus_address

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("新建联系人")
        plus_address_ll_contract.setOnClickListener(this)
        plus_address_tv_checked_area.setOnClickListener(this)
        plus_address_save.setOnClickListener(this)
        RxView.clicks(plus_address_save).throttleFirst(1000, TimeUnit.MILLISECONDS)
    }

    /**
     * 打开通讯录
     */
    private fun openContact() {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        val uri = ContactsContract.Contacts.CONTENT_URI
        val projection = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)
        contentResolver.query(uri, projection, null, null, null)
        val intent = Intent()
        intent.action = "android.intent.action.PICK"
        intent.addCategory("android.intent.category.DEFAULT")
        intent.type = "vnd.android.cursor.dir/phone_v2"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            //Intent { dat=content://com.android.contacts/data/1 flg=0x1 }  根据最后的1或者其他数字来从数据库查询
            val uri = data.data
            val contentResolver = contentResolver
            //使用游标来查询
            val cursor = contentResolver.query(uri,
                    null, null, null, null)
            cursor.moveToFirst()
            val number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Nickname.DISPLAY_NAME))
            cursor.close()
            plus_address_et_name.setText(name)
            plus_address_et_phone.setText(number)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
        //从通迅录中选取联系人
            R.id.plus_address_ll_contract -> {
                RxPermissions(this).request(android.Manifest.permission.READ_CONTACTS)
                        .subscribe({
                            if (it) {
                                //当同意授权之后打开通讯录
                                openContact()
                            } else {
                                toast("不能读取联系人")
                            }
                        }, {
                            toast(it.message.toString())
                        })
            }
        //选取地址
            R.id.plus_address_tv_checked_area -> {
                val v = AddressPickerView(this)
                val pickerClose = v.find<TextView>(R.id.picker_close)
                areaDialog.setContentView(v)
                if (!areaDialog.isShowing) areaDialog.show()
                //选完地址回调显示结果
                v.setOnAddressCheckListener(object : AddressPickerView.OnAddressCheckListener {
                    override fun onCheckListener(str: String) {
                        addressArray = str.split("-")
                        plus_address_tv_checked_area.text = str
                        areaDialog.dismiss()
                    }
                })
                //右上角关闭按钮
                pickerClose.setOnClickListener { areaDialog.dismiss() }

            }
            R.id.plus_address_save -> {
                addNewAddress()
            }
        }
    }

    /**
     * 添加新地址
     */
    private fun addNewAddress() {
        val name = plus_address_et_name.text.toString()
        val phone = plus_address_et_phone.text.toString()
        val detail = plus_address_et_detail.text.toString()
        val province: String
        val city: String
        val area: String
        if (addressArray.size >= 3) {
            province = addressArray[0]
            city = addressArray[1]
            area = addressArray[2]
            if (name.isNotEmpty() and phone.isNotEmpty() and detail.isNotEmpty()) {
                val reqJson = Gson().toJson(RequestEntity.NewPlusAddress(name, phone, null, detail, province, city, area))
                val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), reqJson)
                RetrofitManager.create().getPlusAddress(body)
                        .compose(RxSchedulers.applySchedulers())
                        .compose(ProgressUtils.applyProgressBar(this))
                        .subscribe(object : MySubscribe<CodeAndMsg>() {
                            override fun onSuccess(response: CodeAndMsg?) {
                                if (response?.code == 0) {
                                    ToastUtils.setGravity(Gravity.CENTER, 0, 0)
                                    ToastUtils.showLong("添加成功")
                                    this@PlusAddressActivity.finish()
                                } else {
                                    ToastUtils.setGravity(Gravity.CENTER, 0, 0)
                                    ToastUtils.showLong(response?.msg)
                                }
                            }

                        })
            } else {
                ToastUtils.setGravity(Gravity.CENTER, 0, 0)
                ToastUtils.showLong("不能为空")
            }
        } else {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0)
            ToastUtils.showLong("请选择地址")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (areaDialog.isShowing) {
            areaDialog.dismiss()
        }
    }
}
