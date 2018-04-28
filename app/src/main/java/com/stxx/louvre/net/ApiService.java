package com.stxx.louvre.net;


import com.stxx.louvre.entity.AddressListBean;
import com.stxx.louvre.entity.ArticleResponseBean;
import com.stxx.louvre.entity.ProtfoloListBean;
import com.stxx.louvre.entity.ClassifyBean;
import com.stxx.louvre.entity.CodeAndMsg;
import com.stxx.louvre.entity.DataResponse;
import com.stxx.louvre.entity.HomeLfgBean;
import com.stxx.louvre.entity.ShoppingCarBean;
import com.stxx.louvre.entity.ShoppingCartListRespBean;
import com.stxx.louvre.entity.UpdatePswBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 首页数据
     *
     * @param page page
     */
    @Headers({"Content-type:application/json;charset=utf-8", "Accept:application/json"})//需要添加touch
    @GET("/article/list/json/{page}")
    Observable<DataResponse<ShoppingCarBean>> getHomeArticles(@Path("page") int page);

    /**
     * 发送短信验证码
     */
    @FormUrlEncoded
    @POST("sys/sms/")
    Call<ResponseBody> getSmsCode(@Field("phone") String phone);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("sys/registered/")
    Observable<CodeAndMsg> getRegister(@Field("phone") String phone, @Field("password") String password, @Field("vcode") String vcode);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("sys/login/")
    Observable<CodeAndMsg> getLogin(@Field("username") String phone, @Field("password") String psw);

    /**
     * 忘记密码(发送短信)
     */
    @FormUrlEncoded
    @POST("sys/uppasswordsms")
    Observable<CodeAndMsg> getUpdatewordSms(@Field("phone") String phone);

    /**
     * 验证验证码
     */
    @FormUrlEncoded
    @POST("sys/checkuppassword")
    Observable<UpdatePswBean> getVertifyPassword(@Field("phone") String phone, @Field("vcode") String vCode);

    /**
     * 忘记/修改密码 完成
     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @FormUrlEncoded
    @POST("sys/uppasswordfinal")
    Observable<CodeAndMsg> getPswUpdateFinish(@Field("phone") String phone, @Field("token") String token, @Field("password") String password);

    /**
     * 地址列表
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("address/list/")
    Observable<AddressListBean> getAddressList(@Body RequestBody body);

    /**
     * 添加新地址
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("address/save/")
    Observable<CodeAndMsg> getPlusAddress(@Body RequestBody body);

    /**
     * 删除某个地址
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("address/remove/")
    Observable<CodeAndMsg> getRemoveAddress(@Body RequestBody body);

    /**
     * 修改地址
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("address/update/")
    Observable<CodeAndMsg> getUpdateAddress(@Body RequestBody body);

    /**
     * 设置默认地址
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("address/updateCmsAddressDefalurt/")
    Observable<CodeAndMsg> setDefaultAddress(@Body RequestBody body);

    /**
     * 获取个人信息
     */
    @POST("presonalinfo/getMemberByUserId")
    Response<ResponseBody> getPersonalInfo();

    /**
     * 分类数据
     */
    @GET("index/class/list")
    Observable<ClassifyBean> getClassifyData();

    /**
     * 首页推荐
     */
    @GET("index/getAppZhuanti")
    Observable<HomeLfgBean> getHomeRecommend(@Query("pageNumber") String pageNumber, @Query("pageSize") String pageSize);

    /**
     * 获取购物车列表
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("shoppingcart/list")
    Observable<ShoppingCartListRespBean> getShoppingCartList(@Body RequestBody body);

    /**
     * 购物车加
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("shoppingcart/countup")
    Observable<CodeAndMsg> ShoppingIncrease(@Body RequestBody body);

    /**
     * 购物车减
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("shoppingcart/countdown")
    Observable<CodeAndMsg> ShoppingDecrease(@Body RequestBody body);

    /**
     * 购物车 删除
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("shoppingcart/remove")
    Observable<CodeAndMsg> ShoppingDeleter(@Body RequestBody body);

    /**
     * 购物车 结算
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("shoppingcart/killbill")
    Observable<CodeAndMsg> ShoppingClear(@Body RequestBody body);

    /**
     * 加入收藏夹
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("rs/address/cmsMemberCollection")
    Observable<CodeAndMsg> addCollection(@Body RequestBody body);

    /**
     * 作品
     */
    @Headers({"Content-Type: application/json", "Accept:application/json"})
    @POST("index/getPaingtingWorksByConditions")
    Observable<ProtfoloListBean> getProtfolioList(@Body RequestBody body);

    /**
     * 艺术家
     */
    @Headers({"Content-Type: application/json", "Accept:application/json"})
    @POST("index/getClassificationByConditions")
    Observable<ArticleResponseBean> getArticleList(@Body RequestBody body);

}
