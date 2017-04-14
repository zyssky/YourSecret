package com.example.administrator.yoursecret.Home;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/4/14.
 */

public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View view;

    private HomeContract.Model model;

    private Fragments fragments;



    public final static int RECIEVE_FRAGMENT = 0;
    public final static int DISCOVER_FRAGMENT = 1;
    public final static int RECORD_FRAGMENT = 2;
    public final static int ACCOUNT_FRAGMENT = 3;


    public HomePresenter (HomeContract.View view){
        this.view = view;
        fragments = new FragmentsHouse();
    }

    public void setFragments(Fragments fragments) {
        this.fragments = fragments;
    }

    public void setView(HomeContract.View view) {
        this.view = view;
    }

    public void setModel(HomeContract.Model model) {
        this.model = model;
    }

    @Override
    public void switchContent(int resId) {
        view.switchContent(fragments.getFragment(resId));
//        switch (resId){
//            case RECIEVE_FRAGMENT:
//                view.switchContent(fragments.getFragment(0));
//                break;
//            case DISCOVER_FRAGMENT:
//                view.switchContent(fragments.getFragment(1));
//                break;
//            case RECORD_FRAGMENT:
//                view.switchContent(fragments.getFragment(2));
//                break;
//            case ACCOUNT_FRAGMENT:
//                view.switchContent(fragments.getFragment(3));
//                break;
//
//        }
    }

    public interface Fragments{
        Fragment getFragment(int resId);
    }
}
