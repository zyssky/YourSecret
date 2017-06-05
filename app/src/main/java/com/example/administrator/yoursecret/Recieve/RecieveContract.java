package com.example.administrator.yoursecret.Recieve;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.yoursecret.MetaData.PushMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveContract {
    interface View{
        void setRecyclerViewAdapter();
    }

    interface Model{
        List<String> getTitles();
        Map<String,List<PushMessage>> getDatas();
    }

    interface Presenter{
        void setRecyclerViewAdapter();
    }
}

