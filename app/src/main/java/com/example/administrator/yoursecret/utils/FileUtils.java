package com.example.administrator.yoursecret.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.administrator.yoursecret.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/5.
 */

public class FileUtils {
    /*
    *  判断文件是否存在
    *  filePath的参数格式：/storage/emulated/0/.ToDayNote/UserInfo/28017616_1459134625049.jpg
    */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /*
    *    文件删除
    */
    public static boolean fileDelete(String filePath) {
        File file = new File(filePath);
        if (file.exists() == false) {
            return false;
        }
        return file.delete();
    }

    /**
     * 创建文件夹
     */
    public static boolean fileMkdirs(String filePath) {
        File file = new File(filePath);
        return file.mkdirs();
    }

    /**
     * 主目录地址。在SD卡中，创建一个项目的根目录，根目录的包名为 ToDayNote
     *  获取项目主目录的地址
     */
    public static String toRootPath() {
        String dir;
        if (checkSDcard()) {
            dir = Environment.getExternalStorageDirectory().getPath();
        } else {
            dir = Environment.getDataDirectory().getPath();
        }
        return dir + File.separator+"footprint";
    }

    public static boolean saveAsPng(String path,Bitmap bitmap){
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 检测是否存在Sdcard
     * @return 存在返回true，不存在返回false
     */
    public static boolean checkSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        return flag;
    }


    /**
     * 获取SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }


}
