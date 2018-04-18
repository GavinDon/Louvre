package com.stxx.louvre.net;


import com.stxx.louvre.entity.AddressListBean;
import com.stxx.louvre.entity.CodeAndMsg;
import com.stxx.louvre.entity.DataResponse;
import com.stxx.louvre.entity.ShoppingCarBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
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
    Observable<CodeAndMsg> getLogin( @Field("username") String phone, @Field("password") String psw);

    /**
     * 地址列表
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("address/list/")
    Observable<AddressListBean> getAddressList(@Body RequestBody body);

    /**
     * 添加新地址
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("address/save/")
    Observable<CodeAndMsg> getPlusAddress(@Body RequestBody body);

    /**
     * 删除某个地址
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("address/remove/")
    Observable<CodeAndMsg> getRemoveAddress(@Body RequestBody body);




}
