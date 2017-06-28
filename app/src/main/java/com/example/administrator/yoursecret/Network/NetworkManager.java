package com.example.administrator.yoursecret.Network;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Editor.Manager.EditorDataManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.ArticalResponse;
import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.Entity.Image;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.Service.CallbackListener;
import com.example.administrator.yoursecret.Service.LocationService;
import com.example.administrator.yoursecret.utils.FunctionUtils;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    private CommentService commentService;

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

    private CommentService getCommentService(){
        if(null == commentService )
            commentService = getRetrofit().create(CommentService.class);
        return commentService;
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
                .connectTimeout(10, TimeUnit.SECONDS)
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


    public RequestBody getFileRequestBody(String path){
        if(path == null)
            return null;
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
        if(null == value)
            return null;
        return RequestBody.create(MediaType.parse("text/plain"),value);
    }

    public Observable<UserResponse> register(String phoneNum , String password,String nickName  ){
        String identifier = FunctionUtils.getSHA256String(phoneNum+password);
        UserService service = getUserService();
        return service.register(getTextRequestBody(phoneNum),getTextRequestBody(nickName),getTextRequestBody(identifier));
    }

    public Observable<UserResponse> login(String phoneNum,String password){
        String identifier = FunctionUtils.getSHA256String(phoneNum+password);
        RequestBody requestBody = new FormBody.Builder().add("identifier",identifier).build();
        return getUserService().login(requestBody);
    }

    public Observable<UserResponse> modify(String nickName,String iconLocalTempPath) {
        UserManager userManager = ApplicationDataManager.getInstance().getUserManager();
        String token = userManager.getToken();

        RequestBody requestBody = getFileRequestBody(iconLocalTempPath);

        UserService service = getUserService();
        return service.modify(getTextRequestBody(token),getTextRequestBody(nickName),requestBody);
    }

    public Observable<UserResponse> modifyPassword(String oldPassword,String newPassword){
        String phoneNum = ApplicationDataManager.getInstance().getUserManager().getPhoneNum();
        String theOld = FunctionUtils.getSHA256String(phoneNum+oldPassword);
        String theNew = FunctionUtils.getSHA256String(phoneNum+newPassword);

        RequestBody requestBody = new FormBody.Builder().add("phoneNum",phoneNum)
                .add("theold",theOld).add("thenew",theNew).build();
        return getUserService().modifyPassword(requestBody);
    }


    public Observable<List<Comment>> getComments(int commentPage,String articalHref){
        RequestBody requestBody = new FormBody.Builder()
                .add("commentPage",""+commentPage)
                .add("articalHref",articalHref).build();
        return getCommentService().getComments(requestBody);
    }

    public Observable<ResponseBody> putComment(Comment comment){
        RequestBody requestBody = new FormBody.Builder()
                .add("articalHref",comment.articalHref)
                .add("content",comment.content)
                .add("nickName",comment.nickName)
                .add("authorId",comment.authorId)
                .add("iconPath",comment.iconPath).build();
        return getCommentService().putComment(requestBody);
    }

    public Observable<List<Comment>> getUserComments(String lastDate) {
        String token = ApplicationDataManager.getInstance().getUserManager().getToken();
//        String token = "dc6620a5-1d3e-496e-900f-d25f9b30e9e3";
        RequestBody requestBody = new FormBody.Builder()
                .add("lastDate",lastDate)
                .add("token",token).build();
        return getCommentService().getUserComments(requestBody);
    }


    public Observable<List<Artical>> getUserArticals(String token) {
        RequestBody requestBody = new FormBody.Builder().add("token",token).build();
        return getArticalService().getUserArticals(requestBody);
    }

    public Observable<ResponseBody> deleteArtical(String token,String articalHref) {
        RequestBody requestBody = new FormBody.Builder().add("token",token).add("articalHref",articalHref).build();
        return getArticalService().deleteArtical(requestBody);

    }



    public Observable<ArrayList<Artical>> getArticalsOnType(final String articalType,final int pageNo){
        Location location = getLocation();

        RequestBody requestBody = new FormBody.Builder()
                .add("latitude",""+location.getLatitude())
                .add("longitude",""+location.getLongitude())
                .add("pageNO",""+pageNo)
                .build();
        return getArticalService().getArticalsOnType(articalType,requestBody);
    }



    public void getArticalsOnMap(final Observer<ArrayList<Artical>> observer,final int pageNo){
        Location location = getLocation();

        RequestBody requestBody = new FormBody.Builder()
                .add("latitude",""+location.getLatitude())
                .add("longitude",""+location.getLongitude())
                .add("pageNO",""+pageNo)
                .build();
        getArticalService().getArticalsOnMap(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }





    public void getArticals(final Observer<Map<String ,ArrayList<Artical>>> observer){

        Location location = getLocation();
        RequestBody requestBody = new FormBody.Builder()
                .add("latitude",""+location.getLatitude())
                .add("longitude",""+location.getLongitude())
                .build();
        getArticalService().getArticals(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public Location getLocation(){
        LocationManager locationManager = (LocationManager) ApplicationDataManager.getInstance().getAppContext()
                .getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        Log.d("test 经度纬度", "getLocation: "+lastKnownLocation.getLatitude()+"    "+lastKnownLocation.getLongitude());

        return lastKnownLocation;
    }

    public void getLocationDesc(double latitude,double longitude){
        GeocodeSearch geo = new GeocodeSearch(ApplicationDataManager.getInstance().getAppContext());
        geo.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if(i == 1000)
                Log.d("地理位置描述：", "onRegeocodeSearched: "+regeocodeResult.getRegeocodeAddress().getFormatAddress());
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            }
        });
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latitude,longitude),100,GeocodeSearch.AMAP);
        geo.getFromLocationAsyn(query);
    }
}
