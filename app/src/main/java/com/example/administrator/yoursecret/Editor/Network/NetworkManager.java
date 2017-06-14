package com.example.administrator.yoursecret.Editor.Network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.ApplicationDataManager;
import com.example.administrator.yoursecret.Editor.Manager.DataManager;
import com.example.administrator.yoursecret.MetaData.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.PropUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/12.
 */

public class NetworkManager {
    public void test(Context context){
        String baseUrl = PropUtil.loadResProperties(context, R.raw.api).getProperty("domain");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ArticalService service = retrofit.create(ArticalService.class);
//        Call<ResponseBody> call = service.uploadArtical(getFormBody(),getImagesUploadMap());
        Call<ResponseBody> call = service.test("hello from client");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("network test : ", "onResponse: "+response.body().string());
                    Toast.makeText(ApplicationDataManager.getInstance().getAppContext(),"上传到："+response.body().string(),Toast.LENGTH_SHORT).show();
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

    public void uploadArtical(Context context){
        String baseUrl = PropUtil.loadResProperties(context, R.raw.api).getProperty("domain");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


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

    public RequestBody getHtml(){
        Artical artical = DataManager.getInstance().getArticalManager().getArtical();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),artical.html);
        return requestBody;
    }

    public Map<String, RequestBody> getImagesUploadMap(){
        Map<String, RequestBody> map = new HashMap<>();
        List<Uri> imageList = DataManager.getInstance().getPhotoManager().getPhotos();
        int count = 0;
        for (Uri uri :
                imageList) {
            String path = uri.getPath();
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            map.put("image:"+uri.getPath(),requestBody);

        }
        return map;
    }

    public RequestBody getFormBody(){
        Artical artical = DataManager.getInstance().getArticalManager().getArtical();
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

}
