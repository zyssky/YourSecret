package com.example.administrator.yoursecret.AppManager;


import com.example.administrator.yoursecret.AppManager.FoundationManager;

/**
 * Created by Administrator on 2017/6/8.
 */

public class UserManager {

    public UserManager(){}

    public String getUserId(){
        return "18826075817";
    }

    public String getUserName(){
        return "zyssky";
    }

    public String getUserIconPath(){ return FoundationManager.SERVER_BASE_URL+"userIcon/test.jpg";}
}
