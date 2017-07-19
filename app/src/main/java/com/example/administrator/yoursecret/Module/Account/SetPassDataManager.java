package com.example.administrator.yoursecret.Module.Account;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Entity.UserResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by j on 2017/7/11.
 */

public class SetPassDataManager {
    private String TAG =SetPassDataManager.class.getSimpleName();
    private static SetPassDataManager instance;

    private SetPassDataManager(){}

    public static SetPassDataManager getInstance(){
        if(instance==null){
            instance = new SetPassDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    public void modifypas(String old_password,String  new_password){
        App.getInstance().getNetworkManager().modifyPassword(old_password, new_password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserResponse userResponse) {
                        if (userResponse.code == 200) {

                            Toast.makeText(App.getInstance().getAppContext(), "修改成功", Toast.LENGTH_LONG).show();
                            SetPassActivity.Instance.finish();
                        } else {
                            Toast.makeText(App.getInstance().getAppContext(), "修改失败", Toast.LENGTH_LONG).show();
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
