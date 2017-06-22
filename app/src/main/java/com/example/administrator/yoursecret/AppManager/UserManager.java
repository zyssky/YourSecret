package com.example.administrator.yoursecret.AppManager;


import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.FunctionUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/6/8.
 */

public class UserManager {

    private String password;

    private String phoneNum;

    private String nickName;

    private String token;

    private String iconPath;

    private String iconLocalTempPath;

    public String getIconLocalTempPath() {
        return iconLocalTempPath;
    }

    public void setIconLocalTempPath(String iconLocalTempPath) {
        this.iconLocalTempPath = iconLocalTempPath;
    }

    public UserManager(){
        // TODO: 2017/6/19 init the perference from phone local path
        this.password = "123456";
        this.phoneNum = "18826075817";
        this.nickName = "zyssky";
        this.token = "e9f6c6be-415a-46c3-b7ee-8ef9054ab06a";
        this.iconPath = FoundationManager.SERVER_BASE_URL+"userIcon/test.jpg";
//        this.iconPath = FileUtils.toRootPath()+ File.separator+"test.jpg";
        this.iconLocalTempPath = FileUtils.toRootPath()+ File.separator+"test.jpg";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIdentifier(){
        return FunctionUtils.getSHA256String(phoneNum+password);
    }
}
