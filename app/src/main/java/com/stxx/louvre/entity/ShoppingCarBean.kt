package com.stxx.louvre.entity

/**
 * description:
 * Created by liNan on 2018/3/5 16:35

 */
data class ShoppingCarBean(var checked: Boolean,
                           val imgUrl: String,
                           val goodsName: String,
                           val description: String,
                           val price: Float, //单价
                           var goodsAmount: Int) //选中数量