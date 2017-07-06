package com.example.administrator.yoursecret.utils;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BitmapExtra {
    public Bitmap bitmap;
    public String path;

    public BitmapExtra(){}

    public BitmapExtra(Bitmap bitmap,String path){
        this.bitmap = bitmap;
        this.path = path;
    }
}
