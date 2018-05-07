package com.stxx.louvre.net

import com.stxx.louvre.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * description:
 * Created by liNan on 2018/5/4 11:31

 */
interface ApiService {

    /**
     * 首页数据
     *
     * @param page page
     */
    @Headers("Content-type:application/json;charset=utf-8", "Accept:application/json")//需要添加touch
    @GET("/article/list/json/{page}")
    fun getHomeArticles(@Path("page") page: Int): Observable<DataResponse<ShoppingCarBean>>

    /**
     * 发送短信验证码
     */
    @FormUrlEncoded
    @POST("sys/sms/")
    fun getSmsCode(@Field("phone") phone: String): Call<ResponseBody>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("sys/registered/")
    fun getRegister(@Field("phone") phone: String, @Field("password") password: String, @Field("vcode") vcode: String): Observable<CodeAndMsg>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("sys/login/")
    fun getLogin(@Field("username") phone: String, @Field("password") psw: String): Observable<CodeAndMsg>

    /**
     * 忘记密码(发送短信)
     */
    @FormUrlEncoded
    @POST("sys/uppasswordsms")
    fun getUpdatewordSms(@Field("phone") phone: String): Observable<CodeAndMsg>

    /**
     * 验证验证码
     */
    @FormUrlEncoded
    @POST("sys/checkuppassword")
    fun getVertifyPassword(@Field("phone") phone: String, @Field("vcode") vCode: String): Observable<UpdatePswBean>

    /**
     * 忘记/修改密码 完成
     */
    //    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @FormUrlEncoded
    @POST("sys/uppasswordfinal")
    fun getPswUpdateFinish(@Field("phone") phone: String, @Field("token") token: String, @Field("password") password: String): Observable<CodeAndMsg>

    /**
     * 地址列表
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("address/list/")
    fun getAddressList(@Body body: RequestBody): Observable<AddressListBean>

    /**
     * 添加新地址
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("address/save/")
    fun getPlusAddress(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 删除某个地址
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("address/remove/")
    fun getRemoveAddress(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 修改地址
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("address/update/")
    fun getUpdateAddress(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 设置默认地址
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("address/updateCmsAddressDefalurt/")
    fun setDefaultAddress(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 获取个人信息
     */
    @POST("presonalinfo/getMemberByUserId")
    fun getPersonalInfo(): Observable<UserInfoBean>

    /**
     * 分类数据
     */
    @GET("index/class/list")
    fun getClassifyData(): Observable<ClassifyBean>

    /**
     * 首页推荐
     */
    @GET("index/getAppZhuanti")
    fun getHomeRecommend(@Query("pageNumber") pageNumber: String, @Query("pageSize") pageSize: String): Observable<HomeLfgBean>

    /**
     * 获取购物车列表
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("shoppingcart/list")
    fun getShoppingCartList(@Body body: RequestBody): Observable<ShoppingCartListRespBean>

    /**
     * 购物车加
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("shoppingcart/countup")
    fun ShoppingIncrease(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 购物车减
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("shoppingcart/countdown")
    fun ShoppingDecrease(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 购物车 删除
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("shoppingcart/remove")
    fun ShoppingDeleter(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 购物车 结算
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("shoppingcart/killbill")
    fun ShoppingClear(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 加入收藏夹
     */
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("rs/address/cmsMemberCollection")
    fun addCollection(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 作品
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("index/getPaingtingWorksByConditions")
    fun getProtfolioList(@Body body: RequestBody): Observable<ProtfoloListBean>

    /**
     * 艺术家
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("index/getClassificationByConditions")
    fun getArticleList(@Body body: RequestBody): Observable<ArticleResponseBean>


    /**
     * 查询下单的列表
     */
    @POST("rs/orderOrder/carOrderList")
    fun getOrderList(): Observable<ConfirmOrderListRespBean>

    /**
     * 根据Id查询下单的信息
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("shoppingcart/selectbyarrayid")
    fun getOrderInfoById(@Body body: RequestBody): Observable<MutableList<OrderListByIdRespBean>>

    /**
     * 获取优惠券信息
     */
    @POST("ordRentalOrder/getCmsMemberCoupon")
    fun getCouponInfo(): Observable<CouponInfoBean>

    /**
     * 发票
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("invoice/list")
    fun getReceiptList(@Body body: RequestBody): Observable<ReceiptInfoBean>

    /**
     * 配送方式
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("ordRentalOrder/getMmPaintingWorksInfo")
    fun getSendStyle(@Body body: RequestBody): Observable<SendStyleBean>

    /**
     * 保存订单
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("ali/saveOrderInfoApp")
    fun saveOrderInfoApp(@Body body: RequestBody): Observable<SaveOrderResBean>

    /**
     * 删除订单
     */
    @Headers("Content-Type: application/json", "Accept:application/json")
    @POST("shoppingcart/remove")
    fun removeOrder(@Body body: RequestBody): Observable<CodeAndMsg>

    /**
     * 订单状态
     */
    @GET("mineOrder/listByUserId")
    fun getOrderStatus(@Query("userId") userId: String, @Query("type") type: Int = 0, @Query("orderStatus") orderStatus: String? = null): Observable<AllOrderBean>


    /**
     * 上传头像
     */
    @Multipart
    @POST("uploadFile/member")
    fun uploadMemberIcon(@Part("srcAddress") srcAddress: String, @Part media: MultipartBody.Part): Call<ResponseBody>
}