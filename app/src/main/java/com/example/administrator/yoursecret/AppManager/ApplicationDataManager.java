package com.example.administrator.yoursecret.AppManager;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.administrator.yoursecret.Network.NetworkManager;
import com.example.administrator.yoursecret.Network.NetworkMonitor;
import com.example.administrator.yoursecret.Recieve.RecieveDataManager;
import com.example.administrator.yoursecret.Record.RecordDataManager;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ApplicationDataManager {
    private static ApplicationDataManager instance;

    private ApplicationDataManager(){}

    public static ApplicationDataManager getInstance(){
        if(instance==null){
            instance = new ApplicationDataManager();
        }
        return instance;
    }

    private Context appContext;

    public Context getAppContext(){
        return appContext;
    }

    public void setAppContext(Context context){
        this.appContext = context;
        FoundationManager.setup(context);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    private UserManager userManager;

    private RecordDataManager recordDataManager;

    private RecieveDataManager recieveDataManager;

    public RecieveDataManager getRecieveDataManager() {
        return recieveDataManager;
    }

    public void setRecieveDataManager(RecieveDataManager recieveDataManager) {
        this.recieveDataManager = recieveDataManager;
    }

    public RecordDataManager getRecordDataManager() {
        return recordDataManager;
    }

    public void setRecordDataManager(RecordDataManager recordDataManager) {
        this.recordDataManager = recordDataManager;
    }

    private NetworkManager networkManager;

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public NetworkMonitor getNetworkMonitor() {
        return networkMonitor;
    }

    public void setNetworkMonitor(NetworkMonitor networkMonitor) {
        this.networkMonitor = networkMonitor;
    }

    private NetworkMonitor networkMonitor;



    private AppDatabase db ;

    public AppDatabase getAppDatabase(){
        if(null==db){
            db = Room.databaseBuilder(appContext,AppDatabase.class,"yoursecret_db").build();
        }
        return db;
    }
}
