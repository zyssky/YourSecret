package com.example.administrator.yoursecret.Database;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Database.DAO.CommentDao;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Comment;
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
                App.getInstance().getAppDatabase().articalDao().insert(artical);
            }
        });
        thread.start();
    }

    public static void updateArtical(final Artical artical) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().articalDao().update(artical);
            }
        });
        thread.start();
    }

    public static void updateArtical(final String imageUri, final String articalHref,final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().articalDao().update(imageUri,articalHref,uuid);
            }
        });
        thread.start();
    }

    public static void updateArtical(final int finished,final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().articalDao().update(finished,uuid);
            }
        });
        thread.start();
    }

    public static void deleteArtical(final String uuid){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().articalDao().delete(uuid);
                App.getInstance().getAppDatabase().imageDao().deleteImages(uuid);
            }
        });
        thread.start();
    }

    public static void deleteArtical(final Artical artical){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().articalDao().deleteByHref(artical.articalHref);
            }
        });
        thread.start();

    }

//    public static void deleteCache(){
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                App.getInstance().getAppDatabase().articalDao().deleteCache();
//            }
//        });
//        thread.start();
//    }

    public static Observable<List<Artical>> getCacheArticals(){
        Observable<List<Artical>> observable = new Observable<List<Artical>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Artical>> observer) {
                try {
                    List<Artical> list = App.getInstance().getAppDatabase().articalDao().getCacheArticals();
                    observer.onNext(list);
                }catch (Exception e){
                    observer.onError(e);
                }
                observer.onComplete();
            }
        };
        return observable;
    }

    public static void addCacheArticals(final List<Artical> articals){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().articalDao().deleteCache();
                for (Artical artical :
                        articals) {
                    artical.isCache = 1;
                    App.getInstance().getAppDatabase().articalDao().insert(artical);
                }
            }
        });
        thread.start();
    }

    public static Observable<List<Artical>> getFinishedArticals(){
        Observable<List<Artical>> observable = new Observable<List<Artical>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Artical>> observer) {
                try {
                    String authorID = App.getInstance().getUserManager().getPhoneNum();
                    List<Artical> list = App.getInstance().getAppDatabase().articalDao().getAllFinishedArtical(authorID);
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
                    String authorID = App.getInstance().getUserManager().getPhoneNum();
                    List<Artical> list = App.getInstance().getAppDatabase().articalDao().getAllTempArticals(authorID);
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
                    List<Image> list = App.getInstance().getAppDatabase().imageDao().getImages(uuid);
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
                App.getInstance().getAppDatabase().imageDao().deleteImages(uuid);
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
                        App.getInstance().getAppDatabase().imageDao().insert(image);
                }
            }
        });
        thread.start();
    }

    public static void deleteImage(final Image image){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.getInstance().getAppDatabase().imageDao().deleteImage(image);
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
                    App.getInstance().getAppDatabase().articalDao().insert(artical);
                }
            }
        });
        thread.start();
    }

    public static Observable<List<Comment>> getComments(final String authorId){
        Observable<List<Comment>> observable = new Observable<List<Comment>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Comment>> observer) {
                List<Comment> list = App.getInstance().getAppDatabase().commentDao().getComments(authorId);
                observer.onNext(list);
                observer.onComplete();
            }
        };
        return observable;
    }

    public static void addComments(final List<Comment> list){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CommentDao dao = App.getInstance().getAppDatabase().commentDao();
                for (Comment c :
                        list) {
                    dao.addComment(c);
                }
            }
        });
        thread.start();
    }
}