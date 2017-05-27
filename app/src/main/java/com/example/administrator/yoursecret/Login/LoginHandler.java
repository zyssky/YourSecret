package com.example.administrator.yoursecret.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.R;


/**
 * Created by Administrator on 2017/3/31.
 */

public class LoginHandler {
    public static String TAG = LoginHandler.class.getSimpleName();

    private Context context;

    public LoginHandler(Context context){
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
