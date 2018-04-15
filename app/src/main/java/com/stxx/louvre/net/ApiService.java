package com.stxx.louvre.net;


import com.stxx.louvre.entity.DataResponse;
import com.stxx.louvre.entity.LoginBean;
import com.stxx.louvre.entity.ShoppingCarBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page page
     */
   @GET("/article/list/{page}/json")
   Observable<DataResponse<ShoppingCarBean>> getHomeArticles(@Path("page") int page);


    /**
     * 登录
     */
//    @FormUrlEncoded
    @GET("zhcomplaint/appUser/userLogin")
    Observable<LoginBean> getLogin(@Query("username") String phone, @Query("password") String psw);

    @POST("/lg/collect/usertools/json")
    @FormUrlEncoded
    Observable<DataResponse> delBookmark(@Field("id") int id);
}
