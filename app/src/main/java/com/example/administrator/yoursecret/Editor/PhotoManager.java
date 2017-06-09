package com.example.administrator.yoursecret.Editor;

import android.net.Uri;

import com.example.administrator.yoursecret.MetaData.Artical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */

public class PhotoManager {
    private Map<Uri,Artical.ImageLocation> images;

    public List<Uri> getPhotos() {
        if(photos==null){
            photos = new ArrayList<>();
        }
        return photos;
    }

    private List<Uri> photos;

    public PhotoManager(){}

    public void addPhoto(Uri uri){
        photos.add(uri);
    }

    public void addLatestImageLocation(Artical.ImageLocation location){
        if(images==null){
            images = new HashMap<>();
        }
        images.put(photos.get(photos.size()-1),location);
    }

    public Artical.ImageLocation getImageLocation(Uri uri){
        return images.get(uri);
    }

    public Map<Uri,Artical.ImageLocation> getImagesWithLocation(){
        return images;
    }

}
