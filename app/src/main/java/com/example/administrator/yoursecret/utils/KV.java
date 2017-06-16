package com.example.administrator.yoursecret.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class KV implements Parcelable{
    public String key;
    public int value;

    public KV(String key,int value){
        this.key = key;
        this.value = value;
    }

    protected KV(Parcel in) {
        key = in.readString();
        value = in.readInt();
    }

    public static final Creator<KV> CREATOR = new Creator<KV>() {
        @Override
        public KV createFromParcel(Parcel in) {
            return new KV(in);
        }

        @Override
        public KV[] newArray(int size) {
            return new KV[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeInt(value);
    }
}
