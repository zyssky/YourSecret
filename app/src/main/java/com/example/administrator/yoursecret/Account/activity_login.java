package com.example.administrator.yoursecret.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.yoursecret.R;

/**
 * Created by j on 2017/6/19.
 */

public class activity_login extends AppCompatActivity implements View.OnClickListener{
    private Button register,forget;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login);
        register=(Button) findViewById(R.id.btn_to_regist1);
        forget=(Button) findViewById(R.id.forget_password1);
        super.onCreate(savedInstanceState);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_regist1:
                Intent intent1=new Intent();
                intent1.setClass(getApplicationContext(), activity_register.class);
                startActivity(intent1 );
                break;
            case R.id.forget_password:
                break;
        }
    }
}
