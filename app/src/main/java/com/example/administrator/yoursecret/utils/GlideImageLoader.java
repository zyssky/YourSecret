package com.example.administrator.yoursecret.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/6/2.
 */

public final class GlideImageLoader {
    public static void loadImageNail(Context context, Object path, ImageView imageView) {
//        Integer integer = (Integer) path;
        Glide.with(context).load(path).thumbnail( 0.1f ).into(imageView);
    }

    public static void loadImage(Context context, Object path, ImageView imageView){
        Glide.with(context).load(path).into(imageView);
    }
}