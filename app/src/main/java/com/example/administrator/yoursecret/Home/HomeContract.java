package com.example.administrator.yoursecret.Home;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/4/14.
 */

public interface HomeContract {
    interface View{
        void switchContent(Fragment targetFragment);
    }

    interface Model{

    }

    interface Presenter{
        void switchContent(int resId);
    }
}
