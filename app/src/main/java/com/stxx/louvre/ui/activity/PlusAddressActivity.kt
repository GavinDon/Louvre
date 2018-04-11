package com.stxx.louvre.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.TextView
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.widgets.AddressPickerView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_plus_address.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


/**
 * 新添加地址
 */
class PlusAddressActivity : BaseActivity(), View.OnClickListener {

    private val areaDialog:BottomSheetDialog by lazy { BottomSheetDialog(this) }
    override fun inflateViewId() = R.layout.activity_plus_address

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("新建联系人")
        plus_address_ll_contract.setOnClickListener(this)
        plus_address_tv_checked_area.setOnClickListener(this)
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
                        plus_address_tv_checked_area.text = str
                        areaDialog.dismiss()
                    }
                })
                //右上角关闭按钮
                pickerClose.setOnClickListener { areaDialog.dismiss() }

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (areaDialog.isShowing) {
            areaDialog.dismiss()
        }
    }
}
