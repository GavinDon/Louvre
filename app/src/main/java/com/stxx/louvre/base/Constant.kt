package com.stxx.louvre.base

/**
 * description:
 * Created by liNan on 2018/3/8 10:39

 */
class Constant {

    companion object {
        const val DEBUGER = true

        /**
         * 链接url
         */
        const val REQUEST_BASE_URL = "http://www.hmlfg.net/"
//        const val REQUEST_BASE_URL = "http://192.168.1.146:8082/" 李杰的ip
//        const val REQUEST_BASE_URL = "http://192.168.1.114:8082/" //老周的ip
        const val SHARE_IS_FIRST = "is_first_enter_app"
        const val WEB_BASE_URL = "${REQUEST_BASE_URL}wapapp/dist/view/"
        //        const val WEB_BASE_URL = "http://10.201.8.186:8082/wapapp/dist/view/"
        const val CLASSIFY_URL = "${WEB_BASE_URL}zuopinliebiao.html"  //分类
        const val ARTICLE_DETAIL_URL = "${WEB_BASE_URL}yishujia.html" //艺术家
        const val PROTFOLO_DETAIL_URL = "${WEB_BASE_URL}shopping-pro.html?id=" //作品详情
        const val NEWS_URL = "${WEB_BASE_URL}zixunxiangqing.html?zxid=" //资讯详情
        const val HOME_NEWS_URL = "${WEB_BASE_URL}zixunxiangqingapp.html?zxid=" //资讯详情
        const val BROWSER_HISTORY_URL = "${WEB_BASE_URL}liulanjilu.html" //浏览记录
        const val MY_COLLECTOR_URL = "${WEB_BASE_URL}wodeshoucang.html" //我的收藏
        const val MY_COUPON_URL = "${WEB_BASE_URL}youhuiquan.html" //优惠券
        const val RECEIPT_MANAGER_URL = "${WEB_BASE_URL}fapiaoguanli.html" //发票管理
        const val SEARCH_URL = "${WEB_BASE_URL}zuopinliebiao.html?key=" //搜索
        const val PIC_SEARCH_PIC_URL = "${WEB_BASE_URL}zuopinliebiao.html?id="
        /**
         * requestCode
         */
        const val CHECK_ADDRESS_REQUEST_CODE = 0x64 //确认订单 选择地址跳转到地址列表界面requestCode
        const val PLUS_ADDRESS_INTENT_CODE = 0x65 //添加地址
        const val CHOOSE_ICON_REQUEST_CODE = 0x66 //个人头像选择上传
        const val UCROP_ICON_REQUEST_CODE = 0x67 //个人头像选择上传


        /**
         * intentString
         */
        const val INTENT_CONFIRM_ORDER = "confirmOrder"

        /**
         * sharePerfrence
         */
        const val USER_ID = "userId"
        const val USER_NAME = "userName"
        const val USER_PSW = "password"
        const val USER_ICON = "userIcon"
        const val USER_INFO = "userInfo"
    }
}