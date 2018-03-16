package com.stxx.louvre.entity.event

/**
 * description:事件总线消息
 * Created by liNan on 2018/3/16 10:49

 */
/*
* 显示底部购物车选中数量bage
* */
data class BottomBageEvent(val num: Int)

/*
* 购物车全选状态 价格等
* */
data class ShoppingCartEvent(var state: Boolean?=false, var total: Double?=0.0, var num: Int?=0)

