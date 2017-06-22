package com.example.administrator.yoursecret.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.Login.LoginHandler;
import com.example.administrator.yoursecret.Login.WaitingActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;

/**
 * Created by j on 2017/6/21.
 */

public class handler_login {
    public static String TAG = handler_login.class.getSimpleName();

    private Context context;

    public handler_login(Context context){
        this.context = context;
    }

    public void onLogin(Bundle bundle){
        Log.d(TAG, "OnLogin: "+ AppContants.ACCOUNT+" : "+bundle.getString(AppContants.ACCOUNT));
        if(bundle.getString(AppContants.ACCOUNT).length()==0){
            Toast.makeText(context, R.string.empty_account, Toast.LENGTH_SHORT).show();
            return;
        }
        if(bundle.getString(AppContants.PASSWORD).length()==0){
            Toast.makeText(context, R.string.empty_password, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context,WaitingActivity.class);
        intent.putExtra(AppContants.ACCOUNT,bundle.getString(AppContants.ACCOUNT));
        intent.putExtra(AppContants.PASSWORD,bundle.getString(AppContants.PASSWORD));
        intent.putExtra(AppContants.TYPE,AppContants.LOGIN);
        context.startActivity(intent);


    }

    public void onRegister(Bundle bundle){
        Log.d(TAG, "onRegister: "+ AppContants.ACCOUNT+" : "+bundle.getString(AppContants.ACCOUNT));
        if(bundle.getString(AppContants.ACCOUNT).length()==0){
            Toast.makeText(context, R.string.empty_account, Toast.LENGTH_SHORT).show();
            return;
        }
        if(bundle.getString(AppContants.PASSWORD).length()==0){
            Toast.makeText(context, R.string.empty_password, Toast.LENGTH_SHORT).show();
            return;
        }
        if(!bundle.getString(AppContants.PASSWORD).equals(bundle.getString(AppContants.PASSWORD_CONFIRM))) {
            Toast.makeText(context, R.string.wrong_confirm_pwd, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context,WaitingActivity.class);
        intent.putExtra(AppContants.ACCOUNT,bundle.getString(AppContants.ACCOUNT));
        intent.putExtra(AppContants.PASSWORD,bundle.getString(AppContants.PASSWORD));
        intent.putExtra(AppContants.TYPE,AppContants.REGISTER);
        context.startActivity(intent);
    }
}
