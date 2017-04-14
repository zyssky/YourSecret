package com.example.administrator.yoursecret.Record;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordContract {
    interface View{
        void setRecyclerViewAdapter(RecyclerView.Adapter adapter);
    }

    interface Model{
        List<Record> getInitDatas();
    }

    interface Presenter{
        void setRecyclerViewAdapter();
    }
}
