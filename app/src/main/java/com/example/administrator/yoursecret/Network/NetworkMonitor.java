package com.example.administrator.yoursecret.Network;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.AppDatabaseManager;
import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.ArticalResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/6/22.
 */

public class NetworkMonitor {
    public String TAG = NetworkMonitor.class.getSimpleName();

    private List<Artical> operatingArticalQueue;

    public NetworkMonitor(){
        operatingArticalQueue = new ArrayList<>();
    }

    public Artical popLatestArtical(){
        if(operatingArticalQueue.size()>0)
            return operatingArticalQueue.get(0);
        return null;
    }

    public void pushArtical(Artical artical){
        operatingArticalQueue.add(artical);
    }


    public Observer<? super ArticalResponse> getUploadArticalObserver() {
        Observer<ArticalResponse> observer = new Observer<ArticalResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull ArticalResponse articalResponse) {
                Artical artical = popLatestArtical();
                if(articalResponse.code == 200){
                    artical.imageUri = articalResponse.imageUri;
                    artical.articalHref = articalResponse.articalHref;
                    Toast.makeText(ApplicationDataManager.getInstance().getAppContext(),"上传成功！",Toast.LENGTH_LONG).show();
                    ApplicationDataManager.getInstance().getRecordDataManager().updateArtical(articalResponse.imageUri,articalResponse.articalHref,articalResponse.articalClientUuid);
                    AppDatabaseManager.updateArtical(articalResponse.imageUri,articalResponse.articalHref,articalResponse.articalClientUuid);
                    AppDatabaseManager.deleteImages(articalResponse.articalClientUuid);
                }
                else{
                    Toast.makeText(ApplicationDataManager.getInstance().getAppContext(),"上传失败",Toast.LENGTH_LONG).show();
                    artical.finished = 0;
                    ApplicationDataManager.getInstance().getRecordDataManager().moveArticalToTemp(artical.uuid);
                    AppDatabaseManager.updateArtical(artical.finished,artical.uuid);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Toast.makeText(ApplicationDataManager.getInstance().getAppContext(),"上传失败",Toast.LENGTH_LONG).show();
                Artical artical = popLatestArtical();
                artical.finished = 0;
                ApplicationDataManager.getInstance().getRecordDataManager().moveArticalToTemp(artical.uuid);
                AppDatabaseManager.updateArtical(artical.finished,artical.uuid);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
        return observer;
    }

}
