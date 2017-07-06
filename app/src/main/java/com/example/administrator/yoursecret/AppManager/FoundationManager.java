package com.example.administrator.yoursecret.AppManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.PropUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import static com.example.administrator.yoursecret.utils.FileUtils.checkSDcard;
import static com.example.administrator.yoursecret.utils.FileUtils.toRootPath;

/**
 * Created by Administrator on 2017/6/15.
 */

public class FoundationManager {
    public static String SERVER_BASE_URL;
    public static String ISUNDER_WIFI;
    public static String APP_ICON_URL;

    public static String LOCATION_ICON_URL;

    public static long INTERVAL = 20*60*1000;

    public static void setup(Context context){
        Properties properties = PropUtil.loadResProperties(context, R.raw.api);
        String domain = properties.getProperty("domain");

        SERVER_BASE_URL = domain;

        APP_ICON_URL = SERVER_BASE_URL+"static/logo.png";

        LOCATION_ICON_URL = SERVER_BASE_URL+"static/location.png";

        AppContants.ARTICLE_TYPE_HOT = context.getResources().getString(R.string.hot);
        AppContants.ARTICLE_TYPE_NOTICE = context.getResources().getString(R.string.notice);
        AppContants.ARTICLE_TYPE_ARTICLE = context.getResources().getString(R.string.article);
    }

    public static String getCssPath(){
        if(checkSDcard()){
            File css = new File(toRootPath(),"base.css");
            if(!css.exists()){
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(css);
                    fileOutputStream.write("img {max-width: 100%; width:auto; height:auto;}".getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return css.getPath();
        }
        return null;
    }

    public static boolean isAutoPush(){
        SharedPreferences sharedPreferences = App.getInstance().getAppContext().getSharedPreferences("com.example.administrator.yoursecret",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(AppContants.PUSH,true);
    }

    public static void setAutoPush(boolean status){
        SharedPreferences sharedPreferences = App.getInstance().getAppContext().getSharedPreferences("com.example.administrator.yoursecret",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppContants.PUSH,status);
        editor.commit();
    }

    public static void setLastRreshDate(Date date){
        SharedPreferences sharedPreferences = App.getInstance().getAppContext().getSharedPreferences("com.example.administrator.yoursecret",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(AppContants.LASTREFRESH,date.getTime());
        editor.commit();
    }

    public static boolean needToRefresh(){
        SharedPreferences sharedPreferences = App.getInstance().getAppContext().getSharedPreferences("com.example.administrator.yoursecret",Context.MODE_PRIVATE);
        long last = sharedPreferences.getLong(AppContants.LASTREFRESH,0);
        if(new Date().getTime() - last > INTERVAL){
            return true;
        }else{
            return false;
        }
    }
}
