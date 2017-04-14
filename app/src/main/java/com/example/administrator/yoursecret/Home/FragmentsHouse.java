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

public class FragmentsHouse implements HomePresenter.Fragments{
    private ArrayList<Fragment> fragments;

    public FragmentsHouse(){
        fragments = new ArrayList<>();
        fragments.add(RecieveFragment.newInstance());
        fragments.add(DiscoverFragment.newInstance());
        fragments.add(RecordFragment.newInstance());
        fragments.add(AccountFragment.newInstance());

    }

    @Override
    public Fragment getFragment(int resId) {
        if(resId<fragments.size())
            return fragments.get(resId);
        return null;
    }
}
