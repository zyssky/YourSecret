package com.example.administrator.yoursecret.Home;

import android.support.v4.app.Fragment;

import com.example.administrator.yoursecret.Account.AccountFragment;
import com.example.administrator.yoursecret.Record.RecordFragment;
import com.example.administrator.yoursecret.Discover.DiscoverFragment;
import com.example.administrator.yoursecret.Recieve.RecieveFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FragmentsHouse {

    private Map<String,Fragment> fragments;


    public final static int RECIEVE_FRAGMENT = 0;
    public final static int DISCOVER_FRAGMENT = 1;
    public final static int RECORD_FRAGMENT = 2;
    public final static int ACCOUNT_FRAGMENT = 3;

    private static FragmentsHouse instance;


    private FragmentsHouse() {
        fragments = new HashMap<>();
//        fragments.put(RecieveFragment.class.getSimpleName(),RecieveFragment.newInstance());
//        fragments.put(DiscoverFragment.class.getSimpleName(),DiscoverFragment.newInstance());
//        fragments.put(RecieveFragment.class.getSimpleName(),RecordFragment.newInstance());
//        fragments.put(AccountFragment.class.getSimpleName(),AccountFragment.newInstance());


    }
    public static FragmentsHouse getInstance() {
        if (instance == null) {
            instance = new FragmentsHouse();
        }
        return instance;
    }

    public static void onDestroy() {
        instance = null;
    }

    public Fragment getFragment(String name) {

        Fragment fragment =  fragments.get(name);
        if(fragment == null){
            if(name.equals(RecieveFragment.class.getSimpleName())){
                fragment = new RecieveFragment();
            }
            if(name.equals(DiscoverFragment.class.getSimpleName())){
                fragment = new DiscoverFragment();
            }
            if(name.equals(RecordFragment.class.getSimpleName())){
                fragment = new RecordFragment();
            }
            if(name.equals(AccountFragment.class.getSimpleName())){

                fragment = new AccountFragment();
            }
        }
        return fragment;
    }


    public void putFragment(String name,Fragment fragment){
        fragments.put(name,fragment);

    }
}
