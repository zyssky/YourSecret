package com.example.administrator.yoursecret.AppManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;

import java.security.Permission;

/**
 * Created by Administrator on 2017/6/29.
 */

public class LocationManager {
    public static Location location;

    public static android.location.LocationManager locationManager = (android.location.LocationManager) App.getInstance().getAppContext()
            .getSystemService(Context.LOCATION_SERVICE);

    public static void getLocation(LocationListener listener){
        android.location.LocationManager locationManager = (android.location.LocationManager) App.getInstance().getAppContext()
                .getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = android.location.LocationManager.NETWORK_PROVIDER;

        if(!locationManager.isProviderEnabled(locationProvider)){
            Log.d("test location ", "getLocation: ");
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getInstance().getAppContext().startActivity(intent);
            Toast.makeText(App.getInstance().getAppContext(),"请允许定位以获取附近文章",Toast.LENGTH_SHORT).show();
            listener.onProviderDisabled(locationProvider);
            return;
        }

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if(lastKnownLocation!=null){
            listener.onLocationChanged(lastKnownLocation);
        }else{
            locationManager.requestLocationUpdates(locationProvider,200,200,listener);
        }
    }

    public static void stopLocation(LocationListener listener){
        locationManager.removeUpdates(listener);
    }

    public static void getLocationDesc(double latitude, double longitude, GeocodeSearch.OnGeocodeSearchListener listener){
        GeocodeSearch geo = new GeocodeSearch(App.getInstance().getAppContext());
        geo.setOnGeocodeSearchListener(listener);
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latitude,longitude),100,GeocodeSearch.AMAP);
        geo.getFromLocationAsyn(query);
    }

}
