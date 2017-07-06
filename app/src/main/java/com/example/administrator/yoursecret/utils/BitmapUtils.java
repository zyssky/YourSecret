package com.example.administrator.yoursecret.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/28.
 */

public class BitmapUtils {

    public static File getTempImage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File fileDir = new File(Environment.getExternalStorageDirectory()+File.separator
                    + "footprint"+File.separator+"photos");
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            long name = new Date().getTime();
            File tempFile = new File(fileDir, name+".jpg");
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return tempFile;
        }
        return null;
    }

    public static Bitmap createCircleImage(Bitmap source){
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int limit = source.getHeight()>source.getWidth()?source.getWidth():source.getHeight();
        Bitmap temp = null;
        if(limit==source.getHeight())
            temp = Bitmap.createBitmap(source,(source.getWidth()-limit)/2,0,limit,limit);
        else
            temp = Bitmap.createBitmap(source,0,(source.getHeight()-limit)/2,limit,limit);
        Bitmap target = Bitmap.createBitmap(limit,limit,temp.getConfig());
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(limit/2,limit/2,limit/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(temp,0,0,paint);
        return target;
    }

}
