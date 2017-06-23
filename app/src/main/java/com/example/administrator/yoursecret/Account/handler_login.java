package com.example.administrator.yoursecret.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.Home.HomeActivity;
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
        }
        else {
            String account = bundle.getString(AppContants.ACCOUNT);
            String password = bundle.getString(AppContants.PASSWORD);
            String identifier = FunctionUtils.getSHA256String(account+password);
            Log.d(TAG, "onLogin: "+account+","+password+","+identifier);
            ApplicationDataManager.getInstance().getUserManager().setTempPhoneNum(account);
            ApplicationDataManager.getInstance().getNetworkManager().login(account,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(@NonNull UserResponse userResponse) {
                            if(userResponse.code == 200){
                                UserManager usermanager = ApplicationDataManager.getInstance().getUserManager();
                                usermanager.savePhoneNum();
                                usermanager.setNickName(userResponse.nickName);
                                usermanager.setIconPath(userResponse.userIconPath);
                                usermanager.setToken(userResponse.token);
                            }
                            String s=userResponse.message;
                            Intent intent = new Intent(context, WaitingActivity.class);
                            intent.putExtra(AppContants.ACCOUNT, bundle.getString(AppContants.ACCOUNT));
                            intent.putExtra(AppContants.PASSWORD, bundle.getString(AppContants.PASSWORD));
                            intent.putExtra(AppContants.TYPE, AppContants.LOGIN);
                            intent.putExtra("code",s);
                            context.startActivity(intent);
                                //设置ui，保存对应数据
                                //注册,登录，修改 成功后返回的数据不一样的，有的会没有，需要的就跟我说}
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
            final String account = bundle.getString(AppContants.ACCOUNT);
            String password = bundle.getString(AppContants.PASSWORD);

            String identifier = FunctionUtils.getSHA256String(account+password);
            final String nickname = bundle.getString(AppContants.NICKNAME);
            Log.d(TAG, "onLogin: "+account+","+password+","+identifier);
            ApplicationDataManager.getInstance().getNetworkManager().register(account,password,nickname)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull UserResponse userResponse) {

                            if(userResponse.code == 200)
                            {
                               /* ApplicationDataManager.getInstance().getUserManager().setNickName(nickname);
                                ApplicationDataManager.getInstance().getUserManager().setPhoneNum(account);*/


                            Intent intent = new Intent(context, HomeActivity.class);

                           /* intent.putExtra(AppContants.ACCOUNT, bundle.getString(AppContants.ACCOUNT));
                            intent.putExtra(AppContants.NICKNAME,nic );
                            intent.putExtra(AppContants.TYPE, AppContants.LOGIN);
                            intent.putExtra("code",s);*/
                            context.startActivity(intent);

                            }

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
