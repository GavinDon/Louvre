package com.stxx.louvre.entity

/**
 * description:判断订单状态
 * Created by liNan on 2018/5/4 13:19

 */
@Suppress("UNUSED_EXPRESSION")
 class JudgeOrderStatus {
    /**
     * 订单状态（0：待付款. 1：待出库 3：待发货，4：发货，5：待收货，6：确认收货，
     * 7：交易完成，8：交易关闭 , 9 :退货审批 10：取消订单 ,11 :换货审批 ,12:换货审批通过,
     * 13:换货中,14:换货完成,15:退货审批,16:退货审批通过,17:退货中）
     */

    companion object {
        fun getStatus(status: Int?): String {
            when (status) {
                0 -> return "待付款"
                1 -> return "待出库"
                3 -> return "待发货"
                4 -> return "发货"
                5 -> return "待收货"
                6 -> return "确认收货"
                7 -> return "交易完成"
                8 -> return "交易关闭"
                9 -> return "退货审批"
                10 -> return "取消订单"
                11 -> return "换货审批"
                12 -> return "换货审批通过"
                13 -> return "换货中"
                14 -> return "换货完成"
                15 -> return "退货审批"
                16 -> return "退货审批通过"
                17 -> return "退货中"
                else -> return "未知状态"
            }
        }
    }
}