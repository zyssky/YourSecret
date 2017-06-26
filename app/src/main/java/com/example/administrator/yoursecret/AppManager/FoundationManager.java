package com.example.administrator.yoursecret.AppManager;

import android.content.Context;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.PropUtil;

import java.io.File;
import java.io.FileOutputStream;
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

    public static void setup(Context context){
        Properties properties = PropUtil.loadResProperties(context, R.raw.api);
        String domain = properties.getProperty("domain");
        String port = properties.getProperty("port");
        String root = properties.getProperty("root");

        SERVER_BASE_URL = domain+port+root;

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
}
