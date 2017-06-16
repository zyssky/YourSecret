package com.example.administrator.yoursecret.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ImageLocation implements Parcelable {
    public double latitude;

    public double longtitude;

    public String description;

    protected ImageLocation(Parcel in) {
        latitude = in.readDouble();
        longtitude = in.readDouble();
        description = in.readString();
    }
    public ImageLocation(){}

    public ImageLocation(double latitude,double longtitude,String description){
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.description = description;
    }

    public static final Creator<ImageLocation> CREATOR = new Creator<ImageLocation>() {
        @Override
        public ImageLocation createFromParcel(Parcel in) {
            return new ImageLocation(in);
        }

        @Override
        public ImageLocation[] newArray(int size) {
            return new ImageLocation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longtitude);
        dest.writeString(description);
    }
}
