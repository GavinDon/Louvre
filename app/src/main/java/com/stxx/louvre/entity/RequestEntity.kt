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
     * 删除某一地址
     */
    data class DeleterAddress(var id: String)

}