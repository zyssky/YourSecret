package com.example.administrator.yoursecret.Module.Editor.Manager;

import android.util.Log;

import com.example.administrator.yoursecret.Entity.Image;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/6/8.
 */

public class PhotoManager {
    public static final String TAG = PhotoManager.class.getSimpleName();

    private List<Image> images;

    public PhotoManager(){
        images = new ArrayList<>();
    }

    public void setImages(List<Image> images){
        this.images.addAll(images);
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image){
        image.uuid = EditorDataManager.getInstance().getArticalManager().getArtical().uuid;
        images.add(image);
    }

    public Image getImage(int position){
        return images.get(position);
    }

    public void addLatestImageLocation(Image image){
        images.get(images.size()-1).description = image.description;
        images.get(images.size()-1).latitude = image.latitude;
        images.get(images.size()-1).longtitude = image.longtitude;

//        if(!EditorDataManager.getInstance().getArticalManager().hasLocation()){
//            EditorDataManager.getInstance().getArticalManager().setLocation(image);
//        }

    }

    public Observer<List<Image>> getObserver(){
        Observer<List<Image>> observer = new Observer<List<Image>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Image> images2) {
                images.addAll(images2);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                AdapterManager.getInstance().getWriteImagesAdapter().setmDatas(images);

            }
        };
        return observer;
    }

}
