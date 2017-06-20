package com.example.administrator.yoursecret.Network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2017/6/19.
 */

public interface UserService {

    @Multipart
    @POST("Rest/user/register")
    Call<ResponseBody> register(@Part("phoneNum") RequestBody phoneNum, @Part("nickName")RequestBody nickname,
                                       @Part("identifier") RequestBody identifier, @Part("image")RequestBody data);


    @POST("Rest/user/login")
    Call<ResponseBody> login(@Body RequestBody body);

    @Multipart
    @POST("Rest/user/modify")
    Call<ResponseBody> modify(@Part("token") RequestBody token, @Part("nickName")RequestBody nickname,@Part("image")RequestBody data);
}
