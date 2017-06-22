package com.example.administrator.yoursecret.Account;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.yoursecret.R;

/**
 * Created by j on 2017/6/20.
 */

public class activity_setItem1 extends Activity implements View.OnClickListener{
    private NavigationView navigationView;
    private EditText editText;
    private Button save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_item);
        super.onCreate(savedInstanceState);
    }
  private void initView() {


        navigationView.setTitle("Title");
        navigationView.setClickCallback(new NavigationView.ClickCallback() {

            @Override
            public void onRightClick() {



            }

            @Override
            public void onBackClick() {

                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
