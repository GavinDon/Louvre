package com.stxx.louvre.ui.contract

import com.stxx.louvre.base.BaseMvp
import com.stxx.louvre.entity.AddressListBean

/**
 * description: 地址列表
 * Created by liNan on 2018/4/18 16:43

 */
interface DeliveryAddressContact {

    interface View : BaseMvp.View {
        fun showAddress(rows:List<AddressListBean.RowsBean>) //成功显示列表
        fun setDefault() //设置默认地址
        fun deleterSuccess(pos:Int) //删除地址成功
    }

    interface Presenter : BaseMvp.Presenter<View> {
        fun getAddressList()

        fun deleterAddress(id:String ,pos: Int)

        fun setDefaultAddress()

    }

}