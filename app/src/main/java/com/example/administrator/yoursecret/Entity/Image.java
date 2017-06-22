package com.example.administrator.yoursecret.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

/**
 * Created by Administrator on 2017/6/21.
 */
@Entity(indices = {@Index("uuid")})
public class Image {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String uuid;

    public String path;

    public double latitude;

    public double longtitude;

    public String description;

    @Ignore
    public boolean isNew = false;

    public Image(Uri uri){
        this.path = uri.getPath();
        isNew = true;
    }

    public Image(){}
}
