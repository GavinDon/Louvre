package com.stxx.louvre.entity.event

import com.stxx.louvre.entity.UserInfoBean

/**
 * description:事件总线消息实体类
 * Created by liNan on 2018/3/16 10:49

 */
/*
* 显示底部购物车选中数量badge
* */
data class BottomBadgeEvent(val num: Int)

/*
* 购物车全选状态 价格等
* */
data class ShoppingCartEvent(var state: Boolean? = false, var totalPrice: Double? = 0.0, var checkNum: Int? = 0, var totalNum: Int = 0)

/**
 * 成功获取个人信息
 */
data class LoginResultEvent(val userInfoBean: UserInfoBean)

