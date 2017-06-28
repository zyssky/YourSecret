package com.example.administrator.yoursecret.Account;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Login.LoginActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.io.File;

import static android.support.v7.appcompat.R.styleable.CompoundButton;


public class AccountFragment extends Fragment implements View.OnClickListener , OnCheckedChangeListener{

    private LinearLayout basic_set,exit,login,pssword_set;
    private Switch wifi_set;
    private ImageView touxiang;
    private TextView m_nickname,m_account;
    String parent = FileUtils.toRootPath();
    String savepath = parent+File.separator+"icon.png" ;
    public AccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_account, container, false);
        wifi_set=(Switch) rootView.findViewById(R.id.wifi_kaiguan);
        pssword_set=(LinearLayout) rootView.findViewById(R.id.set_pas);
        basic_set=(LinearLayout)rootView.findViewById(R.id.basics_set);
        exit=(LinearLayout) rootView.findViewById(R.id.exit1);
        login=(LinearLayout)rootView.findViewById(R.id.denglu) ;
        touxiang=(ImageView)rootView.findViewById(R.id.touxiang1) ;
        basic_set.setOnClickListener(this);
        wifi_set.setOnCheckedChangeListener(this);
        login.setOnClickListener(this);
        exit.setOnClickListener(this);
        pssword_set.setOnClickListener(this);
        m_account = (TextView) rootView.findViewById(R.id.m_zhanghao) ;
        m_nickname = (TextView) rootView.findViewById(R.id.m_nickname) ;
        initView();

//        initView();
        return rootView;


    }

    private void initView() {
        UserManager user = ApplicationDataManager.getInstance().getUserManager();
        String nic = user.getNickName();
        String acc = user.getPhoneNum();
        m_account.setText("账号:"+acc);
        m_nickname.setText(nic);
    }

    /*private void initView() {





    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basics_set:
                Intent intent1=new Intent();
                intent1.setClass(getActivity(),activity_setBasic.class);
                startActivity(intent1 );
                break;
            case R.id.denglu:
                Intent intent2=new Intent();
                intent2.setClass(getActivity(),activity_login.class);
                startActivity(intent2 );
                break;
            case R.id.exit1:
                break;
            case R.id.set_pas:
                Intent intent3=new Intent();
                intent3.setClass(getActivity(),activity_set_pas.class);
                startActivity(intent3 );
               break;
        }
    }



    private boolean isConnectWithWifi(Context context) {
         {
             ConnectivityManager manager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo networkInfo = manager.getActiveNetworkInfo();
             if(networkInfo != null && networkInfo.isConnected()){
                 String type = networkInfo.getTypeName();
                 return type.equalsIgnoreCase("WIFI");
             }else{
                 return false;
             }
         }
     }



    private boolean readImage() {
        String filesDir;
        filesDir = FileUtils.toRootPath();
        /*if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断sd卡是否挂载
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = getApplicationContext().getExternalFilesDir("");

        }else{//手机内部存储
            //路径：data/data/包名/files
            filesDir = getApplicationContext().getFilesDir();

        }*/
        File file = new File(filesDir,"icon.png");
        if(file.exists()){
            //存储--->内存
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            touxiang.setImageBitmap(bitmap);
            return true;
        }
        else
            return false;

    }
    public void onResume() {
        super.onResume();
        initView();

        UserManager usermanager = ApplicationDataManager.getInstance().getUserManager();


        if(usermanager.hasLogin()){
            String iconPath = usermanager.getIconPath();
            GlideImageLoader.loadImageNail(this,iconPath,touxiang);
        }
//            String filesDir = ApplicationDataManager.getInstance().getUserManager().getIconPath();
//            File file = new File(filesDir);
//            if(file.exists()){
//                //存储--->内存
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                touxiang.setImageBitmap(bitmap);
//
//            }
//            else{
//
//
//            }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if((isChecked)&&isConnectWithWifi(getContext()))

        {
            FoundationManager.ISUNDER_WIFI="TRUE";
            Toast.makeText(getContext(),(String)FoundationManager.ISUNDER_WIFI,Toast.LENGTH_LONG).show();
        }
        else {
            FoundationManager.ISUNDER_WIFI="fALSE";
            Toast.makeText(getContext(),FoundationManager.ISUNDER_WIFI,Toast.LENGTH_LONG).show();
        }
    }


}
