package com.example.administrator.yoursecret.Module.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Home.FragmentsHouse;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Service.PushService;
import com.example.administrator.yoursecret.utils.GlideImageLoader;


public class AccountFragment extends Fragment implements View.OnClickListener , OnCheckedChangeListener{

    private LinearLayout basic_set,exit,login,pssword_set;
//    private Switch wifi_set;
    private Switch push_set;
    private ImageView touxiang;
    private TextView m_nickname,m_account;

    public AccountFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FragmentsHouse.getInstance().putFragment(AccountFragment.class.getSimpleName(),this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_account, container, false);
//        wifi_set=(Switch) rootView.findViewById(R.id.wifi_kaiguan);
        push_set=(Switch) rootView.findViewById(R.id.switch_push);
        pssword_set=(LinearLayout) rootView.findViewById(R.id.set_pas);
        basic_set=(LinearLayout)rootView.findViewById(R.id.basics_set);
        exit=(LinearLayout) rootView.findViewById(R.id.exit1);
        login=(LinearLayout)rootView.findViewById(R.id.denglu) ;
        touxiang=(ImageView)rootView.findViewById(R.id.touxiang1) ;
        m_account = (TextView) rootView.findViewById(R.id.m_zhanghao) ;
        m_nickname = (TextView) rootView.findViewById(R.id.m_nickname) ;

        basic_set.setOnClickListener(this);
//        wifi_set.setOnCheckedChangeListener(this);
        push_set.setOnCheckedChangeListener(this);
        login.setOnClickListener(this);
        exit.setOnClickListener(this);
        pssword_set.setOnClickListener(this);


        initView();

        return rootView;


    }

    private void initView() {
        UserManager user = App.getInstance().getUserManager();
        String nic = user.getNickName();
        String acc = user.getPhoneNum();
        m_account.setText("账号:"+acc);
        m_nickname.setText(nic);

        push_set.setChecked(FoundationManager.isAutoPush());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basics_set:
                Intent intent1=new Intent();
                intent1.setClass(getActivity(),SetBasicActivity.class);
                startActivity(intent1 );
                break;
            case R.id.denglu:
                Intent intent2=new Intent();
                intent2.setClass(getActivity(),LoginActivity.class);
                startActivity(intent2 );
                break;
            case R.id.exit1:
                getActivity().finish();
                break;
            case R.id.set_pas:
                Intent intent3=new Intent();
                intent3.setClass(getActivity(),SetPassActivity.class);
                startActivity(intent3 );
               break;
        }
    }



//    private boolean isConnectWithWifi(Context context) {
//         {
//             ConnectivityManager manager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//             NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//             if(networkInfo != null && networkInfo.isConnected()){
//                 String type = networkInfo.getTypeName();
//                 return type.equalsIgnoreCase("WIFI");
//             }else{
//                 return false;
//             }
//         }
//     }



    public void onResume() {
        super.onResume();
        initView();

        UserManager usermanager = App.getInstance().getUserManager();


        if(usermanager.hasLogin()){
            String iconPath = usermanager.getIconPath();
            GlideImageLoader.loadImageNail(this,iconPath,touxiang);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
//            case R.id.wifi_kaiguan:
//                if((isChecked)&&isConnectWithWifi(getContext())) {
//                    FoundationManager.ISUNDER_WIFI="TRUE";
//                    Toast.makeText(getContext(),(String)FoundationManager.ISUNDER_WIFI,Toast.LENGTH_LONG).show();
//                }
//                else {
//                    FoundationManager.ISUNDER_WIFI="fALSE";
//                    Toast.makeText(getContext(),FoundationManager.ISUNDER_WIFI,Toast.LENGTH_LONG).show();
//                }
//                break;
            case R.id.switch_push:
                Log.d("推送测试", "onCheckedChanged: "+isChecked);
                FoundationManager.setAutoPush(isChecked);
                Intent intent = new Intent(getActivity(), PushService.class);
                if(isChecked){
                    getActivity().startService(intent);
                }else{
                    getActivity().stopService(intent);
                }
                break;
        }

    }


}
