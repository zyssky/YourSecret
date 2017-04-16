package com.example.administrator.yoursecret.Recieve;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveContract {
    interface View{
        void setRecyclerViewAdapter(RecyclerView.Adapter adapter);
    }

    interface Model{
        List<PushMessage> getInitDatas();
    }

    interface Presenter{
        void setRecyclerViewAdapter();
    }
}

