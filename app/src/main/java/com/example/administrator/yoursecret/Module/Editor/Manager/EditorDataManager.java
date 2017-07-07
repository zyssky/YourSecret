package com.example.administrator.yoursecret.Module.Editor.Manager;

import com.example.administrator.yoursecret.Network.NetworkManager;

/**
 * Created by Administrator on 2017/6/8.
 */

public class EditorDataManager {
    private static EditorDataManager instance;

    private EditorDataManager(){}

    public static EditorDataManager getInstance(){
        if(instance==null){
            instance = new EditorDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    //the resources and their getter and setter

    public ArticalManager getArticalManager() {
        if(articalManager == null){
            articalManager = new ArticalManager();
        }
        return articalManager;
    }


    private ArticalManager articalManager;

    private PhotoManager photoManager;

    public PhotoManager getPhotoManager() {
        if(photoManager == null){
            photoManager = new PhotoManager();
        }
        return photoManager;
    }

}
