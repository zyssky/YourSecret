package com.example.administrator.yoursecret.AppManager;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.administrator.yoursecret.Database.AppDatabase;
import com.example.administrator.yoursecret.Network.NetworkManager;
import com.example.administrator.yoursecret.Network.NetworkMonitor;
import com.example.administrator.yoursecret.Module.Recieve.RecieveDataManager;
import com.example.administrator.yoursecret.Module.Record.RecordDataManager;
import com.example.administrator.yoursecret.utils.PermissionGrantHouse;

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



    private UserManager userManager;

    public UserManager getUserManager() {
        if(userManager==null){
            userManager = new UserManager();
        }
        return userManager;
    }




    private RecieveDataManager recieveDataManager;

    public RecieveDataManager getRecieveDataManager() {
        if(recieveDataManager == null){
            recieveDataManager = new RecieveDataManager();
        }
        return recieveDataManager;
    }



    private RecordDataManager recordDataManager;

    public RecordDataManager getRecordDataManager() {
        if(recordDataManager == null){
            recordDataManager = new RecordDataManager();
        }
        return recordDataManager;
    }



    private NetworkManager networkManager;

    public NetworkManager getNetworkManager() {
        if(networkManager == null){
            networkManager = new NetworkManager();
        }
        return networkManager;
    }


    private NetworkMonitor networkMonitor;

    public NetworkMonitor getNetworkMonitor() {
        if(networkMonitor == null){
            networkMonitor = new NetworkMonitor();
        }
        return networkMonitor;
    }


    private PermissionGrantHouse house;

    public PermissionGrantHouse getPermissionHouse(){
        if(house == null){
            house = new PermissionGrantHouse();
        }
        return house;
    }



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
        getRecordDataManager().refresh();
    }

}
