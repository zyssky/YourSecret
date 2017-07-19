package com.example.administrator.yoursecret.Module.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.yoursecret.R;

/**
 * Created by j on 2017/6/20.
 */

public class SetItemActivity extends Activity implements SetItemObserver{
    private NavigationView navigationView;
    private EditText editText;
    private ImageView left,right,clan;
    public static SetItemActivity Instance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_item);
        Instance = this;
        initView();
        initListener();
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String str=bundle.getString("title");
        navigationView=(NavigationView)super.findViewById(R.id.nav);
        editText=(EditText)findViewById(R.id.edit);
        clan=(ImageView)findViewById(R.id.s_iv_clean_phone);
        left=navigationView.getLeftView();
        right=navigationView.getRightView();
        left.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        right.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp);
        navigationView.setTitle(str);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {

            @Override
            public void onBackClick() {

               finish();

            }

            @Override
            public void onRightClick() {
               final String nic = editText.getText().toString();
                if (nic.length()==0)
                {

                    Toast.makeText(SetItemActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();

                }
                else {
                    SetItemDataManager.getInstance().modifynic(nic);

                }
            }
        });

    }
    private void initListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clan.getVisibility() == View.GONE) {
                    clan.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clan.setVisibility(View.GONE);
                }
                else if (!s.toString().matches("[\\u4e00-\\u9fa5\\w]+")) {
                    String temp = s.toString();
                    Toast.makeText(SetItemActivity.this, R.string.please_input_limt_setitem, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    editText.setSelection(s.length());
                }
            }

        });

        clan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });



    }

}
