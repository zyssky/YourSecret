package com.example.administrator.yoursecret.Editor.Network;

import com.example.administrator.yoursecret.MetaData.Artical;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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



}
