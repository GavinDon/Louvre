package com.stxx.louvre.utils

import android.Manifest
import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import com.blankj.utilcode.util.ToastUtils
import com.stxx.louvre.R
import com.stxx.louvre.base.Constant
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.io.File

/**
 * description:
 * Created by liNan on 2018/5/9 12:28

 */
class UploadImgUtil(val activity: Activity) {

    /**
     * 选择头像
     */
     fun choosePicture() {
        val rxPermissions = RxPermissions(activity)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe({
                    if (it) {
                        Matisse.from(activity)
                                .choose(MimeType.allOf())
                                .countable(true)
                                .maxSelectable(1)
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
                    ToastUtils.showShort(it.message.toString())
                })
    }
     fun uCropImage(sourceUri: Uri) {
        val filePath = "${activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}" //android/data/pictures
        val fileName = "search${System.currentTimeMillis()}.png"
        val destinationUri = Uri.fromFile(File(filePath, fileName)) //裁剪完成后保存图片路径
        //裁剪属性设置
        val options = UCrop.Options()
        options.setToolbarColor(Color.parseColor("#303030"))
        options.setStatusBarColor(Color.parseColor("#303030"))
        options.setShowCropGrid(true)
        options.setShowCropFrame(true)
        options.setCircleDimmedLayer(false) //设置圆形裁剪
        options.setHideBottomControls(true)//隐藏下边控制栏
        options.withAspectRatio(16F, 9F)
//        options.withMaxResultSize(48, 48)
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(activity, Constant.UCROP_ICON_REQUEST_CODE)
    }


}