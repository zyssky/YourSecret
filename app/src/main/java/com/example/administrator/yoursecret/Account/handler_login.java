package com.example.administrator.yoursecret.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.Login.LoginHandler;
import com.example.administrator.yoursecret.Login.WaitingActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FunctionUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by j on 2017/6/21.
 */

public class handler_login {
    public static String TAG = handler_login.class.getSimpleName();

    private Context context;

    public handler_login(Context context){
        this.context = context;
    }

    public void onLogin(final Bundle bundle) {
        Log.d(TAG, "OnLogin: " + AppContants.ACCOUNT + " : " + bundle.getString(AppContants.ACCOUNT));
        if (bundle.getString(AppContants.ACCOUNT).length() == 0) {
            Toast.makeText(context, R.string.empty_account, Toast.LENGTH_SHORT).show();
            return;
        }
        if (bundle.getString(AppContants.PASSWORD).length() == 0) {
            Toast.makeText(context, R.string.empty_password, Toast.LENGTH_SHORT).show();
            return;
        } else {
            ApplicationDataManager.getInstance().getNetworkManager().register()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(@NonNull UserResponse userResponse) {
                            if (FunctionUtils.getSHA256String(bundle.getString(AppContants.ACCOUNT) + bundle.getString(AppContants.ACCOUNT)) ==
                                    ApplicationDataManager.getInstance().getUserManager().getIdentifier())

                            {

                                Intent intent = new Intent(context, WaitingActivity.class);
                                intent.putExtra(AppContants.ACCOUNT, bundle.getString(AppContants.ACCOUNT));
                                intent.putExtra(AppContants.PASSWORD, bundle.getString(AppContants.PASSWORD));
                                intent.putExtra(AppContants.TYPE, AppContants.LOGIN);
                                context.startActivity(intent);
                                //设置ui，保存对应数据
                                //注册,登录，修改 成功后返回的数据不一样的，有的会没有，需要的就跟我说}
                            }
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                            Log.d(TAG, "onError: ");
                        }

                        @Override
                        public void onComplete() {
                            //如果有什么想在完成后做的可以在这里做
                            Log.d(TAG, "onComplete: ");
                        }

                    });



        }
    }

    public void onRegister(final Bundle bundle) {
        Log.d(TAG, "onRegister: " + AppContants.ACCOUNT + " : " + bundle.getString(AppContants.ACCOUNT));
        if (bundle.getString(AppContants.ACCOUNT).length() == 0) {
            Toast.makeText(context, R.string.empty_account, Toast.LENGTH_SHORT).show();
            return;
        }
        if (bundle.getString(AppContants.PASSWORD).length() == 0) {
            Toast.makeText(context, R.string.empty_password, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!bundle.getString(AppContants.PASSWORD).equals(bundle.getString(AppContants.PASSWORD_CONFIRM))) {
            Toast.makeText(context, R.string.wrong_confirm_pwd, Toast.LENGTH_SHORT).show();
            return;
        } else {

            ApplicationDataManager.getInstance().getNetworkManager().register()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull UserResponse userResponse) {



                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }
    }
}
