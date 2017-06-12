package com.example.administrator.yoursecret.MetaData;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6.
 */

public class Artical{
    public Artical(){

    }

    public String artical_id;

    public String author_id;

    public String title;

    public String content_html;

    public ImageLocation location;

    public String articalType;

    public Uri imageUri;

    public Map<Uri,ImageLocation> images;

    public String introduction;

    public int saveType = -1;

    public String dateString;

    public String articalUrl;

    public static class ImageLocation implements Parcelable{
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
}
