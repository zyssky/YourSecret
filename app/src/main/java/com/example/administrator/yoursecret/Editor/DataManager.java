package com.example.administrator.yoursecret.Editor;

/**
 * Created by Administrator on 2017/6/8.
 */

public class DataManager {
    private static DataManager instance;

    private DataManager(){}

    public static DataManager getInstance(){
        if(instance==null){
            instance = new DataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    //the resources and their getter and setter

    public ArticalManager getArticalManager() {
        return articalManager;
    }

    public void setArticalManager(ArticalManager articalManager) {
        this.articalManager = articalManager;
    }

    private ArticalManager articalManager;

    private PhotoManager photoManager;

    public PhotoManager getPhotoManager() {
        return photoManager;
    }

    public void setPhotoManager(PhotoManager photoManager) {
        this.photoManager = photoManager;
    }
}
