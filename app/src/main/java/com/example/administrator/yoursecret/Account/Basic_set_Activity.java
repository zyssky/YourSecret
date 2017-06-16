package com.example.administrator.yoursecret.Account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.yoursecret.R;

/**
 * Created by j on 2017/6/16.
 */

public class Basic_set_Activity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout touxiang;
    private Button save;
   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.account_change);
        initView();
        super.onCreate(savedInstanceState);
    }
    private void initView()
    {
        touxiang=(LinearLayout)findViewById(R.id.touxiang1);
        touxiang.setOnClickListener(this);
        save=(Button)findViewById(R.id.save_basic);

    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.touxiang1:
             break;
         case R.id.save_basic:
             break;
     }
    }
}
