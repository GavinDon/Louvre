package com.stxx.louvre.base

/**
 * description:
 * Created by liNan on 2018/3/8 10:39

 */
class Constant {

    companion object {
        const val DEBUGER = true
        const val PLUS_ADDRESS_REQUEST_CODE = 101
//        const val REQUEST_BASE_URL = "http://124.115.16.18/"
        const val REQUEST_BASE_URL = "http://www.hmlfg.net/"
        const val SHARE_IS_FIRST = "is_first_enter_app"
        const val WEB_BASE_URL = "${REQUEST_BASE_URL}wapapp/dist/view/"
        const val CLASSIFY_URL = "${WEB_BASE_URL}zuopinliebiao.html"  //分类
        const val ARTICLE_DETAIL_URL = "${WEB_BASE_URL}yishujia.html" //艺术家
        const val PROTFOLO_DETAIL_URL = "${WEB_BASE_URL}shopping-pro.html?id=" //作品详情


    }
}