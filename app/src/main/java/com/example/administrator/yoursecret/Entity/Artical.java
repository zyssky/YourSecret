package com.example.administrator.yoursecret.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.yoursecret.utils.AppContants;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
@Entity
public class Artical implements Parcelable{

    public Artical(){}

//    public String articalId;
    @PrimaryKey
    public String uuid;

    public String authorId = "";

    public String title = "";

//    public String contentHtml = "";

    public double latitude;

    public double longtitude;

    public String locationDesc = "";

    public String articalType = "";

    public String imageUri = "";

    public long date;

    public String articalHref = "";

    public String introduction = "";

    public int commentNum;

    public int saveType = AppContants.PRIVATE;

    public int finished = 0;

    public String html = "";

    public int isCache = 0;

    public int isOutside = 0;

//    @Ignore
//    public String fullHtml;

    @Ignore
    public List<Image> images;

    protected Artical(Parcel in) {
        uuid = in.readString();
        authorId = in.readString();
        title = in.readString();
        latitude = in.readDouble();
        longtitude = in.readDouble();
        locationDesc = in.readString();
        articalType = in.readString();
        imageUri = in.readString();
        date = in.readLong();
        articalHref = in.readString();
        introduction = in.readString();
        saveType = in.readInt();
        finished = in.readInt();
        html = in.readString();
        commentNum = in.readInt();
    }

    public static final Creator<Artical> CREATOR = new Creator<Artical>() {
        @Override
        public Artical createFromParcel(Parcel in) {
            return new Artical(in);
        }

        @Override
        public Artical[] newArray(int size) {
            return new Artical[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(authorId);
        dest.writeString(title);
        dest.writeDouble(latitude);
        dest.writeDouble(longtitude);
        dest.writeString(locationDesc);
        dest.writeString(articalType);
        dest.writeString(imageUri);
        dest.writeLong(date);
        dest.writeString(articalHref);
        dest.writeString(introduction);
        dest.writeInt(saveType);
        dest.writeInt(finished);
        dest.writeString(html);
        dest.writeInt(commentNum);
    }
}
