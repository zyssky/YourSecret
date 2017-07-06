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

public class App {
    private static App instance;

    private App(){}

    public static App getInstance(){
        if(instance==null){
            instance = new App();
        }
        return instance;
    }

    private Context appContext;

    public Context getAppContext(){
        return appContext;
    }

    public void setAppContext(Context context){
        if(appContext!=null){
            return;
        }
        this.appContext = context;
        FoundationManager.setup(context);
    }

    public UserManager getUserManager() {
        if(userManager==null){
            userManager = new UserManager();
        }
        return userManager;
    }

//    public void setUserManager(UserManager userManager) {
//        this.userManager = userManager;
//    }

    private UserManager userManager;

    private RecordDataManager recordDataManager;

    private RecieveDataManager recieveDataManager;

    public RecieveDataManager getRecieveDataManager() {
        if(recieveDataManager == null){
            recieveDataManager = new RecieveDataManager();
        }
        return recieveDataManager;
    }

//    public void setRecieveDataManager(RecieveDataManager recieveDataManager) {
//        this.recieveDataManager = recieveDataManager;
//    }

    public RecordDataManager getRecordDataManager() {
        if(recordDataManager == null){
            recordDataManager = new RecordDataManager();
        }
        return recordDataManager;
    }

//    public void setRecordDataManager(RecordDataManager recordDataManager) {
//        this.recordDataManager = recordDataManager;
//    }

    private NetworkManager networkManager;

    public NetworkManager getNetworkManager() {
        if(networkManager == null){
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

//    public void setNetworkManager(NetworkManager networkManager) {
//        this.networkManager = networkManager;
//    }

    public NetworkMonitor getNetworkMonitor() {
        if(networkMonitor == null){
            networkMonitor = new NetworkMonitor();
        }
        return networkMonitor;
    }

//    public void setNetworkMonitor(NetworkMonitor networkMonitor) {
//        this.networkMonitor = networkMonitor;
//    }

    private NetworkMonitor networkMonitor;



    private AppDatabase db ;

    public AppDatabase getAppDatabase(){
        if(null==db){
            db = Room.databaseBuilder(appContext,AppDatabase.class,"yoursecret_db").build();
        }
        return db;
    }

    public static void onDestroy() {
        instance = null;
    }

    public void refresh(){
        recordDataManager.refresh();
    }

}
