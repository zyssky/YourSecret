package com.example.administrator.yoursecret.Editor.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.example.administrator.yoursecret.Entity.ImageLocation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */

public class PhotoManager {
    private Map<Uri,ImageLocation> images;


    public List<Uri> getPhotos() {
        if(photos==null){
            photos = new ArrayList<>();
        }
        return photos;
    }

    private List<Uri> photos;

    public void setPhotos(List<Uri> photos) {
        this.photos = photos;
    }

    public PhotoManager(){}

    public void addPhoto(Uri uri){
        photos.add(uri);
    }

    public void addLatestImageLocation(ImageLocation location){
        if(images==null){
            images = new HashMap<>();
        }
        images.put(photos.get(photos.size()-1),location);
    }

    public ImageLocation getImageLocation(Uri uri){
        return images.get(uri);
    }

    public Map<Uri,ImageLocation> getImagesWithLocation(){
        return images;
    }

    public String getBase64String(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);

        return encodeString;
    }

}
