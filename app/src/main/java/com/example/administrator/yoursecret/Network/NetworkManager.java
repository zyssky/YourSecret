package com.example.administrator.yoursecret.Network;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Editor.Manager.EditorDataManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/12.
 */

public class NetworkManager {

    public void uploadArtical(){
        Retrofit retrofit = getRetrofit();

        ArticalService service = retrofit.create(ArticalService.class);
        Call<ResponseBody> call = service.uploadArtical(getFormBody(),getHtml(),getImagesUploadMap());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    Log.d("network test : ", "onResponse: "+result);
                    Toast.makeText(ApplicationDataManager.getInstance().getAppContext(),"上传到："+result,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("network test : ", "onFailure: net work fail !!!!!!!!!");
                Toast.makeText(ApplicationDataManager.getInstance().getAppContext(),"上传失败",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public Retrofit getRetrofit(){
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FoundationManager.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public RequestBody getHtml(){
        String html = EditorDataManager.getInstance().getArticalManager().getArticalHtml();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),html);
        return requestBody;
    }

    public Map<String, RequestBody> getImagesUploadMap(){
        Map<String, RequestBody> map = new HashMap<>();
        List<Uri> imageList = EditorDataManager.getInstance().getPhotoManager().getPhotos();
        int count = 0;
        for (Uri uri :
                imageList) {
            String path = uri.getPath();
            RequestBody requestBody= getFileRequestBody(path);
            map.put("image:"+uri.getPath(),requestBody);

        }
        return map;
    }

    public Observable<Map<String,ArrayList<Artical>>> getArticals(){
        Retrofit retrofit = getRetrofit();
        ArticalService service = retrofit.create(ArticalService.class);
        return service.getArticals();
    }

    public RequestBody getFileRequestBody(String path){
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        return requestBody;
    }

    public RequestBody getFormBody(){
        Artical artical = EditorDataManager.getInstance().getArticalManager().getArtical();
        RequestBody formBody = new FormBody.Builder()
                .add("authorId",artical.authorId)
                .add("introduction",artical.introduction)
                .add("latitude",""+artical.latitude)
                .add("longtitude",""+artical.longtitude)
                .add("locationDesc",artical.locationDesc)
                .add("title",artical.title)
                .add("articalType",artical.articalType)
                .add("saveType",""+artical.saveType)
                .add("imageUri",artical.imageUri)
                .build();

        return formBody;
    }

    public RequestBody getTextRequestBody(String value){
        return RequestBody.create(MediaType.parse("text/plain"),value);
    }

    public void register(){
        UserManager userManager = ApplicationDataManager.getInstance().getUserManager();
        String identifier = userManager.getIdentifier();
        String phoneNum = userManager.getPhoneNum();
        String nickName = userManager.getNickName();
        RequestBody requestBody = getFileRequestBody(userManager.getIconLocalTempPath());

        Retrofit retrofit = getRetrofit();
        UserService service = retrofit.create(UserService.class);
        Call<ResponseBody> call = service.register(getTextRequestBody(phoneNum),getTextRequestBody(nickName),getTextRequestBody(identifier),requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("user netword test ", "onResponse: "+response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("user network test ", "onFailure: "+"fail to register");
                t.printStackTrace();
            }
        });
    }

    public void login(){
        String identifier = ApplicationDataManager.getInstance().getUserManager().getIdentifier();
        RequestBody requestBody = new FormBody.Builder().add("identifier",identifier).build();
        Call<ResponseBody> call = getRetrofit().create(UserService.class).login(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("user netword test ", "onResponse: "+response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("user network test ", "onFailure: "+"fail to login");
                t.printStackTrace();
            }
        });
    }

    public void modify() {
        UserManager userManager = ApplicationDataManager.getInstance().getUserManager();
        String token = userManager.getToken();
        String nickName = userManager.getNickName();
        RequestBody requestBody = getFileRequestBody(userManager.getIconLocalTempPath());

        Retrofit retrofit = getRetrofit();
        UserService service = retrofit.create(UserService.class);
        Call<ResponseBody> call = service.modify(getTextRequestBody(token),getTextRequestBody(nickName),requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("user netword test ", "onResponse: "+response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("user network test ", "onFailure: "+"fail to register");
                t.printStackTrace();
            }
        });
    }
}
