package com.stxx.louvre.ui.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomSheetDialog
import android.text.TextUtils
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.stxx.louvre.R
import com.stxx.louvre.base.BaseActivity
import com.stxx.louvre.base.Constant
import com.stxx.louvre.entity.CodeAndMsg
import com.stxx.louvre.entity.UploadIconRespBean
import com.stxx.louvre.entity.UserInfoBean
import com.stxx.louvre.entity.UserInfoUpdateReqBean
import com.stxx.louvre.net.MySubscribe
import com.stxx.louvre.net.RetrofitManager
import com.stxx.louvre.net.RxSchedulers
import com.stxx.louvre.net.dialog.ProgressUtils
import com.stxx.louvre.widgets.AddressPickerView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_setting.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.io.File
import java.util.*


/**
 * description:设置中心
 * Created by liNan on 2018/5/8 11:06

 */
class SettingActivity : BaseActivity() {

    private lateinit var userInfo: UserInfoBean
    override fun inflateViewId() = R.layout.activity_setting


    override fun initView(savedInstanceState: Bundle?) {
        setTitle("个人设置")
        setting_tv_city.setOnClickListener { choiceCity() }
        setting_tv_birthday.setOnClickListener { birthdayChoose() }
        setting_fbl_sex.setOnClickListener { sexChoose() }
        setting_fbl_icon.setOnClickListener { chooseIcon() }
        userInfo = Gson().fromJson(SPUtils.getInstance().getString(Constant.USER_INFO), UserInfoBean::class.java)
        showIcon(userInfo.member.picture) //头像
        setting_tv_sex.text = if (userInfo.member.sex == 1) "男" else "女"//性别
        setting_tv_birthday.text = userInfo.member.brithday?.toString()//出生日期
        setting_tv_city.text = "${userInfo.member.province}${userInfo.member.city}${userInfo.member.areaLocal}"//城市
        setting_et_city_detail.setText(userInfo.member.address?.toString())//城市具体地址
        setting_et_truename.setText(userInfo.member.trueName?.toString()) //姓名
        setting_et_nickname.setText(userInfo.member.nickName?.toString())//昵称
        setting_et_email.setText(userInfo.member.email?.toString())//邮箱
        setting_btn_update.setOnClickListener { reqUpdate() }

    }

    /**
     * 性别选择
     */
    private fun sexChoose() {
        val sexArray = arrayOf("女", "男")// 性别选择
        val builder = AlertDialog.Builder(this)// 自定义对话框
        builder.setSingleChoiceItems(sexArray, 0, DialogInterface.OnClickListener { dialog, which ->
            setting_tv_sex.text = sexArray[which]
            dialog.dismiss()// 随便点击一个item消失对话框，不用点击确认取消
        })
        builder.show()// 让弹出框显示
    }

    /**
     * 出生日期选择
     */
    private fun birthdayChoose() {
        val mYear = Calendar.YEAR
        val mMouth = Calendar.MONTH + 1
        val mDay = Calendar.DAY_OF_MONTH
        val dialog = DatePickerDialog(this, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            setting_tv_birthday.text = "$year-$month-$dayOfMonth"
        }, mYear, mMouth, mDay)
        if (dialog.isShowing) {
            dialog.dismiss()
        } else {
            dialog.show()
        }
    }

    /**
     * 选择城市
     */
    private fun choiceCity() {
        val pickerView = AddressPickerView(this)
        val cityDialog = BottomSheetDialog(this)
        pickerView.find<TextView>(R.id.picker_close).setOnClickListener { cityDialog.dismiss() }
        pickerView.find<TextView>(R.id.picker_tv).text = "选择您的地址"
        cityDialog.setContentView(pickerView)
        if (!cityDialog.isShowing) {
            cityDialog.show()
        } else {
            cityDialog.dismiss()
        }
        pickerView.setOnAddressCheckListener(object : AddressPickerView.OnAddressCheckListener {
            override fun onCheckListener(city: String) {
                setting_tv_city.text = city
                cityDialog.dismiss()
            }
        })

    }

    /**
     * 选择头像
     */
    private fun chooseIcon() {
        val rxPermissions = RxPermissions(this)
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
                                .capture(false)
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
        val filePath = "${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}" //android/data/pictures
        val fileName = "headIcon${System.currentTimeMillis()}.png"
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
//        options.withMaxResultSize(48, 48)
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this, Constant.UCROP_ICON_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && null != data)
            when (requestCode) {
            //选择图片
                Constant.CHOOSE_ICON_REQUEST_CODE -> {
                    val mSelected = Matisse.obtainResult(data)
                    uCropImage(mSelected[0])
                }
            //裁剪图片
                Constant.UCROP_ICON_REQUEST_CODE -> {
                    val cropResultUri = UCrop.getOutput(data)
                    val f = File(cropResultUri!!.path)
                    reqUploadFile(f)
                }

            }

    }

    /**
     * 显示图片
     */
    private fun showIcon(strUrl: String) {
        Glide.with(this)
                .load(strUrl)
                .apply(RequestOptions().circleCrop())
                .into(setting_iv_user_icon)
    }

    /**
     * 上传图片
     */
    private fun reqUploadFile(file: File) {
        val userName = SPUtils.getInstance().getString(Constant.USER_ID)
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val serverImgUri = "headinfo/$userName${System.currentTimeMillis()}.png"
        val builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("srcAddress", serverImgUri)
                .addFormDataPart("media", file.name, body)
        val parts = builder.build().parts()
        RetrofitManager.create().uploadMemberIcon(parts)
                .compose(RxSchedulers.applySchedulers())
                .compose(ProgressUtils.applyProgressBar(this))
                .doOnNext {
                    if (TextUtils.equals(it.type, "0"))
                        showIcon(it.url)
                }
                .observeOn(Schedulers.io())
                .flatMap(Function<UploadIconRespBean, Observable<CodeAndMsg>> {
                    return@Function RetrofitManager.create().saveIcon(it.url)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MySubscribe<CodeAndMsg>() {
                    override fun onSuccess(response: CodeAndMsg?) {
                        if (null != response && response.code == 0) {
//                            ToastUtils.showShort("头像更换成功")
                        } else {
                            ToastUtils.showShort(response?.msg)
                        }
                    }
                })

    }

    /**
     * 更新个人信息
     */
    private fun reqUpdate() {
        UserInfoUpdateReqBean().let {
            it.trueName = setting_et_truename.text.toString()
            it.nickName = setting_et_nickname.text.toString()
            val sex = setting_tv_sex.text.toString()
            it.sex = if (sex == "女") 1 else 2
            it.email = setting_et_email.text.toString()
            it.brithday = setting_tv_birthday.text.toString()
            val localArray = setting_tv_city.text.toString().split("-")
            if (localArray.size == 3) {
                it.province = localArray[0]
                it.city = localArray[1]
                it.areaLocal = localArray[2]
            }
            it.address = setting_et_city_detail.text.toString()
            val reqJson = Gson().toJson(it)
            val body = RequestBody.create(MediaType.parse("application/json"), reqJson)
            RetrofitManager.create().updateUserInfo(body)
                    .compose(RxSchedulers.applySchedulers())
                    .compose(ProgressUtils.applyProgressBar(this))
                    .doOnNext {
                        if (it.code == 0) toast("更新成功") else toast("更新失败")
                    }
                    .observeOn(Schedulers.io())
                    .flatMap(Function<CodeAndMsg, Observable<UserInfoBean>> {
                        return@Function RetrofitManager.create().getPersonalInfo()
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : MySubscribe<UserInfoBean>() {
                        override fun onSuccess(response: UserInfoBean?) {
                            if (null != response && null != response.member) {
                                SPUtils.getInstance().put(Constant.USER_ICON, response.member.picture)
                                val userJson = Gson().toJson(response)
                                SPUtils.getInstance().put(Constant.USER_INFO, userJson)
                            }
                        }

                    })

        }


    }

}