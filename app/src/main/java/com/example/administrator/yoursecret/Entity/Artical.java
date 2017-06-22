package com.example.administrator.yoursecret.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import com.example.administrator.yoursecret.utils.AppContants;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
@Entity
public class Artical{

//    public String articalId;
    @PrimaryKey
    public String uuid;

    public String authorId = "";

    public String title = "";

    public String contentHtml = "";

    public double latitude;

    public double longtitude;

    public String locationDesc = "";

    public String articalType = "";

    public String imageUri = "";

    public long date;

    public String articalHref = "";

    public String introduction = "";

    public int saveType = AppContants.PRIVATE;

    public int finished = 0;

    public String html = "<h1>请输入标题......</h1><hr>请输入内容......";

    @Ignore
    public List<Image> images;

}
