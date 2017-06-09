package com.example.administrator.yoursecret;


/**
 * Created by Administrator on 2017/6/8.
 */

public class UserManager {
    private static UserManager instance;

    private UserManager(){}

    public static UserManager getInstance(){
        if(instance==null){
            instance = new UserManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    public String getUserId(){
        return "18826075817";
    }

    public String getUserName(){
        return "zyssky";
    }
}
