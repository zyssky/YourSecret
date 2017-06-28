package com.example.administrator.yoursecret.Home;

import android.support.v4.app.Fragment;

import com.example.administrator.yoursecret.Account.AccountFragment;
import com.example.administrator.yoursecret.Record.RecordFragment;
import com.example.administrator.yoursecret.Discover.DiscoverFragment;
import com.example.administrator.yoursecret.Recieve.RecieveFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/14.
 */

public class FragmentsHouse {
    private ArrayList<Fragment> fragments;

    public final static int RECIEVE_FRAGMENT = 0;
    public final static int DISCOVER_FRAGMENT = 1;
    public final static int RECORD_FRAGMENT = 2;
    public final static int ACCOUNT_FRAGMENT = 3;

    private static FragmentsHouse instance;

    private FragmentsHouse(){
        fragments = new ArrayList<>();
        fragments.add(RecieveFragment.newInstance());
        fragments.add(DiscoverFragment.newInstance());
        fragments.add(RecordFragment.newInstance());
        fragments.add(AccountFragment.newInstance());

    }

    public static FragmentsHouse getInstance(){
        if(instance == null){
            instance = new FragmentsHouse();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    public Fragment getFragment(int resId) {
        if(resId<fragments.size())
            return fragments.get(resId);
        return null;
    }
}
