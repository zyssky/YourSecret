package com.example.administrator.yoursecret.Entity;

import android.net.Uri;

import com.example.administrator.yoursecret.utils.AppContants;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class Artical{
    public Artical(){

    }

//    public String articalId;

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

    public String html = "<h1>请输入标题......</h1><hr>请输入内容......";

    public List<Uri> photos;

}
