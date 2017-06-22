package com.example.administrator.yoursecret.Network;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Editor.Manager.EditorDataManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.ArticalResponse;
import com.example.administrator.yoursecret.Entity.Image;
import com.example.administrator.yoursecret.Entity.UserResponse;
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
import io.reactivex.Observer;
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

    private UserService userService;
    private ArticalService articalService;

    private UserService getUserService(){
        if(userService == null){
            userService = getRetrofit().create(UserService.class);
        }
        return userService;
    }

    private ArticalService getArticalService(){
        if(null == articalService){
            articalService = getRetrofit().create(ArticalService.class);
        }
        return articalService;
    }

    public Observable<ArticalResponse> uploadArtical(){
        Observable<ArticalResponse> observable = getArticalService().uploadArtical(getFormBody(),getHtml(),getImagesUploadMap());
        return observable;
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
        List<Image> imageList = EditorDataManager.getInstance().getPhotoManager().getImages();
        for (Image image :
                imageList) {
            String path = image.path;
            RequestBody requestBody= getFileRequestBody(path);
            map.put("image:"+image.path,requestBody);

        }
        return map;
    }

    public Observable<Map<String,ArrayList<Artical>>> getArticals(){
        return getArticalService().getArticals();
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
                .add("uuid",artical.uuid)
                .build();

        return formBody;
    }

    public RequestBody getTextRequestBody(String value){
        return RequestBody.create(MediaType.parse("text/plain"),value);
    }

    public Observable<UserResponse> register(){
        UserManager userManager = ApplicationDataManager.getInstance().getUserManager();
        String identifier = userManager.getIdentifier();
        String phoneNum = userManager.getPhoneNum();
        String nickName = userManager.getNickName();
        RequestBody requestBody = getFileRequestBody(userManager.getIconLocalTempPath());

        UserService service = getUserService();
        return service.register(getTextRequestBody(phoneNum),getTextRequestBody(nickName),getTextRequestBody(identifier),requestBody);
    }

    public Observable<UserResponse> login(){
        String identifier = ApplicationDataManager.getInstance().getUserManager().getIdentifier();
        RequestBody requestBody = new FormBody.Builder().add("identifier",identifier).build();
        return getUserService().login(requestBody);
    }

    public Observable<UserResponse> modify() {
        UserManager userManager = ApplicationDataManager.getInstance().getUserManager();
        String token = userManager.getToken();
        String nickName = userManager.getNickName();
        RequestBody requestBody = getFileRequestBody(userManager.getIconLocalTempPath());

        UserService service = getUserService();
        return service.modify(getTextRequestBody(token),getTextRequestBody(nickName),requestBody);
    }



    public Observable<List<Artical>> getUserArticals(String token) {
        RequestBody requestBody = new FormBody.Builder().add("token",token).build();
        return getArticalService().getUserArticals(requestBody);
    }
}
