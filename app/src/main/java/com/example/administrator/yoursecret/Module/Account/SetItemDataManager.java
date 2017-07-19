package com.example.administrator.yoursecret.Module.Account;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Entity.UserResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by j on 2017/7/11.
 */

public class SetItemDataManager {

    private String TAG =SetItemDataManager.class.getSimpleName();
    private static SetItemDataManager instance;

    private SetItemDataManager(){}

    public static SetItemDataManager getInstance(){
        if(instance==null){
            instance = new SetItemDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    public void modifynic(final String nic){

        App.getInstance().getNetworkManager().modify(nic,null )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserResponse userResponse) {

                        if (userResponse.code == 200) {
                            UserManager usermanager = App.getInstance().getUserManager();
                            usermanager.setNickName(nic);
                            Toast.makeText(App.getInstance().getAppContext(), "修改成功", Toast.LENGTH_SHORT).show();
                            SetItemActivity.Instance.finish();

                        } else {
                            Toast.makeText(App.getInstance().getAppContext(), "修改失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
