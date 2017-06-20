package com.example.administrator.yoursecret.Network;

import com.example.administrator.yoursecret.Entity.Artical;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Administrator on 2017/6/12.
 */

public interface ArticalService {

    @Multipart
    @POST("Rest/artical")
    Call<ResponseBody> uploadArtical(@Part("artical") RequestBody data,@Part("html") RequestBody html,@PartMap Map<String,RequestBody> map);

    @Multipart
    @POST("Rest/artical")
    Call<ResponseBody> test(@Part("data") String data);

    @GET("Rest/artical")
    Observable<Map<String,ArrayList<Artical>>> getArticals();



}
