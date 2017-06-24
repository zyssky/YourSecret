package com.example.administrator.yoursecret.AppManager;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.FunctionUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/6/8.
 */

public class UserManager {

    private String phoneNum;

    private String nickName;

    private String token;

    private String iconPath;

    private String iconLocalTempPath;

    private String tempPhoneNum;


    public UserManager(){
        // TODO: 2017/6/19 init the perference from phone local path
//        this.password = "123456";
//        this.phoneNum = "18826075817";
//        this.nickName = "zyssky";
//        this.token = "e9f6c6be-415a-46c3-b7ee-8ef9054ab06a";
//        this.iconPath = FoundationManager.SERVER_BASE_URL+"userIcon/test.jpg";
//        this.iconPath = FileUtils.toRootPath()+ File.separator+"test.jpg";
//        this.iconLocalTempPath = FileUtils.toRootPath()+ File.separator+"test.jpg";

        SharedPreferences sharedPreferences = ApplicationDataManager.getInstance().
                getAppContext().getSharedPreferences(AppContants.packageName, Context.MODE_PRIVATE);
        phoneNum = sharedPreferences.getString(AppContants.ACCOUNT,"");
        nickName = sharedPreferences.getString(AppContants.NICKNAME,"");
        token = sharedPreferences.getString(AppContants.TOKEN,"");
        iconPath = sharedPreferences.getString(AppContants.ICONPATH,"");
    }


    public void setTempPhoneNum(String tempPhoneNum){
        this.tempPhoneNum = tempPhoneNum;
    }

    public String getIconLocalTempPath() {
        return iconLocalTempPath;
    }

    public void setIconLocalTempPath(String iconLocalTempPath) {
        this.iconLocalTempPath = iconLocalTempPath;
    }

    public boolean hasLogin(){
        if(token!=null && !token.isEmpty()){
            return true;
        }
        return false;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getNickName() {
        return nickName;
    }

    public String getToken() {
        return token;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void savePhoneNum(){
        this.phoneNum = tempPhoneNum;
        SharedPreferences sharedPreferences = ApplicationDataManager.getInstance().
                getAppContext().getSharedPreferences(AppContants.packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppContants.ACCOUNT,phoneNum);
        editor.commit();
    }

    public void setNickName(String nickName) {
        SharedPreferences sharedPreferences = ApplicationDataManager.getInstance().
                getAppContext().getSharedPreferences(AppContants.packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppContants.NICKNAME,nickName);
        this.nickName = nickName;
        editor.commit();
    }

    public void setToken(String token) {
        SharedPreferences sharedPreferences = ApplicationDataManager.getInstance().
                getAppContext().getSharedPreferences(AppContants.packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppContants.TOKEN,token);
        this.token = token;
        editor.commit();
    }

    public void setIconPath(String iconPath) {
        SharedPreferences sharedPreferences = ApplicationDataManager.getInstance().
                getAppContext().getSharedPreferences(AppContants.packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppContants.ICONPATH,iconPath);
        this.iconPath = iconPath;
        editor.commit();
    }
}
