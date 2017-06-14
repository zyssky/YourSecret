package com.example.administrator.yoursecret;

import android.content.Context;

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
    }

}
