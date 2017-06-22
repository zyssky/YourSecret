package com.example.administrator.yoursecret.AppManager;

import android.util.Log;

import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Image;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/6/21.
 */

public class AppDatabaseManager {
    public static void addArtical(final Artical artical){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDataManager.getInstance().getAppDatabase().articalDao().insert(artical);
            }
        });
        thread.start();
    }

    public static void updateArtical(final Artical artical) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDataManager.getInstance().getAppDatabase().articalDao().update(artical);
            }
        });
        thread.start();
    }

    public static void updateArtical(final String imageUri, final String articalHref,final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDataManager.getInstance().getAppDatabase().articalDao().update(imageUri,articalHref,uuid);
            }
        });
        thread.start();
    }

    public static void updateArtical(final int finished,final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDataManager.getInstance().getAppDatabase().articalDao().update(finished,uuid);
            }
        });
        thread.start();
    }

    public static void deleteArtical(final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDataManager.getInstance().getAppDatabase().articalDao().delete(uuid);
            }
        });
        thread.start();
    }

    public static Observable<List<Artical>> getFinishedArticals(){
        Observable<List<Artical>> observable = new Observable<List<Artical>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Artical>> observer) {
                try {
                    List<Artical> list = ApplicationDataManager.getInstance().getAppDatabase().articalDao().getAllFinishedArtical();
                    observer.onNext(list);
                }catch (Exception e){
                    observer.onError(e);
                }
                observer.onComplete();
            }
        };
        return observable;
    }

    public static Observable<List<Artical>> getTempArticals(){
        Observable<List<Artical>> observable = new Observable<List<Artical>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Artical>> observer) {
                try {
                    List<Artical> list = ApplicationDataManager.getInstance().getAppDatabase().articalDao().getAllTempArticals();
                    observer.onNext(list);
                }catch (Exception e){
                    observer.onError(e);
                }
                observer.onComplete();
            }
        };
        return observable;
    }

    public static Observable<List<Image>> getImages(final String uuid){
        Observable<List<Image>> observable = new Observable<List<Image>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Image>> observer) {
                try {
                    List<Image> list = ApplicationDataManager.getInstance().getAppDatabase().imageDao().getImages(uuid);
                    observer.onNext(list);
                }catch (Exception e){
                    observer.onError(e);
                }
                observer.onComplete();
            }
        };
        return observable;
    }

    public static void deleteImages(final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDataManager.getInstance().getAppDatabase().imageDao().deleteImages(uuid);
            }
        });
        thread.start();
    }

    public static void saveImages(final List<Image> images){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Image image :
                        images) {
                    if(image.isNew)
                        ApplicationDataManager.getInstance().getAppDatabase().imageDao().insert(image);
                }
            }
        });
        thread.start();
    }


    public static void addFinishedArticals(final List<Artical> articals) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Artical artical : articals) {
                    artical.finished = 1;
                    ApplicationDataManager.getInstance().getAppDatabase().articalDao().insert(artical);
                }
            }
        });
        thread.start();
    }
}
