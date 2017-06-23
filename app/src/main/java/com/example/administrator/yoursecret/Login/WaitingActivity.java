package com.example.administrator.yoursecret.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.R;


public class WaitingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

//        AsyncLogin.TYPE = getIntent().getIntExtra(AppContants.TYPE,-1);
        final String account = getIntent().getStringExtra(AppContants.ACCOUNT);
        final String password = getIntent().getStringExtra(AppContants.PASSWORD);
        String str=getIntent().getStringExtra("code");
//        new AsyncLogin(this).execute(account,password);
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();

    }

}

