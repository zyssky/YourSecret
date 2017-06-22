package com.example.administrator.yoursecret.Account;

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

import com.example.administrator.yoursecret.Login.LoginActivity;
import com.example.administrator.yoursecret.Login.LoginHandler;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;

/**
 * Created by j on 2017/6/19.
 */

public class activity_register extends AppCompatActivity implements View.OnClickListener{
    private EditText r_et_mobile,r_et_password,r_et_password_confirm;
    private ImageView r_iv_show_pwd,r_iv_show_pwd_confirm,r_clean_phone,r_clean_password,r_clean_password_confirm;
    private Button r_register;
    handler_login RegisterHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.register);
        RegisterHandler=new handler_login(this);
        initView();
        initListener();
        super.onCreate(savedInstanceState);

    }



    private void initView() {

        r_register=(Button) findViewById(R.id.register_btn_register);
        r_et_mobile = (EditText) findViewById(R.id.register_et_mobile);
        r_et_password = (EditText) findViewById(R.id.register_et_password);
        r_et_password_confirm = (EditText) findViewById(R.id.register_et_confirm_password);
        r_clean_phone = (ImageView) findViewById(R.id.register_clean_phone);
        r_clean_password = (ImageView) findViewById(R.id.register_clean__password);
        r_iv_show_pwd = (ImageView) findViewById(R.id.register_iv_show_pwd);
        r_clean_password_confirm = (ImageView) findViewById(R.id.register_clean_confirm_password);
        r_iv_show_pwd_confirm = (ImageView) findViewById(R.id.register_iv_show_confirm_pwd);
    }
    private void initListener() {

        r_et_mobile.setOnClickListener(this);
        r_et_password.setOnClickListener(this);
        r_et_password_confirm.setOnClickListener(this);
        r_clean_phone.setOnClickListener(this);
        r_clean_password.setOnClickListener(this);
        r_iv_show_pwd .setOnClickListener(this);
        r_clean_password_confirm.setOnClickListener(this);
        r_iv_show_pwd_confirm .setOnClickListener(this);
        r_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity_register.this,"test",Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AppContants.ACCOUNT,r_et_mobile.getText().toString());
                bundle.putCharSequence(AppContants.PASSWORD,r_et_password.getText().toString());
                bundle.putCharSequence(AppContants.PASSWORD_CONFIRM,r_et_password_confirm.getText().toString());
                RegisterHandler.onRegister(bundle);
            }
        });
        r_et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && r_clean_phone.getVisibility() == View.GONE) {
                   r_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    r_clean_phone.setVisibility(View.GONE);

                }
                else if(!s.toString().matches("[0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_register.this, R.string.please_input_limit_acc, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    r_et_mobile.setSelection(s.length());
                }
            }
        });
        r_et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && r_clean_password.getVisibility() == View.GONE) {
                    r_clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    r_clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_register.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    r_et_password.setSelection(s.length());
                }
            }
        });

        r_et_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && r_clean_password_confirm.getVisibility() == View.GONE) {
                    r_clean_password_confirm.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    r_clean_password_confirm.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_register.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    r_et_password_confirm.setSelection(s.length());
                }
            }
        });



    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.register_clean_phone:
                r_et_mobile.setText("");
                break;
            case R.id.register_clean__password:
                r_et_password.setText("");
                break;
            case R.id.register_clean_confirm_password:
                r_et_password_confirm.setText("");
                break;
            case R.id.register_iv_show_pwd:
                if (r_et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    r_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    r_iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    r_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    r_iv_show_pwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = r_et_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    r_et_password.setSelection(pwd.length());
                break;
            case R.id.register_iv_show_confirm_pwd:
                if (r_et_password_confirm.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    r_et_password_confirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    r_iv_show_pwd_confirm.setImageResource(R.drawable.pass_visuable);
                } else {
                    r_et_password_confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    r_iv_show_pwd_confirm.setImageResource(R.drawable.pass_gone);
                }
                String pwd_confirm = r_et_password_confirm.getText().toString();
                if (!TextUtils.isEmpty(pwd_confirm))
                    r_et_password_confirm.setSelection(pwd_confirm.length());
                break;
        }
    }

    }

