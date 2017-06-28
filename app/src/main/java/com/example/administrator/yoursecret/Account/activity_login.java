package com.example.administrator.yoursecret.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

/**
 * Created by j on 2017/6/19.
 */

public class activity_login extends AppCompatActivity implements View.OnClickListener{
    private ImageView touxiang;
    private EditText l_et_mobile;
    private EditText l_et_password;
    private ImageView l_iv_clean_phone;
    private ImageView l_clean_password;
    private ImageView l_iv_show_pwd;
    private Button l_btn_register, l_btn_forget, l_btn_to_login;
    private handler_login loginHandler;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login);
        loginHandler = new handler_login(this);
        intiView();
        initListener();
        super.onCreate(savedInstanceState);

    }

    private void intiView() {
        l_btn_register =(Button) findViewById(R.id.l_btn_to_regist);
        l_btn_forget =(Button) findViewById(R.id.l_forget_password);
        l_btn_to_login =(Button)findViewById(R.id.l_btn_login);
        touxiang = (ImageView) findViewById(R.id.l_logo);
        l_et_mobile = (EditText) findViewById(R.id.l_et_mobile);
        l_et_password = (EditText) findViewById(R.id.l_et_password);
        l_iv_clean_phone = (ImageView) findViewById(R.id.l_iv_clean_phone);
        l_clean_password = (ImageView) findViewById(R.id.l_clean_password);
        l_iv_show_pwd = (ImageView) findViewById(R.id.l_iv_show_pwd);


    }
    private void initListener() {
        l_iv_clean_phone.setOnClickListener(this);
        l_clean_password.setOnClickListener(this);
        l_iv_show_pwd.setOnClickListener(this);
        l_btn_register.setOnClickListener(this);
        l_btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AppContants.ACCOUNT, l_et_mobile.getText().toString());
                bundle.putCharSequence(AppContants.PASSWORD, l_et_password.getText().toString());
                loginHandler.onLogin(bundle);
            }
        });
        l_et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && l_iv_clean_phone.getVisibility() == View.GONE) {
                    l_iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    l_iv_clean_phone.setVisibility(View.GONE);
                }
                else if(!s.toString().matches("[0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_login.this, R.string.please_input_limit_acc, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    l_et_mobile.setSelection(s.length());
                }
            }
        });
        l_et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && l_clean_password.getVisibility() == View.GONE) {
                    l_clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    l_clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_login.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    l_et_password.setSelection(s.length());
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.l_btn_to_regist:
                    Intent intent1=new Intent();
                    intent1.setClass(activity_login.this, activity_register.class);
                    startActivity(intent1 );
                case R.id.l_iv_clean_phone:
                    l_et_mobile.setText("");
                    break;
                case R.id.l_clean_password:
                    l_et_password.setText("");
                    break;
                case R.id.l_iv_show_pwd:
                    if (l_et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        l_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        l_iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                    } else {
                        l_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        l_iv_show_pwd.setImageResource(R.drawable.pass_gone);
                    }
                    String pwd = l_et_password.getText().toString();
                    if (!TextUtils.isEmpty(pwd))
                        l_et_password.setSelection(pwd.length());
                    break;

            }
        }
        @Override
    public void onResume()
        {
            super.onResume();
            UserManager usermanager = ApplicationDataManager.getInstance().getUserManager();
            if(usermanager.hasLogin()){
                String iconPath = usermanager.getIconPath();
                GlideImageLoader.loadImageNail(this,iconPath,touxiang);
            }
        }

    }

