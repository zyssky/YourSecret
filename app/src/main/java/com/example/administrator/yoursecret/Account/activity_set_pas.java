package com.example.administrator.yoursecret.Account;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.R;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by j on 2017/6/23.
 */

public class activity_set_pas extends Activity  implements View.OnClickListener{
    private EditText set_old_password,set_new_password;
    private ImageView clean_old,clean_new,show_old,show_new,left,right;
    private NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_password);
        initView();
        initListener();
        super.onCreate(savedInstanceState);
    }


    private void initView() {
        navigationView=(NavigationView)super.findViewById(R.id.nav2);
        set_old_password = (EditText)findViewById(R.id.set_old_password);
        set_new_password = (EditText)findViewById(R.id.set_new_password);
        clean_new = (ImageView)findViewById(R.id.set_clean_new_password);
        clean_old = (ImageView)findViewById(R.id.set_clean_old_password);
        show_old = (ImageView)findViewById(R.id.set_iv_show_old_pwds);
        show_new = (ImageView)findViewById(R.id.set_iv_show_new_pwd) ;
        left=navigationView.getLeftView();
        right=navigationView.getRightView();
        left.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        right.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {
            @Override
            public void onBackClick() {
                finish();

            }

            @Override
            public void onRightClick() {
                String old_password = set_old_password.getText().toString();
                String new_password = set_new_password.getText().toString();
                if(old_password.length()==0)
                {
                    Toast.makeText(activity_set_pas.this,"旧密码不能为空",Toast.LENGTH_SHORT).show();
                }
                else if( new_password.length()==0)
                {
                    Toast.makeText(activity_set_pas.this,"新密码不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(old_password.length()!=0&&new_password.length()!=0) {


                    App.getInstance().getNetworkManager().modifyPassword(old_password, new_password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UserResponse>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull UserResponse userResponse) {
                                    if (userResponse.code == 200) {

                                        Toast.makeText(activity_set_pas.this, "修改成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(activity_set_pas.this, "修改失败", Toast.LENGTH_LONG).show();
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
        });
    }



    private void initListener() {
        clean_new.setOnClickListener(this);
        clean_old.setOnClickListener(this);
        show_new.setOnClickListener(this);
        show_old.setOnClickListener(this);
        set_old_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_old.getVisibility() == View.GONE) {
                    clean_old.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_old.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_set_pas.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    set_old_password.setSelection(s.length());
                }

            }
        });
        set_new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s) && clean_new.getVisibility() == View.GONE) {
                    clean_new.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_new.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(activity_set_pas.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    set_old_password.setSelection(s.length());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.set_clean_new_password:
                set_new_password.setText("");
                break;
            case R.id.set_clean_old_password:
                set_old_password.setText("");
                break;
            case R.id.set_iv_show_new_pwd:
                if (set_new_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    set_new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    show_new.setImageResource(R.drawable.pass_visuable);
                } else {
                    set_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show_new.setImageResource(R.drawable.pass_gone);
                }
                String pwd = set_new_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    set_new_password.setSelection(pwd.length());
                break;

            case R.id.set_iv_show_old_pwds:
                if (set_old_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    set_old_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    show_old.setImageResource(R.drawable.pass_visuable);
                } else {
                    set_old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show_old.setImageResource(R.drawable.pass_gone);
                }
                String pwd2 = set_new_password.getText().toString();
                if (!TextUtils.isEmpty(pwd2))
                    set_old_password.setSelection(pwd2.length());
                break;




        }

    }
}
