package com.example.administrator.yoursecret.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.yoursecret.R;

/**
 * Created by Administrator on 2017/6/2.
 */

public final class GlideImageLoader {
    public static void loadImageNail(Context context, Object path, ImageView imageView) {
//        Integer integer = (Integer) path;
        Glide.with(context).load(path).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);
    }

    public static void loadImageNail(Fragment fragment, Object path, ImageView imageView) {
//        Integer integer = (Integer) path;
        Glide.with(fragment).load(path).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);
    }

    public static void loadImage(Context context, Object path, ImageView imageView){
        Glide.with(context).load(path).placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);
    }
}