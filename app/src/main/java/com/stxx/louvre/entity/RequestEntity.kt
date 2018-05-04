package com.stxx.louvre.entity

/**
 * description: http json格式请求体
 * Created by liNan on 2018/4/18 11:19

 */
class RequestEntity {
    /**
     * 地址列表请求json
     */
    data class AddressListBean(var pageNumber: Int = 1, var pageSize: Int = 20)

    /**
     * 添加新地址
     */
    data class NewPlusAddress(var trueName: String, //真实姓名
                              var phone: String, // 电话号码
                              var spareTel: String? = null,// 备用电话
                              var address: String,// 详细地址
                              var province: String,// 省
                              var city: String,// 市
                              var area: String)// 区域名


    /**
     * 更新密码
     */
    data class UpdatePassWordBean(var phone: String, var token: String, var password: String)

    /**
     * 作品
     */
    data class ProtfolioBean(val type: String = "2",
                             val cName: String = "不限",
                             val kongjian: String = "不限",
                             val chicun: String = "不限",
                             val xingzhi: String = "不限",
                             val lowprice: String = "0",
                             val hprice: String = "999999999",
                             val parId: String = "",
                             val key: String = "",
                             val pageNumber: String = "1",
                             val pageSize: String = "1",
                             val ids: String? = null
    )

    /**
     * 艺术家json
     */
    data class ArticleReqBean(var pageNumber: Int = 1, var pageSize: Int = 10)

    /**
     * 购物车列表json
     */
    data class ShoppingCartReqBean(var pageNumber: Int = 1, var pageSize: Int = 100)


    /**
     * 结算购物车
     */
    data class BalanceAccountReqBean(var number: Int = 0,
                                     var totalprice: String,
                                     var lessprice: Int = 0,
                                     var postage: Int = 0,
                                     var idlist: List<String>)

    /**
     *保存订单 在支付前一步
     */
    data class SaveOrder(val orderName: String?,
                         val depositPrice: String?,
                         val shippingMethod: String?,
                         val shortName: String?,
                         val commodityOldPrice: String?,
                         val depositAmount: String?,
                         val couponPrice: String?,
                         val productId: String?,
                         val payMethod: String?,
                         val addressId: String?,
                         val type: String?,
                         val picture: String?,
                         val orderPrice: String?,
                         val commodityName: String?,
                         val invoiceId: String?,
                         val carId: String
    )


}