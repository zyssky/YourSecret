package com.example.administrator.yoursecret.Editor.Photo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.yoursecret.Editor.Manager.EditorDataManager;
import com.example.administrator.yoursecret.Entity.ImageLocation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationService extends Service implements AMapLocationListener{

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = this;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public MyBinder binder;

    public class MyBinder extends Binder{
        public void getLocation(){
            startOnceLoaction();
        }
    }


    public LocationService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(true);

        binder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    //开启单次定位
    public void startOnceLoaction() {
        AMapLocationClientOption option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
            //可在其中解析amapLocation获取相应内容。
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                String day = df.format(date);

                double latitude = amapLocation.getLatitude();//获取纬度
                double longtitude = amapLocation.getLongitude();//获取经度

                String address = amapLocation.getAddress();//地址，

                EditorDataManager.getInstance().getPhotoManager().addLatestImageLocation(new ImageLocation(latitude,longtitude,address));
                Log.d("Location: ", "onLocationChanged: "+day+"时间, "+latitude+"维度, "+longtitude+"经度， "+address);
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
//        mLocationClient.stopLocation();
    }
}
