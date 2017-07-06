package com.example.administrator.yoursecret.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Home.HomeActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BitmapExtra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.content.ContentValues.TAG;


public class PushService extends Service {
    
    public PushService() {
    }

    private PendingIntent pendingIntent;

    private Map<String,Artical> articalMap;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            if(bundle.getString(AppContants.KEY,"").equals("mission")){
                mission();
            }
        }
        if(pendingIntent!=null){
            return super.onStartCommand(intent, flags, startId);
        }

        App.getInstance().setAppContext(this.getApplicationContext());
        Log.d("PushService  ", "onStartCommand: "+"test the service !!!!!!!!!!!!!!!!!");
        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        long wait = getNextTaskWaitTime();
//        long wait = new Date().getTime()+60*1000;
        Intent intent1 = new Intent(this,PushService.class);
        intent1.putExtra(AppContants.KEY,"mission");
        pendingIntent = PendingIntent.getService(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC,wait,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    public long getNextTaskWaitTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String noon = "12:00";
        String afternoon = "18:00";
        String today = dateFormat.format(date);
        String tomorrow = dateFormat.format(new Date(date.getTime()+24*60*60*1000L));
        String todayNoon = today+noon;
        String todayAfternoon = today+afternoon;
        String tomorrowNoon = tomorrow+noon;
        try {
            Date tn = dateFormat1.parse(todayNoon);
            Date ta = dateFormat1.parse(todayAfternoon);
            Date ton = dateFormat1.parse(tomorrowNoon);
            if(tn.after(date)){
//                return tn.getTime()-date.getTime();
                return tn.getTime();
            }
            if(ta.after(date)){
//                return ta.getTime()-date.getTime();
                return ta.getTime();
            }
//            return ton.getTime()-date.getTime();
            return ton.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return date.getTime()+60*60*1000;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        if(pendingIntent!=null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent = null;
        }
    }

    public void mission(){
        Log.d("Mission test  ", "mission: "+"do the pull message mission!____________________");
        pendingIntent = null;

        final Consumer<BitmapExtra> consumer = new Consumer<BitmapExtra>() {
            @Override
            public void accept(@NonNull BitmapExtra bitmapExtra) throws Exception {
                Artical artical = articalMap.get(bitmapExtra.path);
                if(artical==null){
                    return;
                }
                Notification notification = getNotification(artical,bitmapExtra);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(new Random().nextInt(),notification);
            }
        };

        Observer<ArrayList<Artical>> observer = new Observer<ArrayList<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                articalMap = null;
                articalMap = new HashMap<>();
            }

            @Override
            public void onNext(@NonNull ArrayList<Artical> articals) {
                for (Artical artical :
                        articals) {
                    articalMap.put(artical.imageUri,artical);
                    App.getInstance().getNetworkManager().getBitmapFromInternet(consumer,artical.imageUri);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: ");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };


        App.getInstance().getNetworkManager().getPushArticals(observer);



    }


    private Notification getNotification(Artical artical,BitmapExtra bitmapextra){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(AppContants.KEY,artical);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d(TAG, "getNotification: "+bitmapextra.path);

        Intent intent1 = new Intent(this,HomeActivity.class);

        TaskStackBuilder builder = TaskStackBuilder.create(this);
        builder.addNextIntent(intent1).addNextIntent(intent);
        PendingIntent pi = builder.getPendingIntent(new Random().nextInt(),PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteview = new RemoteViews(this.getPackageName(), R.layout.notification);
        remoteview.setImageViewBitmap(R.id.notification_image,bitmapextra.bitmap);
        remoteview.setTextViewText(R.id.notification_title,artical.title);
        remoteview.setTextViewText(R.id.notification_text,artical.introduction);
        Notification notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_knowledg)
                .setContent(remoteview).setContentIntent(pi).setAutoCancel(true).build();


        return notification;
    }
}
