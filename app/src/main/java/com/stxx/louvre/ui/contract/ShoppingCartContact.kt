package com.stxx.louvre.ui.contract

import com.stxx.louvre.base.BaseMvp
import com.stxx.louvre.entity.ShoppingCartListRespBean

/**
 * description:
 * Created by liNan on 2018/4/27 16:57

 */
interface ShoppingCartContact {

    interface View : BaseMvp.View {
        fun loadListSuccess(shoppingCartList: ShoppingCartListRespBean)
        fun loadListFail()


    }

    interface Presenter : BaseMvp.Presenter<View> {
        fun reqShoppingCartList() //获取购物车列表

        fun reqBalanceAccounts(rows: MutableList<ShoppingCartListRespBean.RowsBean>) //结算
    }
}