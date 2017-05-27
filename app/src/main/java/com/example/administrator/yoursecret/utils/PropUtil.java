package com.example.administrator.yoursecret.utils;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/11.
 */

public class PropUtil {

    public static final String PROPERTY_PATH = "api.properties";

    /**
     * 读取assets文件夹下的文件
     */
    public static Properties loadAssetsProperties(Context context, String arg) {
        Properties prop = new Properties();
        //first load default properties
        try {
            prop.load(context.getAssets().open(arg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;

    }

    /**
     * 读取Res文件夹的文件
     */
    public static Properties loadResProperties(Context context, int id) {
        Properties prop = new Properties();
        //first load default properties
        try {
            prop.load(context.getResources().openRawResource(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;

    }

    /**
     * 获取配置文件中对应的URL地址
     *
     */
    private static String getValue(Context context , String propKey) {
        Properties properties = loadAssetsProperties(context,PROPERTY_PATH);
        return properties.getProperty(propKey,null);
    }
}
