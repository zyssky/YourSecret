package com.example.administrator.yoursecret.Editor.Manager;

import com.example.administrator.yoursecret.Editor.Network.NetworkManager;

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

    private NetworkManager networkManager;

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public PhotoManager getPhotoManager() {
        return photoManager;
    }

    public void setPhotoManager(PhotoManager photoManager) {
        this.photoManager = photoManager;
    }
}