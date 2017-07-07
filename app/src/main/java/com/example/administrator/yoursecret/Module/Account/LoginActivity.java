package com.example.administrator.yoursecret.Module.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

/**
 * Created by j on 2017/6/19.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView touxiang;
    private EditText l_et_mobile;
    private EditText l_et_password;
    private ImageView l_iv_clean_phone;
    private ImageView l_clean_password;
    private ImageView l_iv_show_pwd;
    private Button l_btn_register, l_btn_to_login;
    private ScrollView scrollView;
    private handler_login loginHandler;
    private KeyboardLayout bindingView;
    public static Activity Instance;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login);
        super.onCreate(savedInstanceState);
        loginHandler = new handler_login(this);
        Instance = this;
        intiView();
        initListener();


    }

    private void intiView() {
        bindingView = (KeyboardLayout)findViewById(R.id.main_ll);
        l_btn_register = (Button) findViewById(R.id.l_btn_to_regist);
        l_btn_to_login = (Button) findViewById(R.id.l_btn_login);
        touxiang = (ImageView) findViewById(R.id.l_logo);
        l_et_mobile = (EditText) findViewById(R.id.l_et_mobile);
        l_et_password = (EditText) findViewById(R.id.l_et_password);
        l_iv_clean_phone = (ImageView) findViewById(R.id.l_iv_clean_phone);
        l_clean_password = (ImageView) findViewById(R.id.l_clean_password);
        l_iv_show_pwd = (ImageView) findViewById(R.id.l_iv_show_pwd);
        scrollView = (ScrollView) findViewById(R.id.scrollView2);
        final String account = getIntent().getStringExtra(AppContants.ACCOUNT);
        final String password = getIntent().getStringExtra(AppContants.PASSWORD);
        if(account!=null&&password!=null)
        {
            l_et_mobile.setText(account);
            l_et_password.setText(password);
        }
    }

    private void initListener() {
        l_iv_clean_phone.setOnClickListener(this);
        l_clean_password.setOnClickListener(this);
        l_iv_show_pwd.setOnClickListener(this);
        l_btn_register.setOnClickListener(this);

        addLayoutListener();
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
                } else if (!s.toString().matches("[0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_acc, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    l_et_password.setSelection(s.length());
                }
            }
        });


    }

    public void addLayoutListener() {
        bindingView.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                Log.e("onKeyboardStateChanged", "isActive:" + isActive + " keyboardHeight:" + keyboardHeight);
                if (isActive) {
                    scrollToBottom();
                }
            }
        });
    }

    /**
     * 弹出软键盘时将SVContainer滑到底
     */
    private void scrollToBottom() {

       scrollView.postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollView.smoothScrollTo(0, scrollView.getBottom());
            }
        }, 100);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.l_btn_to_regist:
                Intent intent1 = new Intent();
                intent1.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                finish();
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
    public void onResume() {
        super.onResume();
        UserManager usermanager = App.getInstance().getUserManager();
        if (usermanager.hasLogin()) {
            String iconPath = usermanager.getIconPath();
            GlideImageLoader.loadImageNail(this, iconPath, touxiang);
        }
    }

}


