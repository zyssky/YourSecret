package com.example.administrator.yoursecret.Module.Discover;

import com.example.administrator.yoursecret.Entity.Artical;

import java.util.ArrayList;

/**
 * Created by inj3ct0r on 2017/6/26.
 */

//Calculate what to show by This Class.

public class MapCalculator {

    ArrayList<Artical> Articles;
    Long Default_radius = 1000L; //1KM.
    private double EARTH_RADIUS = 6378.137;

    public MapCalculator(){

    }

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000000000) / 1000000;
        return s;
    }

}
